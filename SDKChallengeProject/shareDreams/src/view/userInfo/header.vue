<template>
    <div>
        <Header />
        <div class="userinfo">
            <div class="head">
                <div class="intro">
                <div class="user-avatar">
                    <img :src="loginUser.userAvatar" style="width:80px;height:80px;border-radius:50%;"/>
                </div>
                <div class="user-userinfo">
                    <p class="user-name">{{loginUser.userName}}</p>
                    <div class="user-intro-h"><input type="text" :value="loginUser.userIntro" disabled /></div>
                </div>
                </div>
            </div>
            <div class="title">
                <div class="my-info" :class="showBody=='userinfo'?'selected':''" @click="select('userinfo')"><i class="el-icon-user-solid" ></i>个人中心</div>
                <div class="my-video"  :class="showBody==='record'?'selected':''" @click="select('record')"><i class="el-icon-video-camera-solid"></i>录制视频</div>
                <div class="my-live"  :class="showBody==='live'?'selected':''" @click="select('live')"><i class="el-icon-s-platform"></i>开启直播</div>
            </div>
        </div>
    </div>
</template>
<script>
import Header from '../../components/header'
export default {
  components: {
    Header
  },
  data () {
    return {
      loginUser: this.$store.state.loginUser,
      showBody: 'userinfo'
    }
  },
  methods: {
    select (title) {
      this.showBody = title
      this.$router.push(`/user/${title}`)
    },
    active (path) {
      path = path.replace(/\/user\//gi, '')
      this.showBody = path
    }
  },
  mounted () {
    this.active(this.$route.path)
  }
}
</script>
<style lang="less" scoped>
.userinfo{
    width: 90%;
    margin-left: 5%;
    padding-bottom: 10px;
    .head{
        background-image: url('../../../static/logo.png');
        background-repeat: no-repeat;
        background-position-x: center;
        background-color: rgb(27, 27, 36);
        width: 100%;
        height:200px;
        margin-bottom: 2px;
        .intro{
            flex:1;
            display: flex;
            flex-direction: row;
            padding-top: 100px;
            padding-left: 20px;
            .user-avatar{
                border: 2px solid hsla(0,0%,100%,.4);
                width: 80px;
                height: 80px;
                border-radius: 50%;
                .avatar-cover{
                    position: absolute;
                    top: 0;
                    left: 0;
                    width: 64px;
                    height: 64px;
                    width: 80px;
                    height: 80px;
                    background: rgba(0, 0, 0, 0.6);
                    font-size: 12px;
                    color: #fff;
                    line-height: 64px;
                    text-align: center;
                    opacity: 0;
                    border-radius: 50%;
                    transition: opacity .2s;
                }
            }
            .user-userinfo{
                flex:1;
                padding-left: 20px;
                flex-direction: column;
                .user-name{
                    font-size: 25px;
                    color: white;
                    width: 600px;
                }
                .user-intro-h{
                    margin-top: -20px;
                    font-size: 10px;
                    input{
                        width: 600px;
                        background: transparent;
                        border-radius: 4px;
                        border: none;
                        box-shadow: none;
                        outline: none;
                        color: #d6dee4;
                        font-size: 12px;
                        font-family: Microsoft Yahei;
                        line-height: 26px;
                        height: 26px;
                        margin-left: -5px;
                        padding: 0 5px;
                        position: relative;
                        top: -1px;
                        width: 730px;
                    }
                    input:hover{
                        border: 1px solid rgb(130, 180, 197);
                    }
                }
            }
        }
    }
    .title{
        width: 100%;
        height:60px;
        background-color: white;
        flex: 1;
        display: flex;
        flex-direction: row;
        font-size: 13px;
        .my-info{
            flex:1;
            display: flex;
            justify-content: center;
            align-items: center;
            text-align: center;
            cursor: pointer;
            i{
                font-size: 20px;
                padding-right: 5px;
                color: lightgreen;
            }
        }
        .my-info:hover{
            border-bottom: 2px solid gold;
        }
        .selected{
            border-bottom: 2px solid gold;
        }
        .my-video{
            flex: 1;
            display: flex;
            justify-content: center;
            align-items: center;
            text-align: center;
            cursor: pointer;
            i{
                font-size: 20px;
                padding-right: 5px;
                color:lightsalmon;
                margin-top:5px;
            }
        }
        .my-video:hover{
            border-bottom: 2px solid gold;
        }
        .my-live{
            flex: 1;
            display: flex;
            justify-content: center;
            align-items: center;
            text-align: center;
            cursor: pointer;
            i{
                font-size: 20px;
                padding-right: 5px;
                margin-top:5px;
                color: lightblue;
            }
        }
        .my-live:hover{
            border-bottom: 2px solid gold;
        }
        .my-supportors{
            flex: 1;
            display: flex;
            justify-content: center;
            align-items: center;
            text-align: center;
            i{
                font-size: 20px;
                padding-right: 5px;
                color: red;
            }
        }
        .my-supportors:hover{
            border-bottom: 2px solid gold;
        }
    }
}
</style>
