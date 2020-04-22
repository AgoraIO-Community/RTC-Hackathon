<template>
  <transition name="fade">
    <div class="message_box" :class="type" v-if="visible">
      <div class="content">
        {{content}}
      </div>
    </div>
  </transition>
</template>

<script>
  export default {
    name: "MyMessage",
    data() {
      return {
        content: '',
        time: 3000,
        visible: false,
        type:'info',//'success','warning','error'
        hasClose:false,
      }
    },
    mounted() {
      this.close()
    },
    methods: {
      close() {
        window.setTimeout(() =>{
          this.visible = false
        }, this.time);
      }
    }
  }
</script>

<style lang="scss">
.message_box{
  box-sizing: border-box;
  width: 400px;
  height: 50px;
  font-size: 18px;
  line-height: 48px;
  padding: 0 30px;
  text-align: center;
  border-radius: 4px;
  border: 1px solid rgb(180, 84, 84);
  color: rgb(241, 32, 13);
  font-weight: 500;
  background: rgba(255, 255, 255, 0.8);
  position: fixed;
  left: 50vw;
  transform: translateX(-50%);
  z-index: 10000;
}
@media screen and (max-width: 580px) {
  .message_box{
    width: 200px;
    font-size: 14px;
    height: 40px;
    line-height: 40px;
    position: absolute;
  }
}
</style>