<template>
    <div >
        <div id="message">
            <div id="above">  
                <div class="four" v-for="(item,index) in res" :key="index">
                    <div :class="[{le:item.userid!=$store.state.userid},{rg:item.userid==$store.state.userid}]">
                        <img v-if="(item.userid!=$store.state.userid)&&otherImg!=''" :src="HOST+'/photos/'+otherImg" class="userImg" alt="">
                        {{item.content}}
                        <img v-if="(item.userid==$store.state.userid)&&userImg!=''" :src="HOST+'/photos/'+userImg" class="userImg" alt="">
                    </div>                  
                </div>
            </div>
            <div style="text-align: left" id="below"  ref="div" contenteditable="true" @blur="beforeSent($event.target.innerText)">              
            </div>
            <div id="sent">
                 <el-button style="position:absolute;right:30px;" type="primary" plain @click="sentMessage()">发送</el-button>
            </div>
           
        </div>
    </div>
</template>

<script>
import axios from 'axios'
import store from '@/vuex/store'

export default {
    store,
    data(){
        return{
            res:[],
            value:"",
            userImg:"",
            otherImg:""
        }
    },
    beforeRouteEnter (to, from, next) {
        next(vm => {
            if(to.path=='/initmes'){
                console.log("Mes");
                vm.$router.push({path:'/home'});
            }
        })
    },
    mounted: function () {
         var orderHeight = document.documentElement.clientHeight;
        document.getElementById("message").style.height = orderHeight + 'px';
    },
    methods:{
        getInit(){
            let _this = this;
            var url =this.HOST + this.api  +'/messagelist';    
            this.$axios.get(url,{
                params:{
                    otherid:_this.$route.params.id
                }
            }
            ).then(function(response){
                if(response.data==null){
                    return;
            }else if(response.data.message=='success'){
                let record = response.data.record.reverse();
                for(let i=0;i<record.length;i++){
                    var arr =new Object();
                    arr.userid=record[i].senderUserId;
                    arr.content=record[i].content;
                    _this.res.push(arr);
                }
            }    
            }).catch(function(response){
                // _this.$message.error('请求失败');
            });
        },
        getInitImg(){
            let _this = this;
            var url =this.HOST + this.api  +'/userimg';    
            this.$axios.get(url,{
                params:{
                    otherId:_this.$route.params.id
                }
            }
            ).then(function(response){
                if(response.data==null){
                    return;
            }else if(response.data.message=='success'){
                _this.userImg = response.data.userImg;
                _this.otherImg = response.data.otherImg;
            }    
            }).catch(function(response){
            
            });
        },
        beforeSent(e){
            this.value=e;
        },
        sentMessage(){
            var mes = {
                otherId:this.$route.params.id.toString(),
                context:this.value
            }
            var json = JSON.stringify(mes);
            //发送
            this.$store.state.socket.send(json);
            //文本置空
            this.$refs.div.innerHTML = "";
            //消息栏更新
            var arr =new Object();
            arr.userid=this.$store.state.userid;
            arr.content=this.value;
            if(this.res.length==4){
                this.res.splice(0,1);
            }
            this.res.push(arr);
            
        },
    },
    created: function(){
        console.log("mes创建");
        let _this = this;
        _this.getInit();
        _this.getInitImg(); 
        var so = _this.$store.state.socket;
        _this.$store.state.socket.onmessage=function(msg){
                console.log(msg.data);
                let message = JSON.parse(msg.data);
                if(message.vedioFlag){
                    var  user = message.sourse;
                    var  channel = message.channel;
                    _this.$router.push({name:'showvideo',params:{username:user,channel:channel}});
                }
                if(message.sourse==_this.$route.params.id){
                    var arr =new Object();
                    arr.userid=message.sourse;
                    arr.content=message.msgContent;
                    _this.res.splice(0,1);
                    _this.res.push(arr);
                }
        }
        
    },
    watch: {
        $route() {
            if (this.$route) {
               this.res=[];
               this.$refs.div.innerHTML = "";
               this.getInit();
               this.getInitImg();
            }
        },
    }
}
</script>

<style>
#message{
    width: 64%;
    margin-top: 0px;
    border-left: blue 1px solid;
    float: left;
    background-color:floralwhite;
}
#above{
    height: 70%;
}
#below{
    height: 20%;
    border: blue 1px solid;
}
#sent{
    height: 8%;
    overflow :scroll
}
.four{
    height: 25%;
    width: 100%;
    line-height:50px;
}
.le{
    float: left;
}
.rg{
    float: right;
}
.userImg{
    width: 40px;
    height: 40px;
}



</style>