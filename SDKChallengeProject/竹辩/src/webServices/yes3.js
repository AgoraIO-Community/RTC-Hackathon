import axios from "axios";
import debug from "./debug";
import md5 from "js-md5";
import APP_SECRET from "../config";

import { toast } from "react-toastify";
import qs from 'qs';

toast.configure({
  autoClose: 5000,
  draggable: false,
});

const notifyError = (e) => {
  toast.error(e, {
    position: toast.POSITION.TOP_RIGHT,
  });
};

const CONFIG = {
  INTERFACE_URL: "/yesapi",
};

const API_OBJ = {
  PREFIX: CONFIG.INTERFACE_URL + "?s=",
  USER_REGISTER: "App.User.Register",
  USER_LOGIN: "App.User.Login",
  USER_CHANGE_PROPS: "App.User.UpdateExtInfo",
  USER_CHECK_IF_LOGIN: "App.User.Check",
  USER_EXT_INFO: "App.User.OtherProfile",
  USER_SELF_INFO: "App.User.Profile",
  USER_CHANGE_ROLE: "App.User.ChangeRole",
  USER_LOGOUT: "App.User.Logout",
  USER_SEARCH: "App.User.Search",

  TABLE_CREATE: "App.Table.Create",
  TABLE_READ: "App.Table.FreeFindOne",
  TABLE_READ_ID: "App.Table.Get",
  TABLE_UPDATE_WHERE: "App.Table.FreeUpdate",
  TABLE_UPDATE: "App.Table.Update",
  TABLE_READ_PAGE: "App.Table.FreeQuery",
  TABLE_UNION_QUERY: "App.Table.FreeLeftJoinQuery",

  OTHERS_UNIQUE_ID: "App.Common_UniqueId.GetUniqueId",
};

async function HGet(url) {
  try {
    let res = await axios.get(url);
    res = res.data;
    return new Promise((resolve) => {
      if (res.ret === 200) {
        resolve(res);
      } else {
        resolve(res);
      }
    });
  } catch (err) {
    notifyError("您已与服务器断开连接。");
    console.log(err);
  }
}

let { PREFIX } = API_OBJ;
const yesapi = {
  user: {
    /**
     * 用户登录接口
     * @param username string
     * @param password string
     * @param funcs
     */
    login: async function (username, password) {
      //app_key,password,s,username,secret
      let passwordMD5 = md5(password);
      let url =
        PREFIX +
        API_OBJ.USER_LOGIN +
        `&username=${username}&password=${passwordMD5}`;
      return await HGet(url);
    },
    /**
     * 用户注册接口
     * @param username string
     * @param password string
     * @param extData json->{a:123}
     * @param funcs
     * @example yesapi_mobile
     */
    register: async function (username, password, extData) {
      //app_key ext_info password s username
      let passwordMD5 = md5(password);
      let dataStr = JSON.stringify(extData);
      let url =
        PREFIX +
        API_OBJ.USER_REGISTER +
        `&ext_info=${dataStr}&username=${username}&password=${passwordMD5}`;
      return await HGet(url);
    },
    /**
     * 用户身份修改接口
     * @param uuid string
     * @param token string
     * @param new_role string
     * @param funcs
     * @example yesapi_mobile
     */
    changeOtherRole: async function (other_uuid, new_role) {
      let uuid = localStorage.getItem("uuid");
      let token = localStorage.getItem("token");
      //app_key s token uuid
      let PORT = API_OBJ.USER_CHANGE_ROLE;
      let url =
        PREFIX +
        PORT +
        `&uuid=${uuid}&other_uuid=${other_uuid}&token=${token}&new_role=${new_role}`;
      return await HGet(url);
    },
    /**
     * 用户搜索接口
     * @param username string
     * @param password string
     * @param extData json->{a:123}
     * @param funcs
     * @example yesapi_mobile
     */
    userSearch: async function (username) {
      //app_key s token uuid
      let PORT = API_OBJ.USER_SEARCH;
      let uuid = localStorage.getItem("uuid");
      let token = localStorage.getItem("token");
      let url =
        PREFIX + PORT + `&uuid=${uuid}&token=${token}&username=${username}`;
      return await HGet(url);
    },
    /**
     * 检查是否登录
     * @param uuid
     * @param token
     * @param funcs
     * @example
     * data.err_code 0已登录1未登录
     */
    checkIfLogin: async function () {
      let uuid = localStorage.getItem("uuid");
      let token = localStorage.getItem("token");
      //app_key s token uuid
      let PORT = API_OBJ.USER_CHECK_IF_LOGIN;
      let url = PREFIX + PORT + `&uuid=${uuid}&token=${token}`;
      return await HGet(url);
    },
    /**
     * 退出登录
     * @param uuid
     * @param token
     * @param funcs
     * @example
     * data.err_code 0已登录1未登录
     */
    logOut: async function () {
      let uuid = localStorage.getItem("uuid");
      let token = localStorage.getItem("token");
      //app_key s token uuid
      let PORT = API_OBJ.USER_LOGOUT;
      let url = PREFIX + PORT + `&uuid=${uuid}&token=${token}`;
      return await HGet(url);
    },
    /**
     * 获取一个用户的额外信息
     * @param uuid string
     * @param token string
     * @param funcs retFunc
     */
    getOtherExtInfo: async function (other_uuid) {
      let uuid = localStorage.getItem("uuid");
      let token = localStorage.getItem("token");
      //app_key other_uuid s token
      let PORT = API_OBJ.USER_EXT_INFO;
      let url =
        PREFIX + PORT + `&other_uuid=${other_uuid}&uuid=${uuid}&token=${token}`;
      return await HGet(url);
    },
    /**
     * 获取自己的额外信息
     * @param uuid string
     * @param token string
     * @param funcs retFunc
     */
    getSelfInfo: async function () {
      let uuid = localStorage.getItem("uuid");
      let token = localStorage.getItem("token");
      //app_key other_uuid s token
      let PORT = API_OBJ.USER_SELF_INFO;
      let url = PREFIX + PORT + `&uuid=${uuid}&token=${token}`;
      return await HGet(url);
    },
    /**
     * 使用token来更新用户额外信息
     * @param uuid
     * @param extData
     * @param token
     * @param funcs
     */
    updateExtInfo: async function (extData) {
      let uuid = localStorage.getItem("uuid");
      let token = localStorage.getItem("token");
      debug(arguments);
      //app_key ext_info sign token uuid
      let dataStr = JSON.stringify(extData);
      let url =
        PREFIX +
        API_OBJ.USER_CHANGE_PROPS +
        `&ext_info=${dataStr}&uuid=${uuid}&token=${token}`;
      return await HGet(url);
    },
  },
  table: {
    /**
     * 创建一条数据
     * @param table_name string
     * @param data {a:123,b:234}
     * @param uuid string
     * @param token string
     * @param funcs retFunc
     */
    create: async function (table_name, data) {
      let uuid = localStorage.getItem("uuid");
      let token = localStorage.getItem("token");
      //app_key data model_name s uuid token
      let PORT, url;
      let dataStr;
      //Define User Data
      dataStr = JSON.stringify(data);
      //Define Post Data
      PORT = API_OBJ.TABLE_CREATE;
      url =
        PREFIX +
        PORT +
        `&data=${dataStr}&uuid=${uuid}&token=${token}&model_name=${table_name}`;
      //Execute Post
      return await HGet(url);
    },
    /**
     * 使用ID查询查询一条数据
     * @param table_name string
     * @param where [["a","=","3142"],[...],...]
     * @param logic "and"/"or"
     * @param uuid string
     * @param funcs
     */
    readOneViaID: async function (table_name, id) {
      let uuid = localStorage.getItem("uuid");
      let token = localStorage.getItem("token");
      //app_key logic model_name s uuid where
      let PORT, url;
      //Define Post Data
      PORT = API_OBJ.TABLE_READ_ID;
      url =
        PREFIX +
        PORT +
        `&id=${id}&uuid=${uuid}&token=${token}&model_name=${table_name}`;
      //Execute Post
      return await HGet(url);
    },
    /**
     * 查询一条数据
     * @param table_name string
     * @param where [["a","=","3142"],[...],...]
     * @param logic "and"/"or"
     * @param uuid string
     * @param funcs
     */
    read: async function (table_name, where, logic) {
      let uuid = localStorage.getItem("uuid");
      let token = localStorage.getItem("token");
      //app_key logic model_name s uuid where
      let PORT, url;
      let dataStr;
      //Define User Data
      dataStr = JSON.stringify(where);
      //Define Post Data
      PORT = API_OBJ.TABLE_READ;
      url =
        PREFIX +
        PORT +
        `&logic=${logic}&where=${dataStr}&uuid=${uuid}&token=${token}&model_name=${table_name}`;
      return await HGet(url);
    },
    /**
     * 使用UUID来以页为单位查询数据
     * @param table_name
     * @param where [["a","=","3142"],[...],...]
     * @param logic "and"/"or"
     * @param page number
     * @param perPage number
     * @param uuid
     * @param funcs
     */
    readPage: async function (table_name, where, logic, page, perPage) {
      let uuid = localStorage.getItem("uuid");
      let token = localStorage.getItem("token");
      //app_key logic model_name page perpage s uuid where
      let PORT, url, dataStr;
      //Define User Data
      dataStr = JSON.stringify(where);
      //Define Post Data
      PORT = API_OBJ.TABLE_READ_PAGE;
      url =
        PREFIX +
        PORT +
        `&logic=${logic}&where=${dataStr}&token=${token}&page=${page}&perpage=${perPage}&model_name=${table_name}&uuid=${uuid}`;
      //Execute Post
      return await HGet(url);
    },
    /**
     * 使用UUID来以页为单位查询数据
     * @param table_name
     * @param where [["a","=","3142"],[...],...]
     * @param logic "and"/"or"
     * @param page number
     * @param perPage number
     * @param uuid
     * @param funcs
     */
    readPageSelect: async function (
      table_name,
      where,
      logic,
      page,
      perPage,
      select
    ) {
      let uuid = localStorage.getItem("uuid");
      let token = localStorage.getItem("token");
      //app_key logic model_name page perpage s uuid where
      let PORT, url, dataStr;
      //Define User Data
      dataStr = JSON.stringify(where);
      //Define Post Data
      PORT = API_OBJ.TABLE_READ_PAGE;
      url =
        PREFIX +
        PORT +
        `&logic=${logic}&token=${token}&where=${dataStr}&page=${page}&perpage=${perPage}&model_name=${table_name}&uuid=${uuid}&select=${select}`;
      //Execute Post
      return await HGet(url);
    },
    /**
     * 使用UUID来进行联合查询
     * @param table_name
     * @param join_table_name
     * @param join_select
     * @param on
     * @param where
     * @param logic
     * @param page
     * @param perPage
     * @example on：{"cate_id":"id"} cate_id是主表中的字段名，id是副表中的字段名
     * @example where：[ ["TL.view_times",">=",100], ["TR.is_show","=",1]] TL代表主表中的查询，TR代表副表中的查询
     * @example join_select： cate_id,id,views
     */
    readUnion: async function (
      table_name,
      join_table_name,
      join_select,
      on,
      where,
      logic,
      page,
      perPage
    ) {
      let uuid = localStorage.getItem("uuid");
      let token = localStorage.getItem("token");
      //app_key join_model_name join_select logic model_name on page perpage s uuid where
      let PORT, url, dataStr1, dataStr2;
      //Define User Data
      dataStr1 = JSON.stringify(where);
      dataStr2 = JSON.stringify(on);
      //Define Post Data
      PORT = API_OBJ.TABLE_UNION_QUERY;
      url =
        PREFIX +
        PORT +
        `&logic=${logic}&token=${token}&where=${dataStr1}&page=${page}&perpage=${perPage}&model_name=${table_name}&uuid=${uuid}&join_model_name=${join_table_name}&join_select=${join_select}&on=${dataStr2}`;
      //Execute Post
      return await HGet(url);
    },
    /**
     * 使用UUID和Order来进行联合查询
     * @param table_name
     * @param join_table_name
     * @param join_select
     * @param on
     * @param where
     * @param logic
     * @param page
     * @param perPage
     * @param order
     * @param uuid
     * @param funcs
     * @example on：
     * {"cate_id":"id"} cate_id是主表中的字段名，id是副表中的字段名
     * @example where：
     * [ ["TL.view_times",">=",100], ["TR.is_show","=",1]] TL代表主表中的查询，TR代表副表中的查询
     * @example join_select：
     * cate_id,id,views
     * @example order：
     * 每一组排序格式为："字段名 + 空格 + ASC|DESC"，其中：
     * ASC：为指定列按升序排列
     * DESC：为指定列按降序排列。
     */
    readUnion_Order: async function (
      table_name,
      join_table_name,
      join_select,
      on,
      where,
      logic,
      page,
      perPage,
      order
    ) {
      //app_key join_model_name join_select logic model_name on order page perpage s uuid where
      let PORT, url, dataStr1, dataStr2, dataStr3;
      let uuid = localStorage.getItem("uuid");
      let token = localStorage.getItem("token");
      //Define User Data
      dataStr1 = JSON.stringify(where);
      dataStr2 = JSON.stringify(on);
      dataStr3 = JSON.stringify(order);
      //Define Post Data
      PORT = API_OBJ.TABLE_UNION_QUERY;
      url =
        PREFIX +
        PORT +
        `&logic=${logic}&token=${token}&where=${dataStr1}&page=${page}&perpage=${perPage}&model_name=${table_name}&uuid=${uuid}&order=${dataStr3}&join_model_name=${join_table_name}&join_select=${join_select}&on=${dataStr2}`;
      //Execute Post
      return await HGet(url);
    },
    /**
     * 使用条件查询更新一条数据
     * @param table_name string
     * @param data {a:23123, app:"list"}
     * @param where [["a","=","3142"],[...],...]
     * @param logic "and"/"or"
     * @param uuid string
     * @param funcs
     */
    update: async function (table_name, data, where, logic) {
      let uuid = localStorage.getItem("uuid");
      let token = localStorage.getItem("token");
      //app_key data logic model_name s uuid where
      let PORT, url;
      let whereStr, dataStr;
      //Define User Data
      whereStr = JSON.stringify(where);
      dataStr = JSON.stringify(data);
      //Define Post Data
      PORT = API_OBJ.TABLE_UPDATE_WHERE;
      url =
        PREFIX +
        PORT +
        `&data=${dataStr}&token=${token}&logic=${logic}&where=${whereStr}&uuid=${uuid}&model_name=${table_name}`;
      //Execute Post
      return await HGet(url);
    },
    /**
     * 使用id更新一条数据
     * @param table_name string
     * @param data {a:23123, app:"list"}
     * @param where [["a","=","3142"],[...],...]
     * @param logic "and"/"or"
     * @param uuid string
     * @param funcs
     */
    updateViaID: async function (table_name, data, id) {
      let uuid = localStorage.getItem("uuid");
      let token = localStorage.getItem("token");
      //app_key data logic model_name s uuid where
      let PORT, url;
      let dataStr;
      //Define User Data
      dataStr = JSON.stringify(data);
      //Define Post Data
      PORT = API_OBJ.TABLE_UPDATE;
      url =
        PREFIX +
        PORT +
        `&data=${dataStr}&id=${id}&uuid=${uuid}&token=${token}&model_name=${table_name}`;
      //Execute Post
      return await HGet(url);
    },
  },
  others: {
    /**
     * 唯一id生成接口
     * @param uuid string
     * @param token string
     */
    getUniqueId: async function () {
      let uuid = localStorage.getItem("uuid");
      let token = localStorage.getItem("token");
      //app_key,password,s,username,secret
      let url =
        PREFIX + API_OBJ.OTHERS_UNIQUE_ID + `&uuid=${uuid}&token=${token}`;
      return await HGet(url);
    },
  },
};

export default yesapi;

async function getAgora(url){
  const app = APP_SECRET.agora.restfulAppKey+":"+APP_SECRET.agora.restfulCerti;
  let res = await axios.get(url,{headers:{"Authorization":"Basic "+window.btoa(app)}});
  res = res.data;
  return new Promise((resolve) => {
    if (res.ret === 200) {
      resolve(res);
    } else {
      resolve(res);
    }
  });
}
async function postAgora (url, data) {
  const app = APP_SECRET.agora.restfulAppKey+":"+APP_SECRET.agora.restfulCerti;
  let rea = await axios.post(url, JSON.stringify(data),{
    headers: {
      "Authorization":"Basic "+window.btoa(app),
      "Content-type":"application/json",
      "charset":"utf-8"
    }
  })
  let res = rea.data;
  return new Promise((resolve) => {
    console.log(res,rea)
    if (rea.status === 200) {
      resolve(res)
    } else {
      resolve(res)
    }
  }).catch((e) => {});
}
const PREFIX1 = "/agora";
export const recordWeb = {
  /**
   * 获取rid
   * @param {string} appid 
   * @param {string} channelName 
   */
  acquire: async function(appid,channelName) {
    //app_key,password,s,username,secret
    let obj = {
      "cname":channelName,
      "uid":"114514",
      clientRequest: {
        resourceExpiredHour: 1
      }
    };
    let url =
      PREFIX1 + `/v1/apps/${appid}/cloud_recording/acquire`;
    return await postAgora(url,obj);
  },
  /**
   * 使用rid开始进行录制
   * @param {string} appid 
   * @param {string} rid 
   * @param {string} channelName 
   */
  start: async function(appid,rid,channelName) {
    //app_key,password,s,username,secret
    let obj = {
      "cname": channelName,
      "uid": "114514",
      "clientRequest": {
        "recordingConfig": {
          "maxIdleTime": 30,
          "streamTypes": 0,
          "channelType": 0,
          "videoStreamType": 0,
          "subscribeUidGroup": 3
        },
        "storageConfig": {
          "accessKey": "nA3K7p6NTZOXf9LOWjW2JJstLxVEdNkwGfXKvEAW",
          "region": 0,
          "bucket": "self-study-bgm",
          "secretKey": "rOCG70BanjhJ4wVxGAKYcmowh9xxZU90UXj1ABzs",
          "vendor": 0,
          "fileNamePrefix": [
            "cloudDebate"
          ]
        }
      }
    };
    let mode = "mix"
    let url =
      PREFIX1 + `/v1/apps/${appid}/cloud_recording/resourceid/${rid}/mode/${mode}/start`;
    return await postAgora(url,obj);
  },
  /**
   * 结束录制
   * @param {string} appid 
   * @param {string} rid 
   * @param {string} channelName 
   * @param {string} sid start方法里获取到的sid
   */
  stop: async function(appid,rid,channelName,sid) {
    //app_key,password,s,username,secret
    let obj = {
      "cname": channelName,
      "uid": "114514",
      "clientRequest": {}
    };
    let mode = "mix"
    let url =
      PREFIX1 + `/v1/apps/${appid}/cloud_recording/resourceid/${rid}/sid/${sid}/mode/${mode}/stop`;
      // `​/v1/apps/${appid}/cloud_recording/resourceid/${rid}/sid/${sid}/mode/${mode}/stop`;
    return await postAgora(url,obj);
  },
}