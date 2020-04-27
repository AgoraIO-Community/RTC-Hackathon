import React from "react"
import {
  TabContent,
  TabPane,
  Nav,
  NavItem,
  NavLink,
  Card,
  CardBody
} from "reactstrap"
import classnames from "classnames"
import { Settings, Lock, Eye, Voicemail } from "react-feather"
import GeneralTab from "./General"
import ChangePassword from "./ChangePassword"
import InfoTab from "./bioInfo"
import VoiceTab from "./voiceInfo"
import Breadcrumbs from "../../../components/@vuexy/breadCrumbs/BreadCrumb"
import "../../../assets/scss/pages/account-settings.scss"
import {stopStream} from '../../../components/WebCamera/index';

class AccountSettings extends React.Component {
  state = {
    activeTab: "1",
    windowWidth: null
  }

  toggle = tab => {
    this.setState({
      activeTab: tab
    })
  }

  updateWidth = () => {
    this.setState({ windowWidth  : window.innerWidth })
  }

  componentDidMount() {
    document.title = "个人信息 | 云辩论"
    if(window !== undefined){
      this.updateWidth()
      window.addEventListener("resize", this.updateWidth)
    }
  }
  componentWillUnmount() {
    stopStream()
  }


  render() {
    let {windowWidth} = this.state
    return (
      <React.Fragment>
        <Breadcrumbs
          breadCrumbTitle="个人信息"
          breadCrumbParent="个人面板"
          breadCrumbActive="个人信息"
        />
        <div className={`${windowWidth >= 769 ? "nav-vertical" : "account-setting-wrapper"}`}>
          <Nav className="account-settings-tab nav-left mr-0 mr-sm-3" tabs>
            <NavItem>
              <NavLink
                className={classnames({
                  active: this.state.activeTab === "1"
                })}
                onClick={() => {
                  this.toggle("1")
                }}
              >
                <Settings size={16} />
                <span className="d-md-inline-block d-none align-middle ml-1">基本信息</span>
              </NavLink>
            </NavItem>
            <NavItem>
              <NavLink
                className={classnames({
                  active: this.state.activeTab === "2"
                })}
                onClick={() => {
                  this.toggle("2")
                }}
              >
                <Lock size={16} />
                <span className="d-md-inline-block d-none align-middle ml-1">修改密码</span>
              </NavLink>
            </NavItem>
            <NavItem>
              <NavLink
                className={classnames({
                  active: this.state.activeTab === "3"
                })}
                onClick={() => {
                  this.toggle("3")
                }}
              >
                <Voicemail size={16} />
                <span className="d-md-inline-block d-none align-middle ml-1">声纹录入</span>
              </NavLink>
            </NavItem>
            <NavItem>
              <NavLink
                className={classnames({
                  active: this.state.activeTab === "4"
                })}
                onClick={() => {
                  this.toggle("4")
                }}
              >
                <Eye size={16} />
                <span className="d-md-inline-block d-none align-middle ml-1">面部信息录入</span>
              </NavLink>
            </NavItem>
          </Nav>
                 <Card>
                 <CardBody>
              <TabContent activeTab={this.state.activeTab}>
                <TabPane tabId="1">
                  <GeneralTab />
                </TabPane>
                <TabPane tabId="2">
                  <ChangePassword />
                </TabPane>
                <TabPane tabId="3">
                  <VoiceTab />
                </TabPane>
                <TabPane tabId="4">
                 <InfoTab />
                </TabPane>
              </TabContent>
              </CardBody>
                </Card>
        </div>
      </React.Fragment>
    )
  }
}

export default AccountSettings
