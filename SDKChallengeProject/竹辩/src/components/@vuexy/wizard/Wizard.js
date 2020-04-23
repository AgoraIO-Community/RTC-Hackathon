import React from "react"
import BreadCrumbs from "../../@vuexy/breadCrumbs/BreadCrumb"
import {Row, Col} from "reactstrap" 
import WizardBasic from "./WizardBasic"
import WizardIcons from "./WizardIcons"
import WizardValidation from "./WizardValidation"
class Wizard extends React.Component {
  render() {
    return (
      <React.Fragment>
        <BreadCrumbs
          breadCrumbTitle="Wizard"
          breadCrumbParent="Extra Components"
          breadCrumbActive="Wizard"
        />
        <Row>
          <Col sm="12">
            <WizardBasic />
          </Col>
          <Col sm="12">
            <WizardIcons />
          </Col>
          <Col sm="12">
            <WizardValidation />
          </Col>
        </Row>
      </React.Fragment>
    )
  }
}
export default Wizard
