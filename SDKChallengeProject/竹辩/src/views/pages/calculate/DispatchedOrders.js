import React from "react"
import {
  Card,
  CardHeader,
  CardTitle,
  Table,
  UncontrolledTooltip,
  Progress
} from "reactstrap"
import avatar1 from "../../../assets/img/portrait/small/avatar-s-5.jpg"
import avatar2 from "../../../assets/img/portrait/small/avatar-s-7.jpg"
import avatar3 from "../../../assets/img/portrait/small/avatar-s-10.jpg"
import avatar4 from "../../../assets/img/portrait/small/avatar-s-8.jpg"
import avatar5 from "../../../assets/img/portrait/small/avatar-s-1.jpg"
import avatar6 from "../../../assets/img/portrait/small/avatar-s-2.jpg"
import avatar7 from "../../../assets/img/portrait/small/avatar-s-3.jpg"
import avatar8 from "../../../assets/img/portrait/small/avatar-s-4.jpg"
import appGlobal from "./config.js";
import getData from "./getData.js";

class DispatchedOrders extends React.Component {
  state = {
    posi: "",
    neg: "",
    team_info: "",
    img: [],
    name: [],

  }
  async componentWillMount() {
    await getData()
    
    this.setState({
      posi: appGlobal.posi,
      neg: appGlobal.neg,
      team_info: appGlobal.team_info,
      img: [appGlobal.team_info[0].headImg,appGlobal.team_info[1].headImg,appGlobal.team_info[2].headImg,appGlobal.team_info[3].headImg,appGlobal.team_info[4].headImg,appGlobal.team_info[5].headImg,appGlobal.team_info[6].headImg,appGlobal.team_info[7].headImg],
      name: [appGlobal.team_info[0].headImg,appGlobal.team_info[1].name,appGlobal.team_info[2].name,appGlobal.team_info[3].headImg,appGlobal.team_info[4].headImg,appGlobal.team_info[5].headImg,appGlobal.team_info[6].name,appGlobal.team_info[7].name],
    })
    console.log(this.state.team_info[0].headImg)
  }
  getColor(percentage){
    if(parseInt(percentage)>85){
      return "success"
    } else if(parseInt(percentage)>60){
      return "warning"
    } else {return "danger"}
  }
  render() {
    return (
      <Card>
        <CardHeader>
          <CardTitle>赛事得分榜</CardTitle>
        </CardHeader>
        <Table
          responsive
          className="dashboard-table table-hover-animation mb-0 mt-1"
        >
          <thead>
            <tr>
              <th>辩方</th>
              <th>胜负</th>
              <th>队内辩手</th>
              <th>专业度得分</th>
              <th>赛场表现得分</th>
              <th>综合得分</th>
              <th>发布日期</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>正方</td>
              <td>
                <div
                  className={"bg-" + this.state.posi.color}
                  style={{
                    height: "10px",
                    width: "10px",
                    borderRadius: "50%",
                    display: "inline-block",
                    marginRight: "5px"
                  }}
                />
                <span>{this.state.posi.status}</span>
              </td>
              <td className="p-1">
                <ul className="list-unstyled users-list m-0 d-flex">
                  <li className="avatar pull-up">
                    <img
                      src={this.state.img[0]}
                      alt="avatar"
                      height="30"
                      width="30"
                      id="avatar1"
                    />
                    <UncontrolledTooltip placement="bottom" target="avatar1">
                    {this.state.name[0]}
                    </UncontrolledTooltip>
                  </li>
                  <li className="avatar pull-up">
                    <img
                      src={this.state.img[1]}
                      alt="avatar"
                      height="30"
                      width="30"
                      id="avatar2"
                    />
                    <UncontrolledTooltip placement="bottom" target="avatar2">
                    {this.state.name[1]}
                    </UncontrolledTooltip>
                  </li>
                  <li className="avatar pull-up">
                    <img
                      src={this.state.img[2]}
                      alt="avatar"
                      height="30"
                      width="30"
                      id="avatar3"
                    />
                    <UncontrolledTooltip placement="bottom" target="avatar3">
                    {this.state.name[2]}
                    </UncontrolledTooltip>
                  </li>
                  <li className="avatar pull-up">
                    <img
                      src={this.state.img[3]}
                      alt="avatar"
                      height="30"
                      width="30"
                      id="avatar4"
                    />
                    <UncontrolledTooltip placement="bottom" target="avatar4">
                    {this.state.name[3]}
                    </UncontrolledTooltip>
                  </li>
                </ul>
              </td>
              <td>
                <span>{this.state.posi.smart_grade}</span>
                <Progress className="mb-0 mt-1" color={this.getColor(this.state.posi.smart_grade)} value={this.state.posi.smart_grade} />
              </td>
              <td>
                <span>{this.state.posi.prof_grade}</span>
                <Progress className="mb-0 mt-1" color={this.getColor(this.state.posi.release_time)} value={this.state.posi.prof_grade} />
              </td>
              <td>
                <span>{this.state.posi.final_grade}</span>
                <Progress className="mb-0 mt-1" color={this.getColor(this.state.posi.release_time)} value={this.state.posi.final_grade} />
              </td>
              <td>{this.state.posi.release_time}</td>
            </tr>
            <tr>
              <td>反方</td>
              <td>
                <div
                  className={"bg-" + this.state.neg.color}
                  style={{
                    height: "10px",
                    width: "10px",
                    borderRadius: "50%",
                    display: "inline-block",
                    marginRight: "5px"
                  }}
                />
                <span>{this.state.neg.status}</span>
              </td>
              <td className="p-1">
                <ul className="list-unstyled users-list m-0 d-flex">
                  <li className="avatar pull-up">
                    <img
                      src={this.state.img[4]}
                      alt="avatar"
                      height="30"
                      width="30"
                      id="avatar1"
                    />
                    <UncontrolledTooltip placement="bottom" target="avatar1">
                    {this.state.name[4]}
                    </UncontrolledTooltip>
                  </li>
                  <li className="avatar pull-up">
                    <img
                      src={this.state.img[5]}
                      alt="avatar"
                      height="30"
                      width="30"
                      id="avatar2"
                    />
                    <UncontrolledTooltip placement="bottom" target="avatar2">
                    {this.state.name[5]}
                    </UncontrolledTooltip>
                  </li>
                  <li className="avatar pull-up">
                    <img
                      src={this.state.img[6]}
                      alt="avatar"
                      height="30"
                      width="30"
                      id="avatar3"
                    />
                    <UncontrolledTooltip placement="bottom" target="avatar3">
                    {this.state.name[6]}
                    </UncontrolledTooltip>
                  </li>
                  <li className="avatar pull-up">
                    <img
                      src={this.state.img[7]}
                      alt="avatar"
                      height="30"
                      width="30"
                      id="avatar4"
                    />
                    <UncontrolledTooltip placement="bottom" target="avatar4">
                     {this.state.name[7]}
                    </UncontrolledTooltip>
                  </li>
                </ul>
              </td>
              <td>
                <span>{this.state.neg.smart_grade}</span>
                <Progress className="mb-0 mt-1" color={this.getColor(this.state.neg.smart_grade)} value={this.state.neg.smart_grade} />
              </td>
              <td>
                <span>{this.state.neg.prof_grade}</span>
                <Progress className="mb-0 mt-1" color={this.getColor(this.state.neg.prof_grade)} value={this.state.neg.prof_grade} />
              </td>
              <td>
                <span>{this.state.neg.final_grade}</span>
                <Progress className="mb-0 mt-1" color={this.getColor(this.state.neg.release_time)} value={this.state.neg.final_grade} />
              </td>
              <td>{this.state.neg.release_time}</td>
            </tr>
          </tbody>
        </Table>
      </Card>
    )
  }
}
export default DispatchedOrders
