<template>
  <div class="playVideo">
    <div class="header">
      <div class="back" @click="back()"><i class="el-icon-back"></i><p>返回</p></div>
    </div>
    <div class="body">
      <div class="main">
        <div class="video" v-if="isMp4">
          <pl-lazy time="1000">
            <Mp4Player :videoDetail="videoDetail"/>
          </pl-lazy>
        </div>
        <div class="video" v-else>
          <pl-lazy time="1000">
            <VideoPlayer :videoDetail="videoDetail"/>
          </pl-lazy>
        </div>
      </div>
      <div class="time">{{videoDetail.recordTime+' '}}录制</div>
      <el-divider></el-divider>
      <div class="title">
        <p>视频标题</p><input type="text" v-model="videoDetail.title" placeholder="视频标题"/>
      </div>
      <el-divider></el-divider>
      <div class="poster">
        <p>添加封面：</p>
        <img :src="poster" @click="open()"/>
        <input type="file" name="img" class="upload" @change="addImg" hidden ref="inputer" accept="image/png,image/jpeg,image/jpg"/>
      </div>
      <el-divider></el-divider>
      <div class="btn-group">
        <div class="publish" @click="publishVideo()" ><p>{{videoDetail.isPublish?'取消发布':'发布'}}</p></div>
        <div class="save" @click="saveVideo()"><p>保存</p></div>
        <div class="delete" @click="deleteVideo()"><p>删除</p></div>
      </div>
    </div>
  </div>
</template>
<script>
import Mp4Player from '../../../components/videoPlayer/mp4Player'
import VideoPlayer from '../../../components/videoPlayer/index'
import * as time from '../../../utils/timeFormat'
import API from '../../../utils/API'
import util from '../../../utils/util'
export default {
  components: {
    VideoPlayer, Mp4Player
  },
  data () {
    return {
      videoDetail: {},
      file: '',
      poster: '../../../../static/add.png',
      isMp4: false
    }
  },
  methods: {
    open () { // 隐藏input，通过open打开input
      this.$refs.inputer.dispatchEvent(new MouseEvent('click'))
    },
    addImg () {
      var url = this.$refs.inputer.files[0]
      this.file = this.$refs.inputer.files[0]
      if (util.validateFile(this.file)) {
        if (url !== undefined) {
          this.poster = util.getObjectURL(url)
        }
      } else {
        this.file = ''
        this.$toast('请选择图片格式')
      }
    },
    back () {
      this.$router.push('/user/record/myvideos').catch((err) => {
        console.log(err)
      })
    },
    publishVideo () { // 是否发布视频
      if (!this.videoDetail.isPublish) {
        if (this.videoDetail.title !== '') {
          API.record.publishVideo({_id: this.videoDetail._id}, (responseData) => {
            if (responseData.code === 200) {
              this.$toast('发布成功')
            }
          }, (errorData) => {
            console.log(errorData)
          })
        }
      } else {
        API.record.unpublishVideo({_id: this.videoDetail._id}, (responseData) => {
          if (responseData.code === 200) {
            this.$toast('取消发布成功')
            this.$router.go(0)
          }
        }, (errorData) => {
          console.log(errorData)
        })
      }
    },
    saveVideo () { // 保存视频
      if (this.file === '') {
        API.record.updateVideoDetail({title: this.videoDetail.title, _id: this.$route.query.id}, (responseData) => {
          if (responseData.code === 200) {
            this.$toast('保存成功')
            console.log(responseData.data)
          }
        }, (errorData) => {
          console.log(errorData)
        })
      } else {
        API.record.updateVideoDetailWithPoster({title: this.videoDetail.title, poster: this.file, _id: this.$route.query.id}, (responseData) => {
          if (responseData.code === 200) {
            this.$toast('保存成功')
            console.log(responseData)
          }
        }, (errorData) => {
          console.log(errorData)
        })
      }
    },
    deleteVideo () { // 删除视频
      API.record.deleteVideo({_id: this.$route.query.id}, (responseData) => {
        if (responseData.code === 200) {
          this.$toast('删除成功')
          this.$router.back()
        }
      }, (errorData) => {
        console.log(errorData)
      })
    }
  },
  created () {
    API.record.searchVideo({_id: this.$route.query.id}, (responseData) => { // 通过url参数查找视频
      if (responseData.code === 200) {
        this.videoDetail = responseData.data
        this.videoDetail.recordTime = time.getGMTTimeDiff(this.videoDetail.recordTime)
        this.poster = responseData.data.poster || this.poster
        this.isMp4 = responseData.data.videoUrl.substring(responseData.data.videoUrl.lastIndexOf('.')) === '.mp4'
      }
    }, (errorData) => {
      console.log(errorData)
    })
  }
}
</script>
<style lang="less" scoped>
.playVideo{
    margin-bottom: 20px;
    .header{
      display: flex;
      flex-direction: row;
      height: 50px;
      border-bottom:1px solid grey;
      .back{
        display: flex;
        flex-direction: row;
        align-items: center;
        font-size: 13px;
        color: #0B8DD9;
        cursor: pointer;
        i{
          margin-left: 5px;
          font-size: 17px;
        }
        p{
          margin-left: 5px;
        }
      }
      .intro{
        height: 50px;
        display: flex;
        flex-direction: row;
        align-items: center;
        font-size: 13px;
        color: #0B8DD9;
        p{
          border-left: 2px solid #0B8DD9;
          padding-left: 10px;
          margin-left:20px;
        }
      }
    }
    .body{
      display: flex;
      flex-direction: column;
      .time{
        font-size: 10px;
        text-align: right;
        margin-right: 50px;
        color: rgb(66, 66, 66);
      }
      .main{
        margin-top: 30px;
        display: flex;
        justify-content: center;
        align-items: center;
        .video{
          width: 640px;
          height: 360px;
        }
      }
      p{
        font-size: 16px;
        font-weight: bold;
        color: rgb(66, 66, 66);
      }
      .title{
        display: flex;
        flex-direction: row;
        margin-left: 150px;
        input{
          background: transparent;
          color: rgb(66, 66, 66);
          width: 450px;
          margin: 10px 0 0 20px;
          height: 25px;
          font-weight: bold;
          font-size: 16px;
          border: none;
          position: relative;
          outline: none;
          border-bottom: 1px solid black;
        }
      }
      .poster{
        display: flex;
        flex-direction: row;
        margin-left: 150px;
        img{
          margin: 20px 0 0 20px;
          width: 200px;
          height: 200px;
          background: grey;
          cursor: pointer;
        }
      }
      .btn-group{
        display: flex;
        justify-content: center;
        align-items: center;
        .publish{
          min-width: 50px;
          height: 30px;
          background-color: #0B8DD9;
          border-radius: 5px;
          flex-direction: row;
          display: flex;
          justify-content: center;
          align-items: center;
          cursor: pointer;
          p{
            font-size: 13px;
            padding: 0 5px;
            color: white;
          }
        }
        .save{
          margin-left: 60px;
          width: 50px;
          height: 30px;
          background-color: #0B8DD9;
          border-radius: 5px;
          flex-direction: row;
          display: flex;
          justify-content: center;
          align-items: center;
          cursor: pointer;
          p{
            font-size: 13px;
            color: white;
          }
        }
        .delete{
          margin-left: 60px;
          width: 50px;
          height: 30px;
          background-color: grey;
          border-radius: 5px;
          flex-direction: row;
          display: flex;
          justify-content: center;
          align-items: center;
          cursor: pointer;
          p{
            font-size: 13px;
            color: white;
          }
        }
      }
    }
}
</style>
