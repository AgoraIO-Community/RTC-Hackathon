/* eslint-disable */
<template>
  <el-container class="home-container">
    <el-aside width="200px" class="el-aside">
      <!-- 侧边栏菜单 -->
      <el-menu
        background-color="white"
        text-color="#111"
        active-text-color="#027db4"
        :unique-opened="true"
        :default-active="courseTabPath"
      >
        <el-submenu :index="item.classID" v-for="item in courseList" :key="item.classID">
          <template slot="title">
            <i class="el-icon-location"></i>
            <span>{{item.className}}</span>
          </template>
          <el-menu-item-group>
            <template slot="title">课程代码：{{item.classID}}</template>
            <!-- 小黑板 -->
            <el-menu-item
              :index="item.classID + '-blackboard'"
              @click="blackboardClick(item.classID)"
            >
              <template slot="title">
                <i class="el-icon-edit"></i>
                <span>小黑板</span>
              </template>
            </el-menu-item>
          <!-- 课程直播 -->
            <el-menu-item
              :index="item.classID + '-livebroadcast'"
              @click="livebroadcastClick(item.classID)"
            >
              <template slot="title">
                <i class="el-icon-video-play"></i>
                <span>课程直播</span>
              </template>
            </el-menu-item>
          </el-menu-item-group>
        </el-submenu>
      </el-menu>
    </el-aside>
    <el-main class="el-main">
      <router-view></router-view>
    </el-main>
  </el-container>
</template>

<script>
export default {
  data() {
    return {
      courseList:[
    {
        classID:"CS285",
        className:"机电一体化"
    },
    {
        classID:"CS171",
        className:"计算机图形学"
    },
    {
        classID:"EE150",
        className:"信号与系统"
    },
    {
        classID:"EE130",
        className:"电磁学"
    },
    {
        classID:"BIO1011",
        className:"现代生命科学导论"
    },
    {
        classID:"EE130P",
        className:"电磁学课程设计"
    },
    {
        classID:"EE150L",
        className:"信号与系统实验"
    },
    {
        classID:"GESS1014",
        className:"马克思主义原理"
    },{
        classID:"GEPE1026",
        className:"游泳"
    },
    {
        classID:"GESS1025",
        className:"柏拉图《理想国导读》"
    }],
      courseTabPath: ''
    }
  },
  created() {
    // this.getMenuList()
    this.getNavState()
  },
  methods: {
    async getMenuList() {
      const { data: res } = await this.$http.get('courseList')
      // console.log('get courseList', res)
      if (res.meta.status !== 200) return this.$message.error(res.meta.msg)
      this.courseList = res.data
    },
    blackboardClick (id) {
      this.setNavState(id + '-blackboard')
      if (this.$route.path !== ('/blackboard/' + id)) this.$router.push("/blackboard/" + id)
    },
    livebroadcastClick (id) {
      this.setNavState(id + '-livebroadcast')
      if (this.$route.path !== ('/livebroadcast/' + id)) this.$router.push("/livebroadcast")
    },
    setNavState(state) {
      window.sessionStorage.setItem('courseTabState', state)
    },
    getNavState() {
      const path = window.sessionStorage.getItem('courseTabState')
      this.courseTabPath = path
    },
  }
}
</script>

<style lang="less" scoped>
.home-container {
  height: 100%;
}
.el-header {
  background-color: #027db4;
  display: flex;
  justify-content: space-between;
  padding-left: 30px;
  align-items: center;
  color: #fff;
  font-size: 20px;
  > div {
    display: flex;
    align-items: center;
    img {
      width: 200px;
    }
  }
}
.el-aside {
  background-color: white;
  .el-menu {
    border-right: none;
  }
}
.el-main {
  background-color: white;
}
.nav-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  > svg-icon {
    width: 60px;
    color: red;
  }
  > span {
    font-size: 13px;
  }
}
.nav-bar {
  width: 500px;
  display: flex;
  justify-content: space-around;
  margin-left: 100px;
}
</style>
