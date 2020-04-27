<template>
    <div class="notice-container">
    <div class="notice-title">
        {{noticeInfo.head}}
    </div>
    <div class="notice-row" v-for="item in noticeInfo.list" :key="item.info">
        <div class="title">#{{item.title}}</div>
        <div class="time">{{item.time}}</div>
        <div class="content" @click="PDFlink">{{item.info}}</div>
    </div>
    <div class="footer">
        查看更多
    </div>
    </div>
</template>

<script>
export default {
    name:'Notice',
    props: {
    noticeInfo: {
      type: Object,
      required: true
    }
  },
  methods: {
      PDFlink() {
          window.open('https://digitalcommons.usu.edu/cgi/viewcontent.cgi?article=1612&context=extension_histall','_blank')
      }
  }
}
</script>

<style lang="less" scoped>
.notice-container {
    border: 1px solid #eee;
    box-shadow:  2px 1px 5px #999;
    margin-bottom: 10px;
    .notice-title{
        color:#015478;
        font-size:22px;
        padding: 10px 0 10px 10px;
        font-weight: 600;
    }
    .notice-row {
        border-top: 1px solid #eee;
        cursor: pointer;
        .title{
            color:#999;
            font-size: 17px;
            padding: 5px 0 5px 10px;
        }
        .time{
            font-size: 12px;
            padding: 0px 0 5px 10px;
        }
        .content{
            font-size: 14px;
            padding: 0px 0 5px 10px;
        }
        .content:hover, .time:hover {
            color: #027db4;
        }
    }
    .footer{
        border-top: 1px solid #eee;
        text-align: center;
        color:#999;
        font-size: 13px;
        padding: 5px 0;
        cursor: pointer;
    }
    .footer:hover {
            color: #027db4;
    }
}
</style>
