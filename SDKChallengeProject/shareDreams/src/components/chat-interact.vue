<template>
    <div class="chat-room">
        <div class="chat-room-title">聊天互动框</div>
        <div class="chat-room-body">
            <div class="message-list">
                <div v-for="(item,index) in messageLists" :key="index" class="per-message" >
                    <div class="message-userName">{{item.userName}}</div><p style="margin-top:10px">:</p>
                    <div class="trangle"></div>
                    <div class="message-content">{{item.message}}</div>
                </div>
            </div>
            <div class="send-bar">
                <textarea class="input-value" v-model="text" />
                <div class="button-group">
                    <button @click="send()" :disabled="text===''">发送</button>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import rtm from '../utils/rtm/createClient'
import rtmMethods from '../view/message/index'
import API from '../utils/API'
export default { // 聊天框
  props: ['roomId'],
  data () {
    return {
      messageLists: [],
      loginUser: this.$store.state.loginUser,
      params: { // message
        appId: this.$store.state.appId,
        accountName: this.$store.state.loginUser.userName,
        channelName: this.roomId // 聊天频道就是房间号
      },
      text: ''
    }
  },
  methods: {
    send () { // 添加未登录用户发布时提示登录
      if (!this.loginUser.userName) {
        this.$toast('请先登录')
      } else {
        if (this.text.trim() !== '') {
          this.messageLists.push({userAvatar: this.$store.state.loginUser.userAvatar, message: this.text, userName: this.loginUser.userName})
          rtmMethods.rtmSendChannelMessage({ // 发送至1v1房间内
            appId: this.params.appId,
            accountName: this.params.accountName,
            channelName: this.roomId,
            channelMessage: JSON.stringify({userAvatar: this.$store.state.loginUser.userAvatar, message: this.text, userName: this.loginUser.userName})
          }, rtm)
          API.Live.saveLiveRoomMessage({roomId: this.roomId, message: this.text, userName: this.loginUser.userName, time: new Date().getTime()}, (responseData) => {
            if (responseData.code === 200) {
              this.text = ''
            }
          }, (errorData) => {
            this.$toast('发送发生错误')
          })
          this.dropToBottom()
        }
      }
    },
    dropToBottom () {
      this.$nextTick(() => { // 使用nextTick()更新dom
        var ele = document.getElementsByClassName('message-list')[0]
        ele.scrollTop = ele.scrollHeight
      })
    }
  },
  created () {
    window.addEventListener('keyup', (e) => {
      if (e.keyCode === 13) {
        this.send()
      }
    })
    setTimeout(() => {
      rtmMethods.rtmJoin(this.params, rtm)
    }, 500)
    rtm.on('ChannelMessage', ({channelName, args}) => { // 获取通道信息
      const [ message ] = args
      const data = JSON.parse(message.text)
      let params = {userAvatar: data.userAvatar, message: data.message, userName: data.userName}
      console.log(params)
      this.messageLists.push(params)
      this.dropToBottom()
    })
    API.Live.getLiveRoomMessage({roomId: this.roomId}, (responseData) => { // 获取聊天信息
      if (responseData.code === 200) {
        if (responseData.data !== null) {
          this.messageLists = responseData.data
          this.dropToBottom() // 下拉至最底
        }
      }
    }, (errorData) => {
      console.log(errorData)
    })
  }
}
</script>
<style lang="less" scoped>
.chat-room{
  flex:1;
  display: flex;
  flex-direction: column;
  .chat-room-title{
  height: 30px;
  display: flex;
  flex-direction: row;
  align-items: center;
  padding-left: 20px;
  font-size: 10px;
  }
  .chat-room-body{
    flex-direction: column;
    display: flex;
    height: 590px;
    border:1px solid rgb(27, 27, 36);
    .message-list{
      height: 480px;
      width: 100%;
      overflow-y: scroll;
      overflow-x: hidden;
      font-size: 10px;
      background-color: white;
      .per-message{
        min-height: 40px;
        flex: 1;
        display: flex;
        flex-direction: row;
        width: 320px;
        margin: 5px 0;
        .message-userName{
          color: black;
          margin-top: 10px;
          width: 50px;
          text-align: center;
          text-overflow: ellipsis;
          overflow: hidden;
          white-space: nowrap;
        }
        p{
          color: black;
        }
        .trangle{
          width: 0;
          height: 0;
          margin-top: 10px;
          border-top:5px solid transparent;
          border-left:5px solid transparent;
          border-bottom:5px solid transparent;
          border-right:5px solid lightblue;
        }
        .message-content{
          max-width: 240px;
          background-color: lightblue;
          margin-top: 10px;
          padding: 7px;
          border-radius: 0 5px 5px 5px;
          text-align: justify;
          text-justify: newspaper;
          word-break: break-all;
        }
      }
    }
    .message-list::-webkit-scrollbar {
      display: none;
    }
    .send-bar{
      margin-top: 5px;
      height: 130px;
      background-color: white;
      .input-value{
        width: 99%;
        height: 100px;
        border:none;
        font-size: 13px;outline: none;
      }
      .input-value::-webkit-scrollbar {
        display: none;
      }
      .button-group{
        float: right;
        font-size: 10px;
        margin-right: 2%;
        button{
          width: 50px;
          height: 20px;
          font-size: 10px;
          margin-left: 5px;
        }
      }
    }
  }
}
</style>
