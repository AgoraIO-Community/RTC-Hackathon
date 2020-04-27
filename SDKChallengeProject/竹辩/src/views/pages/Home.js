import React from "react";
import {
  Row,
  Col,
  Card,
  CardBody,
} from "reactstrap";
import {
  Eye,
  Award,
  CheckCircle,
  Percent,
} from "react-feather";
import "rc-slider/assets/index.css";

import StatisticsCard from "../../components/@vuexy/statisticsCard/StatisticsCard";
import PersonalInfoCard from "./dashboard/PersonalInfoCard";
import { checkIfLogin } from "../../webServices/auth";
import yesapi from "../../webServices/yes3";
import ArticleCarousel from "./dashboard/articleCarousel";
import { connect } from "react-redux"
import {changeRole} from "../../redux/actions/auth/loginActions"
class Home extends React.Component {
  static getDerivedStateFromProps(props, state) {
    if (props.role !== state.role) {
      return {
        role: props.role
      }
    }
    // Return null if the state hasn't changed
    return null
  }

  state = {
    role: this.props.role,
    sliderData: [
      {
        imgUrl:
          "data:image/svg+xml;charset=UTF-8,%3Csvg%20width%3D%22800%22%20height%3D%22400%22%20xmlns%3D%22http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%22%20viewBox%3D%220%200%20800%20400%22%20preserveAspectRatio%3D%22none%22%3E%3Cdefs%3E%3Cstyle%20type%3D%22text%2Fcss%22%3E%23holder_15ba800aa1d%20text%20%7B%20fill%3A%23555%3Bfont-weight%3Anormal%3Bfont-family%3AHelvetica%2C%20monospace%3Bfont-size%3A40pt%20%7D%20%3C%2Fstyle%3E%3C%2Fdefs%3E%3Cg%20id%3D%22holder_15ba800aa1d%22%3E%3Crect%20width%3D%22800%22%20height%3D%22400%22%20fill%3D%22%23777%22%3E%3C%2Frect%3E%3Cg%3E%3Ctext%20x%3D%22285.921875%22%20y%3D%22218.3%22%3EFirst%20slide%3C%2Ftext%3E%3C%2Fg%3E%3C%2Fg%3E%3C%2Fsvg%3E",
        author: "Slide 1",
        caption: "Slide 1",
        href:"#"
      }
    ],
  };
  async componentDidMount() {
    document.title = "个人主页 | 云辩论"
    await checkIfLogin();
    let { data } = await yesapi.table.readPage(
      "article_slider",
      [["id", "<>", -1]],
      "and",
      1,
      4
    );
    this.setState({ sliderData: data.list });
    console.log(data);
    console.log(this.state.role)
  }
  render() {
    return (
      <>
        <Row className="match-height">
          <Col lg="3" sm="6">
            <StatisticsCard
              hideChart
              iconRight
              iconBg="primary"
              icon={<Eye className="primary" size={22} />}
              stat="13场"
              statTitle="比赛"
            />
          </Col>
          <Col lg="3" sm="6">
            <StatisticsCard
              hideChart
              iconRight
              iconBg="success"
              icon={<Award className="success" size={22} />}
              stat="7场"
              statTitle="获胜"
            />
          </Col>
          <Col lg="3" sm="6">
            <StatisticsCard
              hideChart
              iconRight
              iconBg="danger"
              icon={<CheckCircle className="danger" size={22} />}
              stat="4次"
              statTitle="最佳辩手"
            />
          </Col>
          <Col lg="3" sm="6">
            <StatisticsCard
              hideChart
              iconRight
              iconBg="warning"
              icon={<Percent className="warning" size={22} />}
              stat="77%"
              statTitle="胜率"
            />
          </Col>
        </Row>
        <Row className="match-height">
          <Col lg="4" md="12">
            <PersonalInfoCard />
          </Col>
          <Col lg="8" md="12">
            {/* <TimelineCards /> */}
            <Card>
              <CardBody>
                <ArticleCarousel sliderData={this.state.sliderData} />
              </CardBody>
            </Card>
          </Col>
        </Row>
      </>
    );
  }
}
const mapStateToProps = state => {
  return {
    role: state.auth.login.userRole
  }
}
export default connect(mapStateToProps, { changeRole })(Home)