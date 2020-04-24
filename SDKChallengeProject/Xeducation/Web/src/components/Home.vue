/* eslint-disable */
<template>
  <div class="container" style="background-color: #eee;">
    <div class="page">
      <div class="blank-bar"></div>
      <el-row :gutter="20">
        <!-- UserInfo Card -->
        <el-col :span="4">
          <div class="userInfo">
            <div class="userInfo-top"></div>
            <div class="userInfo-name">
              <div class="avatar-box">
                <img src="../assets/imag/avatar.png" alt />
              </div>
              <div @click="goClass">郭小黑</div>
              <div style="font-size:13px">欢迎来到Xedu课堂!</div>
            </div>
            <div class="userInfo-grade">
              <div class="key">年级</div>
              <div class="value">2018级</div>
            </div>
            <div class="userInfo-major">
              <div class="key">专业</div>
              <div class="value">电子信息工程</div>
            </div>
          </div>
        </el-col>

        <el-col :span="13">
          <Timetable></Timetable>
        </el-col>
        <el-col :span="6">
          <Notice :noticeInfo="noticeInfo"></Notice>
          <Notice :noticeInfo="deadlineInfo"></Notice>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import { MessageBox } from 'element-ui'
export default {
  data() {
    return {
      noticeInfo: {
        head: '通知',
        list: [
          {
            title: '电磁学',
            time: '3小时前',
            info: '王成老师发布了作业：Hw2',
            id: 'ee115'
          },
          {
            title: '机电一体化',
            time: '10小时前',
            info: '罗峰老师发布了作业：Quiz2',
            id: 'ee115'
          },
          {
            title: '现代生命科学导论',
            time: '三天前',
            info: '张栋老师发布了作业：Quiz2',
            id: 'ee115'
          }
        ]
      },
      deadlineInfo: {
        head: 'Deadline',
        list: [
          {
            title: '电磁学',
            time: '需要提交：Hw1.pdf',
            info: '截止日期：3月28日 23:59',
            id: 'ee115'
          },
          {
            title: '信号与系统',
            time: '需要提交：Hw1.pdf',
            info: '截止日期：3月30日 23:59',
            id: 'ee115'
          },
          {
            title: '信号与系统',
            time: '需要提交：Quiz1.pdf',
            info: '截止日期：4月3日 23:59',
            id: 'ee115'
          }
        ]
      }
    }
  },
  created() {},
  methods: {
    goClass() {
      MessageBox.alert('《机电一体化》将在五分钟后上课，请尽快进入课堂', '上课提醒', {
          confirmButtonText: '跳转进入',
          callback: action => {
            if (action) {
              this.$router.push('/livebroadcast')
            }
          }
        })
    }
  }
}
</script>

<style lang="less" scoped>
.color {
  background-color: #999;
  height: 90px;
  margin: 5px 10px;
}
.page {
  width: 1400px;
  height: 100%;
  margin: 0 auto;
  background-color: white;
  .blank-bar {
    height: 20px;
  }
  .userInfo {
    margin-left: 30px;
    // height:500px;
    box-shadow: 2px 1px 5px #999;
    border-radius: 20px;
    display: flex;
    flex-direction: column;
    font-size: 13px;
    .key {
      color: #999;
      padding-top: 10px;
    }
    .value {
      padding-top: 5px;
    }
    .userInfo-top {
      // flex:1;
      height: 60px;
      background-color: #015478;
      border-radius: 20px 20px 0 0;
    }
    .userInfo-name {
      // flex:1.5;
      height: 90px;
      display: flex;
      flex-direction: column;
      justify-content: flex-end;
      font-size: 15px;
      .avatar-box {
        height: 90px;
        width: 90px;
        border: 1px solid #eee;
        border-radius: 50%;
        // box-shadow: 0 0 10px;
        position: relative;
        left: 50%;
        transform: translate(-50%, 0%);
        img {
          width: 100%;
          height: 100%;
          border-radius: 50%;
        }
      }
      div {
        text-align: center;
        line-height: 25px;
      }
    }
    .userInfo-grade {
      // flex:1;
      height: 55px;
    }
    .userInfo-major {
      // flex:1;
      height: 55px;
    }
    .userInfo-name,
    .userInfo-grade {
      border-bottom: solid 1px #eee;
    }
    .userInfo-grade,
    .userInfo-major {
      padding-left: 20px;
      display: flex;
      flex-direction: column;
      // justify-content: space-around;
    }
  }
}
</style>
