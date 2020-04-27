<template>
    <div class="hangup" :style="hangupStatus?'opacity:1':'opacity:0'">
        <div class="options">
            <div class="hangup" @click="hangup()">挂断</div>
        </div>
    </div>
</template>
<script>
export default {
  props: ['hangupStatus'], // 是否隐藏
  methods: {
    hangup () {
      this.$emit('options', 'hangup')
    }
  }
}
</script>
<style lang="less" scoped>
.hangup{
    position: absolute;
    bottom: 10px;
    left:50%;
    transform: translate(-50%,0);
    z-index: 999;
    .tips{
        color: white;
        margin-bottom: 20px;
        text-align: center;
    }
    .options{
        flex: 1;
        display: flex;
        flex-direction: row;
        color: white;
        margin-bottom: 20px;
        justify-content: center;
        .hangup{
            width: 50px;
            height:50px;
            font-size: 12px;
            display: flex;
            flex-direction: row;
            justify-content: center;
            align-items: center;
            border-radius: 50%;
            border: 1px solid white;
            cursor: pointer;
        }
    }
}
</style>
