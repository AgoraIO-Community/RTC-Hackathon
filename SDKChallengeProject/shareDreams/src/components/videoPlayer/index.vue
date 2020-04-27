<template>
    <div id="mse"></div>
</template>
<script>
import HlsJsPlayer from 'xgplayer-hls.js'
export default { // 视频播放组件
  props: ['videoDetail'],
  data () {
    return {
      playerConfig: {
        id: 'mse',
        normalUrl: `/videos/${this.videoDetail.videoUrl}`, // 视频地址
        poster: this.videoDetail.poster // 视频封面
      }
    }
  },
  mounted () {
    this.playerInit()
  },
  methods: {
    playerInit () {
      let player = new HlsJsPlayer({ // player参数
        id: this.playerConfig.id,
        url: this.playerConfig.normalUrl,
        poster: this.playerConfig.poster,
        fluid: true,
        volume: 0.6,
        whitelist: ['iPhone', 'Android'],
        playsinline: true,
        videoInit: true,
        'x5-video-player-type': 'h5'
      })
      player.emit('resourceReady', [{name: '自动', url: this.playerConfig.highUrl}])
    }
  },
  beforeDestroy () {
    this.playerConfig = {}
  }
}
</script>
<style lang="less" scoped>
#mse{
    background-color: black;
    height: 100%;
    width: 100%;
}
</style>
