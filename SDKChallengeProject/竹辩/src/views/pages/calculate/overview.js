import React from "react"
import { Row, Col } from "reactstrap"
import PerformanceCard from "./PerformanceCard"
import AICurve from "./AICurve"
import RefereeCurve from "./RefereeCurve"
import AvgSession from "./AvgSessions"
import DispatchedOrders from "./DispatchedOrders"
import GoalOverview from "./GoalOverview"
import "../../../assets/scss/pages/dashboard-analytics.scss"
import yesapi from "../../../webServices/yes3";
import appGlobal from "./config.js";
let $primary = "#7367F0",
  $stroke_color = "#e8e8e8",
  $label_color = "#e7eef7",
  $success = "#28C76F"




class AnalyticsDashboard extends React.Component {
  
  async componentWillMount() {}

  render() {
    return (
      <React.Fragment>
        <Row className="match-height">
          <Col lg="6" md="12">
            <PerformanceCard />
          </Col>
          <Col lg="3" md="6" sm="12">
            <AICurve />
          </Col>
          <Col lg="3" md="6" sm="12">
            <RefereeCurve />
          </Col>
        </Row>
        <Row className="match-height">
          <Col md="8" sm="12">
            <AvgSession labelColor={$label_color} primary={$primary} />
          </Col>
          <Col lg="4" md="6" sm="12">
            <GoalOverview strokeColor={$stroke_color} success={$success} />
          </Col>
        </Row>
        <Row>
          <Col sm="12">
            <DispatchedOrders />
          </Col>
        </Row>
      </React.Fragment>
    )
  }
}

export default AnalyticsDashboard
