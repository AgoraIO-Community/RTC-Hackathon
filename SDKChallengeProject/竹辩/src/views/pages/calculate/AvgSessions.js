import React from "react"
import {
  Card,
  CardBody,
  Row,
  Col,
  Button,
  UncontrolledDropdown,
  DropdownMenu,
  DropdownItem,
  DropdownToggle,
  Progress,
  Modal,
  ModalHeader,
  ModalBody,
  ModalFooter
} from "reactstrap"
import Chart from "react-apexcharts"
import { ChevronsRight, ChevronDown } from "react-feather"
import appGlobal from "./config.js";
import getData from "./getData.js";
import yesapi from "../../../webServices/yes3";

class AvgSessions extends React.Component {
  state = {
    participation: "",
    fluency: "",
    stage_performance: "",
    self_confidence: "",
    options: {
      chart: {
        sparkline: { enabled: true },
        toolbar: { show: false }
      },
      states: {
        hover: {
          filter: "none"
        }
      },
      colors: [
        this.props.primary,
        this.props.labelColor,
        this.props.labelColor,
        this.props.labelColor,
        this.props.labelColor,
        this.props.labelColor,
        this.props.labelColor
      ],
      grid: {
        show: false,
        padding: {
          left: 0,
          right: 0
        }
      },
      dataLabels: {
        enabled: false
      },
      plotOptions: {
        bar: {
          columnWidth: "45%",
          distributed: true,
          endingShape: "rounded"
        }
      },
      tooltip: {
        x: { show: false }
      },
      xaxis: {
        type: "numeric"
      },
    },
    refAvg: [],
    modal: false,
    modal2: false,
    roundTime: ""
  }
  async componentWillMount() {
    await getData()
    let gid = localStorage.getItem("gid")
    let uuid = localStorage.getItem("uuid");
    let ret2 = await yesapi.table.read(
      "game",
      [
        ["id", "=", gid],
      ],
      "and",
    );
    console.log(ret2)
    let { recording_point,grade } = ret2.data.data
    recording_point = JSON.parse(recording_point)
    grade = JSON.parse(grade)
    let tempMsg = ""
    for(let i=0;i<13;i++) {
      tempMsg = tempMsg + grade[i] + " 切换时间点位于 " + recording_point[i] + " 秒。"
    }
    //recording_point = JSON.parse(recording_point)

    this.setState({
      participation: appGlobal.participation[0],
      fluency: appGlobal.fluency[0],
      stage_performance: appGlobal.stage_performance[0],
      self_confidence: appGlobal.self_confidence[0],
      profession: appGlobal.RefGrade[0],
      roundTime: tempMsg,
      refAvg: [
        {
          name: "裁判评分",
          data: appGlobal.RefAvg
        }
      ]
    })
  }
  getColor(percentage){
    if(parseInt(percentage)>85){
      return "success"
    } else if(parseInt(percentage)>60){
      return "warning"
    } else {return "danger"}
  }

  toggleModal() {
    this.setState({
      modal: true
    })
  }
  closeModal() {
    this.setState({
      modal: false
    })
  }
  toggleModal2() {
    this.setState({
      modal2: true
    })
  }
  closeModal2() {
    this.setState({
      modal2: false
    })
  }

  render() {
    
    return (
      <div>
      <Modal
      isOpen={this.state.modal}
      toggle={() => this.closeModal()}
      className={this.props.className}
    >
      <ModalHeader toggle={() => this.closeModal()}>
        智能评分计算规则
      </ModalHeader>
      <ModalBody>
        <h5>参与度</h5>
        参与度根据全程人脸捕捉成功次数，五秒进行一次识别，成功次数除以总次数。
        <h5>流利度</h5>
        流利度根据发言期间的停顿，加权总发言时间计算。
        <h5>舞台台风</h5>
        舞台台风根据个人表情，若特定表情识别结果大于0.7则认为本次判断结果为良好。
        <h5>自信度</h5>
        根据是否遮挡面部以及微笑可能性的平均值计算得出。
      </ModalBody>
      <ModalFooter>
        <Button color="primary" onClick={() => this.closeModal()}>
          确定
        </Button>{" "}
      </ModalFooter>
    </Modal>

    <Modal
      isOpen={this.state.modal2}
      toggle={() => this.closeModal2()}
      className={this.props.className}
    >
      <ModalHeader toggle={() => this.closeModal2()}>
        赛事复盘
      </ModalHeader>
      <ModalBody>
        若该场赛事选择被录音，你可以在这里看到不同环节的时间点并重复聆听。<br></br><br></br>
        <h5>环节时间戳</h5>
        
        
        {this.state.roundTime}
      </ModalBody>
      <ModalFooter>
        <Button color="primary" onClick={() => this.closeModal2()}>
          确定
        </Button>{" "}
      </ModalFooter>
    </Modal>


      <Card>
        <CardBody>
          <Row className="pb-50">
            <Col
              lg={{ size: 6, order: 1 }}
              sm={{ size: 12, order: 2 }}
              xs={{ order: 2 }}
              className="d-flex justify-content-between flex-column mt-lg-0 mt-2"
            >
              <div className="session-info">
                <h2 className="text-bold-600 mb-25">{this.state.profession}%</h2>
                <p className="text-bold-500 mb-75">专业度 - 基于评委打分得出</p>
                <h5 className="font-medium-2">
                  <span className="text-success">+0.0% </span>
                  <span>相对于本场比赛平均分</span>
                </h5>
              </div>
              <Button.Ripple className="btn-block shadow" color="primary" onClick={() => this.toggleModal2()}>
                 查看该场赛事回顾<ChevronsRight size={15} />
              </Button.Ripple>
            </Col>
            <Col
              lg={{ size: 6, order: 2 }}
              sm={{ size: 12, order: 1 }}
              xs={{ order: 1 }}
              className="d-flex justify-content-between flex-column text-right"
            >
              <UncontrolledDropdown>
                <DropdownToggle tag="small" className="text-bold-500 cursor-pointer">
                  对数据存在疑问？
                </DropdownToggle>
                <DropdownMenu right>
                  <DropdownItem onClick={() => this.toggleModal()}>查看计算方式</DropdownItem>
                </DropdownMenu>
              </UncontrolledDropdown>
              <Chart
                options={this.state.options}
                series={this.state.refAvg}
                type="bar"
                height={200}
              />
            </Col>
          </Row>
          <hr />
          <Row className="pt-50">
          <Col md="6" sm="12">
              <p className="mb-0">参与度: {this.state.participation}%</p>
              <Progress className="mt-25" color={this.getColor(this.state.participation)} value={this.state.participation} />
            </Col>
            <Col md="6" sm="12">
              <p className="mb-0">流利度: {this.state.fluency}%</p>
              <Progress className="mt-25" color={this.getColor(this.state.fluency)} value={this.state.fluency} />
            </Col>
            <Col md="6" sm="12">
              <p className="mb-0">赛场台风: {this.state.stage_performance}%</p>
              <Progress className="mt-25" color={this.getColor(this.state.stage_performance)} value={this.state.stage_performance} />
            </Col>
            <Col md="6" sm="12">
              <p className="mb-0">自我认可: {this.state.self_confidence}%</p>
              <Progress className="mt-25" color={this.getColor(this.state.self_confidence)} value={this.state.self_confidence} />
            </Col>
          </Row>
        </CardBody>
      </Card>
      </div>
    )
  }
}
export default AvgSessions
