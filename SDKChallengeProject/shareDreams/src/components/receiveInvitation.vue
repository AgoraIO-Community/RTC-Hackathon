<template>
    <div class="receive">
        <div class="top">
            <img :src="chatUser.userAvatar" />
        </div>
        <div class="bottom">
            <div class="name">{{chatUser.userName}}</div>
            <div class="tips">正邀请您{{method}}通话中</div>
            <div class="options">
                <div class="refuse" @click="options('refuse')">拒绝</div>
                <div class="accept" @click="options('accept')">接受</div>
            </div>
        </div>
    </div>
</template>
<script>
export default { // 接收到邀请提示（语音、视频）
  props: ['method'],
  data () {
    return {
      chatUser: this.$parent.chatUser
    }
  },
  methods: {
    options (eventName) {
      this.$emit('options', eventName)
    }
  }
}
</script>
<style lang="less" scoped>
.receive{
    width:300px;
    height: 400px;
    background: black;
    flex-direction: column;
    display: flex;
    position: relative;
    border-radius: 5px;
    .top{
        flex: 2;
        display: flex;
        flex-direction: row;
        justify-content: center;
        align-items: center;
        img{
            width: 120px;
            height: 120px;
            border-radius: 50%;
        }
    }
    .name{
        color: white;
        margin-bottom: 5px;
        text-align: center;
    }
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
        .refuse{
            width: 60px;
            height:30px;
            background-color: red;
            display: flex;
            flex-direction: row;
            justify-content: center;
            align-items: center;
            border-radius: 5px;
            cursor: pointer;
        }
        .accept{
            margin-left: 90px;
            width: 60px;
            height:30px;
            background-color: green;
            display: flex;
            flex-direction: row;
            justify-content: center;
            align-items: center;
            border-radius: 5px;
            cursor: pointer;
        }
        .hangup{
            width: 60px;
            height:60px;
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
