import React from "react"
import { Card, CardHeader, Button, CardBody } from "reactstrap"
import {history} from "../../../history"
class PersonalInfoCard extends React.Component {
  state = {
    avatar: "https://jdc.jd.com/img/200",
    realname: "Loading",
    username: "Loading"
  }
  componentDidMount() {
    let avatar = localStorage.getItem("avatar")
    let realname = localStorage.getItem("realname");
    let username = localStorage.getItem("username");
    this.setState({avatar,realname,username})
  }

  render() {
    let {avatar,realname,username} = this.state;
    return (
      <>
          <Card>
            <CardHeader className="mx-auto flex-column">
    <h4>{username}</h4>
              <p>{realname}</p>
            </CardHeader>
            <CardBody className="text-center pt-0">
              <div className="avatar mr-1 avatar-xl">
                <img src={avatar} alt="avatarImg" />
              </div>
              <div className="d-flex justify-content-between mt-2">
              <p className="font-weight-bold font-medium-2 mb-0">山东省青岛二中辩论队主力队员。曾获精英杯华语辩论赛十六强。</p>
              </div>
              <Button.Ripple className="btn-block gradient-light-primary mt-2" onClick={()=>{history.push("/account")}}>
                修改
              </Button.Ripple>
            </CardBody>
          </Card>
      </>
    )
  }
}
export default PersonalInfoCard
