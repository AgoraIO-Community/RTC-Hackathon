
<template>
    <div class="body">
        <UserInfoHeader />
        <div class='userinfo'>
            <div class="body">
                    <div class="left">
                        <Navbar />
                    </div>
                    <div class="right">
                        <PlayVideo />
                    </div>
            </div>
        </div>
    </div>
</template>

<script>
import PlayVideo from './playVideo'
import UserInfoHeader from '../header'
import Navbar from './navbar'
export default {
  components: {
    UserInfoHeader, Navbar, PlayVideo
  },
  data () {
    return {
      loginUser: this.$store.state.loginUser,
      activeIndex: 0,
      videoDetail: {}
    }
  },
  methods: {
    selected (index) {
      this.activeIndex = index
    }
  }
}
</script>

<style lang="less" scoped>
.body{
    width: 100%;
    height: auto;
    background-color: black;
}
.userinfo{
    margin-top: 10px;
    width: 90%;
    margin-left: 5%;
    padding-bottom: 50px;
    padding-top: 20px;
    //background:white;
    .body{
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
    }
}

</style>
