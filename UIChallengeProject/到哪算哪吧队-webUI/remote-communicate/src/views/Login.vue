<template>
  <div ref='loginc' class="login_container">
    <div @click='changeTheme' class="welcome_text">Welcome to <span class="triangle_box"></span></div>
    <div class="login_form">
      <form autocomplete="off">
        <div class="form_inputbox">
          <label ref='appidlabel'>App ID:</label>
          <input name='appid' v-model="idValue" @focus="handleFocus" @blur='handleBlur' type="text">
        </div>
        <div class="form_inputbox">
          <label ref='channellabel'>Channel:</label>
          <input name='channel' v-model="channelValue" @focus="handleFocus" @blur='handleBlur' type="text">
        </div>
        <div class="form_inputbox">
          <label ref='tokenlabel'>Token:</label>
          <input name='token' v-model="tokenValue" @focus="handleFocus" @blur='handleBlur' type="text">
        </div>
        <button @click='joinRoom'>JOIN</button>
        <button class="right" @click='isShow = !isShow'>Advanced Settings</button>
        <!-- advanced setting  -->
        <div class="login_settingbox" :class="{'show' : isShow}">
          <div class="input_box">
            <div class="label_div"><label for="">UID</label></div>
            <div class="input_div"><input type="number"></div>
          </div>
          <div class="input_box">
            <div class="label_div"><label for="">CAMERA</label></div>
            <div class="input_div"><input type="text"></div>
          </div>
          <div class="input_box">
            <div class="label_div"><label for="">MICROPHONE</label></div>
            <div class="input_div"><input type="text"></div>
          </div>
          <div class="input_box">
            <div class="label_div"><label for="">CAMERA RESOLUTION</label></div>
            <div class="input_div">
              <select name="resolution" id="">
                <option value="default">Default</option>
                <option value="480">480p</option>
                <option value="720">720p</option>
                <option value="1080">1080p</option>
              </select>
            </div>
          </div>
          <div class="input_box radio_box">
            <div class="label_div"><label for="">MODE</label></div>
            <div class="radio_div">
              <span><input type="radio">live</span>
              <span><input type="radio">rtc</span>
            </div>
          </div>
          <div class="input_box">
            <div class="label_div"><label for="">CODEC</label></div>
            <div class="radio_div">
              <span><input type="radio">h264</span>
              <span><input type="radio">vp8</span>
            </div>
          </div>
        </div>
        <!-- advanced setting end -->
      </form>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Login',
  data () {
    return {
      idValue: '',
      tokenValue: '',
      channelValue: '',
      isShow: false,
      lightTheme: false
      // isMounted: false
    }
  },
  mounted() {
  },
  computed: {

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
    // 路由跳转
    joinRoom(e) {
      e.preventDefault();
      if(this.idValue == 'myid'){
        if(this.channelValue == 'mychannel'){
          if(this.tokenValue == 'mytoken') {
            this.$router.push({path: '/facingroom'})
          }else{
            this.$my_message('Incorrect Token value')
          }
        }else{
          this.$my_message('Incorrect Channel value')
        }
      }else{
        this.$my_message('Incorrect App ID')
      }
    },
    // 控制form的input框的样式
    handleFocus(e) {
      let labelName = e.target.name
      this.$refs[labelName + 'label'].style.top= -30+`px`
      this.$refs[labelName + 'label'].style.fontSize= 18+`px`
      let currentTheme = window.document.documentElement.getAttribute('data-theme')
      if(currentTheme == 'light-theme'){
        this.$refs[labelName + 'label'].style.color= `rgb(240, 6, 170)`
      }else{
        this.$refs[labelName + 'label'].style.color= `rgb(91, 245, 237)`
      }
      
    },
    handleBlur(e) {
      if(!e.target.value) {
        let labelName = e.target.name
        this.$refs[labelName + 'label'].style.top= -5 +`px`
        this.$refs[labelName + 'label'].style.fontSize= 20+`px`
        let currentTheme = window.document.documentElement.getAttribute('data-theme')
        if(currentTheme == 'light-theme'){
          this.$refs[labelName + 'label'].style.color= `rgb(82, 65, 71)`
        }else{
          this.$refs[labelName + 'label'].style.color= `#fff`
        }
      } else return
    },
    // 更改主题
    changeTheme() {
       this.lightTheme = !this.lightTheme
    }
  }
}
</script>

<style lang="scss" scoped>
.right {float: right;}
.login_container{
  position: absolute;
  width: 100%;
  height: 100%;
  color: $dark_mainfont;
  background-color: #001d59;
  @include loginBg($login_bg_dark);
  background-size: cover;
  min-width: 580px;
  z-index: -10;
  transition: all .3s;
  .welcome_text{
    font-size: 80px;
    font-weight: bold;
    position: absolute;
    top: 40px;
    left: 50px;
    color: #7992d4;
    @include welcomeColor($wel_color_dark);
    cursor: pointer;
    .triangle_box{
      display: block;
      width: 14px;
      height: 14px;
      border-bottom: 1px solid $dark_mainfont;
      border-left: 1px solid $dark_mainfont;
      transform: rotate(45deg);
      position: absolute;
      right: -35px;
      top: calc( 50% + 4px);
      animation: showAndHidden 3s linear infinite;
    }
    z-index: 9;
    &:hover{
      color: $dark_mainfont;
    }
    &::after{
      content: 'click me';
      display: block;
      font-size: 12px;
      color: $dark_mainfont;
      padding: 2px 8px;
      padding-left: 4px;
      position: absolute;
      right: -90px;
      top: 50%;
      border: 1px solid $dark_mainfont;
      border-left: none;
      animation: showAndHidden 3s linear infinite;
    }
  }
  .login_form{
    padding-top: 30vh;
    position: relative;
    form{
      box-sizing: border-box;
      padding: 30px;
      width: 580px;
      height: auto;
      margin: 0 auto;
      @include formBgColor($form_bg_dark);
      background-color: rgba(20, 47, 51,.6);
      position: relative;
      min-height: 300px;
      @include formColor($dark_mainfont);
      button{
        padding: 10px 20px;
        font-weight: bold;
        background: none;
        cursor: pointer;
      }
      .form_inputbox{
        margin-bottom: 20px;
        min-height: 60px;
        position: relative;
        label{
          font-size: 20px;
          position: absolute;
          top: -5px;
          transition: all .3s;
          z-index: -1;
        }
        input{
          margin-top: 30px;
          display: block;
          width: 100%;
          font-size: 20px;
        }
      }
      &::after{
        content: 'REMOTE';
        display: inline-block;
        position: absolute;
        top: -50px;
        right: -200px;
        font-size: 70px;
        font-weight: bold;
        @include rcColor($rc_color_dark);
        z-index: -1;
      }
      &::before{
        content: 'COMMUNICATE';
        display: inline-block;
        position: absolute;
        bottom: -40px;
        left: -260px;
        font-size: 70px;
        font-weight: bold;
        @include rcColor($rc_color_dark);
        z-index: -1;
      }
      .login_settingbox{
        visibility: hidden;
        box-sizing: border-box;
        padding: 30px;
        position: absolute;
        width: 0px;
        height: 0px;
        background: #fff;
        border: 1px solid $light_mainfont;
        color:$light_mainfont;
        font-weight: bold;
        bottom: 80px;
        right: 40px;
        opacity: 0;
        transition: all .5s;
        &.show{
          visibility: visible;
          width: 500px;
          height: 295px;
          opacity: 1;
        }
        .input_box{
          width: 100%;
          height: 40px;
          display: flex;
          justify-content: space-between;
          .input_div{
            input{
              border-bottom: 2px solid $light_mainfont;
              color: $light_mainfont;
              &[type = radio] {
                width: 50px;
                border: 1px solid red;
              }
            }
            select{
              width: 175px;
              border: none;
              border-bottom: 2px solid $light_mainfont;
              outline: none;
            }
          }
          .radio_div{
            span{
              display: inline-block;
              width: 88px;
              input[type='radio']{
                margin-right: 10px;
              }
            }
          }
        }
      }
    }
  }
  @keyframes showAndHidden {
    from{
      opacity: .3;
    }
    50% {
      opacity: 1;
    }
    to{
      opacity: .3;
    }
  }
}
// media queries
@media screen and (max-width: 580px) {
  .login_container{
    min-width: 370px;
    position: fixed;
    left: 0;
    top: 0;
    right: 0;
    bottom: 0;
    .welcome_text{
      font-size: 40px;
      margin-top: 30px;
      margin-left: -20px;
    }
    .login_form{
      width: 100%;
      height: 100%;
      padding-top: 0;
      form{
        width: 100%;
        height: 100%;
        margin: 0 auto;
        padding-top: calc(50% - 50px);
        &::after{
          right: -20px;
          top: -30px;
        }
        &::before{
          left: -20px;
          bottom: -20px;
        }
        .login_settingbox{
          width: 100% !important;
          height: 340px !important;
          position: absolute;
          top: 80px;
          right: 0px;
          z-index: 1000;
          .input_box{
            flex-wrap: wrap;
          }
          .radio_box{
            margin-top: 20px;
          }
        }
      }
    }
  }
  
}
</style>
