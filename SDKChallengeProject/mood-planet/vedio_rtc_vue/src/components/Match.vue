<template>
    <div >
        <leftNav></leftNav>
        <div id="match">
           <div v-for="(match,index) in matchlist" :key="index" class="text item mystyle">
                呢称：{{match.user.username}}
                <el-button type="success" round style="float:right;margin-right:3%" @click="sent(match.user.id)">
                    <!-- <router-link to="/matchList/mes">call</router-link> -->
                   call
                </el-button>
                 <el-button type="success" round style="float:right;margin-right:3%" @click="video(match.user.id)">
                   video
                </el-button>
            </div>
        </div>
        <router-view></router-view>
    </div>
</template>

<script>
import axios from 'axios'
import LeftNav from '@/components/LeftNav'
export default {
    data(){
        return{
          matchlist:[]
        }
    },
    components:{
        LeftNav
    },
    created:function(){
        let _this = this;
        var url =this.HOST + this.api  +'/matchlist';    
            this.$axios.get(url
            ).then(function(response){
                    if(response.data==null){
                        return;
                    }else if(response.data.message=='success'){
                        _this.matchlist = response.data.matchlist;
                    }else if(response.data.message=='无匹配好友'){
                        return;
                    }
            }).catch(function(response){
               _this.$message.error('请求失败');
            })
    },
    mounted: function () {
         var orderHeight = document.documentElement.clientHeight;
        document.getElementById("match").style.height = orderHeight + 'px';
    },
   
    methods:{
      sent(id){
        this.$router.push({name:'message',params:{id}});
        // this.$router.push({path:`/matchList/mes/${id}`});
      },
      video(id){
        this.$router.push({name:'video',params:{id}});
      },
    }
}
</script>

<style>
#match{
    width: 30%;
    margin-top: 0px;
    border: white 1px solid;
    float: left;
    /* background-color: darksalmon; */
}

.mystyle{
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    height:10%;
    text-align: left;
    padding-left: 5%;
    padding-top: 2%;
}
.image{
    width: 40px;
    height: 40px;
}
</style>