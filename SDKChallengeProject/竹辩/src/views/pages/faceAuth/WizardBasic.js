import React from "react"
import Wizard from "./WizardComponent"
import Webcamera, {stopStream} from '../../../components/WebCamera/index';
import { clipImage } from '../../../utils/faceUtils/getImageData';
import faceapi, { getDetectorOptions, isModelLoaded, loadModel } from '../../../utils/faceUtils/faceDetectionControl';
import {grayPocess} from '../../../utils/faceUtils/grayProcess';
import histogramEqualize from '../../../utils/faceUtils/histogramEqualize';
import request from '../../../utils/request';
import "../../../assets/scss/pages/compShow.scss"
import "../../../assets/scss/pages/dashboard-analytics.scss"
import {
  Row,
  Col,
  Card,
  CardBody,
  Button
} from "reactstrap"
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { Eye, Mic, LogIn, LogOut } from "react-feather";
import FaceAuthIndex from "./faceAuthIndex";
import {Badge} from "reactstrap"
import {history} from "../../../history";

let face =  "面部识别未通过", voice = "声纹识别未通过"

let updateInfoId
let isUsingCamera=false

let alreadyDetected = false;
toast.configure({
  autoClose: 5000,
  draggable: false,
});
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

//const [isUsingCamera, setIsUsingCamera] = useState(false);


class faceAuthInference extends React.Component {
  state = {
    steps:[
      {
        title: <LogIn size={20} />,
        content: <FaceAuthIndex />
      }, {
          title: <Eye size={20} />,
          content: <div id="" style={{overFlowY:"hidden"}}>
          <Row  className="match-height">
            <Col md="9">
              <Card className="faceMainTab">
                <div className="faceModel">
                    <img src="/images/face.png" alt="face" width="350" height="600"/>
                </div>
                <div className="faceShadow">
                    <img src="/images/shadow.png" alt="face" width="1153" height="678"/>
                </div>
                <div className="detectFace">
                  
                    <Button.Ripple
                        color="success"
                        
                        onClick={() => this.startFaceAuth()}
                        size="lg"
                        className="mr-1 mb-1 btn-block round"
                        >
                        获取面部信息
                    </Button.Ripple>
                </div>
                <CardBody style={{height:720}}>
                <Webcamera ref={webCameraRef} onLoadedMetaData={e => onPlayMark(e)} />
                </CardBody>
              </Card>
            </Col>
            {/*  */}
            <Col className="search-content" key="1">
            <Card className="bg-analytics text-white sales-card">
            <CardBody className="text-center">
              <div className="award-info text-center">
                <h1 className="mb-2 text-white">面部识别</h1>
                <div className="faceAuthHead">
                    <img src="/images/head.png" alt="face" width="330"/>
                </div><br></br><br></br>
                <p className="m-auto mb-0 w-75">
                  请将头部放置于取景框中，点击获取面部信息后微调，直到弹窗提示面部信息采集成功。<br></br>
                  若长时间无法采集或无法从服务器获取面部数据，请刷新页面。
                </p>
              </div>
            </CardBody>
          </Card>

            </Col>
            {/*  */}
          </Row>
        </div>
      
        }, {
          title: <Mic size={20} />,
          content: <div id="" style={{overFlowY:"hidden"}}>
          <Row  className="match-height">
            <Col md="9">
              <Card className="faceMainTab">
              <CardBody style={{height:720}}>
                <h2 className="text-center timer-main" id="mainTimer">
                  1 5 8 2 4 5 2
                </h2>
                <h2 className="text-center roundTxt" id="status">
                  识别码
                </h2>
                <h3 className="text-center roundTxt-next" id="statusnext">
                  请在按下开始录音按钮后朗读以上数字
                </h3>
                <div className="wavesAuth">
                    <img src="/images/waves.png" alt="face" width="800" height="180"/>
                </div>
                <div className="detectVoice">
                    <Button.Ripple
                        color="success"
                        //onClick={() => this.startFaceAuth()}
                        size="lg"
                        className="mr-1 mb-1 btn-block round"
                        outline
                        onClick={() => notifyWarning("请先进行面部识别。")}
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
                <h1 className="mb-2 text-white">声纹识别</h1><br></br>
                <div className="faceAuthHead">
                    <img src="/images/speak.png" alt="face" width="330"/>
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
        }, {
          title: <LogOut size={20} />,
          content: <Row>
          <Col sm="12">
            <Card className="faq-bg" >
              <CardBody className="p-sm-4 p-2">
                <h1 className="white">身份验证结束</h1>
                <p className="mb-2 white">
                  根据识别，你的认证结果如下，不同比赛认证要求不同，请自行选择。
                  <br></br>若页面未更新，请点击下方按钮进入比赛列表。
                  <br></br><br></br>
                  <Badge pill className="badge-glow" color="danger">{face}</Badge> &nbsp;&nbsp; <Badge pill className="badge-glow" color="danger">{voice}</Badge><br></br><br></br>
                </p>
                  <Button.Ripple className="mr-1 mb-1 btn-block" color="primary" size="lg" onClick={() => this.joinMatch()}>
                    进入比赛列表
                  </Button.Ripple>
              </CardBody>
            </Card>
          </Col>
        </Row>
        }
      ]
  }
  startFaceAuth(){
    if(isUsingCamera === false){
      notifySuccess("请调整面部角度，正视摄像头。")
      isUsingCamera = true
    } else {
      notifyWarning("您已经进行过面部识别，请耐心等待结果。")
    }
  }

  updateInfo() {
    if (isUsingCamera) {
      loadModel();
      webCameraRef.current && webCameraRef.current.paused
          ? webCameraRef.current.play() : void 0;
      
    }
    return () => {     
        webCameraRef.current && webCameraRef.current.pause();    
    };
  }
  joinMatch () {
    history.push("/compList")
  }
  componentDidMount() {
    updateInfoId = setInterval(this.updateInfo, 5000);
    console.log(updateInfoId)
  }

  componentWillUnmount() {
    stopStream()
  }

  render() {
    return(
      <Wizard
      enableAllSteps
      onFinish={() => alert("submitted")}
      steps={this.state.steps}
     />
    )
  }
}
export default faceAuthInference;

