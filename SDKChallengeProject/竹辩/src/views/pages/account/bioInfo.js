import React from "react"
import { Button, Card, CardBody, Row, Col } from "reactstrap"
import "../../../assets/scss/pages/compShow.scss"
import "flatpickr/dist/themes/light.css";
import "../../../assets/scss/plugins/forms/flatpickr/flatpickr.scss"
import Webcamera from '../../../components/WebCamera/index';
import { clipImage } from '../../../utils/faceUtils/getImageData';
import faceapi, { getDetectorOptions, isModelLoaded, loadModel } from '../../../utils/faceUtils/faceDetectionControl';
import {grayPocess} from '../../../utils/faceUtils/grayProcess';
import histogramEqualize from '../../../utils/faceUtils/histogramEqualize';
import request from '../../../utils/request';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import "../../../assets/scss/pages/compShow.scss"
import "../../../assets/scss/pages/dashboard-analytics.scss"
let updateInfoId, isUsingCamera=false
const fileMatrixs = [];

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
/*
const notifyError = (e) => {
  toast.error(e, {
    position: toast.POSITION.TOP_RIGHT
  });
}
*/
const notifyWarning = (e) => {
  toast.warning(e, {
    position: toast.POSITION.TOP_RIGHT
  });
}

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

const webCameraRef = React.createRef();
const uuid = localStorage.getItem("uuid");
const onPlayMark = async () => {
  if (!isModelLoaded() || !webCameraRef.current || webCameraRef.current.paused || webCameraRef.current.ended) {
      return setTimeout(() => onPlayMark());
  }

  const options = getDetectorOptions();
  const result = await faceapi.detectSingleFace(webCameraRef.current, options);


  if (result) {
      if (alreadyDetected === false) {
          notifySuccess("检测到面部，正在上传面部信息。")
          const box = result.box;
          const imageScreenShot = clipImage(webCameraRef.current, box);
          const faceImageData = await scaleImage(imageScreenShot);
          const faceData = histogramEqualize(grayPocess(faceImageData).grayMatrix);
          fileMatrixs.push(faceData);
          console.log(fileMatrixs, 'beforeUpload');
          const params = {
              UUID: uuid,
              faceData: fileMatrixs
          };
          request.post('/api/addFacialFeatures', params).then(res => {
            console.log(res)
            console.log(res.data.code)
            if(res.data.code === 200) {
              notifySuccess("面部信息录入成功。")
            } else {
              notifyWarning("面部信息录入失败，请刷新后重试。")
            }
            fileMatrixs.splice(0, fileMatrixs.length);
          });
          alreadyDetected = true;
      }
  }
  setTimeout(() => onPlayMark());
};


class faceAuthInference extends React.Component {

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
  
  componentDidMount() {
    document.title = "生物指纹 | 云辩论"
    updateInfoId = setInterval(this.updateInfo, 5000);
    console.log(updateInfoId)
  }


    render() {
      return (
        <div id="" style={{overFlowY:"hidden"}}>
          <Row  className="match-height">
            <Col md="9">
              <Card className="faceMainTab">
                <div className="faceModelInput">
                    <img src="/images/face.png" alt="face" width="350" height="600"/>
                </div>
                <div className="faceShadow">
                    <img src="/images/shadow.png" alt="face" width="1000" height="678"/>
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
                <CardBody style={{height:690}}>
                <Webcamera ref={webCameraRef} onLoadedMetaData={e => onPlayMark(e)} />
                </CardBody>
              </Card>
            </Col>
            {/*  */}
            <Col className="search-content" key="1">
            <Card className="bg-analytics text-white sales-card">
            <CardBody className="text-center">
              <div className="award-info text-center">
                <h1 className="mb-2 text-white">面部信息录入</h1><br></br>
                <div className="faceAuthHead">
                    <img src="/images/head.png" alt="face" width="260"/>
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
        )}
}
export default faceAuthInference;


