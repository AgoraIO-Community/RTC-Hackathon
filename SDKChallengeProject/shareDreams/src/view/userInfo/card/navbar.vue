<template>
    <div class="navigator">
        <div class="intro">导航</div>
        <div class="content" v-for="(item,index) in navigators" :key="index" :class="index===activeIndex?'selected':''" @click="selected(index)">
            <div class="per"><p>{{item.name}}</p></div>
        </div>
    </div>
</template>
<script>
export default {
  data () {
    return {
      navigators: [{name: '基本信息'}, {name: '我的关注'}, {name: '我的支持者'}],
      activeIndex: 0
    }
  },
  methods: {
    selected (index) { // 选择导航
      if (this.activeIndex !== index && index === 0) {
        this.$router.push('/user/userinfo').catch((err) => {
          console.log(err)
        })
      } else if (this.activeIndex !== index && index === 1) {
        this.$router.push('/user/follows').catch((err) => {
          console.log(err)
        })
      } else if (this.activeIndex !== index && index === 2) {
        this.$router.push('/user/supports').catch((err) => {
          console.log(err)
        })
      } else {
        console.log('重复点干哈')
      }
    },
    active (path) {
      path = path.replace(/\/user\//gi, '')
      if (path === 'userinfo') {
        this.activeIndex = 0
      } else if (path === 'follows') {
        this.activeIndex = 1
      } else if (path === 'supports') {
        this.activeIndex = 2
      }
    }
  },
  mounted () {
    this.active(this.$route.path)
  }
}
</script>
<style lang="less" scoped>
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
</style>
