<template>
  <span>
    <slot v-if="initSuccess"></slot>
  </span>
</template>

<script>
export default { // 组件延迟加载
  props: {
    time: {
      required: false,
      default: 0
    }
  },
  data () {
    return {
      initSuccess: false
    }
  },
  created () {
    this.initSlot()
  },

  methods: {
    initSlot () {
      let vm = this
      setTimeout(function () {
        vm.initSuccess = true
      }, (Number(vm.time || 0)))
    }
  }
}
</script>

<style scoped>

</style>
