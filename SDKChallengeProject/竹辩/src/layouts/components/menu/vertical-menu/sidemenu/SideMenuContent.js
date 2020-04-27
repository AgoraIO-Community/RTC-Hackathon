import React from "react"
import { Link } from "react-router-dom"
import classnames from "classnames"
import navigationConfig from "../../../../../configs/navigationConfig"
import SideMenuGroup from "./SideMenuGroup"
import { Badge } from "reactstrap"
import { ChevronRight } from "react-feather"
import { history } from "../../../../../history"

class SideMenuContent extends React.Component {
  constructor(props) {
    super(props)

    this.parentArr = []
    this.collapsedPath = null
    this.redirectUnauthorized = () => {
      history.push("/misc/not-authorized")
    }
  }
  state = {
    flag: true,
    isHovered: false,
    activeGroups: [],
    currentActiveGroup: [],
    tempArr: []
  }

  handleGroupClick = (id, parent = null, type = "") => {
    let open_group = this.state.activeGroups
    let active_group = this.state.currentActiveGroup
    let temp_arr = this.state.tempArr
    // Active Group to apply sidebar-group-active class
    if (type === "item" && parent === null) {
      active_group = []
      temp_arr = []
    } else if (type === "item" && parent !== null) {
      active_group = []
      if (temp_arr.includes(parent)) {
        temp_arr.splice(temp_arr.indexOf(parent) + 1, temp_arr.length)
      } else {
        temp_arr = []
        temp_arr.push(parent)
      }
      active_group = temp_arr.slice(0)
    } else if (type === "collapse" && parent === null) {
      temp_arr = []
      temp_arr.push(id)
    } else if (type === "collapse" && parent !== null) {
      if (active_group.includes(parent)) {
        temp_arr = active_group.slice(0)
      }
      if (temp_arr.includes(id)) {
        // temp_arr.splice(temp_arr.indexOf(id), 1)
        temp_arr.splice(temp_arr.indexOf(id), temp_arr.length)
      } else {
        temp_arr.push(id)
      }
    } else {
      temp_arr = []
    }

    if (type === "collapse") {
      // If open group does not include clicked group item
      if (!open_group.includes(id)) {
        // Get unmatched items that are not in the active group
        let temp = open_group.filter(function(obj) {
          return active_group.indexOf(obj) === -1
        })
        // Remove those unmatched items from open group
        if (temp.length > 0 && !open_group.includes(parent)) {
          open_group = open_group.filter(function(obj) {
            return !temp.includes(obj)
          })
        }
        if (open_group.includes(parent) && active_group.includes(parent)) {
          open_group = active_group.slice(0)
        }
        // Add group item clicked in open group
        if (!open_group.includes(id)) {
          open_group.push(id)
        }
      } else {
        // If open group includes click group item, remove it from open group
        open_group.splice(open_group.indexOf(id), 1)
      }
    }
    if (type === "item") {
      open_group = active_group.slice(0)
    }

    this.setState({
      activeGroups: open_group,
      tempArr: temp_arr,
      currentActiveGroup: active_group
    })
  }

  initRender = parentArr => {
    this.setState({
      activeGroups: parentArr.slice(0),
      currentActiveGroup: parentArr.slice(0),
      flag: false
    })
  }

  componentDidMount() {
    this.initRender(this.parentArr[0] ? this.parentArr[0] : [])
  }

  componentDidUpdate(prevProps, prevState) {
    if (prevProps.activePath !== this.props.activePath) {
      if (this.collapsedMenuPaths !== null) {
        this.props.collapsedMenuPaths(this.collapsedMenuPaths)
      }

      this.initRender(
        this.parentArr[0] ? this.parentArr[this.parentArr.length - 1] : []
      )
    }
  }

  render() {
    // Loop over sidebar items
    // eslint-disable-next-line
    const menuItems = navigationConfig.map(item => {
      const CustomAnchorTag = item.type === "external-link" ? `a` : Link
      // checks if item has groupheader
      if (item.type === "groupHeader") {
        return (
          <li
            className="navigation-header"
            key={`group-header-${item.groupTitle}`}
          >
            <span>{item.groupTitle}</span>
          </li>
        )
      }

      let renderItem = (
        <li
          className={classnames("nav-item", {
            "has-sub": item.type === "collapse",
            open: this.state.activeGroups.includes(item.id),
            "sidebar-group-active": this.state.currentActiveGroup.includes(
              item.id
            ),
            hover: this.props.hoverIndex === item.id,
            active:
              (this.props.activeItemState === item.navLink &&
              item.type === "item" )|| (item.parentOf && item.parentOf.includes(this.props.activeItemState)),
            disabled: item.disabled
          })}
          key={item.id}
          onClick={e => {
            e.stopPropagation()
            if (item.type === "item") {
              this.props.handleActiveItem(item.navLink)
              this.handleGroupClick(item.id, null, item.type)
              if(this.props.deviceWidth <= 1200 && item.type === "item"){
                this.props.toggleMenu()
              }
            } else {
              this.handleGroupClick(item.id, null, item.type)
            }
          }}
        >
          <CustomAnchorTag
            to={item.filterBase ? item.filterBase : item.navLink && item.type === "item" ? item.navLink :  ""}
            href={item.type === "external-link" ? item.navLink : ""}
            className={`d-flex ${
              item.badgeText
                ? "justify-content-between"
                : "justify-content-start"
            }`}
            onMouseEnter={() => {
              this.props.handleSidebarMouseEnter(item.id)
            }}
            onMouseLeave={() => {
              this.props.handleSidebarMouseEnter(item.id)
            }}
            key={item.id}
            onClick={e => {
              return item.type === "collapse" ? e.preventDefault() : ""
            }}
            target={item.newTab ? "_blank" : undefined}
          >
            <div className="menu-text">
              {item.icon}
              <span className="menu-item menu-title">{item.title}</span>
            </div>

            {item.badge ? (
              <div className="menu-badge">
                <Badge color={item.badge} className="mr-1" pill>
                  {item.badgeText}
                </Badge>
              </div>
            ) : (
              ""
            )}
            {item.type === "collapse" ? (
              <ChevronRight className="menu-toggle-icon" size={13} />
            ) : (
              ""
            )}
          </CustomAnchorTag>
          {item.type === "collapse" ? (
            <SideMenuGroup
              group={item}
              handleGroupClick={this.handleGroupClick}
              activeGroup={this.state.activeGroups}
              handleActiveItem={this.props.handleActiveItem}
              activeItemState={this.props.activeItemState}
              handleSidebarMouseEnter={this.props.handleSidebarMouseEnter}
              activePath={this.props.activePath}
              hoverIndex={this.props.hoverIndex}
              initRender={this.initRender}
              parentArr={this.parentArr}
              triggerActive={undefined}
              currentActiveGroup={this.state.currentActiveGroup}
              permission={this.props.permission}
              currentUser={this.props.currentUser}
              redirectUnauthorized={this.redirectUnauthorized}
              collapsedMenuPaths={this.props.collapsedMenuPaths}
              toggleMenu={this.props.toggleMenu}
              deviceWidth={this.props.deviceWidth}
            />
          ) : (
            ""
          )}
        </li>
      )
      
      if (
        item.navLink &&
        item.collapsed !== undefined &&
        item.collapsed === true
      ) {
        this.collapsedPath = item.navLink
        this.props.collapsedMenuPaths(item.navLink)
      }

      if (
        item.type === "collapse" || item.type === "external-link" ||
        (item.type === "item" &&
          item.permissions &&
          item.permissions.includes(this.props.currentUser)) ||
        item.permissions === undefined
      ) {
        return renderItem
      } else if (
        item.type === "item" &&
        item.navLink === this.props.activePath &&
        !item.permissions.includes(this.props.currentUser)
      ) {
        return this.redirectUnauthorized()
      }
    })
    return <React.Fragment>{menuItems}</React.Fragment>
  }
}
export default SideMenuContent
