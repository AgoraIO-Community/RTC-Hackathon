import React from "react"
import { Row, Col } from "reactstrap"
import BreadCrumbs from "../../@vuexy/breadCrumbs/BreadCrumb"
import DividerDefault from "./DividerDefault"
import DividerText from "./DividerText"
import DividerPosition from "./DividerPosition"
import DividerColors from "./DividerColors"
import DividerIcons from "./DividerIcons"
import DividerStyle from "./DividerStyle"
import Prism from "prismjs"
import "prismjs/components/prism-jsx.min"
class Divider extends React.Component {
  componentDidMount() {
    Prism.highlightAll()
  }
  render() {
    return (
      <React.Fragment>
        <BreadCrumbs
          breadCrumbTitle="Divider"
          breadCrumbParent="Extra Components"
          breadCrumbActive="Divider"
        />
        <Row>
          <Col sm="12">
            <DividerDefault />
          </Col>
          <Col sm="12">
            <DividerText />
          </Col>
          <Col sm="12">
            <DividerPosition />
          </Col>
          <Col sm="12">
            <DividerColors />
          </Col>
          <Col sm="12">
            <DividerIcons />
          </Col>
          <Col sm="12">
            <DividerStyle />
          </Col>
        </Row>
      </React.Fragment>
    )
  }
}
export default Divider
