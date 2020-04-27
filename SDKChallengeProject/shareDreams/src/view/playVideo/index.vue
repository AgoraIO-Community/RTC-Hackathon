<template>
  <div class="playVideo">
    <keep-alive>
    <Header />
    </keep-alive>
    <div class="content">
      <div class="left">
        <div class="title">{{videoDetail.title}}</div>
        <div class="time">{{videoDetail.publishTime}}</div>
        <div class="video" v-if="isMp4">
          <pl-lazy time="1500">
            <Mp4Player :videoDetail="videoDetail"/>
          </pl-lazy>
        </div>
        <div class="video" v-else>
          <pl-lazy time="1000">
            <VideoPlayer :videoDetail="videoDetail"/>
          </pl-lazy>
        </div>
      </div>
      <div class="right">
        <div class="host-info">
          <img :src="uploader.userAvatar" />
          <div class="host-detail">
            <div class="host-name">{{uploader.userName}}</div>
            <div class="host-intro">{{uploader.userIntro}}</div>
          </div>
        </div>
        <div class="options" v-if="uploader._id!==loginUser._id">
          <div class="chat" @click="chat()">私信</div>
          <div class="support" @click="support()" :class="isSupported?'yes':''">{{isSupported?'已支持':'+ 支持'}}</div>
        </div>
        <div class="comments">
          <div class="title">评论列表</div>
          <div class="comment-list">
            <div class="tips">
              <div class="comment-content">内容</div>
              <div class="comment-time">发送时间</div>
            </div>
            <div class="all-comments">
              <div class="per-comment" v-for="(item,index) in comments" :key="index">
                <el-tooltip class="item" effect="dark" :content="item.comment" placement="bottom-start">
                  <div class="per-content">{{item.comment}}</div>
                </el-tooltip>
                <div class="per-time">{{getTime(item.time)}}</div>
              </div>
            </div>
          </div>
        </div>
        <div class="make-comment">
          <textarea class="input-value" v-model="text" />
          <div class="submit" @click="send()"><p>评论</p></div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import VideoPlayer from '../../components/videoPlayer/index'
import Mp4Player from '../../components/videoPlayer/mp4Player'
import Header from '../../components/header'
import API from '../../utils/API'
import * as time from '../../utils/timeFormat'
export default {
  data () {
    return {
      comments: [], // 评论列表
      videoDetail: {}, // 视频信息
      text: '',
      loginUser: this.$store.state.loginUser,
      uploader: {}, // 视频上传用户
      isSupported: false, // 是否支持该追梦人
      isMp4: false // 视频格式是否是mp4
    }
  },
  components: {
    Header, VideoPlayer, Mp4Player
  },
  methods: {
    send () { // 发送评论
      if ((this.text).trim() !== '') {
        if (!this.loginUser.userName) {
          this.$toast('请先登录')
          return
        }
        let params = {comment: this.text, time: new Date().getTime()}
        this.$nextTick(() => {
          this.comments.unshift(params) // 将最新发送的评论置顶
        })
        API.Video.comment({videoId: this.$route.query.videoId, comment: this.text, userName: this.loginUser.userName, time: new Date().getTime()}, (responseData) => {
          if (responseData.code === 200) {
            console.log(responseData)
            this.text = ''
          }
        }, (errorData) => {
          console.log(errorData)
        })
      }
    },
    chat () { // 与视频制作者聊天
      this.$router.push({name: 'message', params: {chatUser: this.uploader}})
    },
    getTime (data) { // 获取视频发布时间
      return time.getGMTTimeDiff(data)
    },
    support () { // 是否支持该视频制作者
      if (this.isSupported) {
        API.users.unSupport({userId: this.loginUser._id, supportUserId: this.uploader._id}, (responseData) => {
          if (responseData.code === 200) {
            this.$toast('取消支持')
            this.$nextTick(() => {
              this.isSupported = false
            })
          }
        }, (err) => {
          this.$toast('支持失败，请重试')
          console.log(err)
        })
      } else {
        API.users.support({userId: this.loginUser._id, supportUserId: this.uploader._id}, (responseData) => {
          if (responseData.code === 200) {
            this.$toast('支持成功')
            this.$nextTick(() => {
              this.isSupported = true
            })
          }
        }, (err) => {
          this.$toast('支持失败，请重试')
          console.log(err)
        })
      }
    }
  },
  created () {
    window.addEventListener('keyup', (e) => { // 监听键盘enter键
      if (e.keyCode === 13) {
        this.send()
      }
    })
    API.record.searchVideo({_id: this.$route.query.videoId, userId: this.loginUser._id}, (responseData) => { // 通过url参数搜索视频信息
      if (responseData.code === 200) {
        console.log(responseData)
        this.videoDetail = responseData.data
        this.isMp4 = responseData.data.videoUrl.substring(responseData.data.videoUrl.lastIndexOf('.')) === '.mp4'
        this.uploader = responseData.user
        this.isSupported = responseData.isSupported
        this.videoDetail.publishTime = time.getGMTTimeDiff(this.videoDetail.publishTime)
      }
    }, (errorData) => {
      console.log(errorData)
    })
    API.Video.getAllComments({videoId: this.$route.query.videoId}, (responseData) => { // 获取视频的评论
      if (responseData.code === 200) {
        console.log(responseData)
        this.comments = responseData.data
      }
    }, (errorData) => {
      console.log(errorData)
    })
  }
}
</script>
<style lang="less" scoped>
.playVideo{
  background-color: black;
  width: 100%;
  min-height: 100%;
  padding-bottom: 20px;
  .content{
    margin-top:40px;
    width: 1100px;
    margin: 40px auto;
    background-color: rgb(27, 27, 36);
    border-radius: 5px;
    display: flex;
    padding-bottom: 30px;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    .left{
      width:720px;
      display: flex;
      flex-direction: column;
      color: white;
      .title{
        margin:40px 0px 5px 50px;
        font-weight: bold;
        font-size: 25px;
      }
      .time{
        margin:0px 0px 5px 50px;
        font-size: 10px;
        color: rgb(66, 66, 66);
      }
      .video{
        margin:10px 0px 5px 50px;
        width: 640px;
        height: 400px;
      }
    }
    .right{
      flex: 1;
      display: flex;
      flex-direction: column;
      .host-info{
        margin-top: 20px;
        width: 300px;
        height: 60px;
        display: flex;
        flex-direction: row;
        img{
          width: 60px;
          height: 60px;
          border-radius: 50%;
        }
        .host-detail{
          margin-top: 10px;
          margin-left: 10px;
          color:white;
          display: flex;
          flex-direction: column;
          .host-name{
            font-size: 13px;
            font-weight: bold;
            color: lightblue;
          }
          .host-intro{
            margin-top: 5px;
            font-size: 10px;
            color: rgb(66, 66, 66);
          }
        }
      }
      .options{
        margin-left: 70px;
        height: 40px;
        display: flex;
        flex-direction: row;
        color: white;
        font-size: 13px;
        .chat{
          width: 50px;
          height: 30px;
          background-color: #0B8DD9;
          border-radius: 5px;
          flex-direction: row;
          display: flex;
          justify-content: center;
          align-items: center;
          cursor: pointer;
        }
        .support{
          margin-left: 10px;
          padding: 0 10px;
          height: 30px;
          background-color: #0B8DD9;
          border-radius: 5px;
          flex-direction: row;
          display: flex;
          justify-content: center;
          align-items: center;
          cursor: pointer;
        }
        .yes{
          background-color: grey
        }
      }
      .comments{
        margin-top: 20px;
        width: 300px;
        height: 300px;
        .title{
          background-color: rgb(66, 66, 66);
          height: 30px;
          border-radius: 5px 5px 0 0 ;
          display: flex;
          flex-direction: row;
          align-items: center;
          padding-left: 20px;
          color: white;
          font-size: 13px;
        }
        .comment-list{
          height: 270px;
          background-color: white;
          display: flex;
          flex-direction: column;
          .tips{
            margin-top: 5px;
            margin-left: 20px;
            height: 20px;
            display: flex;
            flex-direction: row;
            color: rgb(66, 66, 66);
            font-size: 10px;
            .comment-content{
              flex: 1;
              margin-left: 10px;
            }
            .comment-time{
              flex: 1;
              text-align: right;
              margin-right: 30px;
            }
          }
          .all-comments{
            margin-left: 20px;
            height: 250px;
            font-size: 10px;
            overflow: scroll;
            .per-comment{
              display: flex;
              margin-bottom: 5px;
              flex-direction: row;
              .per-content{
                width: 190px;
                text-overflow: ellipsis;
                overflow:hidden;
                white-space:nowrap;
                margin-left: 0px;
              }
              .per-time{
                width: 100px;
                text-align: center;
                margin-right: 20px;
                color: rgb(66, 66, 66);
              }
            }
          }
        }
        .all-comments::-webkit-scrollbar {
          display: none;
        }
      }
      .make-comment{
        margin-top: 20px;
        height: 50px;
        flex-direction: row;
        display: flex;
        .input-value{
          outline: none;
          border: none;
          height: 35px;
          width: 230px;
        }
        .input-value::-webkit-scrollbar {
          display: none;
        }
        .submit{
          margin-left: 10px;
          height: 40px;
          width: 60px;
          background-color: #0B8DD9;
          border-radius: 5px;
          flex-direction: row;
          display: flex;
          justify-content: center;
          align-items: center;
          color: white;
          font-size: 13px;
          cursor: pointer;
        }
      }
    }
  }
}
</style>
