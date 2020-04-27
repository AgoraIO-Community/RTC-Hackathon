<template>
<div class="header">
    <img src='../../static/logo.png' class="logo" @click="backTo()"/>
    <el-menu
      class="el-menu"
      mode="horizontal"
      @select="handleSelect"
      background-color="transparent"
      text-color="#fff"
      :default-active="defaultActive"
      active-text-color="#ffd04b">
      <el-menu-item index="1">首页</el-menu-item>
      <el-menu-item index="2">所有视频</el-menu-item>
      <el-menu-item index="3">直播</el-menu-item>
      <el-menu-item index="4" v-if="this.$store.state.loginUser.userName===''">登录/注册</el-menu-item>
      <el-menu-item index="5" style="width:220px" v-else >
        <el-popover
        placement="top-start"
        width="180"
        trigger="hover"
        >
          <UserInfo />
          <div slot="reference" class="user">
            <img :src="this.$store.state.loginUser.userAvatar" style="width:30px;height:30px;border-radius:50%"/>
            <p>{{this.$store.state.loginUser.userName}}</p>
          </div>
        </el-popover>
      </el-menu-item>
    </el-menu>
  </div>
</template>
<script>
import UserInfo from './userInfo'
export default {
  components: {UserInfo},
  data () {
    return {
      defaultActive: ''
    }
  },
  methods: {
    handleSelect (item) {
      if (this.defaultActive !== '4' && item === '4') {
        this.$router.push('/login')
        this.defaultActive = '4'
      } else if (this.defaultActive !== '1' && item === '1') {
        this.$router.push('/index')
        this.defaultActive = '1'
      } else if (this.defaultActive !== '2' && item === '2') {
        this.$router.push('/allvideos')
        this.defaultActive = '2'
      } else if (this.defaultActive !== '3' && item === '3') {
        this.$router.push('/alllives')
        this.defaultActive = '3'
      }
    },
    backTo () {
      if (this.$route.path !== '/') {
        this.$router.push('/')
      }
    },
    active (path) {
      if (path === '/index') {
        this.defaultActive = '1'
      } else if (path === '/allvideos') {
        this.defaultActive = '2'
      } else if (path === '/alllives') {
        this.defaultActive = '3'
      }
    }
  },
  mounted () {
    this.active(this.$route.path)
  }
}
</script>
<style lang="less" scoped>
.header{
  background-color:black;;
  box-shadow: 0px 1px 3px 0px white;
  height: 60px;
  width: 100%;
  display: flex;
  flex-direction: row;
  margin-bottom: 2px;
  .logo{
    height: 90%;
    width: 240px;
    cursor: pointer;
  }
  .el-menu{
    margin-left: auto;
    justify-content: flex-end;
    display:flex;
    flex-direction: row;
    border-bottom: none;
  }
  .user{
    height: 100%;
    flex:1;
    display:flex;
    flex-direction: row;
    img{
      margin-top: 10%;
    }
    p{
      margin-left: 10%;
      margin-top: 4%;
      width: 60%;
      text-overflow:ellipsis;
      overflow:hidden;
      white-space:nowrap;
    }
  }
}
</style>
