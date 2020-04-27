<template>
  <div class="home-container">
    <div class="left-container" id="leftContainer">
      <!-- 头像区域 -->
      <div class="left-top-container" :style="leftTopOpacityStyle">
        <div class="left-top" :style="leftTopOpacityStyle">
          <!-- 锁定与透明度 -->
          <div>
            <i class="el-icon-lock" style="margin:0 0 0 25px;color:#409eff"></i>
            <div class="block">
              <el-slider
                v-model="leftTopOpacity"
                vertical
                height="70px"
                @input="setLeftTopOpacity"
                :min="0"
                style="margin:18px 0 0 15px"
              ></el-slider>
            </div>
          </div>
          <!-- 人物头像 -->
          <div class="avatarCard" v-for="item in userList" :index="item.name" :key="item.name">
            <div class="avatarBox">
              <img src="../assets/imag/liveAvatar.png" alt />
            </div>
            <div class="avatarName">{{item.name}}</div>
          </div>
        </div>
      </div>
      <!-- 弹幕区域 -->
      <div class="left-middle-barrage">
        <vue-baberrage :isShow="barrageIsShow" :barrageList="barrageList" :loop="barrageLoop"></vue-baberrage>
      </div>
      <!-- 视频区域 -->
      <div class="video" :style="'height:' + videoHeight + 'px'">
        <!-- <img
          src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1587064531251&di=f1c0fb7dd50c2b145f6d2e3d0c2f6399&imgtype=0&src=http%3A%2F%2Fwww.bio-review.com%2Fwp-content%2Fuploads%2F2017%2F07%2Fcheckpoint.jpg"
          alt
        /> -->
        <video id="video" width="100%" height="100%" preload loop autoplay muted>
        <source
          src="../assets/35s.mp4"
          type="video/mp4"
        />
      </video>
      </div>
      <!-- 底部工具栏 -->
      <div class="left-bottom" :style="'opacity:'+leftBottomOpacity+'%;'">
        <!-- <div style="float:left">
          <i class="el-icon-lock" style="margin:0 10px 0 5px;color:#409eff;float:left"></i>
        </div>-->
        <div class="slider">
          <i class="el-icon-lock" style="margin:0 0 0 25px;color:#409eff"></i>
          <div class="block">
            <el-slider
              v-model="leftBottomOpacity"
              vertical
              height="30px"
              :min="0"
              style="margin:15px 0 0 15px"
            ></el-slider>
          </div>
        </div>
        <div class="buttonBox" @click="goHome">
          <i class="el-icon-remove-outline" style="color:#f88 !important"></i>
          <div style="color:#f88 !important">退出课堂</div>
        </div>
        <div class="buttonBox">
          <i class="el-icon-thumb"></i>
          <div>防误触</div>
        </div>
        <div class="buttonBox">
          <i class="el-icon-microphone"></i>
          <div>静音开</div>
        </div>
        <div class="buttonBox">
          <i class="el-icon-camera"></i>
          <div>画面关</div>
        </div>
        <div class="buttonBox">
          <i class="el-icon-setting"></i>
          <div>设置</div>
        </div>
        <div class="buttonBox">
          <i class="el-icon-user"></i>
          <div>47/49</div>
        </div>
        <!-- 发言文本框 -->
        <div class="commentBox">
          <div class="send" @click="addToList()">
            <i class="el-icon-s-promotion"></i>
          </div>
          <textarea
            name
            id
            cols="40"
            rows="3"
            style="resize:none;"
            v-model="msg"
            @keyup.enter="addToList"
          ></textarea>
        </div>
        <!-- 发言选项 -->
        <div class="commentOption">
          <el-switch
            v-model="anonymousOption"
            active-text="匿名"
            inactive-text
            style="margin-bottom:5px;"
          ></el-switch>
          <el-switch v-model="questionOption" active-text="提问题" inactive-text></el-switch>
        </div>
      </div>
    </div>
    <!-- 右侧工具栏 -->
    <div class="right-container" id="rightContainer">
      <template>
        <el-tabs v-model="activeName" @tab-click="handleClick" type="border-card" stretch>
          <el-tab-pane label="笔记" name="note">
            <el-tabs v-model="noteName" stretch>
              <el-tab-pane label="课堂笔记" name="first">
                <div class="editor-box" :style="rightContentHeight" id="editor" contenteditable>
                  <el-input
                    type="textarea"
                    :rows="2"
                    placeholder="请输入内容"
                    v-model="markdownContent"
                    autosize
                  ></el-input>
                </div>
              </el-tab-pane>
              <el-tab-pane label="预览" name="second">
                <div class="preview-box" :style="rightContentHeight">
                  <div id="preview"></div>
                </div>
              </el-tab-pane>
            </el-tabs>
          </el-tab-pane>
          <el-tab-pane label="课堂" name="class">
            <el-tabs v-model="classFuntion" stretch>
              <el-tab-pane label="小功能" name="miniprogram">
                <div class="quiz-box" :style="rightContentHeight">
                  <el-card style="margin-bottom:10px">
                    <div style="color:#409EFF;margin-bottom:6px">
                      <i class="el-icon-star-on"></i> 签到
                    </div>
                    <div class="question-text">管理员刚刚发起了签到，请尽快点击签到。</div>
                    <div class="submit">
                      <el-button
                        round
                        size="small"
                        @click="signState=true"
                        type="primary"
                      >{{signState?'签到成功':'签到'}}</el-button>
                    </div>
                  </el-card>
                  <el-card style="margin-bottom:10px">
                    <div style="color:#409EFF;margin-bottom:6px">
                      <i class="el-icon-edit"></i> 测试
                    </div>
                    <div
                      class="question-text"
                    >A change in the coding region of a gene can lead to a new allele for that gene. Which property of the protein, coded for by this new allele, could be changed as a result?</div>
                    <el-radio-group v-model="radio">
                      <el-radio :label="3">A. its affinity for its substrate</el-radio>
                      <el-radio :label="6">B. the amino acid sequence in it</el-radio>
                      <el-radio :label="9">C. its ability to be affected by allosteric factors</el-radio>
                      <el-radio :label="12">D. the pH that is optimal for its activity</el-radio>
                    </el-radio-group>
                    <div class="submit">
                      <el-button
                        round
                        size="small"
                        @click="submitState=true"
                        type="primary"
                      >{{submitState?'已提交':'确认提交'}}</el-button>
                    </div>
                  </el-card>
                  <el-card>
                    <div style="color:#409EFF;margin-bottom:6px">
                      <i class="el-icon-edit"></i> 测试
                    </div>
                    <div
                      class="question-text"
                    >What type of amino acids would you expect to see in such proteins? Why?</div>
                    <el-input
                      type="textarea"
                      :rows="3"
                      placeholder="请输入您的回答"
                      v-model="quizTextContent"
                    ></el-input>
                    <div class="submit">
                      <el-button
                        round
                        size="small"
                        @click="textState=true"
                        type="primary"
                      >{{textState?'已提交':'确认提交'}}</el-button>
                    </div>
                  </el-card>
                </div>
              </el-tab-pane>
              <el-tab-pane label="发言记录" name="comment">
                <div class="comment-box" :style="rightContentHeight">
                  <div v-for="comment in commentList" :key="comment.time" :index="comment.time">
                    <div class="title">{{comment.name}} {{comment.time}}</div>
                    <div class="content">{{comment.content}}</div>
                  </div>
                </div>
              </el-tab-pane>
            </el-tabs>
          </el-tab-pane>
          <el-tab-pane label="课件" name="slider">
            <div :style="rightPDFHeight" class="PDF" id="slider"></div>
          </el-tab-pane>
          <el-tab-pane label="教材" name="textbook">
            <div :style="rightPDFHeight" class="PDF" id="textbook"></div>
          </el-tab-pane>
          <el-tab-pane label="网页" name="web">
            <div :style="rightPDFHeight" class="PDF">
              <iframe src="https://m.baidu.com/" frameborder="0" width="100%" height="100%"></iframe>
            </div>
          </el-tab-pane>
        </el-tabs>
      </template>
    </div>
  </div>
</template>

<script>
var pdf = require('pdfobject')
export default {
  data() {
    return {
      activeName: 'note',
      noteName: 'first',
      classFuntion: 'comment',
      rightContentHeight: '',
      rightPDFHeight: '',
      fullHeight: document.documentElement.clientHeight,
      markdownContent:
        '# 嘿，你好！\n\n这是一个Markdown编辑器\n\n你可以在这一栏做笔记，然后切换Tab进行预览\n\n侧边栏的功能有:\n\n- 笔记\n\n- 课堂(问答、签到及消息记录)\n\n- 课件查看\n\n- 教材查看\n\n- 拓展网页\n\n使用左侧工具栏，你可以\n\n- 匿名提问,避免尴尬\n\n- 勾选“提问题”，这将使你的问题出现在问题树中，帮助复习时快速定位进度\n\n- 工具栏左侧有滑块，可以调节透明度\n\n- 选择“防误触”开关，这将完全切断你的麦克风和摄像头的连接',
      radio: '',
      submitState: false,
      signState: false,
      textState: false,
      quizTextContent: '',
      leftTopOpacity: 100,
      leftTopOpacityStyle: '',
      anonymousOption: '',
      questionOption: '',
      commentContent: '',
      msg: '',
      barrageIsShow: true,
      currentId: 0,
      barrageLoop: false,
      barrageList: [],
      videoHeight: 0,
      leftBottomOpacity: 100,
      timer:'',
      commentIndex:0,
      commentList:[],
      commentListData: [
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
        }
      ],
      userList: [
        {
          name: '张锋',
          speaking: true,
          category: 'taecher'
        },
        {
          name: '张启轩',
          speaking: true,
          category: 'ta'
        },
        {
          name: '沈俊鞑',
          speaking: false,
          category: 'ta'
        },
        {
          name: '何风华',
          speaking: false,
          category: 'ta'
        },
        {
          name: '李大洋',
          speaking: false,
          category: 'student'
        },
        {
          name: '彭欣悦',
          speaking: false,
          category: 'student'
        },
        {
          name: '李振东',
          speaking: false,
          category: 'student'
        },
        {
          name: '张克帆',
          speaking: false,
          category: 'student'
        }
      ]
    }
  },
  beforeMount() {
    // var pdf = require('pdfobject')
    // this.$nextTick(function () {
    //     pdf.embed("../assets/example.pdf", "#slider")
    // })
  },
  mounted() {
    this.timer = setInterval(() => {
      if (this.commentIndex > this.commentListData.length - 1) return clearInterval(this.timer)
      this.commentList.push(this.commentListData[this.commentIndex])
      this.barrageList.push({
        id: ++this.currentId,
        avatar:
          'https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1088961997,3658015275&fm=15&gp=0.jpg',
        msg: this.commentListData[this.commentIndex].content,
        time: 8
        // type: MESSAGE_TYPE.NORMAL,
      })
      this.commentIndex++
    },1800)
    // 挂载时确定编辑器高度
    var rightContainer = document.getElementById('rightContainer')
    var leftContainer = document.getElementById('leftContainer')
    this.rightContentHeight =
      'height:' + (rightContainer.offsetHeight - 126) + 'px'
    this.rightPDFHeight = 'height:' + (rightContainer.offsetHeight - 80) + 'px'
    this.videoHeight = (leftContainer.offsetWidth * 9.0) / 16.0
    // 高度变化
    const that = this
    window.onresize = () => {
      return (() => {
        window.fullHeight = document.documentElement.clientHeight
        that.fullHeight = window.fullHeight
        const leftContainer = document.getElementById('leftContainer')
        that.videoHeight = (leftContainer.offsetWidth * 9.0) / 16.0
      })()
    }
    pdf.embed(
      'https://www.michigan.gov/documents/explorelabscience/Introduction_to_proteins_and_amino_acids_571576_7.pdf',
      '#slider'
    )
    pdf.embed(
      'https://digitalcommons.usu.edu/cgi/viewcontent.cgi?article=1612&context=extension_histall',
      '#textbook'
    )
    this.updatePreview(this.markdownContent)
  },
  watch: {
    fullHeight(val) {
      if (!this.timer) {
        this.fullHeight = val
        this.rightContentHeight =
          'height:' + (rightContainer.offsetHeight - 126) + 'px'
        this.rightPDFHeight =
          'height:' + (rightContainer.offsetHeight - 80) + 'px'
        this.timer = true
        const that = this
        setTimeout(function() {
          that.timer = false
        }, 500)
      }
    },
    markdownContent(val) {
      this.updatePreview(val)
    }
  },
  methods: {
    goHome() {
      this.$router.push("/course")
    },
    handleClick(tab, event) {
      //   console.log(tab, event)
    },
    setLeftTopOpacity(val) {
      // console.log(val)
      this.leftTopOpacityStyle = 'opacity:' + val + '%'
    },
    updatePreview(val) {
      var marked = require('marked')
      marked.setOptions({
        renderer: new marked.Renderer(),
        gfm: true,
        tables: true,
        breaks: false,
        pedantic: false,
        sanitize: false,
        smartLists: true,
        smartypants: false
      })
      document.getElementById('preview').innerHTML = marked(val)
    },
    addToList() {
      if (!this.msg) return
      this.barrageList.push({
        id: ++this.currentId,
        avatar:
          'https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1088961997,3658015275&fm=15&gp=0.jpg',
        msg: this.msg,
        time: 5
        // type: MESSAGE_TYPE.NORMAL,
      })
      this.msg = ''
    }
  }
}
</script>

<style lang="less" scoped>
.home-container {
  width: 100%;
  height: 100%;
  .left-container {
    width: 70%;
    height: 100%;
    float: left;
    // padding: auto 0;
    .left-middle-barrage {
      height: 120px;
      width: 70%;
      position: fixed;
      top: 120px;
      z-index: 100;
    }
    ， .liveContent {
      top: 300px;
      right: 1000px;
      // position: absolute;
      // z-index:999;
      width: 100px;
      height: 100px;
      background-color: aqua;
    }
    .left-top-container,
    .left-top {
      height: 120px;
      position: fixed;
      top: 0;
      z-index: 98;
    }

    .left-top {
      width: 70%;
      position: fixed;
      top: 0;
      background-color: white;
      border-bottom: solid 1px #eee;
      box-shadow: 1px 1px 2px #ddd;
      display: flex;
      overflow: hidden;
      z-index: 99;
      .avatarCard {
        margin: 9px 5px 0 10px;
        text-align: center;
        .avatarBox {
          width: 130px;
          height: 90px;
          margin-bottom: 4px;
          img {
            height: 100%;
            width: 100%;
          }
        }
        div {
          font-size: 10px;
        }
      }
    }
    .video {
      position: absolute;
      width: 70%;
      top:90px;
      // margin: auto 0;
      // padding:auto 0;
      // height:100%;
      img {
        width: 100%;
        height: 100%;
      }
    }
    .left-bottom {
      width: 70%;
      height: 70px;
      position: fixed;
      bottom: 0;
      border-top: solid 2px #eee;
      box-shadow: 1px 1px 2px #ddd;
      background-color: white;
      .slider {
        float:left;
      }
      // .buttonBox:hover {
      //   text-decoration: underline;
      //   color: #409eff;
      // }
      .buttonBox {
        margin: 15px 15px 0 10px;
        text-align: center;
        float: left;
        cursor: pointer;
        // width:100px;
        i {
          font-size: 30px;
          color: #999;
        }
        i:hover {
          color: #409eff;
        }
        div:hover {
          color: #409eff;
        }
        div {
          font-size: 11px;
          color: #999;
        }
      }
      .commentOption {
        float: right;
        margin: 15px 15px 0 10px;
        border-left: 2px solid #999;
        height: 50px;
        padding-left: 10px;
        display: flex;
        flex-direction: column;
      }
      .commentBox {
        width: 400px;
        height: 45px;
        float: right;
        margin: 15px 15px 0 10px;
        textarea {
          float: right;
        }
        .send {
          width: 40px;
          height: 49px;
          background-color: #409eff;
          float: right;
          padding: auto;
          cursor: pointer;
          i {
            margin: 5px auto;
            font-size: 35px;
            color: white;
          }
        }
      }
    }
  }
  .right-container {
    width: 30%;
    height: 100%;
    float: right;
    position: fixed;
    right: 0;
    .editor-box {
      width: 100%;
    }
    .preview-box {
      width: 100%;
      overflow: auto;
      padding: 0 20px;
    }
    .quiz-box,
    .comment-box {
      width: 100%;
      overflow: auto;
      //   background-color: red;
    }
    .comment-box {
      .title {
        color: #409eff;
      }
      .content {
        margin: 5px 0 10px 0;
        font-size: 15px;
      }
    }
    .quiz-box {
      .question-text {
        margin-bottom: 10px;
        font-size: 15px;
      }
      .submit {
        text-align: center;
        padding-top: 5px;
      }
    }
    .PDF {
      width: 100%;
      //   background-color: red;
    }
  }
}
</style>
