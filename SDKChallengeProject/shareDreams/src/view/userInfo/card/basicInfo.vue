<template>
  <div class="basic-info">
    <div class="intro">
      <p>我的信息</p>
    </div>
    <div class="detail">
      <div class="user-avatar-p">
        <p class="user-avatar-h">头像:</p>
        <div class="user-avatar">
          <img :src="info.userAvatar" style="width:80px;height:80px;border-radius:50%;border:1px solid grey"/>
          <a class="avatar-cover" target="_blank" @click="open()" >更换头像</a>
          <input type="file" name="img" class="upload" @change="addImg" hidden ref="inputer" accept="image/png,image/jpeg,image/gif,image/jpg"/>
        </div>
      </div>
      <div class="user-name">
        <p>昵称：</p>
        <input v-model="info.userName" />
      </div>
      <div class="user-intro">
        <p>简介：</p>
        <input v-model="info.userIntro" />
      </div>
      <div class="user-sex">
        <p>性别：</p>
        <div class="options">
          <el-radio-group @change="select()" v-model="info.sex" >
            <el-radio label="0">男</el-radio>
            <el-radio label="1">女</el-radio>
            <el-radio label="-1">未知</el-radio>
          </el-radio-group>
        </div>
      </div>
      <div class="user-email">
        <p>用户邮箱：</p>
        <input v-model="info.phoneNumber" disabled/>
      </div>
      </div>
      <div class="btn-groups">
        <div class="cancel" @click="cancel()"><p>取消</p></div>
          <div class="submit" :class="change?'active':''" @click="submit()"><p>更改</p></div>
        </div>
    </div>
</template>
<script>
import API from '../../../utils/API'
import Util from '../../../utils/util'
export default {
  data () {
    return {
      change: false,
      info: this.$store.state.loginUser,
      file: ''
    }
  },
  watch: { // 监听用户数据，改变之后，保存按钮变色
    info: {
      handler () {
        this.change = true
      },
      deep: true
    }
  },
  methods: {
    open () { // 点击打开file
      this.$refs.inputer.dispatchEvent(new MouseEvent('click'))
    },
    addImg () { // 添加图片
      var url = this.$refs.inputer.files[0]
      this.file = this.$refs.inputer.files[0]
      if (Util.validateFile(this.file)) { // 验证文件格式
        if (url !== undefined) {
          this.info.userAvatar = Util.getObjectURL(url) // 获取本地图片url
        }
      } else {
        this.file = ''
        this.$toast('请选择图片格式')
      }
    },
    cancel () { // 恢复原本的用户数据
      this.info = {
        userName: this.$store.state.loginUser.userName,
        userAvatar: this.$store.state.loginUser.userAvatar,
        sex: this.$store.state.loginUser.sex.toString(),
        email: this.$store.state.loginUser.email
      }
      this.change = false
    },
    submit () {
      if (this.file !== '') { // 用户改变了头像
        API.users.uploadUserAvatar(this.file, (responseData) => { // 上传头像
          if (responseData.code === 200) {
            this.info.userAvatar = responseData.url
            API.users.saveUserInfo(this.info, (responseData) => { // 更新其他用户数据
              if (responseData.code === 200) {
                this.$toast(responseData.message)
                this.$store.dispatch('saveUser', responseData.data) // 保存用户信息
                localStorage.setItem('loginUser', JSON.stringify(responseData.data))
              } else {
                this.$toast('信息更改失败')
              }
            }, (errorData) => {
              console.log('change fail')
              console.log(errorData)
            })
          } else {
            console.log(responseData)
          }
        }, (errorData) => {
          console.log('change fail')
          console.log(errorData)
        })
      } else { // 头像没有改变，只更新用户其他数据
        API.users.saveUserInfo(this.info, (responseData) => {
          if (responseData.code === 200) {
            this.$toast(responseData.message)
            this.$store.dispatch('saveUser', responseData.data)
            localStorage.setItem('loginUser', JSON.stringify(responseData.data))
          } else {
            this.$toast('信息更改失败')
          }
        }, (errorData) => {
          console.log('change fail')
          console.log(errorData)
        })
      }
    },
    select () {
      console.log(this.info.sex)
    }
  },
  created () {
    this.info.sex = this.info.sex.toString()
  }
}
</script>
<style lang="less" scoped>
.basic-info{
  width: 100%;
  .intro{
    height: 50px;
    border-bottom:1px solid grey;
    display: flex;
    flex-direction: row;
    align-items: center;
    font-size: 13px;
    color: #0B8DD9;
    p{
      border-left: 2px solid #0B8DD9;
      padding-left: 10px;
      margin-left:20px;
    }
  }
  .detail{
    margin-left: 60px;
    margin-top: 20px;
    flex: 1;
    display: flex;
    flex-direction: column;
    color: rgb(66, 66, 66);
    font-size: 13px;
    .user-avatar-p{
      flex: 1;
      display: flex;
      flex-direction: row;
      p{
        margin-top: 60px;
        width: 90px;
        text-align: right;
      }
      .user-avatar{
        margin-left: 30px;
      }
      .avatar-cover{
        cursor: pointer;
      }
    }
    .user-name{
      flex:1;
      margin-top: 20px;
      display: flex;
      flex-direction: row;
      p{
        width: 100px;
        text-align: right;
      }
      input{
        margin-left: 20px;
        margin-top: 6px;
        outline: none;
        border-radius: 5px;
        font-size: 13px;
        width: 200px;
        border: 1px solid grey;
        height: 25px;
        color:rgb(66, 66, 66);
      }
    }
    .user-intro{
      flex:1;
      margin-top: 20px;
      display: flex;
      flex-direction: row;
      p{
        width: 100px;
        text-align: right;
      }
      input{
        margin-left: 20px;
        margin-top: 6px;
        outline: none;
        border-radius: 5px;
        font-size: 13px;
        width: 200px;
        border: 1px solid grey;
        height: 25px;
        color:rgb(66, 66, 66);
      }
    }
    .user-sex{
      flex: 1;
      margin-top: 20px;
      display: flex;
      flex-direction: row;
      p{
        width: 100px;
        text-align: right;
      }
      .options{
        margin-left: 20px;
        margin-top: 12px;
      }
    }
    .user-email{
      flex: 1;
      margin-top: 20px;
      display: flex;
      flex-direction: row;
      p{
        width: 100px;
        text-align: right;
      }
      input{
        margin-left: 20px;
        margin-top: 6px;
        outline: none;
        border-radius: 5px;
        font-size: 13px;
        width: 200px;
        border: 1px solid grey;
        height: 25px;
        color:rgb(66, 66, 66);
      }
    }
  }
  .btn-groups{
    margin-left: 200px;
    margin-top: 60px;
    height: 40px;
    display: flex;
    flex-direction: row;
    color: white;
    font-size: 13px;
    .cancel{
      width: 50px;
      height: 30px;
      background-color: grey;
      border-radius: 5px;
      flex-direction: row;
      display: flex;
      justify-content: center;
      align-items: center;
      cursor: pointer;
    }
    .submit{
      margin-left: 10px;
      padding: 0 10px;
      height: 30px;
      background-color: grey;
      border-radius: 5px;
      flex-direction: row;
      display: flex;
      justify-content: center;
      align-items: center;
      cursor: pointer;
    }
    .active{
      background-color: #0B8DD9;
    }
  }
}
</style>
