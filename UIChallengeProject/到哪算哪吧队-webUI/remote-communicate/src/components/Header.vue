<template>
  <header ref='header'>
    <div class="web_logo">
      <img @click='changeTheme' src="~@/assets/image/logo_w.png" alt="">
      <span>R.C.</span>
    </div>
    <div class="login_info_box" @click='isExitShow = !isExitShow'>
      <div class="login_name">Tonghui</div>
      <button @click='logOut' :class="{show: isExitShow}">Exit</button>
    </div>
  </header>
</template>

<script>
export default {
  name: 'Header',
  data() {
    return{
      isExitShow: false,
      lightTheme: false
    }
  },
  watch: {
    lightTheme() {
      if(this.lightTheme) {
        window.document.documentElement.setAttribute('data-theme','light-theme')
      }else{
        window.document.documentElement.setAttribute('data-theme','dark-theme')
      }
      
    }
  },
  methods: {
    logOut(e) {
      e.preventDefault();
      this.$router.push({path: '/'})
    },
    // 更改主题
    changeTheme() {
       this.lightTheme = !this.lightTheme
    }
  }
}
</script>
<style lang='scss' scoped>
header{
  box-sizing: border-box;
  padding: 0 50px;
  line-height: 80px;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 80px;
  display: flex;
  @include header($header_bg_dark); 
  justify-content: space-between;
  z-index: 100;
  .web_logo{
    font-size: 28px;
    font-weight: bold;
    color: #fff;
    img{
      width: 80px;
      height: auto;
      margin-top: 22px;
      margin-right: 20px;
      cursor: pointer;
    }
  }
  .login_info_box{
    display: flex;
    color: #fff;
    font-size: 18px;
    font-weight: bold;
    button{
      background: none;
      border: 1px solid #fff;
      color: #fff;
      padding: 0 20px;
      margin-left: 20px;
      height: 30px;
      margin-top: 26px;
      border-radius: 4px;
      cursor: pointer;
    }
  }
}
@media screen and (max-width: 580px) {
  header{
    .web_logo{
      span{
        display: none;
      }
    }
    .login_info_box{
      cursor: pointer;
      button{
        visibility: hidden;
        position: absolute;
        bottom: -20px;
        right: 50px;
        opacity: 0;
        transition: all .3s;
        &.show{
          opacity: 1;
          visibility: visible;
        }
      }
    }
  }
}
</style>