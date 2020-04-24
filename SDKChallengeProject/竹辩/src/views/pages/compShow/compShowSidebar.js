import React from "react"
import { X, ChevronUp } from "react-feather"
import PerfectScrollbar from "react-perfect-scrollbar"
import classnames from "classnames"
import { ButtonGroup, Nav, NavItem, NavLink, TabContent, Form, TabPane, Label, Input, FormGroup, Button, Card, CardBody, Collapse, CardHeader, CardTitle, Media, UncontrolledButtonDropdown, DropdownToggle, DropdownMenu, DropdownItem } from "reactstrap"
import { User, ArrowDown, Settings, FileMinus } from "react-feather"
import appGlobal from './config.js';
import "../../../assets/scss/pages/dashboard-analytics.scss"

import {
  nextStage,
  lastStage,
  timerStatusChangeTo,
  getStatusInfo,
} from "./compShowTimer";
import { sendRtmMsg } from './compShow'
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import NumericInput from "react-numeric-input"
import yesapi from "../../../webServices/yes3";
import {Badge} from "reactstrap"
import Webcamera, {stopStream} from '../../../components/WebCamera/small';
import { clipImage } from '../../../utils/faceUtils/getImageData';
import faceapi, { getDetectorOptions, isModelLoaded, loadModel } from '../../../utils/faceUtils/faceDetectionControl';
import {grayPocess} from '../../../utils/faceUtils/grayProcess';
import histogramEqualize from '../../../utils/faceUtils/histogramEqualize';
import request from '../../../utils/request';

let lastStatus = 0, gradeStorge = [0,0,0,0,0,0,0,0], roundCounter;
export let mountTimerId = 0;
let teamInfo = [
  { "id":1, "title":"", "name":"", "UUID":"", "headImg":"", "introTxt":"" },
  { "id":2, "title":"", "name":"", "UUID":"", "headImg":"", "introTxt":"" },
  { "id":3, "title":"", "name":"", "UUID":"", "headImg":"", "introTxt":"" },
  { "id":4, "title":"", "name":"", "UUID":"", "headImg":"", "introTxt":"" },
  { "id":5, "title":"", "name":"", "UUID":"", "headImg":"", "introTxt":"" },
  { "id":6, "title":"", "name":"", "UUID":"", "headImg":"", "introTxt":"" },
  { "id":7, "title":"", "name":"", "UUID":"", "headImg":"", "introTxt":"" },
  { "id":8, "title":"", "name":"", "UUID":"", "headImg":"", "introTxt":"" },
]
let judgeInfo = [
  { "id":1, "title":""},
  { "id":2, "title":""},
  { "id":3, "title":""},
  { "id":4, "title":""},
  { "id":5, "title":""},
  { "id":6, "title":""},
  { "id":7, "title":""},
  { "id":8, "title":""},
  { "id":9, "title":""},
  { "id":10, "title":""},
  { "id":11, "title":""}
]
let compGrade = ["","","","","","","","","","",""]
let updateInfoId
let isUsingCamera=false
let alreadyDetected = false;


toast.configure({
  autoClose: 5000,
  draggable: false,
});

const notifySuccess = (e) => {
  toast.success(e, {
    position: toast.POSITION.TOP_LEFT
  });
}

 const notifyError = (e) => {
   toast.error(e, {
    position: toast.POSITION.TOP_LEFT
   });
 }

 const notifyWarning = (e) => {
   toast.warning(e, {
     position: toast.POSITION.TOP_LEFT
   });
 }

function handleGrade(debatorGrade, currentRound, teamPosi, roundType) {
  if (roundType === "main") { 
    if(teamPosi === "pos") { 
      gradeStorge[0] = debatorGrade
      let tempGrade = {type:"常规", gradePos: debatorGrade, gradeNeg: gradeStorge[1]}
      compGrade[currentRound] = tempGrade
    } else if (teamPosi === "neg") { 
      gradeStorge[1] = debatorGrade
      let tempGrade = {type:"常规", gradePos: gradeStorge[0], gradeNeg: debatorGrade}
      compGrade[currentRound] = tempGrade
    }
  } else if (roundType === "free" ) {
    gradeStorge[teamPosi] = debatorGrade
    let tempGrade = {type:"自由", P1: gradeStorge[1], P2: gradeStorge[2], P3: gradeStorge[3], P4: gradeStorge[4],  N1: gradeStorge[5], N2: gradeStorge[6], N3: gradeStorge[7], N4: gradeStorge[8]}
    compGrade[currentRound] = tempGrade
  }
  console.log(compGrade)
}

async function uploadGrade() {
  console.log(compGrade)
  compGrade.shift()
  await yesapi.table.update("grade",
  {grade_origin: JSON.stringify(compGrade)},
  [
    ["userid", "=", localStorage.getItem("uuid")],
    ["comp_id", "=", appGlobal.gid],
  ],
  "and",
  )
  notifySuccess("赛事成绩已上传到服务器。")
}

//面部识别开始

const webCameraRef = React.createRef();
const scaleImage = imageData => {
  return new Promise((resolve, reject) => {
      const canvas = document.createElement('canvas');
      const ctx = canvas.getContext('2d');
      canvas.width = imageData.width;
      canvas.height = imageData.height;
      ctx.putImageData(imageData, 0, 0);
      const imgUrl = canvas.toDataURL('image/png');
      const image = new Image();
      image.onload = () => {
          //ctx.clearRect(0, 0, canvas.width, canvas.height);
          //ctx.drawImage(image, 0, 0, 30, 30);
          resolve(imgUrl);
      };
      image.src = imgUrl;
      image.onerror = e => {
          reject(e);
      };
  });
};

const onPlayMark = async () => {

  if (!isModelLoaded() || !webCameraRef.current || webCameraRef.current.paused || webCameraRef.current.ended) {
      return setTimeout(() => onPlayMark());
  }
  const options = getDetectorOptions();
  const result = await faceapi.detectSingleFace(webCameraRef.current, options);

  if (result) {

          if (alreadyDetected === false){
          const box = result.box;
          const imageScreenShot = clipImage(webCameraRef.current, box);
          const faceImageData = await scaleImage(imageScreenShot);
          //console.log(faceImageData)
          
          
          request.post('/api/getEmotion', {
              img: faceImageData,
          }).then(res => {
              console.log(res)
              //console.log(res.data.data.UUID, localStorage.getItem("uuid"));
              if (res.data.code === 200) {
              if (localStorage.getItem("uuid") === res.data.data.UUID) {
                  notifySuccess("面部校验通过，请根据提示进行声纹识别。")
              } else {
                  notifyError("校验不通过，请确保本人参赛。")
              }
            } else {
              notifyError("服务器错误，请联系管理员处理。")
            }
          });
          //notifySuccess("面部校验通过，请根据提示进行声纹识别。")
        }
        alreadyDetected = true
      
  }
  setTimeout(() => onPlayMark());
};



class DataListSidebar extends React.Component {
  state = {
    roundTime: [],
    roundName: [],
    roundSpeak: [],
    barColor: [],
    roundMood: [],
    roundInfo: [],
    roundStatus: "",
    currentRound: 1,
    activeTab: "1",
    collapseID: "",
    debatorCollapseID: "",
    faceStatus: "获取中",
    faceStatusColor: "warning"
  }
  addNew = false
  toggle = tab => {
    this.setState({
      activeTab: tab
    })
  }
  playRecSound() {
      console.log(this.props);
      this.props.playRecSound();
    }
  toggleCollapse = collapseID => {
    this.setState(prevState => ({
      collapseID: prevState.collapseID !== collapseID ? collapseID : ""
    }))
  }
  toggleDebatorInfo = debatorCollapseID => {
    this.setState(prevState => ({
      debatorCollapseID: prevState.debatorCollapseID !== debatorCollapseID ? debatorCollapseID : ""
    }))
  }
  getBasicInfo() {
    const _this = this;
    if(appGlobal.loadStatus === 1) {
      let currentRound=1,barIcon=[],barColor=[],roundInfo=[],roundName=[],roundTime=[],roundSpeak=[],roundMood=[],statusInfo=[];
      for (var timer = 1; timer <= 11 ; timer++){
        statusInfo = getStatusInfo(timer)
        roundName[timer] = statusInfo[3]
        roundTime[timer] = statusInfo[2]
        roundSpeak[timer] = statusInfo[0]
        roundMood[timer] = statusInfo[1]
        if(timer < appGlobal.statusNow) {
          roundInfo[timer] = "已结束"
          barColor[timer] = "timeline-icon bg-danger"

        } else if(timer === appGlobal.statusNow) {
          roundInfo[timer] = "进行中"
          barColor[timer] = "timeline-icon bg-success"
          if(timer <= 4) {
            currentRound = appGlobal.statusNow 
          } else {
            currentRound = 4
          }
        } else {
          roundInfo[timer] = "尚未开始"
          barColor[timer] = "timeline-icon bg-primary"
        }
      }
      teamInfo = appGlobal.teamInfo
      if(lastStatus !== appGlobal.statusNow) {
        _this.toggleCollapse(appGlobal.statusNow)
        roundCounter = appGlobal.statusNow
      }

      lastStatus = appGlobal.statusNow
      _this.setState({
        roundTime: roundTime,
        roundName: roundName,
        roundSpeak: roundSpeak,
        roundMood: roundMood,
        roundInfo: roundInfo,
        barColor: barColor,
        currentRound: currentRound,
        realCurrentRound: appGlobal.statusNow,
        debatorPosi: statusInfo[4],
      });
    }

  }

  updateInfo() {
    //if (isUsingCamera) {
      notifySuccess("摄像头初始化成功。")
      loadModel();
      webCameraRef.current && webCameraRef.current.paused
          ? webCameraRef.current.play() : void 0;
    //}
    return () => {     
        webCameraRef.current && webCameraRef.current.pause();    
    };
  }

  startDetect() {
    if (alreadyDetected === true) {
      this.setState({
        faceStatus: "检测到面部",
        faceStatusColor: "success"
      })
    } else {
      this.setState({
        faceStatus: "请调整角度",
        faceStatusColor: "danger"
      })
    }
    alreadyDetected = false
  }

  componentDidMount() {
    mountTimerId = setInterval(this.getBasicInfo.bind(this),500);
    console.log(mountTimerId)
    this.updateInfo()
    setInterval(this.startDetect.bind(this),10000);

  }

  componentWillUnmount() {
    clearInterval(mountTimerId)
    stopStream()
  }

  render() {
    let { show, handleSidebar} = this.props
    let { roundInfo, barColor, realCurrentRound, debatorPosi, roundSpeak, roundName, roundTime, currentRound } = this.state
    const accordionShadowItems = judgeInfo.map(collapseItem => {
      if (this.state.collapseID === 9) {
      return (
        
        <Card
          className={classnames({
            open: collapseItem.id === this.state.collapseID
          })}
          key={collapseItem.id}
          //onClick={() => this.toggleCollapse(collapseItem.id,true)}
        >
          <CardHeader>
            <CardTitle className="lead collapse-title collapsed">
              {roundName[collapseItem.id]}
            </CardTitle>
          </CardHeader>
          <Collapse
            isOpen={collapseItem.id === this.state.collapseID}
          >
            <CardBody>
            <FormGroup>
                <Label for="data-popularity">正方一辩</Label>
                <NumericInput className="form-control" min={0} max={100} onChange={e => handleGrade(e, appGlobal.statusNow, 1, "free")} noStyle />
            </FormGroup>
            <FormGroup>
                <Label for="data-popularity">正方二辩</Label>
                <NumericInput className="form-control" min={0} max={100} onChange={e => handleGrade(e, appGlobal.statusNow, 2, "free")} noStyle />
            </FormGroup>
            <FormGroup>
                <Label for="data-popularity">正方三辩</Label>
                <NumericInput className="form-control" min={0} max={100} onChange={e => handleGrade(e, appGlobal.statusNow, 3, "free")} noStyle />
            </FormGroup>
            <FormGroup>
                <Label for="data-popularity">正方四辩</Label>
                <NumericInput className="form-control" min={0} max={100} onChange={e => handleGrade(e, appGlobal.statusNow, 4, "free")} noStyle />
            </FormGroup>
              <FormGroup>
                <Label for="data-popularity">反方一辩</Label>
                <NumericInput className="form-control" min={0} max={100} onChange={e => handleGrade(e, appGlobal.statusNow, 5, "free")} noStyle />
            </FormGroup>
            <FormGroup>
                <Label for="data-popularity">反方二辩</Label>
                <NumericInput className="form-control" min={0} max={100} onChange={e => handleGrade(e, appGlobal.statusNow, 6, "free")} noStyle />
            </FormGroup>
            <FormGroup>
                <Label for="data-popularity">反方三辩</Label>
                <NumericInput className="form-control" min={0} max={100} onChange={e => handleGrade(e, appGlobal.statusNow, 7, "free")} noStyle />
            </FormGroup>
            <FormGroup>
                <Label for="data-popularity">反方四辩</Label>
                <NumericInput className="form-control" min={0} max={100} onChange={e => handleGrade(e, appGlobal.statusNow, 8, "free")} noStyle />
            </FormGroup>
            </CardBody>
          </Collapse>
        </Card>
      )} else {
        return (
          <Card
            className={classnames({
              open: collapseItem.id === this.state.collapseID
            })}
            key={collapseItem.id}
          >
            <CardHeader>
              <CardTitle className="lead collapse-title collapsed">
                {roundName[collapseItem.id]}
              </CardTitle>
            </CardHeader>
            <Collapse
              isOpen={collapseItem.id === this.state.collapseID}
            >
              <CardBody>
                <FormGroup>
                  <Label for="data-popularity">正方得分</Label>
                  <NumericInput className="form-control" min={0} max={100} onChange={e => handleGrade(e, appGlobal.statusNow, "pos", "main")} noStyle />
              </FormGroup>
              <FormGroup>
                  <Label for="data-popularity">反方得分</Label>
                  <NumericInput className="form-control" min={0} max={100} onChange={e => handleGrade(e, appGlobal.statusNow, "neg", "main")} noStyle />
              </FormGroup>
              </CardBody>
            </Collapse>
          </Card>
        )
      }
    })

    const debatorInfo = teamInfo.map(item => {
      return (
        <Card
          className={classnames({
            open: item.id === this.state.debatorCollapseID
          })}
          key={item.id}
          //onClick={() => this.toggleDebatorInfo(item.id)}
          onMouseEnter={() => this.toggleDebatorInfo(item.id)}
        >
          <CardHeader>
            <CardTitle className="lead collapse-title collapsed">
              {item.title}
            </CardTitle>
          </CardHeader>
          <Collapse
            isOpen={item.id === this.state.debatorCollapseID}
            onEntering={this.onEntering}
            onEntered={this.onEntered}
            onExiting={this.onExiting}
            onExited={this.onExited}
          >
            <CardBody>
            <div className="media-list">
            <Media>
            <Media left href="#">
              <Media
                object
                src={item.headImg}
                height="64"
                width="64"
              />
            </Media>
            <Media body>
              <Media heading>{item.name}</Media>
              {item.introTxt}
            </Media>
            </Media>
            </div>
            </CardBody>
          </Collapse>
        </Card>
      )
    })
    return (

      <div
        className={classnames("data-list-sidebar", {
          show: show
      })}>
      <div className="data-list-sidebar-header d-flex justify-content-between px-2 pt-2">
        <div className="title">
          <h4 className="mb-0">控制面板</h4>
          <small>个性化属于你的赛事信息</small>
        </div>
        <div
          className="close-icon cursor-pointer"
          //onClick={() => this.props.handleCustomizer(false)}
        >
          <X size={24} />
        </div>
      </div>
      <Nav tabs className="px-1 mt-1">
      <NavItem>
        <NavLink
          className={classnames({
            active: this.state.activeTab === "1"
          })}
          onClick={() => {
            this.toggle("1")
          }}
        >
          <User size={16} />
          <span className="align-middle ml-50">个人信息</span>
        </NavLink>
      </NavItem>
      <NavItem>
        <NavLink
          className={classnames({
            active: this.state.activeTab === "2"
          })}
          onClick={() => {
            this.toggle("2")
          }}
        >
          <FileMinus size={16} />
          <span className="align-middle ml-50">赛事评分</span>
        </NavLink>
      </NavItem>
      <NavItem>
        <NavLink
          className={classnames({
            active: this.state.activeTab === "3"
          })}
          onClick={() => {
            this.toggle("3")
          }}
        >
          <Settings size={16} />
          <span className="align-middle ml-50">赛程管理</span>
        </NavLink>
      </NavItem>
    </Nav>
    <TabContent activeTab={this.state.activeTab}>

      <TabPane tabId="1" style={{height:"93vh"}}>
        <PerfectScrollbar
            className="data-list-fields px-2 mt-3"
            options={{ wheelPropagation: false }}>
          <Card className="bg-analytics text-white sales-card">
            <CardBody className="text-center">
              <div className="award-info text-center">
                <h1 className="mb-2 text-white">{debatorPosi}</h1>
                <p className="m-auto mb-0 w-75">
                  下一环节为{roundName[realCurrentRound+1]}，您{roundSpeak[realCurrentRound+1]}，时间<strong>{roundTime[realCurrentRound+1]}</strong>秒。
                  请注意比赛进程，合理安排时间。
                </p>
              </div>
            </CardBody>
          </Card>
          <Card className="webCameraSidebarCard">
          <div className="faceModelSmall">
              <img src="/images/face.png" alt="face" width="140" height="230"/>
          </div>
          <div className="faceShadowSmall">
              <img src="/images/shadow.png" alt="face" width="371" height="230"/>
          </div>
          <Webcamera ref={webCameraRef} onLoadedMetaData={e => onPlayMark(e)} />
          <div className="faceBadge">
          <Badge pill className="badge-glow" color={this.state.faceStatusColor}>{this.state.faceStatus}</Badge>
          </div>
          </Card>
          <h4>辩手信息</h4>
          <div className="collapse-bordered vx-collapse collapse-icon accordion-icon-rotate">
            {debatorInfo}
          </div><br></br>
        </PerfectScrollbar>
        <div className="d-flex flex-wrap justify-content-between px-2 mt-2">

        
        <UncontrolledButtonDropdown direction="up">
            <DropdownToggle color="primary" caret>
              发送实时评论
              <ChevronUp size={15} />
              </DropdownToggle>
              <DropdownMenu>
              <Form className="px-1 py-1">
                <FormGroup>
                  <Label for="ddEmail">内容</Label>
                  <Input
                    type="text"
                    placeholder=""
                    name="ddemail"
                    id="ddEmail"
                  />
                </FormGroup>
                <Button color="primary" onClick={() => notifySuccess("发送成功，将由管理员审核后显示。")}>
                  {" "}
                  发送{" "}
                </Button>
              </Form>
            </DropdownMenu>
          </UncontrolledButtonDropdown>
          <UncontrolledButtonDropdown direction="up">
            <DropdownToggle color="danger" caret>
              举报
              <ChevronUp size={15} />
              </DropdownToggle>
            <DropdownMenu>
              <Form className="px-1 py-1">
              <FormGroup>
                  <Label for="ddEmail">举报对象</Label>
                  <Input
                    type="text"
                    placeholder=""
                    name="ddemail"
                    id="ddEmail"
                  />
                </FormGroup>
                <FormGroup>
                  <Label for="ddEmail">原因</Label>
                  <Input
                    type="text"
                    placeholder=""
                  />
                </FormGroup>
                <Button color="primary" onClick={() => notifySuccess("提交成功，感谢您的反馈。")}>
                  {" "}
                  提交{" "}
                </Button>
              </Form>
              <DropdownItem tag="a" divider />
              <DropdownItem tag="a" href="#" onClick={() => notifySuccess("已收到您的请求，等待处理中。")}>
                {" "}
                遇到技术问题？
              </DropdownItem>
            </DropdownMenu>
          </UncontrolledButtonDropdown>
          </div>
      </TabPane>
      <TabPane tabId="2" style={{height:"93vh"}}>
        <PerfectScrollbar
        className="data-list-fields px-2 mt-3"
        options={{ wheelPropagation: false }}>

            <h4>环节得分</h4>
            <div className="collapse-bordered vx-collapse collapse-icon accordion-icon-rotate">
              {accordionShadowItems}
            </div><br></br>
      </PerfectScrollbar>

        <FormGroup className="text-left">
              <div className="d-flex flex-wrap justify-content-between px-2 mt-2">
              <Button
                color="primary"
                onClick={() => uploadGrade()}
                >
                上传成绩
              </Button>
              <UncontrolledButtonDropdown direction="up">
                  <DropdownToggle color="primary" caret>
                    流程回顾
                    <ChevronUp size={15} />
                    </DropdownToggle>
                  <DropdownMenu>
                    <DropdownItem tag="a" onClick={() => {if(roundCounter !== 0){roundCounter--};this.toggleCollapse(roundCounter,true)}}>展开上一环节</DropdownItem>
                    <DropdownItem tag="a" onClick={() => {if(roundCounter !== 11){roundCounter++};this.toggleCollapse(roundCounter,true)}}>展开下一环节</DropdownItem>
                  </DropdownMenu>
                </UncontrolledButtonDropdown>
              <Button
                color="danger"
                onClick={() => {notifySuccess("成绩已清空。")}}
                >
                清空
              </Button>
              </div>
            </FormGroup>
      </TabPane>
      <TabPane tabId="3" style={{height:"93vh"}}>

      <PerfectScrollbar
          className="data-list-fields px-2 mt-3"
          options={{ wheelPropagation: false }}>
          <ul className="activity-timeline timeline-left list-unstyled">
            <li>
              <div className={barColor[currentRound]}>
                <ArrowDown size={16} />
              </div>
              <div className="timeline-info">
                <p className="font-weight-bold mb-0">{roundName[currentRound]}</p>
                <span className="font-small-3">
                该环节时间为{roundTime[currentRound]}秒钟，您在该环节{roundSpeak[currentRound]}。
                </span>
              </div>
              <small className="text-muted">{roundInfo[currentRound]}</small>
            </li>
            <li>
              <div className={barColor[currentRound+1]}>
                <ArrowDown size={16} />
              </div>
              <div className="timeline-info">
                <p className="font-weight-bold mb-0">{roundName[currentRound+1]}</p>
                <span className="font-small-3">
                该环节时间为{roundTime[currentRound+1]}秒钟，您在该环节{roundSpeak[currentRound+1]}。
                </span>
              </div>
              <small className="text-muted">{roundInfo[currentRound+1]}</small>
            </li>
            <li>
              <div className={barColor[currentRound+2]}>
                <ArrowDown size={16} />
              </div>
              <div className="timeline-info">
                <p className="font-weight-bold mb-0">{roundName[currentRound+2]}</p>
                <span className="font-small-3">
                该环节时间为{roundTime[3]}秒钟，您在该环节{roundSpeak[currentRound+2]}。
                </span>
              </div>
              <small className="text-muted">{roundInfo[currentRound+2]}</small>
            </li>
            <li>
              <div className={barColor[currentRound+3]}>
                <ArrowDown size={16} />
              </div>
              <div className="timeline-info">
                <p className="font-weight-bold mb-0">{roundName[currentRound+3]}</p>
                <span className="font-small-3">
                该环节时间为{roundTime[currentRound+3]}秒钟，您在该环节{roundSpeak[currentRound+3]}。
                  </span>
              </div>
              <small className="text-muted">{roundInfo[currentRound+3]}</small>
            </li>
            <li>
              <div className={barColor[currentRound+4]}>
                <ArrowDown size={16} />
              </div>
              <div className="timeline-info">
                <p className="font-weight-bold mb-0">{roundName[currentRound+4]}</p>
                <span className="font-small-3">
                该环节时间为{roundTime[5]}秒钟，您在该环节{roundSpeak[currentRound+4]}。
                </span>
              </div>
              <small className="text-muted">{roundInfo[currentRound+4]}</small>
            </li>
            <li>
              <div className={barColor[currentRound+5]}>
                <ArrowDown size={16} />
              </div>
              <div className="timeline-info">
                <p className="font-weight-bold mb-0">{roundName[currentRound+5]}</p>
                <span className="font-small-3">
                该环节时间为{roundTime[currentRound+5]}秒钟，您在该环节{roundSpeak[currentRound+5]}。
                </span>
              </div>
              <small className="text-muted">{roundInfo[currentRound+5]}</small>
            </li>
            <li>
              <div className={barColor[currentRound+6]}>
                <ArrowDown size={16} />
              </div>
              <div className="timeline-info">
                <p className="font-weight-bold mb-0">{roundName[currentRound+6]}</p>
                <span className="font-small-3">
                该环节时间为{roundTime[currentRound+5]}秒钟，您在该环节{roundSpeak[currentRound+6]}。
                </span>
              </div>
              <small className="text-muted">{roundInfo[currentRound+6]}</small>
            </li>
            <li>
              <div className={barColor[currentRound+7]}>
                <ArrowDown size={16} />
              </div>
              <div className="timeline-info">
                <p className="font-weight-bold mb-0">{roundName[currentRound+7]}</p>
                <span className="font-small-3">
                该环节时间为{roundTime[currentRound+7]}秒钟，您在该环节{roundSpeak[currentRound+7]}。
                </span>
              </div>
              <small className="text-muted">{roundInfo[currentRound+7]}</small>
            </li>
          </ul>
          
        </PerfectScrollbar>

        <FormGroup className="text-center">
              <div className="d-flex flex-wrap justify-content-between px-2 mt-2">
              <ButtonGroup>
              <Button.Ripple outline
              color="success"
              onClick={() => {notifySuccess("操作成功，倒计时已开始。");timerStatusChangeTo(1);sendRtmMsg("nihao|roundChange|Start");}}
              >
              开始
              </Button.Ripple>
              <Button.Ripple outline
              color="success"
              onClick={() => {notifySuccess("操作成功，倒计时已暂停。");timerStatusChangeTo(0);sendRtmMsg("nihao|roundChange|Stop");}}
              >
              暂停
              </Button.Ripple>
               </ButtonGroup>
                <UncontrolledButtonDropdown direction="up">
                  
                  <DropdownToggle color="primary" caret>
                    环节管理
                    <ChevronUp size={15} />
                    </DropdownToggle>
                  <DropdownMenu>
                    <DropdownItem tag="a" header>流程控制</DropdownItem>
                    <DropdownItem tag="a" onClick={() => this.playRecSound()}>播放主席音频</DropdownItem>
                    <DropdownItem tag="a" onClick={() => {notifySuccess("操作成功，结算请求已发送。");sendRtmMsg("nihao|roundChange|Calculate")}}>成绩结算</DropdownItem>
                    <DropdownItem tag="a" divider />
                    <DropdownItem tag="a" header>环节控制</DropdownItem>
                    <DropdownItem tag="a" onClick={() => {notifySuccess("操作成功，已切换至上一环节。");lastStage();sendRtmMsg("nihao|roundChange|ChangeTo|" + (this.state.realCurrentRound - 1))}}>上一环节</DropdownItem>
                    <DropdownItem tag="a" onClick={() => {notifySuccess("操作成功，已切换至下一环节。");nextStage();sendRtmMsg("nihao|roundChange|ChangeTo|" + (this.state.realCurrentRound + 1))}}>下一环节</DropdownItem>
                  </DropdownMenu>
                </UncontrolledButtonDropdown>
              </div>
            </FormGroup>
      </TabPane>
    </TabContent>
      </div>
    )
  }
}

export default DataListSidebar
