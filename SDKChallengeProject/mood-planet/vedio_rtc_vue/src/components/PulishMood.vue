<template>
    <div >
        <leftNav></leftNav>
       <div id="pulish">
           <el-row>
               <el-col style="padding-left:20px">
                   <el-tabs  >
                       <el-tab-pane label="文字">
                           <div class="wenzi">
                                <span>心情描述:  </span>
                                <textarea rows="15" cols="80" v-model="context"></textarea>
                                <br><br>
                                图片：
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
                                <el-button type="danger" @click="pulishWord()">发布心情</el-button>
                            </div>
                        
                       </el-tab-pane>
                       <el-tab-pane label="声音"></el-tab-pane>
                   </el-tabs>
               </el-col>
           </el-row>
       </div>
    </div>
</template>

<script>
import axios from 'axios'
import LeftNav from '@/components/LeftNav'
export default {
    data(){
        return{
            context:'',
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
    mounted: function () {
         var orderHeight = document.documentElement.clientHeight;
        document.getElementById("pulish").style.height = orderHeight + 'px';
    },
    methods:{
        pulishWord(){
            if(this.imgsrc!=null){
                let _this = this;
                var url =_this.HOST + _this.api  +'/moodWord';    
                _this.$axios.get(url, {
                    params: {
                    context: _this.context,
                    img:_this.imgsrc
                    }
                }).then(function(response){
                        if(response.data.message=="success"){
                            alert("发布成功");
                        }else{
                            alert('发布失败');
                        }
                }).catch(function(response){
                    _this.$message.error('请求失败');
                })
            }else{
                this.$message.error('亲，请上传一张图片');
            }
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
#pulish{
    width: 94%;
    margin-top: 0px;
    border: white 1px solid;
    float: left;
}

.el-tabs__item{
    padding-right: 200px;
    font-size: 30px;
}

.wenzi{
    text-align: left;
}

</style>