<template>
  <div class="home-container">
    <div class="left-container" id="leftContainer">
      <video id="video" width="100%" height="100%" preload controls loop>
        <source
          src="../assets/35s.mp4"
          type="video/mp4"
        />
      </video>
    </div>
    <!-- 右侧工具栏 -->
    <div class="right-container" id="rightContainer">
      <div class="comment-box">
        <div class="head">发言记录</div>
        <div v-for="comment in commentList" :key="comment.time" :index="comment.time" class="row" @click="jump">
          <!-- <div class="row"> -->
          <div class="title">{{comment.name}} {{comment.time}}</div>
          <div class="content">{{comment.content}}</div>
          <!-- </div> -->
        </div>
      </div>
    </div>
  </div>
</template>

<script>
var pdf = require('pdfobject')
export default {
  data() {
    return {
      commentList: [
        {
          name: '李煦阳',
          time: '11:50:00',
          content: '第六题的B选项是不是没讲清楚啊？'
        },
        {
          name: '郭晓黑',
          time: '11:50:03',
          content: '条件没给全吧'
        },
        {
          name: '郭晓黑',
          time: '11:50:12',
          content: '没讲是哪种分裂过程'
        },
        {
          name: '彭欣悦',
          time: '11:50:16',
          content: '老师能再讲一下嘛？'
        },
        {
          name: '王晶伊',
          time: '11:51:01',
          content: '明白了'
        },
        {
          name: '彭欣悦',
          time: '11:51:08',
          content: '谢谢老师'
        },
        {
          name: '助教-沈俊鞑',
          time: '11:51:18',
          content:
            '第6题的B选项确实存在一些问题，它这里可能默认了末期就是最后的那个末期'
        },
        {
          name: '助教-沈俊鞑',
          time: '11:51:48',
          content:
            '如果是减Ⅰ分裂末期的话，一个染色体x应该是有两条DNA双链分子的。大家提出的问题和质疑很好。'
        },
        {
          name: '李煦阳',
          time: '11:52:00',
          content: '好的'
        },
        {
          name: '郭晓黑',
          time: '11:52:03',
          content: '谢谢助教'
        },
        {
          name: '郭晓黑',
          time: '11:52:12',
          content: '明白了'
        },
        {
          name: '彭欣悦',
          time: '11:52:16',
          content: '谢谢助教'
        },
        {
          name: '王晶伊',
          time: '11:52:01',
          content: '谢谢助教'
        },
      ]
    }
  },
  methods: {
      jump() {
          var video = document.getElementById("video")
          video.currentTime = Math.random() * 29
      }
  },
}
</script>

<style lang="less" scoped>
.home-container {
  width: 100%;
  height: 100%;
  .left-container {
    width: 80%;
    height: 100%;
    float: left;
  }
  .right-container {
    width: 20%;
    height: 100%;
    float: right;
    position: fixed;
    right: 0;
    border-left: 1px solid #eee;
    box-shadow: 11px 11px 21px #ddd;
    .comment-box {
      width: 100%;
      height: 100%;
      overflow: auto;
      cursor: pointer;
      //   padding:30px 10px 10px 10px;
      .head {
        //   background-color: #409eff;
        //   color:white;
        height: 30px;
        font-size: 14px;
        text-align: center;
        padding-top: 10px;
        border-bottom: 1px solid #eee;
        box-shadow: 1px 1px 1px #fff;
        margin-bottom: 10px;
      }
      .title {
        color: #409eff;
        padding: 0 10px;
      }
      .content {
        margin: 5px 0 10px 0;
        font-size: 15px;
        // width:100%;
        padding: 0 10px;
      }
      .content:hover {
        text-decoration: underline;
      }
    }
  }
}
</style>
