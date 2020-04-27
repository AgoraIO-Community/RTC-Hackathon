<template>
<div class="carousel">
    <el-carousel :interval="4000" type="card" height="400px">
    <el-carousel-item v-for="(item,index) in video" :key="index">
      <el-image :src="item.poster" style="width:100%;height:100%"/>
    </el-carousel-item>
  </el-carousel>
</div>
</template>
<script>
import API from '../utils/API'
export default { // 轮播展示推荐视频
  data () {
    return {
      video: []
    }
  },
  created () {
    API.Video.getCarouselVideo({}, (responseData) => {
      if (responseData.code === 200) {
        this.video = responseData.data
      }
    })
  }
}
</script>
<style scoped>

</style>
