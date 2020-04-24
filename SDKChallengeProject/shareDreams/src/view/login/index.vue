<template>
  <div class="login-page">
    <div class="body">
      <div class="login-body">
        <div class="login-left">
          <h1>分享你的天赋</h1>
          <h1 class="left30">开启你的梦想之旅</h1>
          <div class="login-img">
            <img src="../../../static/logo.png" alt="">
          </div>
        </div>
        <div class="login-right">
          <div class="login-form">
            <div class="form">
              <div class="title"><p>用户登录</p></div>
              <div class="content">
                <div class="account">
                  <p>账号</p><input type="text" v-model="accountData.phoneNumber"/>
                </div>
                <div class="password">
                  <p>密码</p><input type="password" v-model="accountData.userPassword"/>
                </div>
                <div class="captcha">
                  <p @click="send()">验证码</p><input type="text" v-model="accountData.captcha" /><div class="captcha-img" @click="refreshVerify" v-html="verifyImg" >
                </div>
              </div>
            </div>
            <div class="login" v-loading='loading' @click="handleSubmit" element-loading-background="black"><p>登录</p></div>
              <div class="tips">
                <a class="pwd-forget" @click="forgetpass()" >忘记密码？</a>
                <a class="register-new" @click="register()" >注册新用户</a>
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
      accountData: { // 账户信息
        phoneNumber: '', // 修改为邮箱登录
        userPassword: '', // 密码
        captcha: '' // 验证码
      },
      loading: false, // 是否验证中
      verifyImg: '' // 验证码
    }
  },
  inject: ['reload'],
  methods: {
    handleSubmit () {
      this.loading = true
      if (this.accountData.phoneNumber !== '') {
        if (this.accountData.userPassword !== '') {
          if (this.accountData.captcha !== '') {
            API.users.login({phoneNumber: this.accountData.phoneNumber, userPassword: this.accountData.userPassword, captcha: this.accountData.captcha}, (responseData) => { // 用户登录
              if (responseData.code === 200) {
                this.$toast('登录成功')
                this.loading = false
                this.$store.dispatch('saveUser', responseData.data) // 保存用户信息
                localStorage.setItem('loginUser', JSON.stringify(responseData.data))
                this.reload()
                setTimeout(() => {
                  this.$router.push('/')
                }, 100)
              } else if (responseData.code === -2) {
                this.$toast('验证码错误')
                this.accountData.captcha = ''
                this.refreshVerify()
                this.loading = false
              } else if (responseData.code === 404) {
                this.$toast('账号或密码错误')
                this.refreshVerify()
                this.loading = false
                this.accountData.userPassword = ''
                this.accountData.captcha = ''
              }
            }, (error) => {
              this.$toast('未知错误')
              console.log(error)
              this.loading = false
            })
          } else {
            this.$toast('请输入验证码')
            this.loading = false
          }
        } else {
          this.$toast('请输入密码')
          this.loading = false
        }
      } else {
        this.$toast('请输入账号')
        this.loading = false
      }
    },
    register () { // 跳转至注册页面
      this.$router.push('/register')
    },
    forgetpass () {
      this.$router.push('/forgetPassword')
    },
    refreshVerify () { // 刷新验证码
      API.users.getCaptcha({}, (responseData) => {
        if (responseData.code === 200) {
          this.verifyImg = responseData.data
        }
      }, (err) => {
        console.log(err)
      })
    }
  },
  created () {
    this.refreshVerify()
  }
}
</script>

<style lang="less" scoped>
.login-page{
    width:100%;
    min-height: 100%;

    background-color: black;
    display: flex;
    flex-direction: column;
}
.login-body {
  flex-direction: row;
  display: flex;
  align-items: center;
  margin-top: 5%;
  .login-left {
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
    .login-img{
      margin-top: 34px;
      img{
        border-radius: 3px;
      }
    }
  }
  .login-right {
      flex: 3;
      margin-top: 5%;
    .login-form {
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
              width: 50px;
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
              width: 50px;
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
              width: 50px;
              text-align: right;
            }
            input{
              margin-top: 10px;
              margin-left: 10px;
              height: 20px;
              width: 80px;
            }
            .captcha-img{
              //margin-top: 10px;
              margin-left: 10px;
              background:white;width:90px;height:48px;
              cursor: pointer;
            }
          }
        }
        .login{
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
          .pwd-forget {
            flex: 1;
            text-align: left;
            padding-left: 20px;
            font-size: 12px;
            font-weight:100;
            list-style: none;
            color:white;
          }
          .register-new {
            flex:1;
            text-align: right;
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
