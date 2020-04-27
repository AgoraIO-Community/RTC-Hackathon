import React from "react"
import { Link } from "react-router-dom"
import { Badge } from "reactstrap"
import classnames from "classnames"
import { ChevronRight } from "react-feather"
class SideMenuGroup extends React.Component {
  constructor(props) {
    super(props)
    this.flag = true
    this.parentArray = []
    this.childObj = {}
  }
  state = {
    isOpen: false,
    activeItem: this.props.activePath
  }

  handleActiveItem = url => {
    this.setState({
      activeItem: url
    })
  }

  componentDidUpdate(prevProps, prevState) {
    if (prevProps.activePath !== this.props.activePath) {
      if (this.childObj.navLink && this.childObj.collapsed) {
        this.props.collapsedMenuPaths(this.childObj.navLink)
      }
      if (
        this.props.activePath === this.childObj.navLink &&
        !this.props.parentArr.includes(this.parentArray[0])
      ) {
        this.props.parentArr.splice(0, this.props.parentArr.length)
        this.props.parentArr.push(this.parentArray)
      } else if (this.props.parentArr.includes(this.parentArray)) {
        this.props.parentArr.splice(0, this.props.parentArr.length)
      }
    }
  }

  renderChild(item, activeGroup, handleGroupClick, handleActiveItem, parent) {
    return (
      <ul className="menu-content">
        {item.children
          ? item.children.map(child => {
              const CustomAnchorTag =
                child.type === "external-link" ? `a` : Link
              if (!this.parentArray.includes(item.id) && this.flag) {
                this.parentArray.push(item.id)
              }

              if (child.navlink && child.collapsed) {
                this.props.collapsedMenuPaths(child.navLink)
              }

              if (this.props.activeItemState === child.navLink) {
                this.childObj = child
                this.props.parentArr.push(this.parentArray)
                this.flag = false
              }
              if (
                (child.permissions &&
                  child.permissions.includes(this.props.currentUser)) ||
                child.permissions === undefined
              ) {
                return (
                  <li
                    key={child.id}
                    className={classnames({
                      hover: this.props.hoverIndex === child.id,
                      "has-sub": child.type === "collapse",
                      open:
                        child.type === "collapse" &&
                        activeGroup.includes(child.id),
                      "sidebar-group-active": this.props.currentActiveGroup.includes(
                        child.id
                      ),
                      active:
                        (this.props.activeItemState === child.navLink &&
                        child.type === "item") || (item.parentOf && item.parentOf.includes(this.props.activeItemState)),
                      disabled: child.disabled
                    })}
                    onClick={e => {
                      e.stopPropagation()
                      handleGroupClick(child.id, item.id, child.type)
                      if (child.navLink && child.navLink !== undefined) {
                        handleActiveItem(child.navLink)
                      }
                      if(this.props.deviceWidth <= 1200 && child.type === "item"){
                        this.props.toggleMenu()
                      }
                    }}
                  >
                    <CustomAnchorTag
                    className={classnames({
                      "d-flex justify-content-between" : child.type === "collapse"
                    })}
                      to={
                        child.navLink && child.type === "item"
                          ? child.navLink
                          : ""
                      }
                      href={child.type === "external-link" ? child.navLink : ""}
                      onMouseEnter={() => {
                        this.props.handleSidebarMouseEnter(child.id)
                      }}
                      onMouseLeave={() => {
                        this.props.handleSidebarMouseEnter(child.id)
                      }}
                      key={child.id}
                      onClick={e => {
                        return child.type === "collapse"
                          ? e.preventDefault()
                          : ""
                      }}
                      target={child.newTab ? "_blank" : undefined}
                    >
                      <div className="menu-text">
                        {child.icon}
                        <span className="menu-item menu-title">
                          {child.title}
                        </span>
                      </div>
                      {child.badge ? (
                        <Badge
                          color={child.badge}
                          className="float-right mr-2"
                          pill
                        >
                          {child.badgeText}
                        </Badge>
                      ) : (
                        ""
                      )}
                      {child.type === "collapse" ? (
                        <ChevronRight className="menu-toggle-icon" size={13} />
                      ) : (
                        ""
                      )}
                    </CustomAnchorTag>

                    {child.children
                      ? this.renderChild(
                          child,
                          activeGroup,
                          handleGroupClick,
                          handleActiveItem,
                          item.id
                        )
                      : ""}
                  </li>
                )
              } else if (
                child.navLink === this.props.activePath &&
                !child.permissions.includes(this.props.currentUser)
              ) {
                return this.props.redirectUnauthorized()
              } else {
                return null
              }
            })
          : null}
      </ul>
    )
  }

  render() {
    return (
      <React.Fragment>
        {this.renderChild(
          this.props.group,
          this.props.activeGroup,
          this.props.handleGroupClick,
          this.props.handleActiveItem,
          null
        )}
      </React.Fragment>
    )
  }
}
export default SideMenuGroup
