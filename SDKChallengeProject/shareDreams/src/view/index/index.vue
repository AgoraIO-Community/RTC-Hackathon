<template>
<div class="index">
    <keep-alive>
    <Header class="header"/>
    </keep-alive>
    <div class="intro">
        <div class="left">在这里尽情展示你的才能</div>
        <div class="from-bottom">和其他人一起分享你的梦想</div>
        <div class="see" @click="see()"><p>去看看</p></div>
    </div>
</div>
</template>
<script>
import Header from '../../components/header'
export default {
  components: {Header},
  methods: {
    see () {
      this.$router.push('/index')
    }
  }
}
</script>
<style lang="less" scoped>
.index{
    background-image: url('../../../static/background.jpg');
    background-size: 100% 100%;
    width: 100%;
    min-height: 100%;
    .header{
        background-color: transparent;
    }
    .intro{
        width: 300px;
        height: 200px;
        position: absolute;
        top: 70%;
        left: 25%;
        transform: translate(-70%,-25%);
        color: white;
        .left{
            font-size: 20px;
            font-weight: bold;
            margin-bottom: 10px;
        }
        .from-bottom{
            font-size: 15px;
        }
        .see{
            width: 100px;
            height: 30px;
            background:#ffd04b;
            margin: 20px;
            color:black;
            flex: 1;
            display: flex;
            flex-direction: row;
            justify-content: center;
            align-items: center;
            border-radius: 5px;
            cursor: pointer;
            p{
                font-weight: bold;
                font-size: 10px;
            }
        }
    }
}
</style>
