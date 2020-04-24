<template>
  <div class="upload">
    <div class="intro">
      <p>上传视频</p>
    </div>
    <div class="body">
        <div >
      <div class="setting" v-loading="loading">
        <div class="video">
          <p>上传视频：</p>
          <div class="select" @click="selectVideo()"><p>{{videoFile?videoFile.name:'选择视频'}}</p></div>
          <input type="file" name="video"  @change="addVideo" hidden  ref="inputVideo" accept="video/mp4"/>
        </div>
        <el-divider></el-divider>
        <div class="title">
          <p>视频标题</p><input type="text" v-model="title" placeholder="视频标题"/>
        </div>
        <el-divider></el-divider>
        <div class="poster">
          <p>添加封面：</p>
          <img :src="poster" @click="open()"/>
          <input type="file" name="img" class="upload" @change="addImg" hidden ref="inputer" accept="image/png,image/jpeg,image/jpg"/>
        </div>
        <el-divider></el-divider>
        <div class="btn-group">
          <div class="save" @click="saveVideo()"><p>保存</p></div>
          <div class="delete" @click="deleteVideo()"><p>重置</p></div>
        </div>
      </div>
        </div>
    </div>
  </div>
</template>
<script>
import Util from '../../../utils/util'
import API from '../../../utils/API'
export default {
  data () {
    return {
      title: '',
      poster: '../../../../static/add.png',
      imgFile: '',
      videoFile: '',
      loginUser: this.$store.state.loginUser,
      loading: false
    }
  },
  methods: {
    selectVideo () {
      this.$refs.inputVideo.dispatchEvent(new MouseEvent('click'))
    },
    addVideo () {
      this.videoFile = this.$refs.inputVideo.files[0]
      if (!Util.validateVideo(this.videoFile)) {
        this.videoFile = ''
        this.$toast('暂时只支持mp4格式')
      }
      console.log(this.videoFile)
    },
    open () {
      this.$refs.inputer.dispatchEvent(new MouseEvent('click'))
    },
    addImg () {
      var url = this.$refs.inputer.files[0]
      this.imgFile = this.$refs.inputer.files[0]
      if (Util.validateFile(this.imgFile)) {
        if (url !== undefined) {
          this.poster = Util.getObjectURL(url)
        }
      } else {
        this.imgFile = ''
        this.$toast('请选择图片格式')
      }
    },
    saveVideo () {
      if (this.videoFile !== '') {
        this.loading = true
        API.users.uploadVideo({video: this.videoFile, userId: this.loginUser._id}, (responseData) => {
          if (responseData.code === 200) {
            if (this.imgFile === '') {
              API.record.updateVideoDetail({title: this.title, _id: responseData.data._id}, (responseData) => {
                if (responseData.code === 200) {
                  this.$toast('上传成功')
                  console.log(responseData.data)
                  this.loading = false
                }
              }, (errorData) => {
                console.log(errorData)
              })
            } else {
              API.record.updateVideoDetailWithPoster({title: this.title, poster: this.imgFile, _id: responseData.data._id}, (responseData) => {
                if (responseData.code === 200) {
                  this.$toast('上传成功')
                  console.log(responseData)
                  this.loading = false
                }
              }, (errorData) => {
                console.log(errorData)
              })
            }
          }
          console.log(responseData)
        }, (err) => {
          console.log(err)
        })
      }
    },
    deleteVideo () {
      if (this.imgFile !== '' || this.videoFile !== '' || this.title !== '') {
        this.imgFile = ''
        this.videoFile = ''
        this.title = ''
      }
    }
  }
}
</script>
<style lang="less" scoped>
.upload{
  padding-bottom: 20px;
  .intro{
    height: 50px;
    border-bottom:1px solid grey;
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
  .body{
    margin-top: 10px;
    max-width: 100%;
    overflow-x: hidden;
    height:auto;
    background-color: white;
    flex: 1;
    display: flex;
    flex-direction: row;
    .setting{
      margin-top: 30px;
      margin-left: 50px;
      .video{
            display: flex;
            flex-direction: row;
            margin-left: 150px;
            .select{
                cursor: pointer;
                margin: 10px 0 0 10px;
                border: 1px solid #0B8DD9;
                height: 30px;
                padding: 0 10px;
                border-radius: 5px;
                display: flex;
                justify-content: center;
                align-items: center;
                p{
                    color: #0B8DD9;
                    font-size: 10px;
                }
            }
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
}
</style>
