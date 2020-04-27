import React from "react"
import { Card, CardHeader, CardTitle, CardBody } from "reactstrap"
import { Plus, AlertCircle, Check } from "react-feather"

class ActivityTimeline extends React.Component {
  render() {
    return (
      <Card>
        <CardHeader>
          <CardTitle>Activity Timeline</CardTitle>
        </CardHeader>
        <CardBody>
          <ul className="activity-timeline timeline-left list-unstyled">
            <li>
              <div className="timeline-icon bg-primary">
                <Plus size={16} />
              </div>
              <div className="timeline-info">
                <p className="font-weight-bold mb-0">Client Meeting</p>
                <span className="font-small-3">
                  Bonbon macaroon jelly beans gummi bears jelly lollipop apple
                </span>
              </div>
              <small className="text-muted">25 mins ago</small>
            </li>
            <li>
              <div className="timeline-icon bg-warning">
                <AlertCircle size={16} />
              </div>
              <div className="timeline-info">
                <p className="font-weight-bold mb-0">Email Newsletter</p>
                <span className="font-small-3">
                  Cupcake gummi bears soufflé caramels candy
                </span>
              </div>
              <small className="text-muted">15 days ago</small>
            </li>
            <li>
              <div className="timeline-icon bg-danger">
                <Check size={16} />
              </div>
              <div className="timeline-info">
                <p className="font-weight-bold mb-0">Plan Webinar</p>
                <span className="font-small-3">
                  Candy ice cream cake. Halvah gummi bears
                </span>
              </div>
              <small className="text-muted">20 days ago</small>
            </li>
            <li>
              <div className="timeline-icon bg-success">
                <Check size={16} />
              </div>
              <div className="timeline-info">
                <p className="font-weight-bold mb-0">Launch Website</p>
                <span className="font-small-3">Candy ice cream cake. </span>
              </div>
              <small className="text-muted">25 days ago</small>
            </li>
            <li>
              <div className="timeline-icon bg-primary">
                <Check size={16} />
              </div>
              <div className="timeline-info">
                <p className="font-weight-bold mb-0">Marketing</p>
                <span className="font-small-3">
                  Candy ice cream. Halvah bears Cupcake gummi bears.
                </span>
              </div>
              <small className="text-muted">28 days ago</small>
            </li>
          </ul>
        </CardBody>
      </Card>
    )
  }
}
export default ActivityTimeline
