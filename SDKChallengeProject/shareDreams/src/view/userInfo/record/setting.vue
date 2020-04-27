<template>
  <div class="all-settings">
    <div class="intro">
      <p>录制设备设置</p>
    </div>
    <div class="content">
      <div class="camera-setting">
        <label for="cameraId" class="active">摄像头</label>
        <el-select class="select" v-model="settingOptions.cameraId" :placeholder="options.cameraOptions.length>0?options.cameraOptions[0].name:''">
          <el-option
            v-for="item in options.cameraOptions"
            :key="item.value"
            :label="item.name"
            :value="item.value">
          </el-option>
        </el-select>
      </div>
      <div class="microphone-setting">
        <label for="microphoneId" class="active">麦克风</label>
        <el-select class="select" v-model="settingOptions.microphoneId" :placeholder="options.microphoneOptions.length>0?options.microphoneOptions[0].name:''">
          <el-option
            v-for="item in options.microphoneOptions"
            :key="item.value"
            :label="item.name"
            :value="item.value">
          </el-option>
        </el-select>
      </div>
    </div>
    <div class="btn-groups">
      <div class="cancel" @click="cancel()"><p>默认</p></div>
      <div class="submit" :class="change?'active':''" @click="submit()"><p>更改</p></div>
    </div>
  </div>
</template>
<script>
import rtcMethods from '../../../utils/rtc/rtcMethods'
export default {
  data () {
    return {
      options: { // 设备列表
        cameraOptions: [],
        microphoneOptions: []
      },
      settingOptions: { // 选中的设备
        cameraId: '',
        microphoneId: ''
      },
      change: false
    }
  },
  watch: {
    settingOptions: {
      handler () {
        this.change = true
      },
      deep: true
    }
  },
  methods: {
    cancel () { // 选中默认的设备
      this.settingOptions.microphoneId = this.options.microphoneOptions[0].value
      this.settingOptions.cameraId = this.options.cameraOptions[0].value
      this.change = false
    },
    submit () { // 保存设置的设备
      this.$store.dispatch('emitSaveRecordSetting', this.settingOptions)
      this.$toast('设置成功')
    }
  },
  created () {
    var _this = this
    rtcMethods.getMedias((devices) => { // 获取用户设备 需要修改
      devices.audios.forEach(function (audio) {
        let params = {value: audio.value, name: audio.name}
        _this.options.microphoneOptions.push(params)
      })
      devices.videos.forEach(function (video) {
        let params = {value: video.value, name: video.name}
        _this.options.cameraOptions.push(params)
      })
      _this.settingOptions.microphoneId = _this.options.microphoneOptions[0].value
      _this.settingOptions.cameraId = _this.options.cameraOptions[0].value
    })
  }
}
</script>
<style lang="less" scoped>
.all-settings{
  padding-bottom: 20px;
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
  .content{
    min-height: 250px;
    height: 100px;
    display: flex;
    margin-left: 30px;
    flex-direction: column;
    .camera-setting{
      margin-top: 30px;
      .select{
         margin-left: 20px;
          width: 400px;
      }
    }
    .microphone-setting{
      margin-top: 30px;
      .select{
        margin-left: 20px;
        width: 400px;
      }
    }
  }
  .btn-groups{
    margin-left: 150px;
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
      margin-left: 30px;
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
