<template>
  <wxc-tab-bar
    :tab-titles="tabTitles"
    :tab-styles="tabStyles"
    title-type="icon"
    @wxcTabBarCurrentTabSelected="wxcTabBarCurrentTabSelected"
  >
    <div class="item-container" :style="contentStyle">
      <div class="flow-box">
        <wxc-cell label="今日安排" :has-margin="true"></wxc-cell>
      </div>
      <div class="flow-box">
        <wxc-simple-flow :list="testData" :themeColor="themeColor"></wxc-simple-flow>
      </div>
    </div>

    <div class="item-container" :style="contentStyle">
      <list>
        <cell v-for="item in newsData">
          <div class="card">
            <text class="label">{{item.label}}</text>
            <text class="time">{{item.date}}</text>
            <text class="content">{{item.content}}</text>
          </div>
        </cell>
      </list>
    </div>

    <div class="item-container" :style="contentStyle">
      <div class="back">
        <div class="Statistics">
          <text class="StaLabel">18</text>
          <text class="StaContent">本周剩余课时</text>
        </div>
        <div class="Statistics">
          <text class="StaLabel">5</text>
          <text class="StaContent">待提交作业</text>
        </div>
        <div class="Statistics">
          <text class="StaLabel">4</text>
          <text class="StaContent">待查看通知</text>
        </div>
        <div class="Statistics">
          <text class="StaLabel">8</text>
          <text class="StaContent">待下载课件</text>
        </div>
      </div>
      <div class="flow-box">
        <wxc-cell label="姓名" title="张小黑" :has-margin="true"></wxc-cell>
        <wxc-cell label="年级" title="2018级" :has-margin="true"></wxc-cell>
        <wxc-cell label="专业" title="电子与计算机工程" :has-margin="true"></wxc-cell>
        <wxc-cell title="上课提醒" :has-arrow="false">
          <switch slot="value"></switch>
        </wxc-cell>
        <wxc-cell title="新文件提醒" :has-arrow="false">
          <switch slot="value"></switch>
        </wxc-cell>
        <wxc-cell title="专注度检测" :has-arrow="false">
          <switch slot="value"></switch>
        </wxc-cell>
      </div>
    </div>
  </wxc-tab-bar>
</template>

<script>
import { WxcTabBar, Utils, WxcSimpleFlow, WxcCell } from "weex-ui";

// https://github.com/apache/incubator-weex-ui/blob/master/example/tab-bar/config.js
import Config from "./config";

export default {
  components: { WxcTabBar, WxcSimpleFlow, WxcCell },
  data: () => ({
    tabTitles: Config.tabTitles,
    tabStyles: Config.tabStyles,
    themeColor: {
      lineColor: "#bf280b",
      pointInnerColor: "#b95048",
      pointBorderColor: "#bf280b",
      highlightTitleColor: "#bf280b",
      highlightPointInnerColor: "#bf280b",
      highlightPointBorderColor: "#d46262"
    },
    testData: [
      {
        date: "上午 8:15-9:55",
        desc: "",
        highlight: true,
        title: "《机电一体化》教学"
      },
      {
        date: "上午 10:15-11:55",
        desc: "",
        title: "《线代生命科学导论》讨论课"
      },
      {
        date: "下午 15:00-16:00",
        desc: "史研-助教：本次习题课将讲解Hw5内容",
        title: "《电磁学习题课》"
      },
      {
        date: "下午 23:59",
        desc: "",
        title: "《信号与系统》 Hw2 截止提交"
      }
    ],
    newsData: [
      {
        label: "#电磁学",
        date: "3小时前",
        content: "王方老师发布了作业：Hw4"
      },
      {
        label: "#信号与系统",
        date: "10小时前",
        content: "徐勇老师发布了课件：Chapter3.pdf"
      },
      {
        label: "#机电一体化",
        date: "15小时前",
        content: "罗峰老师发布了作业：Quiz2.pdf 截止日期：5月1日 23:59"
      },
      {
        label: "#电磁学",
        date: "两天前",
        content: "同学们好，请大家将下一次讨论的ppt转换成pdf之后，再上传到我们的Xedu系统中，不要因为形式的问题而导致上传错误，作业截止日期是这周六。"
      },
      {
        label: "#电磁学",
        date: "三天前",
        content: "王方老师发布了作业：Hw3"
      },
      {
        label: "#信号与系统",
        date: "三天前",
        content: "徐勇老师发布了课件：Chapter3.pdf"
      },
      {
        label: "#电磁学",
        date: "3小时前",
        content: "王方老师发布了作业：Hw2"
      },
      {
        label: "#信号与系统",
        date: "两天前",
        content: "徐勇老师发布了课件：Chapter3.pdf"
      },
      {
        label: "#电磁学",
        date: "3小时前",
        content: "王方老师发布了作业：Hw2"
      },
      {
        label: "#信号与系统",
        date: "两天前",
        content: "徐勇老师发布了课件：Chapter3.pdf"
      },
      {
        label: "#电磁学",
        date: "3小时前",
        content: "王方老师发布了作业：Hw2"
      },
      {
        label: "#信号与系统",
        date: "两天前",
        content: "徐勇老师发布了课件：Chapter3.pdf"
      },
      {
        label: "#电磁学",
        date: "3小时前",
        content: "王方老师发布了作业：Hw2"
      },
      {
        label: "#信号与系统",
        date: "两天前",
        content: "徐勇老师发布了课件：Chapter3.pdf"
      }
    ]
  }),
  created() {
    const tabPageHeight = Utils.env.getPageHeight();
    // If the page doesn't have a navigation bar
    // const tabPageHeight = env.deviceHeight / env.deviceWidth * 750;
    const { tabStyles } = this;
    this.contentStyle = { height: tabPageHeight - tabStyles.height + "px" };
  },
  methods: {
    wxcTabBarCurrentTabSelected(e) {
      const index = e.page;
      // console.log(index);
    }
  }
};
</script>

<style scoped>
.Statistics{
  align-items: center;
}
.StaContent{
  color:white;
  font-size:20px;
}
.StaLabel{
  color:white;
  font-size:60px;
  margin-bottom:5px;
  margin-top:100px;
}
.avatar {
  width: 200px;
  height: 200px;
  border-radius: 50%;
  margin-left: 100px;
  margin-top: 100px;
}
.back {
  height: 300px;
  width: 750px;
  background-image: linear-gradient(to top, #29bdd9, #276ace);
  /* align-items:center; */
  display: flex;
  justify-content: space-around;
  flex-direction: row;
}
.nomore {
  margin-top: 20px;
  color: #797979;
  font-size: 25px;
}
.content {
  font-size: 30px;
  margin-bottom: 15px;
}
.time {
  font-size: 25px;
  margin-bottom: 15px;
  color: #797979;
}
.label {
  font-size: 40px;
  color: #388fb4;
  margin-top: 40px;
  margin-bottom: 15px;
}
.card {
  width: 700px;
  background-color: white;
  margin-top: 40px;
  padding-left: 20px;
  box-shadow: 2px 1px 5px #999;
}
.item-container {
  width: 750px;
  background-color: #f2f3f4;
  align-items: center;
  /* justify-content: center; */
}
.flow-box {
  background-color: white;
  width: 750px;
}
</style>