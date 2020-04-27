<template>
  <div class="recommandVideo">
    <div class="title">
      <div class="left">才艺视频</div>
      <div class="right" @click="to()">更多 <i class="el-icon-d-arrow-right"></i></div>
    </div>
    <div class="recommand-list" >
      <div class="per-video" v-for="(item,index) in videos" :key="index" @click="watchVideo(item)">
        <img :src="item.poster===''?'../../static/defaultPoster.jpg':item.poster" />
        <div class="intro"><p>{{item.title}}</p></div>
       </div>
    </div>
  </div>
</template>

<script>
import API from '../utils/API'
export default {
  data () {
    return {
      videos: []
    }
  },
  methods: {
    watchVideo (item) {
      this.$router.push({path: '/playvideo', query: {videoId: item._id}})
    },
    to () {
      this.$router.push('/allvideos')
    }
  },
  created () {
    API.Video.getRecommandVideos({}, (responseData) => {
      if (responseData.code === 200) {
        this.videos = responseData.data
      } else {
        console.log(responseData)
      }
    }, (errorData) => {
      console.log(errorData)
    })
  }
}
</script>
<style lang='less' scoped>
.recommandVideo{
  margin: 5px;
  height:250px;
  background-color: rgb(27, 27, 36);
  border-radius: 5px;
  .title{
    background-color: rgb(66, 66, 66);
    border-radius: 5px;
    font-size: 12px;
    height: 25px;
    color: white;
    font-size: bold;
    display: flex;
    flex-direction: row;
    align-items: center;
  }
  .left{
    flex: 1;
    text-align: left;
    padding-left: 20px;
  }
  .right{
    flex: 1;
    text-align: right;
    padding-right: 20px;
    cursor: pointer;
  }
  .recommand-list{
    flex: 1;
    height: 225px;
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    .per-video{
      flex: 1;
      display: flex;
      flex-direction: column;
      align-items: center;
      cursor: pointer;
      img{
        margin-top: 10px;
        width: 210px;
        height: 160px;
        background-color: black;
        border: 1px solid grey;
      }
      .intro{
        color:white;
        font-size: 12px;
        p{
          width: 240px;
          text-align: center;
          text-overflow: ellipsis;
          overflow:hidden;
          white-space:nowrap;
        }
      }
    }
    .per-video:hover{
      background-color: rgb(66, 66, 66);
    }
  }
}
</style>
