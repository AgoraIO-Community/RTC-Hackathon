
<template>
  <div class="body">
    <UserInfoHeader />
    <div class='live'>
      <div class="main">
        <div class="left">
          <div class="navigator">
            <div class="intro">导航</div>
            <div class="content" v-for="(item,index) in navigators" :key="index" :class="index===activeIndex?'selected':''" @click="selected(index)">
                <div class="per"><p>{{item.name}}</p></div>
            </div>
          </div>
        </div>
        <div class="right" v-if="!loading">
          <div class="unauthorize" v-if="!authorization">
            <div class="instruc"><p>需要获取直播权限才能查看</p></div>
            <div class="get" @click="get()"><p>申请权限</p></div>
          </div>
          <div  v-else>
            <Room v-if="0===activeIndex"/>
            <Setting v-if="1===activeIndex"/>
          </div>
        </div>
        <div class="right" v-else>
            <div class="loading" element-loading-text="拼命加载中" element-loading-spinner="el-icon-loading" v-loading="loading"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import API from '../../../utils/API'
import Setting from './setting'
import Room from './room'
import UserInfoHeader from '../header'
export default {
  components: {
    Setting, UserInfoHeader, Room
  },
  data () {
    return {
      loginUser: this.$store.state.loginUser,
      navigators: [{name: '我的直播'}, {name: '开播设置'}],
      activeIndex: 0,
      authorization: this.$store.state.live.roomId !== null,
      loading: true
    }
  },
  methods: {
    selected (index) {
      this.activeIndex = index
    },
    get () { // 开通权限
      API.Live.createLiveRoom({_id: this.loginUser._id}, (responseData) => {
        if (responseData.code === 200) {
          this.$toast('权限开通成功')
          this.$store.dispatch('emitSaveRoomId', responseData.data.roomId)
          localStorage.setItem('roomId', responseData.data.roomId) // 保存用户直播间Id
          this.$nextTick(() => {
            this.authorization = true
          })
        } else {
          this.$toast(responseData.message)
          console.log(responseData.data)
        }
      }, (error) => {
        console.log(error)
      })
    }
  },
  created () {
    let roomId = localStorage.getItem('roomId')
    if (roomId !== null) { // 直接通过判断本地用户直播间id是否存在来判断是否已获取权限
      this.$store.dispatch('emitSaveRoomId', roomId)
      this.loading = false
    } else { // 清除存储
      API.Live.getLiveRoomId({hostId: this.loginUser._id}, (responseData) => { // 获取直播间信息
        if (responseData.code === 200 && responseData.data !== null) {
          this.$store.dispatch('emitSaveRoomId', responseData.data.roomId)
          localStorage.setItem('roomId', responseData.data.roomId)
        } else { // 不存在就是没有权限
          this.authorization = false
        }
        this.loading = false
      }, (err) => {
        console.log(err)
      })
    }
  }
}
</script>

<style lang="less" scoped>
.body{
  width: 100%;
  height: auto;
  background-color: black;
  .live{
    margin-top: 10px;
    width: 90%;
    margin-left: 5%;
    padding-bottom: 50px;
    padding-top: 20px;
    .main{
      min-height: 500px;
      flex: 1;
      flex-direction: row;
      display: flex;
      .left{
        width: 160px;
        background-color:rgb(27, 27, 36);
        border-radius: 5px 0 0 5px;
        border-right:1px solid grey;
        .navigator{
          .intro{
            height: 50px;
            border-bottom:1px solid grey;
            flex: 1;
            display: flex;
            flex-direction: row;
            justify-content: center;
            align-items: center;
            color: rgb(66, 66, 66);
            font-size: 16px;
          }
          .content{
            .per{
              height: 50px;
              flex: 1;
              font-size: 12px;
              display: flex;
              flex-direction: row;
              justify-content: center;
              align-items: center;
              cursor: pointer;
              font-size: 16px;
              color: rgb(66, 66, 66);
            }
            .per:hover{
              background-color: grey;
            }
          }
        .selected{
          background-color: #0B8DD9;
          .per{
            color: white;
          }
        }
      }
    }
    .right{
      flex: 1;
      background-color: white;
      border-radius: 0 5px 5px 0;
    }
    .loading{
      margin-top: 200px;
    }
    .unauthorize{
      width: 100%;
      height: 100%;
      flex-direction: column;
      display: flex;
      justify-content: center;
      align-items: center;
      .instruc{
        p{
          color: rgb(66, 66, 66);
        }
      }
      .get{
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
}
}
</style>
