<template>
  <div id="app">
    <router-view v-if="isRouterAlive"/>
  </div>
</template>

<script>
import rtmMethods from './view/message/index'
import rtm from './utils/rtm/createClient'
import rtcMethods from './utils/rtc/rtcMethods'
import API from './utils/API'
export default {
  name: 'App',
  provide () {
    return {
      reload: this.reload
    }
  },
  data () {
    return {
      params: {
        appId: ''
      },
      chatUser: {},
      isRouterAlive: true
    }
  },
  methods: {
    reload () { // 刷新页面
      this.isRouterAlive = false
      this.$nextTick(() => {
        this.isRouterAlive = true
        let loginUser = JSON.parse(localStorage.getItem('loginUser')) // 解决用户登录之后rtm没有登录的问题
        this.$store.dispatch('emitSaveAppId', this.params.appId)
        if (loginUser !== null) {
          this.$store.dispatch('saveUser', loginUser)
          rtmMethods.rtmLogin({appId: this.params.appId, uid: loginUser._id}, rtm) // 登录rtm client
        }
      })
    },
    init () {
      rtm.on('MessageFromPeer', (message, peerId) => { // 全局接受实时消息,1秒后自动关闭
        const h = this.$createElement
        this.$notify({
          duration: 1000,
          message: h('i', { style: 'color: teal' }, '您有一条新信息'),
          position: 'bottom-right'
        })
      })
      rtm.on('RemoteInvitationReceived', (remoteInvitation) => { // 全局监听远程邀请
        rtm.remoteInvitation = remoteInvitation // 只保留最新的邀请
        this.showCall()
        rtm.remoteInvitation.on('RemoteInvitationCanceled', () => {
          console.log('cancel')
          this.$notify.closeAll(() => {
            console.log('caller 取消')
          })
          rtm.remoteInvitation = null
        })
        rtm.remoteInvitation.on('RemoteInvitationFailure', () => {
          this.$notify.closeAll(() => {
            console.log('callee 长时间未响应')
          })
          rtm.remoteInvitation = null
        })
      })
      rtm.on('ConnectionStateChanged', (newState, reason) => { // 账号只能在一个设备登录
        if (newState === 'ABORTED') {
          if (reason === 'REMOTE_LOGIN') {
            this.$toast('该账号在其他设备登录')
            this.$store.dispatch('clearUser')
            localStorage.clear()
            this.$router.go(0)
          }
        }
      })
    },
    showCall () { // 显示邀请
      this.chatUser = JSON.parse(rtm.remoteInvitation.content)
      var _this = this
      var method = ''
      if (this.chatUser.method === 'voice') {
        method = '语音'
      } else {
        method = '视频'
      }
      this.$notify({ // 不会关闭，除非用户点击或者caller取消等操作
        message: `${this.chatUser.userName}正邀请您${method}通话`,
        duration: 0,
        position: 'bottom-right',
        customClass: 'tips',
        onClick () {
          console.log('click')
          _this.$router.push('/message') // 点击跳转至聊天页面
          this.$notify.closeAll()
        }
      })
    }
  },
  created () {
    let appId = localStorage.getItem('appId')
    this.params.appId = appId
    if (appId === null) {
      API.users.getAppId({}, (responseData) => {
        if (responseData.code === 200) {
          this.params.appId = responseData.data
          localStorage.setItem('appId', this.params.appId)
        }
      }, (err) => {
        console.log(err)
      })
    }
    if (this.isRouterAlive) {
      let loginUser = JSON.parse(localStorage.getItem('loginUser')) // 从本地存储中获取登录信息
      this.$store.dispatch('emitSaveAppId', this.params.appId)
      if (loginUser !== null) {
        this.$store.dispatch('saveUser', loginUser)
        rtmMethods.rtmLogin({appId: this.params.appId, uid: loginUser._id}, rtm) // 登录rtm client
        this.init()
      }
    }
  },
  beforeDestroyed () {
    rtmMethods.rtmLogout(rtm) // 退出登录
  }
}
</script>

<style>
#app{
  width: 100%;
  min-height: 100%;
}
.tips{
  cursor: pointer;
  border: none;
}
</style>
