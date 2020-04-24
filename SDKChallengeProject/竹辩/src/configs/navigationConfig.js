import React from "react";
import * as Icon from "react-feather";
const navigationConfig = [
  {
    id: "home",
    title: "个人面板",
    type: "item",
    icon: <Icon.Home size={20} />,
    permissions: ["admin", "debator"],
    navLink: "/"
  },
  {
    type: "groupHeader",
    groupTitle: "基础设置"
  },
  {
    id: "account",
    title: "个人信息",
    type: "item",
    icon: <Icon.Edit size={20} />,
    permissions: ["admin", "debator"],
    navLink: "/account"
  },
  {
    id: "shield",
    title: "生物指纹",
    type: "item",
    icon: <Icon.Shield size={20} />,
    permissions: ["admin", "debator"],
    navLink: "/auth"
  },
  {
    type: "groupHeader",
    groupTitle: "赛事管理"
  },
  {
    id: "compList",
    title: "比赛列表",
    type: "item",
    icon: <Icon.List size={20} />,
    permissions: ["admin", "debator"],
    navLink: "/compList"
  },
  {
    id: "gammeList",
    title: "场次列表",
    type: "item",
    icon: <Icon.File size={20} />,
    permissions: ["admin", "debator"],
    navLink: "/gameList"
  },
  {
    id: "anaylize",
    title: "成绩分析",
    type: "item",
    icon: <Icon.Target size={20} />,
    permissions: ["admin", "debator"],
    navLink: "/analyze/2"
  },
  {
    id: "compS",
    title: "比赛页面",
    type: "item",
    icon: <Icon.Users size={20} />,
    permissions: ["admin"],
    navLink: "/comp"
  },
  
];

export default navigationConfig;
