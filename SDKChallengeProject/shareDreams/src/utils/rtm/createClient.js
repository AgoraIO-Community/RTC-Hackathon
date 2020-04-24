import RtmClient from './rtmClient'

var RtmInstance = (function () { // 用户在浏览时只创建一个rtm客户端
  var instance
  return {
    getInstance: function () {
      if (!instance) {
        instance = new RtmClient()
      }
      return instance
    }
  }
}())
var rtm = RtmInstance.getInstance()
export default rtm
