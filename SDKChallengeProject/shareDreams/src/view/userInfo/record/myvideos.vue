<template>
  <div class="all-videos">
    <div class="intro">
      <p>我的视频</p>
    </div>
    <div v-if="!loading">
      <div class="content" v-if="currentList.length!==0">
        <div class="all-videos" v-for="(item,index) in currentList" :key="index" >
          <div class="per-video" @click="see(item,index)">
            <img :src="item.poster === ''?'../../../static/defaultPoster.jpg':item.poster" />
            <div class="status"><p>{{item.isPublish?'已发布':'未发布'}}</p></div>
            <div class="per-intro"><p>{{item.title}}</p></div>
          </div>
        </div>
        <el-pagination class="page"
          @current-change="handleCurrentChange"
          :page-size="9"
          layout=" prev, pager, next, jumper,  total"
          :total="totalList.length">
        </el-pagination>
      </div>
      <div class="none" v-else>
        <p>暂未录制任何视频</p>
        <div class="go" @click="navigateTo()">现在去录制</div>
      </div>
    </div>
      <div v-else>
        <div class="loading" element-loading-text="拼命加载中" element-loading-spinner="el-icon-loading" v-loading="loading"></div>
      </div>
    </div>
</template>
<script>
import API from '../../../utils/API'
export default {
  data () {
    return {
      totalList: [],
      currentList: [],
      loginUser: this.$store.state.loginUser,
      loading: true
    }
  },
  methods: {
    handleCurrentChange (val) { // 应该放在后端处理
      this.currentList = this.totalList.slice(9 * (val - 1), 9 * (val - 1) + 9)
    },
    navigateTo () {
      this.$router.push(`/user/record`)
    },
    see (item, index) {
      this.$router.push({path: `/user/record/playvideo`, query: {id: item._id}})
    }
  },
  created () {
    API.record.getRecordVideos({userId: this.loginUser._id}, (responseData) => { // 获取用户录制的视频
      if (responseData.code === 200) {
        this.totalList = responseData.data
        console.log(this.totalList)
        this.loading = false
        this.currentList = this.totalList.slice(0, 9)
      } else {
        console.log(responseData)
      }
    }, (errorData) => {
      console.log(errorData)
    })
  }
}
</script>
<style lang="less" scoped>
.all-videos{
  margin-bottom: 20px;
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
  .loading{
    margin-top: 200px;
  }
  .content{
    padding-top: 30px;
    min-height: 250px;
    flex:1;
    flex-wrap: wrap;
    display: flex;
    flex-direction: row;
    .all-videos{
      margin-bottom: 30px;
      .per-video{
        margin-left: 30px;
        width: 300px;
        height: 220px;
        text-align: center;
        cursor: pointer;
        position: relative;
        img{
          margin-top: 10px;
          width: 210px;
          height: 160px;
          border: 1px solid grey;
        }
        .status{
          position: absolute;
          z-index: 99;
          color: white;
          top: 140px;
          left: 45px;
          width: 210px;
          height: 30px;
          background: grey;
          opacity: 0.4;
          display: flex;
          justify-content: center;
          align-items: center;
          p{
            font-size: 12px;
          }
        }
        .per-intro{
          font-size: 12px;
          p{
            width: 300px;
            padding-left: -20px;
            text-overflow: ellipsis;
            overflow:hidden;
            white-space:nowrap;
          }
        }
      }
    }
    .page{
      width: 100%;
      display: flex;
      margin-top: 50px;
      justify-content: center;
    }
  }
  .none{
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    min-height: 250px;
    p{
      color: rgb(66, 66, 66);
    }
    .go{
      margin-left: 10px;
      padding: 0 10px;
      height: 30px;
      background-color: #0B8DD9;
      border-radius: 5px;
      flex-direction: row;
      display: flex;
      justify-content: center;
      align-items: center;
      color: white;
      font-size: 12px;
      cursor: pointer;
    }
  }
}
</style>
