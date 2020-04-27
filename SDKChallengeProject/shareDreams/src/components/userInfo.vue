<template>
  <div>
    <div style="margin-bottom:-20px" @click="navigateTo('/user/userinfo')">
      <img  style="width:30px;height:30px;border-radius:50%" :src="this.$store.state.loginUser.userAvatar" />
      <p>{{this.$store.state.loginUser.userName}}</p>
      <p>个人中心</p>
    </div>
    <el-divider style="height:20px"></el-divider>
    <div class="p" @click="navigateTo('/message')" ><p>聊天信息</p></div>
    <el-divider></el-divider>
    <div @click="logout()" ><p style="text-align:center">退出登录</p></div>
  </div>
</template>

<script>
export default {
  methods: {
    logout () {
      this.$alert('你确定要退出吗？', {
        confirmButtonText: '退出',
        callback: action => {
          if (action === 'confirm') {
            this.$store.dispatch('clearUser')
            localStorage.clear()
            this.$router.go(0)
          }
        }
      })
    },
    navigateTo (route) {
      if (this.$route.path !== route) {
        this.$router.push(`${route}`)
      }
    }
  }
}
</script>
<style lang="less" scoped>
.p{
    height: 20px;
    margin: -15px 0;
}
p{
    margin-top: 0px;
    font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;
    font-size: 13px;
    cursor: pointer;
}
</style>
