const APPID = "xxxx"; // 请注册，让后输入ID


if(APPID === ""){
  wx.showToast({
    title: `请联系管理员，配置的APPID`,
    icon: 'none',
    duration: 5000
  });
}

module.exports = {
  APPID: APPID
}