<template>
  <div class="voiceModal">
    <ReceiveInvitations @options="receiveOptions" :chatUser="chatUser" v-show="status" method="语音"/>
    <div class="content" v-show="!status">
      <div class="top">
        <img :src="chatUser.userAvatar" />
      </div>
      <div class="bottom">
        <div class="tips">通话中</div>
          <div class="agora-theme">
            <div class="video-grid" id="video">
              <div class="remote_video">
                <div id="remote_video_123456" class="remote-video-placeholder"></div>
                <div id="remote_video_info_123456" class="video-profile"></div>
                <div id="video_autoplay_123456" class="autoplay-fallback hide"></div>
              </div>
            </div>
          </div>
          <Hangup @options="receiveOptions" :hangupStatus="hangupStatus"/>
        </div>
      </div>
    </div>
</template>
<script>
import rtm from '../../utils/rtm/createClient'
import AgoraRTC from 'agora-rtc-sdk'
import ReceiveInvitations from '../../components/receiveInvitation' // 接收到邀请
import Hangup from '../../components/hangup' // 挂断组件
export default {
  props: ['chatUser', 'channel'],
  components: {
    ReceiveInvitations, Hangup
  },
  data () {
    return {
      status: true, // 接受邀请中
      hangupStatus: true, // 是否隐藏
      loginUser: this.$store.state.loginUser,
      remoteInvitation: rtm.remoteInvitation, // 不需要添加在这里，rtm中就有了
      rtc: this.$store.state.rtc,
      settingOptions: { // 将所有数据统一放置在一个接口里边
        appID: this.$store.state.appId,
        channel: this.channel,
        codec: 'h264',
        mode: 'rtc',
        token: '', // 验证安全
        uid: this.$store.state.loginUser._id, // 用户
        microphoneId: this.$store.state.liveSetting.microphoneId,
        cameraId: ''
      }
    }
  },
  methods: {
    receiveOptions (option) { // 接收到邀请的操作
      if (option === 'accept') {
        rtm.remoteInvitation.accept()
        this.status = false
      } else if (option === 'refuse') {
        console.log(rtm.remoteInvitation)
        rtm.remoteInvitation.refuse()
      } else {
        console.log('hangup')
      }
      setTimeout(() => {
        this.$emit('receiveOptions', option)
      }, 500)
    }
  },
  mounted () {
    rtm.remoteInvitation.on('RemoteInvitationRefused', () => { // callee拒绝
      console.log('refused')
    })
    rtm.remoteInvitation.on('RemoteInvitationAccepted', () => { // callee接受邀请
      console.log('accepted')
      this.$store.dispatch('emitRtcCreateClient', AgoraRTC.createClient({mode: this.settingOptions.mode, codec: this.settingOptions.codec})) // 当接受视频通话邀请时创建client
      this.$store.dispatch('emitRtcAddParams', this.settingOptions)
      this.rtc.client.init(this.settingOptions.appID, () => {
        this.rtc.client.join(this.settingOptions.token ? this.settingOptions.token : null, this.settingOptions.channel, this.settingOptions.uid ? +this.settingOptions.uid : null, (uid) => { // channel是通过rtm传递过来
          this.$store.dispatch('emitRtcCreateLocalStream', AgoraRTC.createStream({ // 创建流
            streamId: this.rtc.params.uid,
            audio: true,
            video: false,
            screen: false,
            microphoneId: this.settingOptions.microphoneId,
            cameraId: this.settingOptions.cameraId
          }))
          this.rtc.localStream.init(() => { // 初始化一个流
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
              stream.play('remote_video_123456', (err) => {
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
    rtm.remoteInvitation.on('RemoteInvitationCanceled', () => { // caller取消邀请
      console.log('cancel')
      this.$emit('receiveOptions', 'refuse')
    })
    rtm.remoteInvitation.on('RemoteInvitationFailure', () => { // callee 长时间未响应
      console.log('failure')
      this.$emit('receiveOptions', 'refuse')
    })
  }
}
</script>
<style lang="less" scoped>
@import url('../../assets/css/videoModal.css');
.voiceModal{
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
    z-index: 99;
}
.content{
    width:300px;
    height: 400px;
    background: black;
    flex-direction: column;
    display: flex;
    border-radius: 5px;
    .top{
        height: 260px;
        display: flex;
        flex-direction: row;
        justify-content: center;
        align-items: center;
        img{
            width: 120px;
            height: 120px;
            border-radius: 50%;
        }
    }
    .agora-theme{
      width: 100px;
      height: 50px;
      position: absolute;
    }
    .bottom{
      flex: 1;
      position: relative;
      .tips{
        color: white;
        margin-bottom: 20px;
        text-align: center;
      }
    }
}
</style>
