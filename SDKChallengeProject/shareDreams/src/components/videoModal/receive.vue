<template>
  <div class="videoModal">
    <ReceiveInvitations @options="receiveOptions" :chatUser="chatUser" v-if="status" method="视频"/>
    <div class="content"  v-else ref="content">
      <div class="agora-theme">
      <div class="video-grid" id="video">
          <div class="remote_video">
            <div id="remote_video_123456" class="remote-video-placeholder"></div>
            <div id="remote_video_info_123456" class="video-profile hide"></div>
            <div id="video_autoplay_123456" class="autoplay-fallback hide"></div>
          </div>
          <div class="video-view">
            <div id="local_stream" class="video-placeholder"></div>
            <div id="local_video_info" class="video-profile hide"></div>
            <div id="video_autoplay_local" class="autoplay-fallback hide"></div>
          </div>
      </div>
      <Hangup @options="receiveOptions" :hangupStatus="hangupStatus"/>
      </div>
    </div>
  </div>
</template>
<script>
import ReceiveInvitations from '../../components/receiveInvitation'
import rtm from '../../utils/rtm/createClient'
import Hangup from '../../components/hangup'
import AgoraRTC from 'agora-rtc-sdk'

export default {
  components: {
    Hangup, ReceiveInvitations
  },
  data () {
    return {
      status: true, // 接受邀请中
      loginUser: this.$store.state.loginUser,
      chatUser: JSON.parse(rtm.remoteInvitation.content),
      hangupStatus: true, // 是否显示挂断提示
      settingOptions: { // 将所有数据统一放置在一个接口里边
        appID: this.$store.state.appId,
        channel: 'videochannel123456',
        codec: 'h264',
        mode: 'rtc',
        token: '', // 验证安全
        uid: this.$store.state.loginUser._id, // 用户
        microphoneId: this.$store.state.liveSetting.microphoneId,
        cameraId: this.$store.state.liveSetting.cameraId
      },
      rtc: this.$store.state.rtc // 一个对话只能有一个client，设置为全局只能有一个client
    }
  },
  methods: {
    receiveOptions (option) { // callee 接受邀请操作
      if (option === 'accept') {
        rtm.remoteInvitation.accept()
        this.status = false
      } else if (option === 'refuse') {
        console.log(rtm.remoteInvitation)
        rtm.remoteInvitation.refuse()
      } else {
        console.log('hangup') // 添加挂断之后断开连接清除流等操作。
      }
      setTimeout(() => {
        this.$emit('receiveOptions', option)
      }, 500)
    }
  },
  created () {
    rtm.remoteInvitation.on('RemoteInvitationRefused', () => { // callee拒绝邀请
      console.log('refused')
    })
    rtm.remoteInvitation.on('RemoteInvitationAccepted', () => { // callee 接受邀请
      console.log('accepted')
      this.$store.dispatch('emitRtcCreateClient', AgoraRTC.createClient({mode: this.settingOptions.mode, codec: this.settingOptions.codec}))
      this.$store.dispatch('emitRtcAddParams', this.settingOptions)

      this.rtc.client.init(this.settingOptions.appID, () => {
        this.rtc.client.join(this.settingOptions.token ? this.settingOptions.token : null, this.settingOptions.channel, this.settingOptions.uid ? +this.settingOptions.uid : null, (uid) => { // channel是通过rtm传递过来
          console.log(`join channel : success,uid: ${uid}`)

          this.$store.dispatch('emitRtcCreateLocalStream', AgoraRTC.createStream({ // 创建流
            streamId: this.rtc.params.uid,
            audio: true,
            video: true,
            screen: false,
            microphoneId: this.settingOptions.microphoneId,
            cameraId: this.settingOptions.cameraId
          }))
          this.rtc.localStream.init(() => { // 初始化一个流
            this.rtc.localStream.play('local_stream', (err) => { // 播放本地流
              console.log(err)
            })
            var oldState = this.rtc.published
            this.rtc.client.publish(this.rtc.localStream, (err) => {
              this.$store.dispatch('emitRtcPublish', oldState) // 流发布失败，publish状态不变
              console.log('publish failed')
              console.log(err)
            })
            this.$store.dispatch('emitRtcPublish', true) // 流发布成功，更改publish状态
          }, (err) => {
            console.log('init local stream fail: ', err)
          })
          this.rtc.client.on('stream-added', (evt) => { // 监听remote stream是否存在
            var stream = evt.stream
            this.$nextTick(() => {
              stream.play('remote_video_123456', (err) => { // 播放caller流
                console.log('remote stream error')
                console.log(err)
              })
            })
            this.rtc.client.subscribe(stream, (err) => {
              if (err) {
                console.log('subscribe fail')
                console.log(err)
              }
            })
          })
          this.rtc.client.on('peer-leave', (evt) => {
            this.$emit('receiveOptions', 'hangup')
            console.log('peer-leave')
          })
        })
      })
    })
    rtm.remoteInvitation.on('RemoteInvitationCanceled', () => {
      console.log('cancel')
      this.$emit('receiveOptions', 'refuse')
    })
    rtm.remoteInvitation.on('RemoteInvitationFailure', () => {
      console.log('failure')
      this.$emit('receiveOptions', 'refuse')
    })
  },
  mounted () {
    if (!this.status) { // 监听鼠标，是否显示挂断提示
      this.$refs.content.addEventListener('mouseover', (e) => {
        this.hangupStatus = true
      })
      this.$refs.content.addEventListener('mouseout', (e) => {
        this.hangupStatus = false
      })
    }
  }
}
</script>
<style lang="less" scoped>
@import url('../../assets/css/videoModal.css');
.videoModal{
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    z-index: 10;
}
.content{
    position: relative;
    width:300px;
    height: 400px;
    .agora-theme{
      width: 100%;
      height: 100%;
      background: black;
      border-radius: 5px;
    .video-grid{
      .remote_video{
        width: 100%;
        height: 100%;
        background: black;
        border-radius: 5px;
      }
      .video-view{
        position: absolute;
        top: 10px;
        right: 10px;
        width: 100px;
        height: 100px;
        background: blanchedalmond;
        z-index: 99;
      }
    }
    }
}
</style>
