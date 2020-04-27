<template>
  <div class="register-page">
    <div class="body">
      <div class="register-body">
        <div class="register-left">
          <h1>分享你的天赋</h1>
          <h1 class="left30">开启你的梦想之旅</h1>
          <div class="register-limg">
            <img src="../../../static/logo.png" alt="">
          </div>
        </div>
        <div class="register-right">
          <div class="register-form">
            <div class="form">
              <div class="title"><p>用户注册</p></div>
              <div class="content">
                <div class="account">
                  <p>邮箱</p><input type="text" v-model="accountData.phoneNumber"/>
                </div>
                <div class="captcha">
                  <p>验证码</p><input type="text" v-model="accountData.captcha" /><div class="get-captcha" @click="get()" :class="getCode?'get':''"><p>{{getCode?`${countDown}秒`:'获取验证码'}}</p></div>
                </div>
                <div class="password">
                  <p>密码</p><input type="password" v-model="accountData.userPassword"/>
                </div>
                <div class="password">
                  <p>验证密码</p><input type="password" v-model="accountData.checkPassword"/>
                </div>
              </div>
              <div class="register" v-loading='loading' @click="handleRegister" element-loading-background="black"><p>注册</p></div>
              <div class="tips">
                <a class="register-new" @click="login()" >登录</a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import API from '../../utils/API'
export default {
  data () {
    return {
      accountData: { // 账号信息
        phoneNumber: '', // 手机号修改为邮箱
        userPassword: '',
        checkPassword: '',
        captcha: ''
      },
      loading: false,
      getCode: false, // 是否获取验证码
      countDown: 0, // 倒计时数字
      countInterval: null // 倒计时定时器
    }
  },
  methods: {
    get () { // 获取验证码
      if (this.accountData.phoneNumber !== '') {
        if (!this.getCode) {
          this.getCode = true
          localStorage.setItem('phoneVerifyExpireTime', new Date().getTime() + 60000) // 保存本地，防止刷新改变状态
          this.countDown = 60
          this.interval() // 倒计时
          API.users.getEmailVerify({email: this.accountData.phoneNumber}, (responseData) => {
            console.log(responseData)
          }, (err) => {
            console.log(err)
          })
        }
      } else {
        this.$toast('请输入邮箱')
      }
    },
    interval () { // 倒计时
      this.countInterval = setInterval(() => {
        if (this.countDown > 0) {
          this.$nextTick(() => {
            this.countDown--
          })
        } else {
          localStorage.removeItem('phoneVerifyExpireTime') // 删除本地保存的时间倒计时
          clearInterval(this.countInterval) // 清除定时器
          this.getCode = false
        }
      }, 1000)
    },
    handleRegister () { // 提交注册
      this.loading = true
      if (this.accountData.phoneNumber !== '') {
        if (this.accountData.captcha !== '') {
          if (this.accountData.userPassword !== '' && this.accountData.checkPassword !== '') {
            if (this.accountData.userPassword === this.accountData.checkPassword) {
              API.users.register({phoneNumber: this.accountData.phoneNumber, userPassword: this.accountData.userPassword, captcha: this.accountData.captcha}, (responseData) => {
                console.log(responseData)
                if (responseData.code === 200) {
                  this.$toast('注册成功')
                  this.loading = false
                  this.$router.push('/login')
                } else {
                  this.$toast(`${responseData.data}`)
                  this.loading = false
                }
              }, (error) => {
                this.$toast('未知错误!')
                console.log(error)
                this.loading = false
              })
            } else {
              this.$toast('两次密码不一致')
              this.accountData.checkPassword = ''
              this.accountData.userPassword = ''
              this.loading = false
            }
          } else {
            this.$toast('请输入密码')
            this.loading = false
          }
        } else {
          this.$toast('请输入验证码')
          this.loading = false
        }
      } else {
        this.$toast('请输入登陆账号')
        this.loading = false
      }
    },
    login () {
      this.$router.push('/login')
    }
  },
  created () {
    var num = localStorage.getItem('phoneVerifyExpireTime') // 获取验证码的获取过期时间
    if (num !== null) { // 存在
      if (num !== 0) { // 不等于0
        this.getCode = true
        this.countDown = Math.round((num - new Date().getTime()) / 1000) // 倒计时数字
        this.interval() // 进行倒计时
      }
    }
  },
  beforeDestroy () { // 清除定时器
    clearInterval(this.countInterval)
  }
}
</script>

<style lang="less" scoped>
.register-page{
    width:100%;
    min-height: 100%;
    background-color: black;
    display: flex;
    flex-direction: column;
}
.register-body {
  flex-direction: row;
  display: flex;
  align-items: center;
  margin-top: 5%;
  .register-left {
    flex: 4;
    padding-left: 4%;
    h1 {
      color: white;
      font-weight: bold;
      font-size: 36px;
      line-height: 20px;
      &.left30 {
        margin-left: 165px;
      }
    }
    .register-limg{
      margin-top: 34px;
      img{
        border-radius: 3px;
      }
    }
  }
  .register-right {
      flex: 3;
      margin-top: 5%;
      .register-form {
      top:80px;
      width:400px;
      height: 100%;
      background-color: rgb(66, 66, 66);
      border-radius: 5px;
    .form {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        color: white;
        .pwd-forget, .register-new {
          cursor: pointer;
        }
        .title {
          width: 290px;
          height: 40px;
          margin-top: 10px;
          margin-bottom: 20px;
          border-radius: 10px;
          color: #fff;
          background-color:rgb(27, 27, 36);
          font-size: 24px;
          font-weight: bold;
          display: flex;
          justify-content: center;
          align-items: center;
        }
        .content{
          display: flex;
          flex-direction: column;
          font-size: 15px;
          .account{
            display: flex;
            flex-direction: row;
            p{
              width: 60px;
              text-align: right;
            }
            input{
              margin-top: 10px;
              margin-left: 10px;
              height: 20px;
              width: 180px;
            }
          }
          .password{
            display: flex;
            flex-direction: row;
            p{
              width: 60px;
              text-align: right;
            }
            input{
              margin-top: 10px;
              margin-left: 10px;
              height: 20px;
              width: 180px;
            }
          }
          .captcha{
            display: flex;
            flex-direction: row;
            p{
              width: 60px;
              text-align: right;
            }
            input{
              margin-top: 10px;
              margin-left: 10px;
              height: 20px;
              width: 100px;
            }
            .get-captcha{
              margin-top: 10px;
              width: 70px;
              height: 26px;
              background: rgb(27, 27, 36);
              margin-left: 10px;
              cursor: pointer;
              display: flex;
              justify-content: center;
              align-items: center;
              p{
                font-size: 4px;
                text-align: center;
              }
            }
            .get{
              background: grey;
            }
          }
        }
        .register{
          width: 290px;
          height: 40px;
          margin-top: 10px;
          border-radius: 10px;
          color: #fff;
          background-color:rgb(27, 27, 36);
          font-size: 20px;
          font-weight: bold;
          display: flex;
          justify-content: center;
          align-items: center;
          cursor: pointer;
        }
        .tips{
          width: 100%;
          align-items: center;
          flex:1;
          display: flex;
          flex-direction: row;
          margin: 10px 0;
          color:white;
          .register-new {
            flex:1;
            text-align: center;
            padding-right: 20px;
            font-size: 12px;
            font-weight: 100;
            list-style: none;
          }
        }
      }
    }
  }
}
</style>
