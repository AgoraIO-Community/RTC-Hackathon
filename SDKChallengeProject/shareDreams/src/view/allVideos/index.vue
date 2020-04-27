<template>
  <div class="index">
    <Header/>
    <div class="body">
      <div class="header">
        <input type="text" placeholder="输入标题" v-model="title"/>
        <div class="search" @click="search()"><p>查询</p></div>
      </div>
      <div v-if="!loading">
        <div class="content" v-if="currentList.length!==0">
          <div class="all-videos" v-for="(item,index) in currentList" :key="index">
            <div class="per-video" @click="watchVideo(item)">
              <img :src="item.poster===''?'../../../static/defaultPoster.jpg':item.poster" />
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
        <div class="content" v-else>
          <div class="no-result"><p>暂时没有该视频</p></div>
        </div>
      </div>
      <div v-else>
        <div class="loading" element-loading-text="拼命加载中" element-loading-spinner="el-icon-loading" v-loading="loading"></div>
      </div>
    </div>
  </div>
</template>
<script>
import Header from '../../components/header'
import API from '../../utils/API'
import Util from '../../utils/util'
export default {
  components: {Header},
  data () {
    return {
      currentList: [],
      totalList: [],
      title: '',
      loading: true
    }
  },
  methods: {
    handleCurrentChange (val) { // 应该放在后端处理
      this.currentList = this.totalList.slice(9 * (val - 1), 9 * (val - 1) + 9)
    },
    watchVideo (item) {
      this.$router.push({path: '/playvideo', query: {videoId: item._id}})
    },
    search () {
      if (this.title !== '') {
        Util.debounce(() => { // 防抖设置
          API.record.searchAllVideos({title: this.title}, (responseData) => { // 查询视频
            if (responseData.code === 200) {
              this.totalList = responseData.data
              this.currentList = this.totalList.slice(0, 9)
            }
          }, (err) => {
            console.log(err)
            this.totalList = []
          })
        }, 500)
      }
    }
  },
  created () {
    API.Video.getAllVideos({}, (responseData) => { // 获取所有视频信息
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
.index{
  background-color: black;
  width: 100%;
  min-height: 100%;
  padding-bottom: 10px;
  .body{
    margin-top:40px;
    width: 80%;
    margin-left: 10%;
    min-height: 550px;
    background-color: rgb(27, 27, 36);
    border-radius: 5px;
    display: flex;
    padding-bottom: 10px;
    flex-direction: column;
    .header{
      height: 80px;
      display: flex;
      flex-direction: row;
      justify-content: center;
      margin-top: 20px;
      input{
        width: 250px;
        background-color: #717171;
        border: none;
        outline: none;
        height: 30px;
        border-radius: 5px;
        font-size: 13px;
        color: white;
      }
      .search{
        margin-left: 10px;
        height: 30px;
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
    .loading{
      margin-top: 200px;
    }
    .content{
      flex: 1;
      min-height: 400px;
      flex-direction: row;
      flex-wrap: wrap;
      display: flex;
      .no-result{
        flex: 1;
        flex-direction: row;
        display: flex;
        justify-content: center;
        align-content: center;
        color: grey;
      }
      .all-videos{
        margin-bottom: 10px;
        .per-video{
          margin-left: 30px;
          width: 310px;
          height: 220px;
          text-align: center;
          img{
            margin-top: 10px;
            width: 210px;
            height: 160px;
            background-color: black;
            border: 1px solid grey;
            cursor: pointer;
          }
          .per-intro{
            color:white;
            font-size: 12px;
            //display: flex;
            //flex-direction: row;
            //justify-content: left;
            //align-items: flex-start;
            p{
              width: 270px;
              padding-left: 20px;
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
      .page{
        margin-top: 30px;
        margin-bottom: 20px;
        width: 100%;
        display: flex;
        justify-content: center;
      }
    }
  }
}
</style>
