import React from "react";
import AgoraRTC from "agora-rtc-sdk-ng";
import {
  Row,
  Col,
  Card,
  CardHeader,
  CardTitle,
  CardBody,
  Media,
  Badge,
  Button,
  ButtonGroup
} from "reactstrap";
import Breadcrumbs from "../../../components/@vuexy/breadCrumbs/BreadCrumb";
import Avatar from "../../../components/@vuexy/avatar/AvatarComponent";
import AgoraRTM from "agora-rtm-sdk";
import { checkIfLogin } from "../../../webServices/auth";
import { Mic, MicOff, Volume2, VolumeX, DownloadCloud, List, Share2 } from "react-feather";
import Tooltip from "rc-tooltip";
import Slider from "rc-slider";
import "rc-slider/assets/index.css";
import APP_SECRET from "../../../config";
import {
  timerClear,
  timerStatusChangeTo,
  changeStatusTo,
  checkTimer,
  roundSync,
  setTime
} from "./compShowTimer";
import appGlobal from "./config.js";
import yesapi,{recordWeb} from "../../../webServices/yes3";
import SweetAlert from "react-bootstrap-sweetalert";
import Sidebar,{mountTimerId} from "./compShowSidebar";
import classnames from "classnames";
import "../../../assets/scss/pages/compShow.scss"
import {history} from "../../../history";
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Webcamera, {stopStream} from '../../../components/WebCamera/small';
import { clipImage } from '../../../utils/faceUtils/getImageData';
import faceapi, { getDetectorOptions, isModelLoaded, loadModel } from '../../../utils/faceUtils/faceDetectionControl';
import {grayPocess} from '../../../utils/faceUtils/grayProcess';
import histogramEqualize from '../../../utils/faceUtils/histogramEqualize';
import request from '../../../utils/request';

let updateInfoId
let isUsingCamera = false
let alreadyDetected = false;
let speakerVolume = [],debatorIntros=[],startRecording=false,recordingId=0,timeRecTotal=0

toast.configure({
  autoClose: 5000,
  draggable: false,
});
let hasInited = false;
const notifySuccess = (e) => {
  toast.success(e, {
    position: toast.POSITION.TOP_RIGHT
  });
}

const notifyError = (e) => {
  toast.error(e, {
    position: toast.POSITION.TOP_RIGHT
  });
}

const notifyWarning = (e) => {
  toast.warning(e, {
    position: toast.POSITION.TOP_RIGHT
  });
}

let rules = [],
  checkTimeId = -1,
  roundSyncId = -1,
  veriCode = "";

let TIMER = {
  MAIN: 1,
  FREE: 2,
};
let rtcPublic = {
  client: null,
  localAudioTrack: null,
  remoteAudioTracks: [],
};
let rtcPrivate = {
  client: null,
  localAudioTrack: null,
};
let options = {
  appId: APP_SECRET.agora.appId,
  channel: "demo_channel_name",
  token: null,
  uid: localStorage.getItem("uuid"),
  channelID: null,
};
let recTracks = [null];
function leaveCall(rtc) {
  rtc.localAudioTrack.close();
  rtc.client.leave();
}

function leaveRtm(rtm) {
  rtm.logout()
}

const rtm = {
  client: null,
  channel: null,
};
async function initRtm(cata, gid, gidData) {
  rtm.client = AgoraRTM.createInstance(options.appId);
  let uid = options.uid;
  await rtm.client
    .login({
      token: null,
      uid: uid// + Math.round(Math.random() * 100).toString(),
    })
    // .login({ token: null, uid: uid })
    .then((ret) => {
      console.log(ret);
    })
    .catch();
  let { rtmChannel } = gidData;
  //0辩手1工作人员2裁判

  if (cata === 1 && rtmChannel === "") {
    let { data } = await yesapi.others.getUniqueId();
    let { id } = data;
    id = id.toString();

    rtm.channel = rtm.client.createChannel(id);
    options.channelID = id;
    let a = {};
    a.rtmChannel = id;
    rtmChannel = id;

    await yesapi.table.updateViaID("game", a, gid);
  }
  rtm.channel = rtm.client.createChannel(rtmChannel);
  await rtm.channel
    .join()
    .then((ret) => {

    })
    .catch();
  rtm.channel.on("ChannelMessage", ({ text }, senderId) => {
    // text 为收到的频道消息文本，senderId 为发送方的 User ID
    /* 收到频道消息的处理逻辑 */
    console.log(text);
    console.log(senderId);
    let c = text.split("|");
    if (c[1] === "roundChange") {
      switch (c[2]) {
        case "ChangeTo":
          if (parseInt(c[3]) >= 0) {
            changeStatusTo(parseInt(c[3]));
          }
          notifySuccess("请注意，赛事环节已切换。")
          break;
        case "CLR":
          timerClear();
          break;
        case "Start":
          timerStatusChangeTo(1);
          notifySuccess("请注意，倒计时已开始。")
          break;
        case "Stop":
          timerStatusChangeTo(0);
          notifyWarning("请注意，倒计时已暂停。")
          break;
        default:
          break;
      }
    }
  });
}

export async function sendRtmMsg(msg) {
  await rtm.channel
    .sendMessage({ text: msg })
    .then((ret) => {
      console.log(ret);
    })
    .catch();
}

async function initRtc(cata, gid, gidData) {
  let uuid = localStorage.getItem("uuid");
  rtcPublic.client = AgoraRTC.createClient({ mode: "rtc", codec: "h264" });
  rtcPrivate.client = AgoraRTC.createClient({ mode: "rtc", codec: "h264" });
  let { rtcChannelPublic, rtcChannelPrivate } = gidData;
  //0辩手1工作人员2裁判
  if (cata === 1) {
    let a = {};
    if (rtcChannelPublic === "") {
      let { data } = await yesapi.others.getUniqueId();
      let { id } = data;
      id = id.toString();
      console.log(id);
      a.rtcChannelPublic = id;
      rtcChannelPublic = id;
    }
    if (rtcChannelPrivate === "") {
      let { data } = await yesapi.others.getUniqueId();
      let { id } = data;
      id = id.toString();
      console.log(id);
      a.rtcChannelPrivate = id;
      rtcChannelPrivate = id;
    }
    await yesapi.table.updateViaID("game", a, gid);
  }
  await rtcPublic.client.join(
    options.appId,
    rtcChannelPublic,
    options.token,
    uuid
  );

  rtcPublic.client.on("user-published", async (user, mediaType) => {
    await rtcPublic.client.subscribe(user);
    console.log("subscribe success");
    console.log(user);
    rtcPublic.remoteAudioTracks.push(user.audioTrack);
    rtcPublic.remoteAudioTracks[rtcPublic.remoteAudioTracks.length - 1].play();
    rtcPublic.remoteAudioTracks[rtcPublic.remoteAudioTracks.length - 1].setVolume(50);
  });

  rtcPublic.client.on("user-unpublished", (user) => {});

  rtcPublic.localAudioTrack = await AgoraRTC.createMicrophoneAudioTrack();
  await rtcPublic.client.publish([rtcPublic.localAudioTrack]);
  console.log("publish success!");


  rtcPublic.client.enableAudioVolumeIndicator();
  rtcPublic.client.on("volume-indicator", volumes => {
  volumes.forEach((volume, index) => {
    speakerVolume[index] = volume.uid + "|" + volume.level
    //console.log(`${index} UID ${volume.uid} Level ${volume.level}`);
  });
})
for(let i=1;i<=11;i++) {
  const audioFileTrack = await AgoraRTC.createBufferSourceAudioTrack({
    source: "http://cdn.puluter.cn/"+i+".m4a",
  });
  recTracks.push(audioFileTrack)
}
}

// startRtm();
function getTimerStr(iTime) {
  let min = Math.floor(iTime / 60);
  let sec = iTime % 60;
  let minStr = min < 10 ? "0" + min : min.toString();
  let secStr = sec < 10 ? "0" + sec : sec.toString();
  return minStr + ":" + secStr;
}
// const createSliderWithTooltip = Slider.createSliderWithTooltip;
const Handle = Slider.Handle;
const handle = (props) => {
  const { value, dragging, index, ...restProps } = props;
  return (
    <Tooltip
      prefixCls="rc-slider-tooltip"
      overlay={value}
      visible={dragging}
      placement="top"
      key={index}
    >
      <Handle value={value} {...restProps} />
    </Tooltip>
  );
};

function requestRoundSync() {
  roundSync()
    notifySuccess("赛事信息同步成功。")
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
          ctx.clearRect(0, 0, canvas.width, canvas.height);
          ctx.drawImage(image, 0, 0, 30, 30);
          resolve(ctx.getImageData(0, 0, 30, 30));
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
      if (alreadyDetected === false) {
          notifySuccess("检测到面部，正在校验身份。")
          const box = result.box;
          const imageScreenShot = clipImage(webCameraRef.current, box);
          const faceImageData = await scaleImage(imageScreenShot);
          const faceData = histogramEqualize(grayPocess(faceImageData).grayMatrix);
          request.post('/api/faceIdentification', {
              faceData,
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
          alreadyDetected = true;

      }
  }
  setTimeout(() => onPlayMark());
};

class CompShow extends React.Component {
  state = {
    com_logo: "https://jdc.jd.com/img/200",
    gameTitle: "竹辩 · 场次标题",
    debateTitles: ["竹辩 · 正方辩题", "竹辩 · 反方辩题"],
    debatorIntros: [
      {
        name: "正方",
        headImg: "https://jdc.jd.com/img/200",
      },
      {
        name: "正方",
        headImg: "https://jdc.jd.com/img/200",
      },
      {
        name: "正方",
        headImg: "https://jdc.jd.com/img/200",
      },
      {
        name: "正方",
        headImg: "https://jdc.jd.com/img/200",
      },
      {
        name: "反方",
        headImg: "https://jdc.jd.com/img/200",
      },
      {
        name: "反方",
        headImg: "https://jdc.jd.com/img/200",
      },
      {
        name: "反方",
        headImg: "https://jdc.jd.com/img/200",
      },
      {
        name: "反方",
        headImg: "https://jdc.jd.com/img/200",
      },
    ],
    workerIntros: [
      {
        name: "评委",
        headImg: "https://jdc.jd.com/img/200",
        introTxt:
          "评委的介绍会展示在这里",
      },
      {
        name: "评委",
        headImg: "https://jdc.jd.com/img/200",
        introTxt:
          "评委的介绍会展示在这里",
      },
      {
        name: "工作人员",
        headImg: "https://jdc.jd.com/img/200",
        introTxt:
          "工作人员的介绍会展示在这里",
      },
      {
        name: "工作人员",
        headImg: "https://jdc.jd.com/img/200",
        introTxt:
          "工作人员的介绍会展示在这里",
      },
    ],
    timer: {
      status: TIMER.MAIN,
      main: getTimerStr(0),
      free: [getTimerStr(0), getTimerStr(0)],
    },
    roundNow: "准备环节",
    value: 20,
    roundNext: "开篇立论 · 正一",
    speakPermission: "允许发言",
    inputAlert: true,
    sidebar: false,
    currentData: null,
    addNew: "",
    micro: 100,
    sound: 50,
    game_status: 0,//0未开始1进行中
    gidData: null,
    sid: null,
    rid: null,
    ifSpeaking: [100,100,100,100,100,100,100,100]
  };


  interfaceCheck() {
    //console.log(appGlobal.ifMute)
    try{
    if(appGlobal.ifMute === true) {
      rtcPublic.localAudioTrack.setMute(true);
    } else {
      rtcPublic.localAudioTrack.setMute(false);
    }} catch(err) {}
  
    let speakingVolume = [0,0,0,0,0,0,0,0]
    let teamInfo = debatorIntros
    for(let numOfSpeaking=0;numOfSpeaking<speakerVolume.length;numOfSpeaking++) {
      let clipVolume = speakerVolume[0].split("|")
      if(clipVolume[1]>0.01){
        for(let i=0;i<8;i++) {
          if(clipVolume[0] === teamInfo[i].UUID) {
            speakingVolume[i] = 100
          }
        }
      }
    }
    this.setState({
      ifSpeaking: speakingVolume
    })
    //} catch {}
  }

  roomInit(game_rules, debatorInfo, current_round) {
    debatorInfo = JSON.parse(debatorInfo);
    appGlobal.debatorPosi = debatorInfo.position;
    if (debatorInfo.side === "negative") {
      appGlobal.debatorPosi = appGlobal.debatorPosi + 4;
    }
    let rule_arr = game_rules.rules.split(";");
    for (let i = 0; i < rule_arr.length; i++) {
      rules[i] = rule_arr[i].split(",");
    }
    appGlobal.rules = rules;
    appGlobal.loadStatus = 1;
    changeStatusTo(current_round[0].currentRound);
    setTime(current_round[0].bigTime,current_round[0].freeTimeLeft,current_round[0].freeTimeRight)
    timerStatusChangeTo(current_round[0].currentStatus);
    checkTimeId = setInterval(checkTimer, 1000);
    setInterval(this.interfaceCheck.bind(this), 2000);
    //roundSyncId = setInterval(roundSync, 5000);
    notifySuccess("房间初始化成功。")
  }

  onSliderChange(value) {
    let id = arguments[0];
    let v = arguments[1];
    if (id === 1) {
      this.setState({ micro: v * 2  });
      rtcPublic.localAudioTrack.setVolume(v * 2);
      console.log(rtcPublic.remoteAudioTracks);
    } else {
      console.log(v)
      this.setState({ sound: v} );
      for (let i in rtcPublic.remoteAudioTracks) {
        console.log(rtcPublic.remoteAudioTracks);
        rtcPublic.remoteAudioTracks[i].setVolume(v);
      }
    }
  }

  async componentDidMount() {
    document.title = "比赛页面 | 云辩论"
    checkIfLogin();
    let gid = this.props.match.params.id;
    appGlobal.gid = gid;
    let uuid = localStorage.getItem("uuid");
    if (gid === undefined) gid = 1;
    let ret1 = await yesapi.table.read(
      "veri",
      [
        ["game_id", "=", gid],
        ["userid", "=", uuid],
      ],
      "and",
    );
    console.log(ret1)
    let { vcode, position, catagory } = ret1.data.data;
    this.setState({
      debatorInfo: position,
    });
    veriCode = vcode;
    appGlobal.catagory = catagory;
    //TODO: fOR测试用！
    this.roomInit2.bind(this)()
    hasInited = true;
  }
  recordingStarted() {
    timeRecTotal = timeRecTotal + 1
    appGlobal.recordingTime = timeRecTotal
    //console.log(appGlobal.recordingTime)
  }
  async onStartClicked() {
    
    startRecording = true;
    let {appId} = options;
    let {rtcChannelPublic} = this.state.gidData;
    let {rid,sid} = this.state;
    let gid = this.props.match.params.id;
    if(this.state.game_status === 0) {
      notifySuccess("赛事录制已开始。")
      recordingId = setInterval(this.recordingStarted.bind(this),1000)
      this.setState({game_status:1,});
      if (rid === null||rid === "") {
        let ret1 = await recordWeb.acquire(appId,rtcChannelPublic);
        console.log(ret1);
        let {resourceId} = ret1;
        await yesapi.table.updateViaID("game",{resourceId:resourceId},gid);
        rid = resourceId;
      }
      let {sid} = await recordWeb.start(appId,rid,rtcChannelPublic);
      await yesapi.table.updateViaID("game",{sid:sid,game_status:1},gid);
      this.setState({sid:sid,rid:rid});
    } else {
      notifySuccess("本次赛事录制已结束。")
      clearInterval(recordingId)
      let ret = await recordWeb.stop(appId,rid,rtcChannelPublic,sid);
      await yesapi.table.updateViaID("game",{sid:"",resourceId:"",game_status:0},gid);
      this.setState({game_status:0,sid:"",rid:""})
      console.log(ret);
      await yesapi.table.update("game",
      {recording_point: JSON.stringify(appGlobal.recordingPoint)},
      [
        ["id", "=", gid],
      ],
      "and",
      )
      notifySuccess("录制时间戳已上传至服务器。")
      
    }
  }

  onMuteClick() {
    console.log(arguments);
    let id = arguments[0];
    let v = 0;
    if (id === 1) {
      this.setState({ micro: v });
      rtcPublic.localAudioTrack.setVolume(v);
      console.log(rtcPublic.remoteAudioTracks);
    } else {
      console.log(v)
      this.setState({sound: v});
      for (let i in rtcPublic.remoteAudioTracks) {
        console.log(rtcPublic.remoteAudioTracks);
        rtcPublic.remoteAudioTracks[i].setVolume(v);
      }
    }
  }


  async handleSecret(res) {
    let { data } = await yesapi.table.read(
      "game",
      [["id", "=", this.props.match.params.id]],
      "and",
    );
    if(data.err_code===3) {
      notifyError("房间尚未初始化。")
      history.push("/compList");
      return;
    }
    let {
      game_status
    } = data.data;
    if (game_status === 3) {
      console.log()
      history.push("/analyze")
      localStorage.setItem("gid",this.props.match.params.id)
      return;
    } else {
      if (res === veriCode) {
        this.setState({ inputAlert: false });
        this.roomInit2.bind(this)()
        hasInited = true;
      } else {
        //this.setState({wrongSecret:"请输入正确密钥。"})
      }

    }
  }
  async roomInit2() {
    let gid = this.props.match.params.id;
    if (gid === undefined) gid = 1;

    let _this = this;
    let { data } = await yesapi.table.read(
      "game",
      [["id", "=", gid]],
      "and",
    );
    if(data.err_code===3) {
      notifyError("房间尚未初始化。")
      history.push("/compList");
      return;
    }
    let {
      game_title,
      team_info,
      com_id,
      judge_info,
      rules,
      current_round,
      game_status,
      sid,
      resourceId,
    } = data.data;
    if (game_status === 3) {
      console.log()
      history.push("/analyze")
      localStorage.setItem("gid",this.props.match.params.id)
      return;
    }
    current_round = JSON.parse(current_round);
    team_info = JSON.parse(team_info);
    debatorIntros = team_info;
    appGlobal.teamInfo = team_info;
    judge_info = JSON.parse(judge_info);

    let gidData = data.data;
    if (true) {
      // delete data;
      // let {data} = await yesapi.user.getSelfInfo(uuid,token)
      initRtm(2, gid, gidData);
      initRtc(2, gid, gidData);
      this.setState({gidData:gidData});
    }
    rules = JSON.parse(rules);
    let ret = await yesapi.table.read(
      "competition",
      [["id", "=", com_id]],
      "and",
    );
    data = ret.data.data;

    let { com_name, logo } = data;
    _this.setState({
      gameTitle: com_name, 
      com_logo: logo,
      workerIntros: judge_info,
      debatorIntros: team_info,
      debateTitles: game_title.split("|"),
      game_status:game_status,
      sid:sid,
      rid:resourceId
    });
    _this.roomInit(rules[0], this.state.debatorInfo, current_round);
  }

  componentWillUnmount () {
    
    try{
    if(hasInited) {
      leaveCall(rtcPublic)
      leaveRtm(rtm.client)
      console.log(mountTimerId)
      clearInterval(roundSyncId)
      clearInterval(checkTimeId)
      // clearInterval(mountTimerId)
    }
  }catch(err){}}

  handleSidebar = (boolean, addNew = false) => {
    this.setState({ sidebar: boolean });
    if (addNew === true) this.setState({ currentData: null, addNew: true });
  };
  async playRecSound() {
    let i = appGlobal.statusNow;
    recTracks[i].startProcessAudioBuffer();
    await rtcPublic.client.publish([recTracks[i]]);
    recTracks[i].play();
  }
  render() {
    let {
      gameTitle,
      debateTitles,
      debatorIntros,
      workerIntros,
      timer,
      roundNow,
      roundNext,
      sidebar,
      currentData
    } = this.state;

    return (

      <div id="" style={{overFlowY:"hidden"}}>
        {/* <SweetAlert
          title="提示"
          input
          show={this.state.inputAlert}
          validationMsg="请输入有效密钥。"
          inputType="password"
          placeHolder="密钥"
          onConfirm={(res) => this.handleSecret(res)}
        >
          <p className="sweet-alert-text">请输入房间密钥。</p>
        </SweetAlert> */}
        <div
        className={'data-list'}>
        <Sidebar 
          show={sidebar}
          data={currentData}
          updateData={this.props.updateData}
          addData={this.props.addData}
          handleSidebar={this.handleSidebar}
          thumbView={this.props.thumbView}
          getData={this.props.getData}
          dataParams={this.props.parsedFilter}
          addNew={this.state.addNew}
          playRecSound = {this.playRecSound.bind(this)}
        />
        <div
          className={classnames("data-list-overlay", {
            show: sidebar
          })}
          onClick={() => this.handleSidebar(false, true)}
        />
      </div>
        <Breadcrumbs
          breadCrumbTitle={
            <div style={{ display: "flex", flexDirection: "row" }}>
              <Avatar
                img={this.state.com_logo}
                size="sm"
                style={{ display: "inline" }}
              />
              <h1>{gameTitle}</h1>
            </div>
          }
          breadCrumbParent="比赛直播"
          breadCrumbActive={"房间：" + gameTitle}
        />

        <Row  className="match-height">
          <Col md="9">
            <Card className="main-tab">
              <CardHeader>
                <CardTitle></CardTitle>
              </CardHeader>
              <CardBody>
                {/* 辩题部分开始 */}
                <Row className="justify-content-md-center">
                  <Col md="4">
                    <h1 className="text-info">{debateTitles[0]}</h1>
                  </Col>
                  <Col md={{ size: 4, offset: 4 }}>
                    <h1 className="text-danger text-right">
                      {debateTitles[1]}
                    </h1>
                  </Col>
                </Row>
                {/* 辩题部分结束 */}
                {/* 计时器部分开始 */}
                <Row>
                  <Col md={{ size: 4, offset: 2 }}>
                    <h2
                      className="text-left timer-free text-info timer-hide"
                      id="freeTimerLeft"
                    >1</h2>
                  </Col>
                  <Col md={{ size: 4, offset: 0 }}>
                    <h2
                      className="text-right timer-free text-danger timer-hide"
                      id="freeTimerRight"
                    >2</h2>
                  </Col>
                </Row>
                <h2 className="text-center timer-main" id="mainTimer">
                  {timer.main}
                </h2>
                {/* 计时器部分结束 */}

                {/* 环节部分开始 */}
                <h2 className="text-center roundTxt" id="status">
                  {roundNow}
                </h2>
                {/* 环节部分结束 */}

                {/* 下一环节部分开始 */}
                <h3 className="text-center roundTxt-next" id="statusnext">
                  {roundNext}
                </h3>
                {/* 下一环节部分结束 */}
                {/* 选手头像部分开始 */}
                <Row
                  className="debator-imgs"
                  style={{ display: "flex", justifyContent: "space-between" }}
                >
                  <Col md="4">
                    <Row>
                      {debatorIntros.map((item, index) => {
                        if (index > 3) return "";
                        return (
                          <Col key={index}>
                            <Badge pill className="badge-glow" style={{opacity:this.state.ifSpeaking[index]}}color="success"><Mic size={12} /></Badge>
                            <Avatar
                              img={item.headImg}
                              size="lg"
                              style={{ display: "block" }}
                            />
                            <h4 className="debator-name text-center">
                            {item.name}
                            </h4>
                          </Col>
                        );
                      })}
                    </Row>
                  </Col>
                  <Col md="4"></Col>
                  <Col md="4" style={{ marginLeft: "auto" }}>
                    <Row className="justify-content-end">
                      {debatorIntros.map((item, index) => {
                        if (index <= 3) return "";
                        return (
                          <Col key={index}>
                            <Badge pill className="badge-glow" style={{opacity:this.state.ifSpeaking[index]}}color="success"><Mic size={12} /></Badge>
                            <Avatar
                              img={item.headImg}
                              size="lg"
                              style={{ display: "block" }}
                            />
                            <h4 className="debator-name text-center">
                              {item.name}
                            </h4>
                          </Col>
                        );
                      })}
                    </Row>
                  </Col>
                </Row>
                {/* 选手头像部分结束 */}
              </CardBody>
            </Card>
          </Col>

          {/* 工作人员介绍开始 */}
          <Col>
            <Card className="workers-card">
              <CardHeader>
                <CardTitle>评委与工作人员介绍</CardTitle>
              </CardHeader>
              <CardBody>
                {workerIntros.map((item, index) => {
                  let { name, headImg, introTxt } = item;
                  return (
                    <div key={index + 1}>
                      <Media>
                        <Media left href="#">
                          <Media
                            className="rounded-circle"
                            object
                            src={headImg}
                            height="60"
                            width="60"
                            alt={name}
                          />
                        </Media>
                        <Media body className="debatorIntroTxtC">
                          <Media heading>{name}</Media>
                          {introTxt}
                        </Media>
                      </Media>
                      {index !== workerIntros.length - 1 ? <hr /> : <div></div>}
                    </div>
                  );
                })}
              </CardBody>
            </Card>
          </Col>

          {/* 工作人员介绍结束 */}

          <Col md="9">
            <Card>
              <CardBody>
                <Row>
                  <Col
                    md="4"
                    style={{
                      justifyContent: "space-around",
                      display: "flex",
                      flexDirection: "row",
                    }}
                  >
                    <span>
                      <Mic height="30" onClick={this.onMuteClick.bind(this,1)} style={{display:this.state.micro === 0?"none":true,cursor:"pointer"}} />
                      <MicOff height="30" onClick={this.onSliderChange.bind(this, 1,50)} style={{display:this.state.micro === 0?true:"none",cursor:"pointer"}}/>
                    </span>
                    <Slider
                      style={{
                        height: "20px",
                        lineHeight: "30px",
                        marginTop: "10px",
                        marginLeft: "10px",
                      }}
                      trackStyle={{ backgroundColor: "#7367f0" }}
                      min={0}
                      onChange={this.onSliderChange.bind(this, 1)}
                      max={100}
                      value={this.state.micro/2}
                      defaultValue={50}
                      handle={handle}
                      tipProps={{
                        prefixCls: "rc-slider-tooltip",
                      }}
                    />
                  </Col>
                  <Col
                    md="4"
                    style={{ display: "flex", justifyContent: "center" }}
                  >
                    <Badge
                      className="badge-glow badge-lg"
                      color="success"
                      id="speakPermission"
                    >
                      {this.state.speakPermission}
                    </Badge>
                  </Col>
                
                  <Col
                    md="4"
                    style={{
                      justifyContent: "space-around",
                      display: "flex",
                      flexDirection: "row",
                    }}
                  >
                    <Slider
                      style={{
                        height: "20px",
                        lineHeight: "30px",
                        marginTop: "10px",
                        marginRight: "10px",
                      }}
                      trackStyle={{ backgroundColor: "#7367f0" }}
                      min={0}
                      onChange={this.onSliderChange.bind(this, 2)}
                      max={100}
                      defaultValue={50}
                      handle={handle}
                      value={this.state.sound}
                      reverse={true}
                      tipProps={{
                        prefixCls: "rc-slider-tooltip",
                      }}
                    />
                    <span>
                      <Volume2 height="30" onClick={this.onMuteClick.bind(this,2)} style={{display:this.state.sound === 0?"none":true,cursor:"pointer"}} />
                      <VolumeX height="30" onClick={this.onSliderChange.bind(this,2,50)} style={{display:this.state.sound === 0?true:"none",cursor:"pointer"}}/>
                    </span>
                  </Col>
                </Row>
              </CardBody>
            </Card>
          </Col>
          <Col>
            <Card>
              <CardBody>
                <Row style={{ height: "30px" }}>
                  <Col style={{marginTop: "-6px"}}>
                  <ButtonGroup>
                    <Button.Ripple outline
                      color="success"
                      //style={{ marginLeft: "15px", marginTop: "-4px" }}
                      onClick={() => requestRoundSync()}
                    >
                      <DownloadCloud size={14} />
                      同步
                    </Button.Ripple>
                    <Button.Ripple outline
                      color="success"
                      //style={{ marginLeft: "15px", marginTop: "-4px" }}
                      onClick={() => this.handleSidebar(true, true)}
                    >
                      <List size={14} />
                      管理
                    </Button.Ripple>
                  </ButtonGroup>
                  <Button.Ripple style={{marginLeft: "5px"}} className="mr-1 mb-1" outline color="primary" onClick={this.onStartClicked.bind(this)}>
                    {this.state.game_status===0?"开始":"结束"}
                  </Button.Ripple>
                  </Col>
                </Row>
              </CardBody>
            </Card>
          </Col>
        </Row>
      </div>
    );
  }
}

export default CompShow;