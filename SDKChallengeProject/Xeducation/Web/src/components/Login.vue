<template>
  <div class="login_container">
    <div class="background"></div>
    <div class="login_box">
      <div class="avatar_box">
        <img src="../assets/logo.png" alt />
      </div>

      <el-form
        ref="LoginFormRef"
        :model="LoginForm"
        :rules="LoginFormRules"
        label-width="0px"
        class="login_form"
      >
        <el-form-item prop="username">
          <el-input v-model="LoginForm.username" prefix-icon="el-icon-user"></el-input>
        </el-form-item>

        <el-form-item prop="passward">
          <el-input
            v-model="LoginForm.passward"
            prefix-icon="el-icon-lock"
            placeholder="请输入密码"
            show-password
          ></el-input>
        </el-form-item>

        <el-form-item class="btns">
          <el-button type="primary" @click="Login">登录</el-button>
          <el-button type="info" @click="resetLoginForm">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="copyright">2020 ShanghaiTech Student Innovation Center Femto Studio</div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      LoginForm: {
          username:'admin',
          passward:'123456',
      },
      LoginFormRules:{
          username:[
              { required: true, message: '请输入用户名', trigger: 'blur' },
              { min: 2, max: 20, message: '请至少输入2个字符', trigger: 'blur' }
          ],
          passward:[
              { required: true, message: '请输入密码', trigger: 'blur' },
              { min: 2, max: 20, message: '请至少输入6个字符', trigger: 'blur' }
          ],
      },
    }
  },

    methods: {
        resetLoginForm() {
            this.$refs.LoginFormRef.resetFields()
        },
        Login() {
            this.$refs.LoginFormRef.validate(async valid => {
                if (!valid) return
                const { data:res } = await this.$http.post('login', this.LoginForm)
                if (res.meta.status !== 200) return this.$message.error('登录失败')
                this.$message.success('登录成功')
                window.sessionStorage.setItem('token', res.data.token)
                this.$router.push('/home')
            })
        }
    }

}
</script>

<style lang="less" scoped>
.copyright {
  color:white;
  opacity: 0.6;
  font-size: 12px;
  position: absolute;
  bottom:20px;
  left: 50%;
  transform: translate(-50%, 0%);
}
.login_container {
  // background: url("../assets/logo.png") no-repeat;
  // background-size:100% 100%;
  width: 100%;
  height: 100%;
  // filter: blur(0px);
  // z-index:-1 !important;
  background-origin:border-box;
}
.background {
  background: url("../assets/imag/background.jpg") no-repeat;
  width: 100%;
  height: 100%;
  background-size:100% 100%;
  filter: blur(5px);
}
.login_box {
  // filter: blur(0px) !important;
  width: 450px;
  height: 300px;
  background-color: #fff;
  border-radius: 3px;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  opacity: 0.92;
  // z-index:999 !important;
}

.avatar_box {
  height: 130px;
  width: 130px;
  border: 1px solid #eee;
  border-radius: 50%;
  padding: 10px;
  box-shadow: 0 0 10px;
  position: absolute;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: #fff;
  img {
    width: 100%;
    height: 100%;
    border-radius: 50%;
    background-color: #eee;
  }
}

.btns {
  display: flex;
  justify-content: flex-end;
}

.login_form {
  position: absolute;
  bottom: 0;
  width: 100%;
  padding: 0 20px;
  box-sizing: border-box;
}
</style>
