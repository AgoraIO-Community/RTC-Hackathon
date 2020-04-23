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
import { dividerPosition } from "./DividerSourceCode"

class DividerPosition extends React.Component {
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
            <CardTitle>Text Position</CardTitle>
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
              Use class{" "}
              <code>.divider-[left | left-center | right | right-center]</code>{" "}
              with <code>.divider</code> to set text position.
            </p>
            <TabContent activeTab={this.state.activeTab}>
              <TabPane tabId="1">
                <div className="divider divider-left">
                  <div className="divider-text">Left</div>
                </div>

                <div className="divider divider-left-center">
                  <div className="divider-text">Left Center</div>
                </div>

                <div className="divider">
                  <div className="divider-text">Center(Default)</div>
                </div>

                <div className="divider divider-right-center">
                  <div className="divider-text">Right Center</div>
                </div>

                <div className="divider divider-right">
                  <div className="divider-text">Right</div>
                </div>
              </TabPane>
               <TabPane className="component-code" tabId="2">{dividerPosition}</TabPane>
            </TabContent>
          </CardBody>
        </Card>
      </React.Fragment>
    )
  }
}
export default DividerPosition
