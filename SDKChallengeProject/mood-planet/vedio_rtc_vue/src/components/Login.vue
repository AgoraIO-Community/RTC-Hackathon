<template>
    <div id="login">
        <div class="login-main">
                <el-form ref="form" :model="form" label-width="80px">
                    <el-form-item label="用户:">
                        <el-input placeholder="请输入用户名"  v-model="form.username" clearable></el-input>
                    </el-form-item>
                    <el-form-item label="密码:">
                        <el-input placeholder="请输入密码" v-model="form.password" clearable show-password class="password"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="submitlogin()">登录</el-button>
                        <el-button type="primary" @click="register()">去注册</el-button>
                    </el-form-item>
                </el-form>

        </div>
            
        
    </div>
    
</template>

<script>
import axios from 'axios'
export default {
    data(){
        return{
            form:{
                username:'',
                password:'',
            }
           
        }
    },
     mounted: function () {
        var orderHeight = document.documentElement.clientHeight;
        document.getElementById("login").style.height = orderHeight + 'px';
    },
    methods:{
        submitlogin(){
            let _this = this;
            var url =this.HOST  +'/doLogin'; 
            axios.get(url, {
                params: {
                username: _this.form.username,
                password: _this.form.password
                }
            }).then(function(response){
                    if(response.data=='SUCCESS'){
                        _this.$router.push({path:'/home'});
                        window.location.reload();
                    }else{
                        alert('登录失败');
                    }
            }).catch(function(response){
                alert('请求失败');
            })
        },

        register(){
            this.$router.replace('/register')
        }
    }
}
</script>

<style>
.login-main{
    width: 20%;
    margin-top:70px;
    margin-right: 10%;
    text-align: center;
    padding-top: 12%;
    float:right;
}

#login{
    width:100%;
    background-image:url(../assets/login2.jpg);
}

</style>