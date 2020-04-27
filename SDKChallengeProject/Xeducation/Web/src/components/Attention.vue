/* eslint-disable */
<template>
  <el-container class="home-container">
    <el-aside width="200px" class="el-aside">
      <!-- 侧边栏菜单 -->
      <el-menu
        background-color="white"
        text-color="#111"
        active-text-color="#027db4"
        :unique-opened="true"
        :default-active="courseTabPath"
      >
        <el-menu-item index="daily" @click="switch2Daily()">
          <i class="el-icon-menu"></i>
          <span slot="title">专注度日报</span>
        </el-menu-item>
        <!-- <el-menu-item>
            <i class="el-icon-menu"></i>
            <span slot="title">专注度周报</span>
        </el-menu-item>
        <el-menu-item>
            <i class="el-icon-menu"></i>
            <span slot="title">专注度月报</span>
        </el-menu-item>-->
        <el-submenu index="course">
          <template slot="title">
            <i class="el-icon-location"></i>
            <span>按课程分</span>
          </template>
          <el-menu-item-group>
            <el-menu-item
              v-for="item in courseList"
              :key="item.classID"
              :index="item.classID"
              @click="switch2Course(item.className)"
            >
              <template slot="title">
                <i class="el-icon-edit"></i>
                <span>{{item.className}}</span>
              </template>
            </el-menu-item>
          </el-menu-item-group>
        </el-submenu>
      </el-menu>
    </el-aside>

    <el-main class="el-main">
      <!-- 专注度日报 -->
      <div v-show="dispalyMode==='daily'">
        <el-row :gutter="0">
          <el-col :span="4">
            <el-card>
              <div class="infoCard">
                <div class="head">昨日学习概览</div>
                <div class="title">总专注时间</div>
                <div class="content">246分钟</div>
                <div class="title">课内手机使用</div>
                <div class="content">40分钟</div>
                <div class="title">最专注时段</div>
                <div class="content">09:50-10:50</div>
                <div class="title">专注度最高课程</div>
                <div class="content">信号与系统</div>
              </div>
            </el-card>
          </el-col>
          <el-col :span="20">
            <el-card>
              <div id="daily" class="longChart"></div>
            </el-card>
          </el-col>
        </el-row>
        <el-row :gutter="0">
          <el-col :span="10">
            <el-card>
              <div id="ratio"></div>
            </el-card>
          </el-col>
          <el-col :span="14">
            <el-card>
              <div id="rank"></div>
            </el-card>
          </el-col>
        </el-row>
      </div>

      <div v-show="dispalyMode==='course'">
        <el-card>
          <div id="history1" class="longChart"></div>
        </el-card>
        <el-card>
          <div id="history2" class="longChart"></div>
        </el-card>
      </div>
    </el-main>
  </el-container>
</template>

<script>
import echarts from 'echarts'
export default {
  data() {
    return {
      courseList: [
        {
          classID: 'CS285',
          className: '机电一体化'
        },
        {
          classID: 'CS171',
          className: '计算机图形学'
        },
        {
          classID: 'EE150',
          className: '信号与系统'
        },
        {
          classID: 'EE130',
          className: '电磁学'
        },
        {
          classID: 'BIO1011',
          className: '现代生命科学导论'
        },
        {
          classID: 'EE130P',
          className: '电磁学课程设计'
        },
        {
          classID: 'EE150L',
          className: '信号与系统实验'
        },
        {
          classID: 'GESS1014',
          className: '马克思主义原理'
        },
        {
          classID: 'GEPE1026',
          className: '游泳'
        },
        {
          classID: 'GESS1025',
          className: '柏拉图《理想国导读》'
        }
      ],
      courseTabPath: 'daily',
      dispalyMode: 'daily'
    }
  },
  created() {},
  mounted() {
    this.initDaily()
    this.initRatio()
    this.initRank()
  },
  methods: {
    initDaily() {
      var daily = echarts.init(document.getElementById('daily'))
      var dailyOption = {
        title: {
          text: '您昨日上午的专注度曲线'
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: [
            '9:00',
            '9:05',
            '9:10',
            '9:15',
            '9:20',
            '9:25',
            '9:30',
            '9:35',
            '9:40',
            '9:45',
            '9:50',
            '9:55',
            '10:00',
            '10:05',
            '10:10',
            '10:15',
            '10:20',
            '10:25',
            '10:30',
            '10:35',
            '10:40',
            '10:45',
            '10:50',
            '10:55',
            '11:00',
            '11:05',
            '11:10',
            '11:15',
            '11:20',
            '11:25',
            '11:30',
            '11:35',
            '11:40',
            '11:45',
            '11:50',
            '11:55'
          ]
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: [
              20,
              20,
              10,
              10,
              10,
              30,
              30,
              0,
              0,
              0,
              30,
              30,
              30,
              30,
              20,
              20,
              10,
              10,
              20,
              20,
              30,
              30,
              30,
              10,
              20,
              10,
              20,
              20,
              20,
              20,
              10,
              10,
              20,
              20,
              20,
              20
            ],
            type: 'line',
            areaStyle: {}
          }
        ]
      }
      daily.setOption(dailyOption)
    },
    initRatio() {
      var ratio = echarts.init(document.getElementById('ratio'))
      var ratioOption = {
        title: {
          text: '整体课堂时间分布',
          left: 'center'
        },
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        legend: {
          orient: 'vertical',
          left: 10,
          data: ['放松状态', '玩手机', '离开座位', '高度专注', '较为专注']
        },
        series: [
          {
            name: '访问来源',
            type: 'pie',
            radius: ['50%', '70%'],
            avoidLabelOverlap: false,
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: '30',
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: [
              { value: 30, name: '放松状态' },
              { value: 15, name: '玩手机' },
              { value: 20, name: '离开座位' },
              { value: 40, name: '高度专注' },
              { value: 200, name: '较为专注' }
            ]
          }
        ]
      }
      ratio.setOption(ratioOption)
    },
    initRank() {
      var rank = echarts.init(document.getElementById('rank'))
      var rankOption = {
        title: {
          text: '各课程状况',
          left: '70px',
          textAlign: 'center'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            // 坐标轴指示器，坐标轴触发有效
            type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
          }
        },
        legend: {
          data: ['高度专注', '较为专注', '放松状态', '玩手机', '离开座位']
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'value'
        },
        yAxis: {
          type: 'category',
          data: ['机电一体化', '信号与系统', '经典文明导读', '电磁学']
        },
        series: [
          {
            name: '高度专注',
            type: 'bar',
            stack: '总量',
            label: {
              show: true,
              position: 'insideRight'
            },
            data: [17, 16, 21, 31]
          },
          {
            name: '较为专注',
            type: 'bar',
            stack: '总量',
            label: {
              show: true,
              position: 'insideRight'
            },
            data: [43, 57, 27, 34]
          },
          {
            name: '放松状态',
            type: 'bar',
            stack: '总量',
            label: {
              show: true,
              position: 'insideRight'
            },
            data: [12, 3, 6, 4]
          },
          {
            name: '玩手机',
            type: 'bar',
            stack: '总量',
            label: {
              show: true,
              position: 'insideRight'
            },
            data: [8, 4, 24, 8]
          },
          {
            name: '离开座位',
            type: 'bar',
            stack: '总量',
            label: {
              show: true,
              position: 'insideRight'
            },
            data: [2, 2, 2, 3]
          }
        ]
      }
      rank.setOption(rankOption)
    },
    switch2Course(className) {
      this.dispalyMode = 'course'
      var history1 = echarts.init(document.getElementById('history1'))
      history1.setOption(
        this.genRandomChart(
          this.GetDateStr(-1) + ' 《' + className + '》 专注度曲线'
        )
      )
      var history2 = echarts.init(document.getElementById('history2'))
      history2.setOption(
        this.genRandomChart(
          this.GetDateStr(-2) + ' 《' + className + '》 专注度曲线'
        )
      )
    },
    switch2Daily() {
      this.dispalyMode = 'daily'
    },
    genRandomChart(title) {
      var Option = {
        title: {
          text: title
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: [
            '0:00',
            '0:05',
            '0:10',
            '0:15',
            '0:20',
            '0:25',
            '0:30',
            '0:35',
            '0:40',
            '0:45',
            '0:50',
            '0:55',
            '1:00',
            '1:05',
            '1:10',
            '1:15',
            '1:20',
            '1:25',
            '1:30'
          ]
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: null,
            type: 'line',
            areaStyle: {}
          }
        ]
      }
      const randomlist = []
      for (let i = 0; i < 19; i++) {
        randomlist[i] = Math.floor(Math.random() * 10)
      }
      Option.series[0].data = randomlist
      return Option
    },
    GetDateStr(AddDayCount) {
      var dd = new Date()
      dd.setDate(dd.getDate() + AddDayCount)
      var y = dd.getFullYear()
      var m =
        dd.getMonth() + 1 < 10 ? '0' + (dd.getMonth() + 1) : dd.getMonth() + 1
      var d = dd.getDate() < 10 ? '0' + dd.getDate() : dd.getDate()
      // console.log(y + '-' + m + '-' + d)
      return y + '-' + m + '-' + d
    }
  }
}
</script>

<style lang="less" scoped>
.longChart {
  width: 1100px;
  height: 300px;
}
#ratio {
  width: 100%;
  height: 250px;
}
#rank {
  width: 100%;
  height: 250px;
}
.infoCard {
  height: 300px;
  div {
    text-align: center;
  }
  .head {
    font-size: 18px;
    text-align: left !important;
    font-weight: 600;
    margin-bottom: 10px;
  }
  .title {
    font-size: 15px;
    color: #999;
    margin-bottom: 10px;
  }
  .content {
    margin-bottom: 10px;
  }
}
.shortCard {
  width: 100%;
  height: 250px;
}
.home-container {
  height: 100%;
}
.el-header {
  background-color: #027db4;
  display: flex;
  justify-content: space-between;
  padding-left: 30px;
  align-items: center;
  color: #fff;
  font-size: 20px;
  > div {
    display: flex;
    align-items: center;
    img {
      width: 200px;
    }
  }
}
.el-aside {
  background-color: white;
  .el-menu {
    border-right: none;
  }
}
.el-main {
  background-color: white;
}
.nav-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  > svg-icon {
    width: 60px;
    color: red;
  }
  > span {
    font-size: 13px;
  }
}
.nav-bar {
  width: 500px;
  display: flex;
  justify-content: space-around;
  margin-left: 100px;
}
</style>
