<template>
    <div id="left">
        <div class="left-nav">
            <ul>
                <li @click="userinfo()">
                    <i class="icon iconfont icongerenxinxi"></i>
                    <div>个人信息</div>
                </li>

                <li  @click="personalMood()">
                    <i class="icon iconfont iconwater_icon"></i>
                    <div>个人动态</div>
                </li>

                <li  @click="pulish()">
                    <i class="icon iconfont iconwater_icon"></i>
                    <div>发布心情</div>
                </li>

                <li @click="xingqiu()">
                    <i class="icon iconfont iconhuo"></i>
                    <div>星球心情</div>
                </li>

                <li @click="mat()">
                    <i class="icon iconfont iconhaoyou"></i>
                    <div>匹配好友</div>
                </li>

                <li @click="rgs()">
                    <i class="icon iconfont icontianjiayuzhuceqiye"></i>
                    <div>注册</div>
                </li>

                <li>
                    <i class="icon iconfont icontuichu"></i>
                    <div>退出</div>
                </li>
                <li>{{username}}</li>
                <li style="display:none">a
                    <!-- <message></message> -->
                </li>
            </ul>
        </div>
  </div>
</template>

<script>
import store from '@/vuex/store'
import Message from '@/components/Message'
export default {
  store,
  name: 'Main',
  data () {
    return {
      
    }
  },
  beforeRouteEnter (to, from, next) {
        next(vm => {
            if(from.path=='/'){
                console.log("home");
                vm.$router.push({path:'/initmes'});
            }
        })
   },
  components:{
    Message
  },
  methods:{
      userinfo(){
        this.$router.push({path:'/userinfo'});
      },
      pulish(){
         this.$router.push({path:'/pulish'});
      },
      personalMood(){
        this.$router.push({path:'/personalMood'});
      },
      xingqiu(){
        this.$router.push({path:'/xingqiuMood'});
      },
      mat(){
          this.$router.push({path:'/matchList'});
      },
      rgs(){
          this.$router.push({path:'/register'});
      }
  },
   computed:{
       username(){
           return this.$store.state.username;
       }
    }
}
</script>

<style>
    .left-nav{
       color:#fff;
       font-size:10px;
       height:100%;
       background-color: #1D8ce0;
       float:left;
       width:5%;
    }
    .iconfont{
       font-size:24px;
    }
    .left-nav ul{
        padding:0px;
        margin: 0px;
    }
    .left-nav li{
        list-style: none;
        text-align: center;
        border-bottom:1px solid #20a0ff;
        padding:10px;
    }

</style>
