import React from "react"
import Wizard from "./WizardComponent"
import {
  Form,
  FormGroup,
  Input,
  Label,
  CustomInput,
  Row,
  Col,
  Card,
  CardBody,
  CardTitle,
  CardHeader,
  Button
} from "reactstrap"
import Checkbox from "../checkbox/CheckboxesVuexy"
import { Check, Home, Briefcase, Image } from "react-feather"
class WizardIcons extends React.Component {
  state = {
    activeStep: 0,
    steps: {
      step_1: (
        <Form className="form-horizontal">
          <Row>
            <Col md="6" sm="12">
              <FormGroup>
                <Label> First Name </Label>
                <Input type="text" />
              </FormGroup>
            </Col>
            <Col md="6" sm="12">
              <FormGroup>
                <Label> Last Name </Label>
                <Input type="text" />
              </FormGroup>
            </Col>
            <Col md="6" sm="12">
              <FormGroup>
                <Label> Email Name </Label>
                <Input type="email" />
              </FormGroup>
            </Col>
            <Col md="6" sm="12">
              <FormGroup>
                <Label> City Name </Label>
                <CustomInput type="select" name="select" id="city">
                  <option>New York</option>
                  <option>Chicago</option>
                  <option>San Francisco</option>
                  <option>Boston</option>
                </CustomInput>
              </FormGroup>
            </Col>
          </Row>
          <div className="wizard-actions d-flex justify-content-between">
            <Button color="primary" disabled>
              Prev
            </Button>
            <Button color="primary" onClick={() => this.handleActiveStep(1)}>
              Next
            </Button>
          </div>
        </Form>
      ),
      step_2: (
        <Form className="form-horizontal">
          <Row>
            <Col md="6" sm="12">
              <FormGroup>
                <Label> Proposal Title </Label>
                <Input type="text" />
              </FormGroup>
              <FormGroup>
                <Label> Job Title </Label>
                <Input type="text" />
              </FormGroup>
            </Col>
            <Col md="6" sm="12">
              <FormGroup>
                <Label> Proposal Title </Label>
                <Input type="textarea" rows="5" />
              </FormGroup>
            </Col>
          </Row>
          <div className="wizard-actions d-flex justify-content-between">
            <Button color="primary" onClick={() => this.handleActiveStep(0)}>
              Prev
            </Button>
            <Button color="primary" onClick={() => this.handleActiveStep(2)}>
              Next
            </Button>
          </div>
        </Form>
      ),
      step_3: (
        <Form className="form-horizontal">
          <Row>
            <Col md="6" sm="12">
              <FormGroup>
                <Label> Event Name </Label>
                <Input type="text" />
              </FormGroup>
            </Col>
            <Col md="6" sm="12">
              <FormGroup>
                <Label> Event Location </Label>
                <CustomInput type="select" name="select" id="location">
                  <option>New York</option>
                  <option>Chicago</option>
                  <option>San Francisco</option>
                  <option>Boston</option>
                </CustomInput>
              </FormGroup>
            </Col>
            <Col md="6" sm="12">
              <FormGroup>
                <Label> Event Status </Label>
                <CustomInput type="select" name="select" id="status">
                  <option>Planning</option>
                  <option>In Process</option>
                  <option>Finished</option>
                </CustomInput>
              </FormGroup>
            </Col>
            <Col md="6" sm="12">
              <FormGroup>
                <Label> Event Status </Label>
                <Label className="mr-2">Requirements :</Label>
                <div className="stacked-checkbox">
                  <div className="d-inline-block mr-2">
                    <Checkbox
                      color="primary"
                      icon={<Check className="vx-icon" size={16} />}
                      label="Staffing"
                      defaultChecked={false}
                    />
                  </div>
                  <div className="d-inline-block">
                    <Checkbox
                      color="primary"
                      icon={<Check className="vx-icon" size={16} />}
                      label="Catering"
                      defaultChecked={false}
                    />
                  </div>
                </div>
              </FormGroup>
            </Col>
          </Row>
          <div className="wizard-actions d-flex justify-content-between">
            <Button color="primary" onClick={() => this.handleActiveStep(1)}>
              Prev
            </Button>
            <Button color="primary" onClick={() => alert("Form Submitted")}>
              Submit
            </Button>
          </div>
        </Form>
      )
    }
  }

  handleActiveStep = step => {
    this.setState({
      activeStep: step
    })
  }

  render() {
    const { steps } = this.state
    return (
      <Card>
        <CardHeader>
          <CardTitle>Wizard with Icons</CardTitle>
        </CardHeader>
        <CardBody>
          <Wizard
            activeStep={this.state.activeStep}
            stepsTitle={[
              <Home size={20} />,
              <Briefcase size={20} />,
              <Image size={20} />
            ]}
            stepsContent={[steps.step_1, steps.step_2, steps.step_3]}
          ></Wizard>
        </CardBody>
      </Card>
    )
  }
}

export default WizardIcons
