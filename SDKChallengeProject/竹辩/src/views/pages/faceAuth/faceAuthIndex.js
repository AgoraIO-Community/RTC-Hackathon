import React from "react"
import { Card, CardBody, Button, Row, Col } from "reactstrap"
import "../../../assets/scss/pages/compShow.scss"
import {Badge} from "reactstrap"
class QuestionSearch extends React.Component {
  state = {
    value: ""
  }

  render() {
    return (
      <Row>
        <Col sm="12">
          <Card className="faq-bg" >
            <CardBody className="p-sm-4 p-2">
              <h1 className="white">生物信息识别</h1>
              <p className="mb-2 white">
                为验证辩手身份，需要您在进入比赛前进行面部和声纹识别。
                <br></br>请授权浏览器访问您的摄像头和麦克风以完成鉴权。<br></br><br></br>
                <Badge pill className="badge-glow" color="success">摄像头已授权</Badge> &nbsp;&nbsp; <Badge pill className="badge-glow" color="success">麦克风已授权</Badge><br></br><br></br>
              </p>
              <Button.Ripple className="mr-1 mb-1 btn-block" color="primary" size="lg">
                开始识别
              </Button.Ripple>
            </CardBody>
          </Card>
        </Col>
      </Row>
    )
  }
}
export default QuestionSearch
