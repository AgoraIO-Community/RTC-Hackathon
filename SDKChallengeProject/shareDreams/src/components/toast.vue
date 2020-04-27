<template>
  <transition name="alert-fade">
    <div id="toast"
         v-show="visible"
         class="dialog-tips dialog-center">
      {{message}}
    </div>
  </transition>
</template>
<script>
export default {
  data () {
    return {
      visible: false,
      message: ''
    }
  }
}
</script>
<style lang="less" scoped>

.alert-fade-enter-active,
.alert-fade-leave-active {
  transition: opacity 0.3s;
}
.alert-fade-enter,
.alert-fade-leave-to {
  opacity: 0;
}
.dialog-tips {
  position: fixed;
  z-index: 100;
  min-width: 220px;
  padding: 30px;
  border-radius: 15px;
  white-space: nowrap;
  background-color: #fff;
  box-shadow: 0px 1px 5px 0 rgba(0, 0, 0, 0.363);
  text-align: center;
  color: rgb(70, 70, 70);
}
.dialog-center {
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
</style>
