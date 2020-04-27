import React from "react"
import * as Icon from "react-feather"

const horizontalMenuConfig = [
  {
    id: "home",
    title: "Home",
    type: "item",
    icon: <Icon.Home size={20} />,
    permissions: ["admin", "editor"],
    navLink: "/",
  },
  {
    id: "page2",
    title: "Page 2",
    type: "item",
    icon: <Icon.File size={20} />,
    permissions: ["admin", "editor"],
    navLink: "/page2",
  },
  {
    id: "compList",
    title: "比赛列表",
    type: "item",
    icon: <Icon.List size={20} />,
    permissions: ["admin", "editor"],
    navLink: "/compList",
  }  
]

export default horizontalMenuConfig
