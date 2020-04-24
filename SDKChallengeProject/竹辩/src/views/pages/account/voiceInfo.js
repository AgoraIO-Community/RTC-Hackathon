import React from "react"
import { Button, Card, CardBody, Row, Col } from "reactstrap"
import "../../../assets/scss/pages/compShow.scss"
import "flatpickr/dist/themes/light.css";
import "../../../assets/scss/plugins/forms/flatpickr/flatpickr.scss"
import 'react-toastify/dist/ReactToastify.css';
import "../../../assets/scss/pages/compShow.scss"
import "../../../assets/scss/pages/dashboard-analytics.scss"
import { toast } from 'react-toastify';
toast.configure({
  autoClose: 5000,
  draggable: false,
});

const notifyWarning = (e) => {
  toast.warning(e, {
    position: toast.POSITION.TOP_LEFT
  });
}

class voiceAuthInference extends React.Component {

    render() {
      return (
        <div id="" style={{overFlowY:"hidden"}}>
          <Row  className="match-height">
            <Col md="9">
              <Card className="faceMainTab">
              <CardBody style={{height:690}}>
                <h2 className="text-center timer-main" id="mainTimer">
                  1 5 8 2 4 5 2
                </h2>
                <h2 className="text-center roundTxt" id="status">
                  识别码
                </h2>
                <h3 className="text-center roundTxt-next" id="statusnext">
                  请在按下开始录音按钮后朗读以上数字
                </h3>

                <div className="wavesAuthInput">
                    <img src="/images/waves.png" alt="face" width="700" height="180"/>
                </div>
                <div className="detectVoiceInput">
                    <Button.Ripple
                        color="success"
                        //onClick={() => this.startFaceAuth()}
                        size="lg"
                        className="mr-1 mb-1 btn-block round"
                        outline
                        onClick={() => notifyWarning("声纹识别在当前编译版本中不可用。")}
                        >
                        开始录音
                    </Button.Ripple>
                </div>
                </CardBody>
              </Card>
            </Col>
            {/*  */}
            <Col className="search-content" key="1">
            <Card className="bg-analytics text-white sales-card">
            <CardBody className="text-center">
              <div className="award-info text-center">
                <h1 className="mb-2 text-white">声纹录入</h1><br></br>
                <div className="faceAuthHead">
                    <img src="/images/speak.png" alt="face" width="260"/>
                </div><br></br><br></br>
                <p className="m-auto mb-0 w-75">
                  请按下采集按钮后按照页面提示大声朗读相应文字，再次按下按钮结束。<br></br>
                  若无法采集或无法从服务器获取声纹数据，请刷新页面。
                </p>
              </div>
            </CardBody>
          </Card>
            </Col>
            {/*  */}
          </Row>
        </div>
        )}
}
export default voiceAuthInference;
