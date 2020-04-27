import AgoraRTC from 'agora-rtc-sdk'

const rtcMethods = {
  getMedias: function (next) {
    AgoraRTC.getDevices(function (items) {
      items.filter(function (item) {
        return ['audioinput', 'videoinput'].indexOf(item.kind) !== -1
      })
        .map(function (item) {
          return {
            name: item.label,
            value: item.deviceId,
            kind: item.kind
          }
        })
      var videos = []
      var audios = []
      for (var i = 0; i < items.length; i++) {
        var item = items[i]
        if (item.kind === 'videoinput') {
          var name = item.label
          var value = item.deviceId
          if (!name) {
            name = 'camera-' + videos.length
          }
          videos.push({
            name: name,
            value: value,
            kind: item.kind
          })
        }
        if (item.kind === 'audioinput') {
          var name = item.label
          var value = item.deviceId
          if (!name) {
            name = 'microphone-' + audios.length
          }
          audios.push({
            name: name,
            value: value,
            kind: item.kind
          })
        }
      }
      next({videos: videos, audios: audios})
    })
  }
}
export default rtcMethods
