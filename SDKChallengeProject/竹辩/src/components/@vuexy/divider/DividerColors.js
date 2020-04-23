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
import { Eye, Code } from "react-feather"
import { dividerColors } from "./DividerSourceCode"

class DividerColors extends React.Component {
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
            <CardTitle>Colors</CardTitle>
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
            <p>
              Use class <code>.divider-[color-name]</code> to change color of
              divider
            </p>
            <TabContent activeTab={this.state.activeTab}>
              <TabPane tabId="1">
                <div className="divider">
                  <div className="divider-text">Default</div>
                </div>
                <div className="divider divider-primary">
                  <div className="divider-text">Primary</div>
                </div>
                <div className="divider divider-success">
                  <div className="divider-text">Success</div>
                </div>
                <div className="divider divider-danger">
                  <div className="divider-text">Danger</div>
                </div>
                <div className="divider divider-info">
                  <div className="divider-text">Info</div>
                </div>
                <div className="divider divider-warning">
                  <div className="divider-text">Warning</div>
                </div>
                <div className="divider divider-light">
                  <div className="divider-text">Light</div>
                </div>
                <div className="divider divider-dark">
                  <div className="divider-text">Dark</div>
                </div>
              </TabPane>
               <TabPane className="component-code" tabId="2">{dividerColors}</TabPane>
            </TabContent>
          </CardBody>
        </Card>
      </React.Fragment>
    )
  }
}
export default DividerColors
