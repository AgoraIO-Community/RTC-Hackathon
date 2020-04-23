import React from "react"
import Wizard from "./WizardComponent"
import { AvForm, AvInput, AvGroup, AvFeedback, AvField } from "availity-reactstrap-validation"
import {
  Label,
  Row,
  Col,
  Card,
  CardBody,
  CardTitle,
  CardHeader,
  Button
} from "reactstrap"
import Checkbox from "../checkbox/CheckboxesVuexy"
import { Check } from "react-feather"
class WizardValidation extends React.Component {
  state = {
    activeStep: 0,
    steps: {
      step_1: (
        <AvForm className="form-horizontal" onSubmit={(e, errors) => this.handleActiveStep(1, errors)}>
          <Row>
            <Col md="6" sm="12">
              <AvGroup>
                <Label> First Name </Label>
                <AvInput name="first-name" type="text" required/>
                <AvFeedback>Please enter valid First Name</AvFeedback>
              </AvGroup>
            </Col>
            <Col md="6" sm="12">
              <AvGroup>
                <Label> Last Name </Label>
                <AvInput name="last-name" type="text" required />
                <AvFeedback>Please enter valid Last Name</AvFeedback>
              </AvGroup>
            </Col>
            <Col md="6" sm="12">
            <AvGroup>
                <Label> Email </Label>
                <AvInput name="last-name" type="email" required/>
                <AvFeedback>Please enter valid email</AvFeedback>
              </AvGroup>
            </Col>
            <Col md="6" sm="12">
            <AvGroup>
                <AvField type="select" name="city-name" label="City Name" required>
                    <option selected>Select City</option>
                    <option>New York</option>
                    <option>Chicago</option>
                    <option>San Francisco</option>
                    <option>Boston</option>
                </AvField>
              </AvGroup>
            </Col>
          </Row>
          <div className="wizard-actions d-flex justify-content-between">
            <Button color="primary" disabled>
              Prev
            </Button>
            <Button color="primary">
              Next
            </Button>
          </div>
        </AvForm>
      ),
      step_2: (
        <AvForm className="form-horizontal" onSubmit={(e, errors) => this.handleActiveStep(2, errors)}>
          <Row>
            <Col md="6" sm="12">
              <AvGroup>
                <Label> Proposal Title </Label>
                <AvInput name="Proposal-Title" type="text" required/>
                <AvFeedback>Please enter valid Title</AvFeedback>
              </AvGroup>
              <AvGroup>
                <Label> Job Title </Label>
                <AvInput name="Job-Title" type="text" required/>
                <AvFeedback>Please enter valid Job Title</AvFeedback>
              </AvGroup>
            </Col>
            <Col md="6" sm="12">
              <AvGroup>
                <Label> Proposal Description </Label>
                <AvInput name="description" type="textarea" rows="5" required/>
                <AvFeedback>Please enter valid Description</AvFeedback>
              </AvGroup>
            </Col>
          </Row>
          <div className="wizard-actions d-flex justify-content-between">
            <Button color="primary" onClick={() => this.handleActiveStep(0)}>
              Prev
            </Button>
            <Button color="primary">
              Next
            </Button>
          </div>
        </AvForm>
      ),
      step_3: (
        <AvForm className="form-horizontal" onSubmit={(e, errors) => errors.length === 0 && alert("Form Submitted")}>
          <Row>
            <Col md="6" sm="12">
              <AvGroup>
                <Label> Event Name </Label>
                <AvInput name="Event-Name" type="text" required/>
                <AvFeedback>Event Name</AvFeedback>
              </AvGroup>
            </Col>
            <Col md="6" sm="12">
              <AvGroup>
                <AvField type="select" name="city-name" label="City Name" required>
                  <option selected>Select City</option>
                  <option>New York</option>
                  <option>Chicago</option>
                  <option>San Francisco</option>
                  <option>Boston</option>
                </AvField>
              </AvGroup>
            </Col>
            <Col md="6" sm="12">
              <AvGroup>
                <AvField type="select" name="status" label="Event Status" required>
                  <option>Planning</option>
                  <option>In Process</option>
                  <option>Finished</option>
                </AvField>
              </AvGroup>
            </Col>
            <Col md="6" sm="12">
              <AvGroup>
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
              </AvGroup>
            </Col>
          </Row>
          <div className="wizard-actions d-flex justify-content-between">
            <Button color="primary" onClick={() => this.handleActiveStep(1)}>
              Prev
            </Button>
            <Button color="primary">
              Submit
            </Button>
          </div>
        </AvForm>
      )
    }
  }

  handleActiveStep = (step, errors) => {
   if(errors.length <= 0){
     this.setState({
       activeStep: step
     })
   }
  }

  render() {
    const { steps } = this.state
    return (
      <Card>
        <CardHeader>
          <CardTitle>Wizard with Validation</CardTitle>
        </CardHeader>
        <CardBody>
          <Wizard
            activeStep={this.state.activeStep}
            stepsTitle={[1, 2, 3]}
            stepsContent={[steps.step_1, steps.step_2, steps.step_3]}
          ></Wizard>
        </CardBody>
      </Card>
    )
  }
}

export default WizardValidation
