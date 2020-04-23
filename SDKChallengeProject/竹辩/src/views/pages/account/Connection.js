import React from "react"
import { Button, Row, Col } from "reactstrap"

class Connection extends React.Component {
  render() {
    return (
      <React.Fragment>
        <Row>
          <Col sm="12">
            <div className="d-flex flex-wrap justify-content-between align-items-center mb-3">
              <div className="social-media">
                <p className="mb-0">Account is connected with Google.</p>
                <p className="text-bold-500">john@gmail.com</p>
              </div>
              <div className="disconnect">
                <Button.Ripple color="danger" outline>
                  Disconnect
                </Button.Ripple>
              </div>
            </div>
          </Col>
          <Col sm="12">
            <div className="d-flex flex-wrap justify-content-between align-items-center mb-3">
              <div className="social-media">
                <p className="mb-0">Account is connected with Facebook.</p>
                <p className="text-bold-500">@pixinvents</p>
              </div>
              <div className="disconnect">
                <Button.Ripple color="danger" outline>
                  Disconnect
                </Button.Ripple>
              </div>
            </div>
          </Col>
          <Col sm="12">
            <Button.Ripple color="info">Connect to Twitter</Button.Ripple>
          </Col>
          <Col sm="12">
            <Button.Ripple className="mt-2" color="primary">
              Connect to Instagram
            </Button.Ripple>
          </Col>
        </Row>
      </React.Fragment>
    )
  }
}
export default Connection
