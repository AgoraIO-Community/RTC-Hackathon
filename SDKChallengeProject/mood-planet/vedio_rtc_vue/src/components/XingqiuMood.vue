<template>
    <div >
        <leftNav></leftNav>
        <div id="xingqiu">
            <el-row>
                <el-col v-if="$store.state.userid!=o.userId" :span="5" v-for="(o, index) in Moods" :key="index" >
                    <el-card :body-style="{ padding: '0px' }">
                    <img v-if="o.img" :src="HOST+'/photos/'+o.img" class="image" alt="">
                    <div style="padding: 14px;">
                        <span>{{o.content}}</span>
                        <div class="bottom clearfix">
                        <!-- <time class="time">{{ o.createtime }}</time> -->
                        <el-button type="text" class="button" @click="match(o.userId)">匹配</el-button>
                        </div>
                    </div>
                    </el-card>
                </el-col>
            </el-row>

        </div>
    </div>
</template>

<script>
import axios from 'axios'
import LeftNav from '@/components/LeftNav'
import store from '@/vuex/store'
export default {
    store,
    data(){
        return{
            Moods:[]
            
        }
    },
    components:{
        LeftNav
    },
    computed:{
       userid(){
          return this.$store.state.userid;
       }
    },
    created:function(){
        let _this = this;
        var url =this.HOST + this.api  +'/xingqiuMood';    
            this.$axios.get(url
            ).then(function(response){
                    if(response.data!=null){
                        _this.Moods = response.data;
                    }
            }).catch(function(response){
                _this.$message.error('请求失败');
            })
    },
    methods:{
      match(otherid){
        console.log(otherid);
        let _this = this;
        var url =this.HOST + this.api  +'/matchother';    
            this.$axios.get(url,{
                params:{otherid:otherid}
            }
            ).then(function(response){
                    if(response.data==null){
                       alert("匹配失败");
                    }else if(response.data=='matched'){
                        alert("已匹配");
                    }else if(response.data=='success'){
                        alert("匹配成功");
                    }
            }).catch(function(response){
                _this.$message.error('请求失败');
            })
      }
    }
}
</script>

<style>
#xingqiu{
    width: 94%;
    margin-top: 0px;
    border: white 1px solid;
    float: left;
}

.image{
    height: 200px;
}

</style>