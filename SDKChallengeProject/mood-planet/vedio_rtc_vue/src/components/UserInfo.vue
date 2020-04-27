<template>
    <div >
        <leftNav></leftNav>
        <div id="info">
            <el-form ref="form" :model="form" label-width="80px">
                <input type="text" v-model="form.id" style="display:none"/>
                <el-form-item label="手机号"
                prop="phone"
                :rules="[
                { type: 'number', message: '手机号必须为数字值'}
                ]"
                >
                    <el-input v-model.number="form.phone" autocomplete="off"></el-input>
                </el-form-item>
               
                <el-form-item
                    prop="email"
                    label="邮箱"
                    :rules="[
                    { required: true, message: '请输入邮箱地址', trigger: 'blur' },
                    { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
                    ]"
                >
                    <el-input v-model="form.email"></el-input>
                </el-form-item>

                
                <el-form-item label="性别">
                    <el-radio-group v-model="form.sex">
                    <el-radio label="男" value="男"></el-radio>
                    <el-radio label="女" value="女"></el-radio>
                    </el-radio-group>
                </el-form-item>

                用户头像
                <el-upload
                    class="avatar-uploader"
                    action="http://localhost:8081/upload"
                    list-type="picture-card"
                    accept="image/*"
                    :limit="imgLimit"
                    :file-list="productImgs"
                    :multiple="isMultiple"
                    :on-preview="handlePictureCardPreview"
                    :on-remove="handleRemove"
                    :on-success="handleAvatarSuccess"
                    :before-upload="beforeAvatarUpload"
                    :on-exceed="handleExceed"
                    :on-error="imgUploadError"    
                    >
                    <i class="el-icon-plus"></i>
                    </el-upload>
                    <el-dialog :visible.sync="dialogVisible">
                        <img width="100%" :src="dialogImageUrl" alt="">
                    </el-dialog>


                <br>
                <el-form-item>
                    <el-button type="primary" @click="onSubmit">保存</el-button>
                    <el-button>取消</el-button>
                </el-form-item>
            </el-form>

            

        </div>
                 

    </div>
</template>

<script>
import axios from 'axios'
import LeftNav from '@/components/LeftNav'
export default {
    data(){
        return{
           form: {
                id:0,
                phone: '',    
                sex: '',
                email: '',
            },
           dialogImageUrl: '',
           dialogVisible: false,
            productImgs: [],
            isMultiple: true,
            imgLimit: 1,
            imgsrc:null
        }
    },
    components:{
        LeftNav
    },
    created:function(){
        let _this = this;
        var url =this.HOST + this.api  +'/getUserInfo';     
            this.$axios.get(url,
            ).then(function(response){
                    if(response.data==null){
                        return;
                    }else if(response.data.message=='success'){
                        _this.form = response.data.userinfo;
                        _this.form.phone=parseInt(_this.form.phone);
                    }
            }).catch(function(response){
                _this.$message.error('请求失败');
            })
    },
    mounted: function () {
    },
   
    methods:{
     onSubmit() {
        console.log(this.form);
        let _this = this;
        var url =this.HOST + this.api  +'/setUserInfo';   
            _this.$axios({
                url:url,
                method:'POST',
                data: {
                    id:Number(_this.form.id),
                    userId:_this.$store.state.userid,
                    phone:_this.form.phone,
                    email:_this.form.email,
                    sex:_this.form.sex,
                    img:_this.imgsrc
                },
                transformRequest: [function (data) {
                    let ret = ''
                    for (let it in data) {
                        ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
                    }
                    return ret
                }],
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
            })
            .then(function(response){
                   if(response.data==null){
                        return;
                    }else if(response.data==true){
                         _this.$message.success('保存成功!');
                    }else if(response.data==false){
                       _this.$message.error('保存失败!');
                    }
            }).catch(function(response){
                _this.$message.error('请求失败!');
            })
            
     },
      
      handleRemove(file, fileList) {//移除图片
        // console.log(file, fileList);
      },
      handlePictureCardPreview(file) {//预览图片时调用
        this.dialogImageUrl = file.url;
        this.dialogVisible = true;
      },
      beforeAvatarUpload(file) {//文件上传之前调用做一些拦截限制
        const isJPG = true;
        // const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 / 1024 < 2;
        if (!isLt2M) {
          this.$message.error('上传图片大小不能超过 2MB!');
        }
        return isJPG && isLt2M;
      },
      handleAvatarSuccess(res, file) {//图片上传成功
        console.log(res);
        if(res.message=="success"){
            this.imgsrc=res.imgsrc;
        }
      },
      handleExceed(files, fileList) {//图片上传超过数量限制
        this.$message.error('上传图片不能超过1张!');
      },
      imgUploadError(err, file, fileList){//图片上传失败调用
        console.log(err)
      }




    }
}
</script>

<style>
#info{
    width: 30%;
    margin-top: 0px;
    margin-left: 20px;
    border: white 1px solid;
    float: left;
}
.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }

</style>