import React from "react"
import { CustomInput } from "reactstrap"
import { Settings, X } from "react-feather"
import classnames from "classnames"
import PerfectScrollbar from "react-perfect-scrollbar"
import { ContextLayout } from "../../../utility/context/Layout"
import Radio from "../radio/RadioVuexy"
import "../../../assets/scss/components/customizer.scss"

class Customizer extends React.Component {
  state = {
    activeNavbar: this.props.activeNavbar,
    navbarType: null,
    footerType: null,
    menuTheme: null,
    collapsedSidebar: null
  }

  static getDerivedStateFromProps(props, state) {
    if (
      props.navbarType !== state.navbarType ||
      props.footerType !== state.footerType ||
      props.menuTheme !== state.menuTheme ||
      props.activeMode !== state.activeMode ||
      props.sidebarState !== state.collapsedSidebar
    ) {
      return {
        activeNavbar: props.activeNavbar,
        navbarType: props.navbarType,
        footerType: props.footerType,
        menuTheme: props.menuTheme,
        activeMode: props.activeMode,
        collapsedSidebar: props.sidebarState
      }
    }
    // Return null if the state hasn't changed
    return null
  }

  handleNavbarChange = color => {
    this.props.changeNavbar(color)
    this.setState({
      activeNavbar: color
    })
  }

  componentDidMount() {
    this.setState({
      navbarType: this.props.navbarType,
      footerType: this.props.footerType,
      menuTheme: this.props.menuTheme,
      activeMode: this.props.activeMode,
      collapseSidebar: this.props.sidebarState
    })
  }

  render() {
    const {
      activeNavbar,
      navbarType,
      footerType,
      menuTheme,
      activeMode,
      collapsedSidebar
    } = this.state
    return (
      <ContextLayout.Consumer>
        {context => {
          return (
            <div
              className={classnames("customizer d-none d-md-block", {
                open: this.props.customizerState === true
              })}
            >
              <div
                className="customizer-toggle"
                onClick={() =>
                  this.props.handleCustomizer(!this.props.customizerState)
                }
              >
                <Settings className="open-icon" size={15} />
              </div>
              <div className="header d-flex justify-content-between px-2 pt-2">
                <div className="title">
                  <h4 className="text-uppercase mb-0">Theme Customizer</h4>
                  <small>Customize & Preview in Real Time</small>
                </div>
                <div
                  className="close-icon cursor-pointer"
                  onClick={() => this.props.handleCustomizer(false)}
                >
                  <X size={24} />
                </div>
              </div>
              <hr />
              <PerfectScrollbar
                options={{
                  wheelPropagation: false
                }}
                className="customizer-content p-2"
              >
                <div className="menu-theme-colors">
                  <h5>Menu Colors</h5>
                  <ul className="list-inline unstyled-list">
                    <li
                      className={classnames("color-box bg-primary", {
                        selected: menuTheme === "primary" ||  !["primary", "danger", "info", "warning", "dark", "success"].includes(menuTheme)
                      })}
                      onClick={() => this.props.changeMenuTheme("primary")}
                    />
                    <li
                      className={classnames("color-box bg-success", {
                        selected: menuTheme === "success"
                      })}
                      onClick={() => this.props.changeMenuTheme("success")}
                    />
                    <li
                      className={classnames("color-box bg-danger", {
                        selected: menuTheme === "danger"
                      })}
                      onClick={() => this.props.changeMenuTheme("danger")}
                    />
                    <li
                      className={classnames("color-box bg-info", {
                        selected: menuTheme === "info"
                      })}
                      onClick={() => this.props.changeMenuTheme("info")}
                    />
                    <li
                      className={classnames("color-box bg-warning", {
                        selected: menuTheme === "warning"
                      })}
                      onClick={() => this.props.changeMenuTheme("warning")}
                    />
                    <li
                      className={classnames("color-box bg-dark", {
                        selected: menuTheme === "dark"
                      })}
                      onClick={() => this.props.changeMenuTheme("dark")}
                    />
                  </ul>
                </div>
                <hr />
                <div className="theme-layout">
                  <h5 className="mt-1">Theme Layout</h5>
                  <div className="d-inline-block mr-1">
                    <Radio
                      label="Vertical"
                      color="primary"
                      checked={context.state.activeLayout === "vertical" || !["vertical" , "horizontal"].includes(context.state.activeLayout)}
                      name="themeMode"
                      onChange={() => context.switchLayout("vertical")}
                    />
                  </div>
                  <div className="d-inline-block">
                    <Radio
                      label="Horizontal"
                      color="primary"
                      checked={context.state.activeLayout === "horizontal"}
                      name="themeMode"
                      onChange={() => context.switchLayout("horizontal")}
                    />
                  </div>
                </div>
                <hr />
                <div className="theme-mode">
                  <h5 className="mt-1">Theme Mode</h5>
                  <div className="d-inline-block mr-1">
                    <Radio
                      label="Light"
                      color="primary"
                      checked={activeMode === "light" || !["light", "dark", "semi-dark"].includes(activeMode) ? true : false}
                      name="themeLayout"
                      onChange={() => this.props.changeMode("light")}
                    />
                  </div>
                  <div className="d-inline-block mr-1">
                    <Radio
                      label="Dark"
                      color="primary"
                      checked={activeMode === "dark" ? true : false}
                      name="themeLayout"
                      onChange={() => this.props.changeMode("dark")}
                    />
                  </div>
                  {context.state.activeLayout !== "horizontal" && 
                    <div className="d-inline-block">
                      <Radio
                        label="Semi Dark"
                        color="primary"
                        checked={activeMode === "semi-dark" ? true : false}
                        name="themeLayout"
                        onChange={() => this.props.changeMode("semi-dark")}
                      />
                    </div>
                  }
                </div>
                { context.state.activeLayout !== "horizontal" && <React.Fragment>
                    <hr />
                    <div className="collapse-sidebar d-flex justify-content-between align-items-center">
                      <h5 className="pt-25">Collapse Sidebar</h5>
                      <CustomInput
                        type="switch"
                        id="collapseSidebar"
                        name="collapseSidebar"
                        checked={collapsedSidebar}
                        onChange={() => {
                          this.props.collapseSidebar(!collapsedSidebar)
                        }}
                        inline
                      />
                    </div>
                  </React.Fragment>
                }
                <hr />
                {context.state.activeLayout === "vertical" && (
                  <React.Fragment>
                    <div className="navbar-colors">
                      <h5>Navbar Colors</h5>
                      <ul className="list-inline unstyled-list">
                        <li
                          className={classnames("color-box bg-default border", {
                            selected:
                              activeNavbar === "default" ||
                              navbarType === "static" || 
                              !["default", "primary", "danger", "info", "warning", "dark", "success"].includes(activeNavbar),
                            disabled: navbarType === "static"
                          })}
                          onClick={() => this.handleNavbarChange("default")}
                        />
                        <li
                          className={classnames("color-box bg-primary", {
                            selected:
                              activeNavbar === "primary" &&
                              navbarType !== "static",
                            disabled: navbarType === "static"
                          })}
                          onClick={() => this.handleNavbarChange("primary")}
                        />
                        <li
                          className={classnames("color-box bg-success", {
                            selected:
                              activeNavbar === "success" &&
                              this.props.navbarType !== "static",
                            disabled: navbarType === "static"
                          })}
                          onClick={() => this.handleNavbarChange("success")}
                        />
                        <li
                          className={classnames("color-box bg-danger", {
                            selected:
                              activeNavbar === "danger" &&
                              this.props.navbarType !== "static",
                            disabled: navbarType === "static"
                          })}
                          onClick={() => this.handleNavbarChange("danger")}
                        />
                        <li
                          className={classnames("color-box bg-info", {
                            selected:
                              activeNavbar === "info" &&
                              this.props.navbarType !== "static",
                            disabled: navbarType === "static"
                          })}
                          onClick={() => this.handleNavbarChange("info")}
                        />
                        <li
                          className={classnames("color-box bg-warning", {
                            selected:
                              activeNavbar === "warning" &&
                              this.props.navbarType !== "static",
                            disabled: navbarType === "static"
                          })}
                          onClick={() => this.handleNavbarChange("warning")}
                        />
                        <li
                          className={classnames("color-box bg-dark", {
                            selected:
                              activeNavbar === "dark" &&
                              this.props.navbarType !== "static",
                            disabled: navbarType === "static"
                          })}
                          onClick={() => this.handleNavbarChange("dark")}
                        />
                      </ul>
                    </div>
                    <hr />
                  </React.Fragment>
                )}
                <div className="navbar-type">
                  <h5>Navbar Type</h5>
                  { context.state.activeLayout !== "horizontal" && 
                    <div className="d-inline-block mr-1">
                      <Radio
                        label="Hidden"
                        color="primary"
                        checked={navbarType === "hidden" ? true : false}
                        name="navbarType"
                        onChange={() => this.props.changeNavbarType("hidden")}
                      />
                    </div>                  
                  }
                  <div className="d-inline-block mr-1">
                    <Radio
                      label="Static"
                      color="primary"
                      checked={navbarType === "static" ? true : false}
                      name="navbarType"
                      onChange={() => this.props.changeNavbarType("static")}
                    />
                  </div>
                  <div className="d-inline-block mr-1">
                    <Radio
                      label="Sticky"
                      color="primary"
                      checked={navbarType === "sticky" ? true : false}
                      name="navbarType"
                      onChange={() => this.props.changeNavbarType("sticky")}
                    />
                  </div>
                  <div className="d-inline-block">
                    <Radio
                      label="Floating"
                      color="primary"
                      checked={navbarType === "floating" || !["floating", "hidden", "static", "sticky"].includes(navbarType) ? true : false}
                      name="navbarType"
                      onChange={() => this.props.changeNavbarType("floating")}
                    />
                  </div>
                </div>
                <hr />
                <div className="footer-type">
                  <h5>Footer Type</h5>
                  <div className="d-inline-block mr-1">
                    <Radio
                      label="Hidden"
                      color="primary"
                      checked={footerType === "hidden" ? true : false}
                      name="footerType"
                      onChange={() => this.props.changeFooterType("hidden")}
                    />
                  </div>
                  <div className="d-inline-block mr-1">
                    <Radio
                      label="Static"
                      color="primary"
                      checked={footerType === "static" ? true : false}
                      name="footerType"
                      onChange={() => this.props.changeFooterType("static")}
                    />
                  </div>
                  <div className="d-inline-block">
                    <Radio
                      label="Sticky"
                      color="primary"
                      checked={footerType === "sticky" ? true : false}
                      name="footerType"
                      onChange={() => this.props.changeFooterType("sticky")}
                    />
                  </div>
                </div>
                <hr />
                <div className="d-flex justify-content-between align-items-center">
                  <h5 className="pt-25">RTL</h5>
                  <CustomInput
                    type="switch"
                    id="rtl-version"
                    name="rtl-version"
                    inline
                    checked={context.state.direction === "rtl"}
                    onChange={() => {
                      if (context.state.direction === "rtl") {
                        context.switchDir("ltr")
                      } else {
                        context.switchDir("rtl")
                      }
                    }}
                  />
                </div>
                <hr />
                <div className="scroll-to-top d-flex justify-content-between align-items-center">
                  <h5 className="pt-25">Hide Scroll To Top</h5>
                  <CustomInput
                    type="switch"
                    id="scrollToTop"
                    name="scrollToTop"
                    inline
                    onClick={() =>
                      this.props.hideScrollToTop(!this.props.scrollToTop)
                    }
                  />
                </div>
              </PerfectScrollbar>
            </div>
          )
        }}
      </ContextLayout.Consumer>
    )
  }
}

export default Customizer
