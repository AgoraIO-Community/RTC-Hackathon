const Utils = require("./utils/util.js");

//app.js
App({
  onLaunch: function () {
    // 展示本地存储能力
    Utils.checkSystemInfo(this);

    wx.authorize({
      scope: 'scope.record',
    });

    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
      }
    })
  },
  globalData: {
    userInfo: null
  }
})