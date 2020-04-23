import React from "react"
import {
  Card,
  CardHeader,
  CardBody,
  CardTitle,
  TabContent,
  TabPane,
  Nav,
  NavItem,
  NavLink
} from "reactstrap"
import classnames from "classnames"
import {
  Eye,
  Code,
  ArrowDown,
  Star,
  Check,
  XCircle,
  Clock
} from "react-feather"
import { dividerIcon } from "./DividerSourceCode"

class DividerIcon extends React.Component {
  state = {
    activeTab: "1"
  }

  toggleTab = tab => {
    if (this.state.activeTab !== tab) {
      this.setState({ activeTab: tab })
    }
  }

  render() {
    return (
      <React.Fragment>
        <Card>
          <CardHeader>
            <CardTitle>Icons</CardTitle>
            <div className="views">
              <Nav tabs>
                <NavItem>
                  <NavLink
                    className={classnames({
                      active: this.state.activeTab === "1"
                    })}
                    onClick={() => {
                      this.toggleTab("1")
                    }}
                  >
                    <Eye size={15} />
                  </NavLink>
                </NavItem>
                <NavItem>
                  <NavLink
                    className={classnames({
                      active: this.state.activeTab === "2"
                    })}
                    onClick={() => {
                      this.toggleTab("2")
                    }}
                  >
                    <Code size={15} />
                  </NavLink>
                </NavItem>
              </Nav>
            </div>
          </CardHeader>
          <CardBody>
            <TabContent activeTab={this.state.activeTab}>
              <TabPane tabId="1">
                <div className="divider divider-left">
                  <div className="divider-text">
                    <ArrowDown />
                  </div>
                </div>

                <div className="divider divider-left-center">
                  <div className="divider-text">
                    <Star />
                  </div>
                </div>

                <div className="divider">
                  <div className="divider-text">
                    <Check />
                  </div>
                </div>

                <div className="divider divider-right-center">
                  <div className="divider-text">
                    <XCircle />
                  </div>
                </div>

                <div className="divider divider-right">
                  <div className="divider-text">
                    <Clock />
                  </div>
                </div>
              </TabPane>
               <TabPane className="component-code" tabId="2">{dividerIcon}</TabPane>
            </TabContent>
          </CardBody>
        </Card>
      </React.Fragment>
    )
  }
}
export default DividerIcon
