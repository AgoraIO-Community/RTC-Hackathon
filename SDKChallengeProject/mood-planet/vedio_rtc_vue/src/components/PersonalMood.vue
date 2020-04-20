<template>
    <div >
        <leftNav></leftNav>
        <div id="personal">

           <div class="block">
                <div class="radio">
                    排序：
                    <el-radio-group v-model="reverse">
                    <el-radio :label="true">倒序</el-radio>
                    <el-radio :label="false">正序</el-radio>
                    </el-radio-group>
                </div>

                <el-timeline :reverse="reverse">
                    <el-timeline-item
                    class="elTimeLineItem"
                    v-for="(activity, index) in activities" :key="index"
                    :timestamp="activity.createtime">
                    {{activity.content}}
                    <br>
                    <img v-if="activity.img" :src="HOST+'/photos/'+activity.img" class="moodImg" alt="">
                    </el-timeline-item>
                </el-timeline>
            </div>

        
        </div>

    </div>
</template>

<script>
import axios from 'axios'
import LeftNav from '@/components/LeftNav'
export default {
    data(){
        return{
            reverse: true,
            activities: []
        }
    },
    components:{
        LeftNav
    },
    created:function(){
        let _this = this;
        var url =this.HOST + this.api  +'/personalMood';    
            this.$axios.get(url
            ).then(function(response){
                    if(response.data!=null){
                        _this.activities = response.data;
                    }
            }).catch(function(response){
                _this.$message.error('请求失败');
            })
    },
    methods:{
      
    }
}
</script>

<style>
#personal{
    width: 30%;
    margin-top: 0px;
    border: white 1px solid;
    float: left;
}
.elTimeLineItem {
    text-align: left!important;
}
.moodImg{
    width: 150px;
    height: 150px;
}

</style>