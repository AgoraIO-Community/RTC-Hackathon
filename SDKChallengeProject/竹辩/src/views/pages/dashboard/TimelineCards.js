import React from "react"
import { Card, CardHeader, CardTitle, CardBody, Row, Col } from "reactstrap"
import { Plus, AlertCircle, Check } from "react-feather"

class TimelineCards extends React.Component {
  render() {
    return (
      <>
          <Card>
            <CardHeader>
              <CardTitle>Left Timeline</CardTitle>
            </CardHeader>
            <CardBody>
              <ul className="activity-timeline timeline-left list-unstyled">
                <li>
                  <div className="timeline-icon bg-primary">
                    <Plus size="18" />
                  </div>
                  <div className="timeline-info">
                    <p className="font-weight-bold">Task Added</p>
                    <span>
                      Bonbon macaroon jelly beans gummi bears jelly lollipop
                      apple
                    </span>
                  </div>
                  <small className="">25 days ago</small>
                </li>
                <li>
                  <div className="timeline-icon bg-warning">
                    <AlertCircle size="18" />
                  </div>
                  <div className="timeline-info">
                    <p className="font-weight-bold">Task Updated</p>
                    <span>Cupcake gummi bears soufflé caramels candy</span>
                  </div>
                  <small className="">15 days ago</small>
                </li>
                <li>
                  <div className="timeline-icon bg-success">
                    <Check size="18" />
                  </div>
                  <div className="timeline-info">
                    <p className="font-weight-bold">Task Completed</p>
                    <span>Candy ice cream cake. Halvah gummi bears</span>
                  </div>
                  <small className="">20 minutes ago</small>
                </li>
              </ul>
            </CardBody>
          </Card>
      </>
    )
  }
}
export default TimelineCards
