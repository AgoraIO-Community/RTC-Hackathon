<template>
  <div class="recommandLive">
    <div class="title">
      <div class="left">才艺直播</div>
      <div class="right" @click="to()">更多 <i class="el-icon-d-arrow-right"></i></div>
    </div>
    <div class="recommand-list" >
      <div class="per-live" v-for="(item,index) in lives" :key="index" @click="watchLive(item)">
        <img src="../../static/live.jpg"  style="width:210px;height:160px" v-if="item.isLive"/>
        <img src="../../static/unlive.jpg" style="width:210px;height:160px"  v-else/>
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
      lives: []
    }
  },
  methods: {
    watchLive (item) {
      this.$router.push({path: '/user/liveroom', query: {roomId: item.roomId}})
    },
    to () {
      this.$router.push('/alllives')
    }
  },
  created () {
    API.Live.getRecommandLive({}, (responseData) => {
      if (responseData.code === 200) {
        this.lives = responseData.data
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
.recommandLive{
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
    .per-live{
      flex: 1;
      display: flex;
      flex-direction: column;
      align-items: center;
      cursor: pointer;
      img{
        margin-top: 10px;
        width: 210px;
        height: 160px;
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
    .per-live:hover{
      background-color: rgb(66, 66, 66);
    }
  }
}
</style>
