<template>
  <div class="container">
    <el-row :gutter="30">
      <el-col :span="14">
        <!-- 课前视频 -->
        <div class="video">
          <div class="head">课前视频({{id}})</div>
          <div class="grid">
            <div class="video-card" v-for="item in previewList" :key="item"  @click="goVideo">
              <div class="img-box">
                <div class="img-mask">
                  <img src="../assets/imag/protein.png" alt />
                </div>
              </div>
              <div class="title">Chapter3_1 蛋白质</div>
              <div class="info">
                <div class="time">
                  <i class="el-icon-time"></i>1h前发布
                </div>
                <div class="remark">
                  <i class="el-icon-chat-dot-round"></i>讨论：19
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- 直播回放 -->
        <div class="video">
          <div class="head">直播回放</div>
          <div class="grid">
            <div class="video-card" v-for="item in previewList" :key="item" @click="goVideo">
              <div class="img-box">
                <div class="img-mask">
                <img src="../assets/imag/protein.png" alt />
                </div>
              </div>
              <div class="title">Chapter3_1 蛋白质</div>
              <div class="info">
                <div class="time">
                  <i class="el-icon-time"></i>1h前发布
                </div>
                <div class="remark">
                  <i class="el-icon-chat-dot-round"></i>讨论：19
                </div>
              </div>
            </div>
          </div>
        </div>
      </el-col>
      <el-col :span="10">
        <Notice :noticeInfo="noticeInfo"></Notice>
        <el-row :gutter="10">
          <el-col :span="12">
            <Notice :noticeInfo="fileList"></Notice>
          </el-col>
          <el-col :span="12">
            <Notice :noticeInfo="homeworkList"></Notice>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  created() {
    if (window.sessionStorage.getItem('blackboardPage')) this.id = window.sessionStorage.getItem('blackboardPage')
  },
  watch: {
    $route(){
      window.sessionStorage.setItem('blackboardPage',this.$route.params.id)
      this.id = this.$route.params.id
    }
  },
  methods:{
    goVideo() {
      //  window.open('http://s-23539.p.cdn-static.cn/23539_15868606194352.mp4','_blank')
      this.$router.push('/video')
    }
  },
  data() {
    return {
      previewList: [1, 2, 3, 4, 5, 6, 7, 8],
      id:'CS285',
      noticeInfo: {
        head: '布告栏',
        list: [
          {
            title: '下一次作业提交注意事项',
            time: '3小时前 王安',
            info:
              '同学们好，请大家将下一次讨论的ppt转换成pdf之后，再上传到我们的Xedu系统中，不要因为形式的问题而导致上传错误，作业截止日期是这周六。',
            id: 'ee115'
          },
          {
            title: '机电一体化',
            time: '10小时前 张锋',
            info:
              '上一次课堂上quiz的结果已经反馈到Xedu系统中，正确答案附在文件下载区，对这次批分有争议的同学请给助教或者老师发邮件，并附上批改后的pdf',
            id: 'CS285'
          }
        ]
      },
      fileList: {
        head: '课件下载',
        list: [
          {
            title: '#第三章',
            time: '10小时前 王安',
            info: 'Chapter3.pdf',
            id: 'ee115'
          },
          {
            title: '#第二章',
            time: '2020.4.1 王安',
            info: 'Chapter2.pdf',
            id: 'ee115'
          },
          {
            title: '#第一章',
            time: '2020.3.25 王安',
            info: 'Chapter1.pdf',
            id: 'ee115'
          }
        ]
      },
      homeworkList: {
        head: '作业下载',
        list: [
          {
            title: '#第三章',
            time: '10小时前 王安',
            info: 'Homework3.pdf',
            id: 'ee115'
          },
          {
            title: '#第二章',
            time: '2020.4.1 王安',
            info: 'Homework2.pdf',
            id: 'ee115'
          },
          {
            title: '#第一章',
            time: '2020.3.25 王安',
            info: 'Homework1.pdf',
            id: 'ee115'
          }
        ]
      }
    }
  }
}
</script>

<style lang="less" scoped>
.video {
  margin-top: 10px;
  .head {
    font-size: 20px;
    border-bottom: 1px solid #999;
    padding-bottom: 3px;
  }
  .grid {
    display: flex;
    justify-content: space-around;
    flex-wrap: wrap;
  }
}
.video-card {
  padding-top: 10px;
  .img-box {
    width: 160px;
    height: 100px;
    border-radius: 7px;
    .img-mask {
      height:100%;
      width:100%;
    }
    .img-mask:hover{
      background-color: black;
      opacity: 0.2;
    }
    img {
      width: 100%;
      height: 100%;
      border-radius: 7px;
    }
  }
  .img-box:hover{
    cursor: pointer;
  }
  .title {
    font-size: 13px;
    padding: 2px 4px;
    cursor: pointer;
  }
  .title:hover{
    color:#027db4;
  }
  .info {
    display: flex;
    .time,
    .remark {
      flex: 1;
      font-size: 10px;
      color: #777;
    }
  }
}
</style>
