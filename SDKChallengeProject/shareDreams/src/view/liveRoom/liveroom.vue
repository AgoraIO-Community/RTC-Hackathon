<template>
  <div class="div">
    <Header />
    <div class="liveroom">
      <div class="body">
        <div class="body-left">
          <div class="main">
            <div class="host-info">
              <div class="host-avatar">
                <img :src="hostInfo.userAvatar" style="width:80px;height:80px;border-radius:50%;"/>
              </div>
              <div class="room-title">
                <el-tooltip class="item" effect="light" :content="roomInfo.title" placement="bottom-start">
                  <p class="main-title">{{roomInfo.title}}</p>
                </el-tooltip>
              </div>
            </div>
            <div class="agora-theme">
              <img src="../../../static/unlive.jpg"  v-if="!startLive"/>
              <div class="video-grid" id="video" v-else>
                <div class="video-view" id="remote_video_panel_123456">
                  <div id="remote_video_123456" class="video-placeholder"></div>
                  <div id="remote_video_info_123456" class="video-profile"></div>
                  <div id="video_autoplay_123456" class="autoplay-fallback hide"></div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="body-right">
          <pl-lazy time="1000">
            <ChatInteract :roomId="roomId"/>
          </pl-lazy>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import ChatInteract from '../../components/chat-interact'
import Header from '../../components/header'
import AgoraRTC from 'agora-rtc-sdk'
import API from '../../utils/API'
export default {
  components: {
    Header, ChatInteract
  },
  data () {
    return {
      startLive: false, // 是否直播中
      roomInfo: {}, // 直播间信息
      hostInfo: {}, // 主播信息
      roomId: '', // 房间号，从url参数中获取
      loginUser: this.$store.state.loginUser,
      rtc: {
        client: null,
        joined: false,
        published: false,
        localStream: null,
        remoteStreams: [],
        params: {}
      },
      settingOptions: { // live
        appID: this.$store.state.appId,
        channel: '',
        codec: 'h264',
        mode: 'live',
        token: '', // 验证安全
        uid: this.$store.state.loginUser._id, // 用户
        microphoneId: '',
        cameraId: ''
      },
      text: '', // 评论
      messageList: [], // 评论列表
      params: {
        appId: this.$store.state.appId,
        accountName: this.$store.state.loginUser.userName,
        channelName: '' // 频道就是房间号roomId
      }
    }
  },
  methods: {
    start () {
      this.rtc.client = AgoraRTC.createClient({mode: this.settingOptions.mode, codec: this.settingOptions.codec})
      this.rtc.client.init(this.settingOptions.appID, () => {
        this.rtc.client.setClientRole('audience', (e) => { // 设置为观众模式
          if (!e) {
            console.log('setaudience: success')
          } else {
            console.log('setaudience: fail')
          }
        })
        this.rtc.client.join(this.settingOptions.token ? this.settingOptions.token : null, this.settingOptions.channel, this.settingOptions.uid ? +this.settingOptions.uid : null, (uid) => {
          console.log(`join channel : success,uid: ${uid}`)
          this.rtc.client.on('stream-added', (evt) => { // 监听remote stream是否存在
            this.startLive = !this.startLive
            var stream = evt.stream
            console.log('new stream added: ', stream.getId())
            this.$nextTick(() => { stream.play('remote_video_123456') })
            this.rtc.client.subscribe(stream, (err) => {
              if (err) {
                console.log('subscribe fail')
                console.log(err)
              }
            })
          })
          this.rtc.client.on('stream-removed', (evt) => { // 直播关闭
            this.startLive = !this.startLive
          })
        })
      })
    }
  },
  created () {
    this.roomId = this.$route.query.roomId
    this.settingOptions.channel = this.$route.query.roomId
    API.Live.getLiveRoomDetail({roomId: this.$route.query.roomId}, (responseData) => {
      if (responseData.code === 200) {
        console.log(responseData)
        this.hostInfo = responseData.hostInfo
        this.roomInfo = responseData.data
        this.start()
      } else {
        console.log(responseData)
      }
    }, (errorData) => {
      console.log(errorData)
    })
  },
  beforeDestroy () {
    if (this.rtc.client) {
      this.rtc.client.leave(() => { // 离开频道
        this.startLive = false
        this.$store.dispatch('emitRtcCreateLocalStream', null) // 清除所有数据
        this.$store.dispatch('emitRtcCreateClient', null)
        this.$store.dispatch('emitRtcJoin', false)
        console.log('成功离开频道')
      }, (err) => {
        console.log('channel leave fail')
        console.error(err)
      })
    }
  }
}
</script>
<style lang="less" scoped>
@import url('../../assets/css/common.css');
.div{
  width: 100%;
  background-color: black;
  min-height: 100%;
}
.liveroom{
    width: 1100px;
    margin: 0 auto;
    padding-bottom: 50px;
    .body{
        margin-top: 30px;
        width: 100%;
        height:auto;
        flex: 1;
        display: flex;
        flex-direction: row;
        .body-left{
            width: 680px;
            flex-direction: column;
            margin-left: 70px;
            display: flex;
            .main{
                background-color: rgb(27, 27, 36);
                border: 1px solid rgb(27, 27, 36);
                width: 640px;
                border-radius: 5px 5px 0 0 ;
                height: auto;
                margin-right:10px;
                height: 600px;
                .host-info{
                    flex: 1;
                    display: flex;
                    height: 120px;
                    flex-direction: row;
                    .host-avatar{
                        padding: 20px;
                    }
                    .room-title{
                        .main-title{
                          padding-top: 20px;
                            font-size: 20px;
                            font-weight: bold;
                            width: 500px;
                            color: white;
                            text-overflow: ellipsis;
                            overflow: hidden;
                            white-space: nowrap;
                        }
                    }
                }
                .agora-theme{
                    img{
                        width: 640px;
                        height: 480px;
                    }
                    .video-grid{
                        width: 640px;
                        height: 480px;
                    }
                }
            }
        }
        .body-right{
            width: 350px;
            background-color: rgb(27,27,36);
            border-radius: 5px;
            color: white;
            border: 1px solid rgb(27, 27, 36);
        }
    }
}
</style>
