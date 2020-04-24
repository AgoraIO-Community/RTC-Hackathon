/* eslint-disable */
<template>
  <el-container class="home-container">
    <el-header height="60px" class="el-header">
      <div>
        <img src="../assets/Xedu.png" class="logo" alt />
        <div class="nav-bar">
          <div
            class="nav-item"
            :class="item.name==currentTab?'active':'default'"
            style="cursor:pointer"
            v-for="item in tabList"
            :key="item.name"
            @click="switchTab(item)"
          >
            <img :src="require('../assets/icons'+item.path+'.png')" alt="" class="icon">
            <span>{{item.name}}</span>
          </div>
        </div>
      </div>
      <el-input placeholder="Search files" class="search">
      </el-input>
      <el-button type="primary" @click="logout">Quit</el-button>
    </el-header>
    <!-- <el-container class="container"> -->
      <router-view></router-view>
    <!-- </el-container> -->
  </el-container>
</template>

<script>
export default {
    created() {
        const tab = window.sessionStorage.getItem('currentTab')
        if (tab) this.currentTab = tab
    },
  data() {
    return {
      tabList: [
        {
          name: 'Home',
          icon: 'home',
          path: '/home'
        },
        {
          name: 'Course',
          icon: 'book',
          path: '/course'
        },
        {
          name: 'Attention',
          icon: 'attention',
          path: '/attention'
        },
        {
          name: 'Me',
          icon: 'user',
          path: '/me'
        }
      ],
      currentTab: 'Home',
      fun: 'active'
    }
  },
  methods: {
    logout() {
      window.sessionStorage.clear()
      this.$router.push('/login')
    },
    switchTab(item) {
      if (item.name === this.currentTab) return
      window.sessionStorage.setItem('currentTab',item.name)
      this.$router.push(item.path)
      this.currentTab = item.name
    }
  }
}
</script>

<style lang="less" scoped>
.search{
    width:400px;
}
.home-container {
    height:100%;
}
.active {
  border-bottom: solid 2px;
  opacity: 1;
}
.default {
  border-bottom: solid 0px;
  opacity: 0.7;
}
.icon {
  width:30px;
  height:30px;
}
.el-header {
  background-color: #027db4;
  display: flex;
  justify-content: space-between;
  padding-left: 30px;
  align-items: center;
  color: #fff;
  font-size: 10px;
  > div {
    display: flex;
    align-items: center;
    .logo {
      height: 55px;
    }
  }
}
.nav-item {
  display: flex;
  flex-direction: column;
  align-items: center;
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
