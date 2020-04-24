<template>
<div class="index">
    <Header/>
    <Carousel />
    <RecommandVideo />
    <RecommandLive />
</div>
</template>
<script>
import Header from '../../components/header'
import Carousel from '../../components/carousel'
import RecommandVideo from '../../components/recommandVideo'
import RecommandLive from '../../components/recommandLive'
export default {
  components: {Carousel, Header, RecommandVideo, RecommandLive}
}
</script>
<style scoped>
.index{
  background-color: black;
  padding-bottom: 10px;
}
</style>
