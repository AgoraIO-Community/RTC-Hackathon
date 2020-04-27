import React from "react"
import VerticalLayout from "../../layouts/VerticalLayout"
import FullLayout from "../../layouts/FullpageLayout"
import HorizontalLayout from "../../layouts/HorizontalLayout"
import themeConfig from "../../configs/themeConfig"
const layouts = {
  vertical: VerticalLayout,
  full: FullLayout,
  horizontal: HorizontalLayout
}

const ContextLayout = React.createContext()

class Layout extends React.Component {
  state = {
    activeLayout: themeConfig.layout,
    width: window.innerWidth,
    lastLayout: null,
    direction: themeConfig.direction
  }

  updateWidth = () => {
    this.setState({
      width: window.innerWidth
    })
  }

  handleWindowResize = () => {
    this.updateWidth()
    if (this.state.activeLayout === "horizontal" && this.state.width <= 1199) {
      this.setState({
        activeLayout: "vertical",
        lastLayout: "horizontal"
      })
    }

    if (this.state.lastLayout === "horizontal" && this.state.width >= 1199) {
      this.setState({
        activeLayout: "horizontal",
        lastLayout: "vertical"
      })
    }
  }

  componentDidMount = () => {
    if (window !== "undefined") {
      window.addEventListener("resize", this.handleWindowResize)
    }
    this.handleDirUpdate()
    if (this.state.activeLayout === "horizontal" && this.state.width <= 1199) {
      this.setState({
        activeLayout: "vertical"
      })
    } else if (
      themeConfig.layout === "horizontal" &&
      this.state.width >= 1200
    ) {
      this.setState({
        activeLayout: "horizontal"
      })
    } else {
      this.setState({
        activeLayout: "vertical"
      })
    }
  }

  componentDidUpdate() {
    this.handleDirUpdate()
  }

  handleDirUpdate = () => {
    let dir = this.state.direction
    if (dir === "rtl")
      document.getElementsByTagName("html")[0].setAttribute("dir", "rtl")
    else document.getElementsByTagName("html")[0].setAttribute("dir", "ltr")
  }



  render() {
    const { children } = this.props
    return (
      <ContextLayout.Provider
        value={{
          state: this.state,
          fullLayout: layouts["full"],
          VerticalLayout: layouts["vertical"],
          horizontalLayout: layouts["horizontal"],
          switchLayout: layout => {
            this.setState({ activeLayout: layout })
          },
          switchDir: dir => {
            this.setState({ direction: dir })
          }
        }}
      >
        {children}
      </ContextLayout.Provider>
    )
  }
}

export { Layout, ContextLayout }
