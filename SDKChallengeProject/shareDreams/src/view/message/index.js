const rtmMethods = {
  rtmLogin: (params, rtm) => { // 登陆
    if (rtm._logined) {
      console.log('you already logined')
      return
    }
    try {
      rtm.init(params.appId, rtm)
      window.rtm = rtm
      rtm.login(params.uid).then(() => {
        console.log('login')
        rtm._logined = true
        console.log(`login: ${params.uid}`)
      }).catch((err) => {
        console.log(err)
      })
    } catch (err) {
      console.log(`login failed`)
      console.log(err)
    }
  },
  rtmLogout: (rtm) => { // 退出登录
    if (!rtm._logined) {
      console.log('you already logout')
      return
    }
    rtm.logout().then(() => {
      console.log('login')
      rtm._logined = false
      console.log(`login: ${rtm.accountName}`)
    }).catch((err) => {
      console.log(err)
    })
  },
  rtmJoin: (params, rtm) => {
    if (!rtm._logined) {
      console.log('please login first')
      return
    }
    if (rtm.channels[params.channelName] || (rtm.channels[params.channelName] && rtm.channels[params.channelName].joined)) {
      console.log('you aleady joined')
      return
    }
    rtm.joinChannel(params.channelName).then(() => {
      console.log(`${rtm.accountName} join channel success`)
      rtm.channels[params.channelName].joined = true
      return rtm.accountName // 返回给页面进行接收
    }).catch((err) => {
      console.log('join channel failed')
      console.log(err)
    })
  },
  rtmLeave: (params, rtm) => {
    rtm.leaveChannel(params.channelName).then(() => {
      console.log(`${rtm.accountName} leave channel success`)
      if (rtm.channels[params.channelName]) {
        rtm.channels[params.channelName].joined = false
        rtm.channels[params.channelName] = null
      }
    }).catch((err) => {
      console.log(`leave channel failed`)
      console.log(err)
    })
  },
  rtmSendChannelMessage: (params, rtm) => {
    rtm.sendChannelMessage(params.channelMessage, params.channelName).then(() => {
      console.log('account: ' + rtm.accountName + ' send : ' + params.channelMessage + ' channel: ' + params.channelName)
      // 需要吧获取的信息返回给页面
    }).catch((err) => {
      console.log(`send message to channel ${params.channelName}  failed, please open console see more details.`)
      console.log(err)
    })
  },
  rtmSendPeerMessage: (params, rtm) => {
    rtm.sendPeerMessage(params.peerMessage, params.peerId, {enableHistoricalMessaging: true, enableOfflineMessaging: true}).then(() => { // 第三个参数为历史消息和离线消息
      console.log('account: ' + rtm.accountName + ' send : ' + params.peerMessage + ' peerId: ' + params.peerId)
      // 需要把获取到的信息返回给页面
    }).catch((err) => {
      console.log('Send message to peer ' + params.peerId + ' failed, please open console see more details.')
      console.log(err)
    })
  },
  rtmQueryPeer: (params, rtm) => {
    rtm.queryPeersOnlineStatus(params._id).then((res) => {
      let result = res[params._id]
      console.log(`that peer online status: ${res[params._id]}`)
      return result
    }).catch((err) => {
      console.log(`query peer online failed`)
      console.log(err)
      return 'offline'
    })
  },
  rtmCreateLocalInvitation: (params, rtm) => {
    rtm.invitation(params.calleeId).then((res) => {
      console.log(res)
    }).catch((err) => {
      console.log(err)
    })
  },
  rtmSendInvitation: (content, channel, rtm) => { // 发送邀请
    rtm.sendInvitation(content, channel)
  },
  rtmCancelInvitation: (rtm) => {
    rtm.cancelInvitation()
  },
  rtmCreateRemoteInvitation: (params, rtm) => {
    rtm.createRemoteInvitation(params.callerId)
  }
}
export default rtmMethods
