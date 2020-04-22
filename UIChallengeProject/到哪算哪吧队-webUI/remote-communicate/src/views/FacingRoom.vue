<template>
  <div class="facing_room" @scroll="onScroll">
    <Header ref='header' :class="{with_bg: isScrolled}" />
    <!-- 视频模块包括左右列表 -->
    <div class="ing_box">
      <!-- 视频界面 -->
      <div class="ing_video">
        <div ref='svideo' @click="handleClickExitFull(index)" class="video_box" :class="{single: !isSingle}" v-for="(user, index) in fullUsers" :key=index>
            <div class="ing_modal">
              <div class="namebox">{{user.name}}</div>
              <div class="operate_box">
                <img src="~@/assets/image/microphone.png" alt="">
                <img src="~@/assets/image/camera.png" alt="">
                <img @click="exitFullscreen" src="~@/assets/image/exit.png" alt="">
              </div>
            </div>
            <div class="img_box"><img v-bind:src="user.url" alt=""></div>
          </div>
      </div><!-- 视频界面 -->
      <!-- 文字讨论区 -->
      <div class="comment_box">
        <div class="title_box">文字聊天区</div>
        <div class="comment_display">
          <div class="s_comment" v-for="(comment, index) in newCommentStack" :key=index>
            <span class="user_name">{{comment.name}}:</span>
            <span class="comment_content">{{comment.comment}}</span>
          </div>
        </div>
        <div class="input_box">
          <input type="text" v-model="commentValue" @keyup.enter="handleEnter">
          <img src="~@/assets/image/send.png" @click='handleEnter' alt="">
        </div>    
      </div><!-- 文字讨论区 end -->
      <!-- 邀请列表 -->
      <div class="other_userlist comment_box">
        <div class="title_box">待邀请列表</div>
        <div class="comment_display">
          <div class="s_user_card">
            <div class="profile_pic"><img src="~@/assets/image/profile.jpg" alt=""></div>
            <div class="right">
              <div class="username">Tim</div>
              <div class="btn" @click='sendToken'>发送邀请</div>
            </div>
          </div>
          <div class="s_user_card">
            <div class="profile_pic"><img src="~@/assets/image/user_4.jpg" alt=""></div>
            <div class="right">
              <div class="username">Kelly</div>
              <div class="btn" @click='sendToken'>发送邀请</div>
            </div>
          </div>
        </div>
      </div><!-- 邀请列表 end -->
    </div><!-- 视频模块包括左右列表 end -->
    <!-- 用户头像列表悬浮按钮 -->
    <div class="hover_btn" @click="isExpend = true" :class="{hover_btn_expend: isExpend}">
      <img src="~@/assets/image/group.png" alt="">
      <!-- 在线用户小视频窗口列表 -->
      <div class="video_list_container">
        <img @click.stop="isExpend = false" src="~@/assets/image/close.png" alt="">
        <div @click='clickUser(index)' v-for="(user, index) in userList" :key=index class="s_video_box">
          <div class="s_video_modal">
            <div class="fullscreen_btn"><img @click='toFull' src="~@/assets/image/fullscreen.png" alt=""></div>
            <div class="namebox">{{user.name}}</div>
            <div class="operate_box">
              <img src="~@/assets/image/microphone.png" alt="">
              <img src="~@/assets/image/camera.png" alt="">
            </div>
          </div>
          <div class="img_box"><img v-bind:src="user.url" alt=""></div>
        </div>
      </div><!-- 在线用户小视频窗口列表 end -->
    </div>
    <!-- 用户头像列表悬浮按钮 end -->
    <!-- 在线用户小视频窗口列表 -->
    <div class="video_list_container">
      <div @click='clickUser(index)' v-for="(user, index) in userList" :key=index class="s_video_box">
        <div class="s_video_modal">
          <div class="fullscreen_btn"><img @click='toFull' src="~@/assets/image/fullscreen.png" alt=""></div>
          <div class="namebox">{{user.name}}</div>
          <div class="operate_box">
            <img src="~@/assets/image/microphone.png" alt="">
            <img src="~@/assets/image/camera.png" alt="">
          </div>
        </div>
        <div class="img_box"><img v-bind:src="user.url" alt=""></div>
      </div>
    </div><!-- 在线用户小视频窗口列表 end -->
  </div>
</template>
<script>
import Header from '../components/Header.vue'

export default {
  name: 'FacingRoom',
  components: {
    Header
  },
  data() {
    return{
      userList: [
        {name: 'User1', id: 0, url: require('../assets/image/user_9.jpg')},
        {name: 'User2', id: 1, url: require('../assets/image/user_8.jpg')},
        {name: 'User3', id: 2, url: require('../assets/image/user_4.jpg')},
        {name: 'User4', id: 3, url: require('../assets/image/user_7.jpg')},
        {name: 'User5', id: 4, url: require('../assets/image/user_1.jpg')},
        {name: 'User6', id: 5, url: require('../assets/image/user_2.jpg')},
        {name: 'User7', id: 6, url: require('../assets/image/user_6.jpg')}
      ],
      isScrolled: false,
      isExpend: false,
      commentValue: '',
      newCommentStack: [
        {name: 'Hellen', comment: 'hello'},
        {name: 'Kate', comment: '可以听到我的声音吗？'},
      ],
      fullUsers: [
        {name: 'User1', id: 0, url:require('../assets/image/user_5.jpg')},
      ],
      tempUser: '',
      flag: false,
      exitTempUser: '',
      exitFlag: false,
      isSingle: true
    }
  },
  computed: {
  },
  mounted() {
  },
  watch: {
    fullUsers() {
      let fullUsersLen = this.fullUsers.length
      if(fullUsersLen < 5) {
        this.$nextTick(() => {
          for(let i = 0; i < fullUsersLen; i ++) {
            this.$refs.svideo[i].style.width = 49 +`%`
            this.$refs.svideo[i].style.height = 245 + `px`
          }
        })
        if(fullUsersLen == 1) {
          this.isSingle = true
          this.$nextTick(() => {
            this.$refs.svideo[0].style.width = 100 +`%`
            this.$refs.svideo[0].style.height = 100 +`%`
          })
        }else{
          this.isSingle = false
        }
      }
      
    }
  },
  methods: {
    onScroll(e) {
      let scrollTop = e.target.scrollTop
      if(scrollTop){
        this.isScrolled = true;
      }else{
        this.isScrolled = false;
      }
    },
    handleEnter() {
      let newComment = {name: 'Tonghui', comment: this.commentValue}
      this.newCommentStack.push(newComment)
      this.commentValue = ''
    },
    sendToken() {
      this.$my_message('邀请码已发送')
    },
    toFull(e) {
      this.flag = true
      setTimeout(() => {
        if(this.fullUsers.length<4) {
          this.fullUsers.push(this.tempUser)
        }else{
          this.$my_message('no more full screen')
        }
      }, 50)
    },
    clickUser(index) {
      if(this.flag) {
        this.flag = false
        if(this.fullUsers.length<4) {
          this.tempUser = this.userList[index]
          this.userList.splice(index, 1)
        }else{
          this.$my_message('no more full screen')
        }
      }else return
    },
    handleClickExitFull(index) {
      this.exitTempUser = this.fullUsers[index]
      if(this.exitFlag) {
        if(this.fullUsers.length>1) {
          this.userList.push(this.exitTempUser)
          this.fullUsers.splice(index, 1)
        }else {
          this.$my_message('Cannot be less')
        }
      }else return
    },
    exitFullscreen() {
      this.exitFlag = true
    }
  }
}
</script>

<style lang="scss" scoped>
@mixin sideBlockStyle{
  box-sizing: border-box;
  width: 300px;
  height: 100%;
  position: absolute;
  padding: 20px;
  top: 0;
  transition: all .3s;
  overflow: hidden;
  @include sideBlock($side_block_border_dark);
}
.hover_btn{
  display: none;
  @include hoverBtn( $body_bg_dark, $side_block_border_dark);
  width: 80px;
  height: 80px;
  position: fixed;
  right: 20px;
  bottom: 30%;
  border-radius: 20px;
  z-index: 1001;
  cursor: pointer;
  transition: all .3s;
  img{
    width: 100%;
    height: 100%;
  }
  .video_list_container{
    display: none;
    &>img{
      display: none;
    }
  }
  &.hover_btn_expend{
    width: 90%;
    height: auto;
    min-height: 50px;
    max-height: 80%;
    overflow: auto;
    bottom: 60px;
    right: 25px;
    &>img{
      display: none;
    }
    .video_list_container{
      display: block;
      display: flex;
      justify-content: space-between;
      flex-wrap: wrap;
      &>img{
        display: block;
        width: 30px;
        height: 30px;
        position: absolute;
        right: 20px;
        top: 20px;
        cursor: pointer;
        z-index: 1002;
      }
      .s_video_box{
        margin: 0;
        margin-bottom: 15px;
        width: 29%;
        height: 0;
        border-radius: 50%;
        padding-bottom: 29%;
      }
    }
    &::-webkit-scrollbar { 
      display: none; 
    }
  }
}
.s_video_modal{
  width: 100%;
  height: 100%;
  border-radius: 50%;
  background: rgba($color: #000, $alpha: .5);
  position: absolute;
  top: 0;
  left: 0;
  display: none;
  .fullscreen_btn{
    position: absolute;
    top: 20px;
    right: 20px;
    &>img{
      width: 30px;
      height: 30px;
    }
  }
  .namebox{
    color: $dark_mainfont;
    font-weight: bold;
    position: absolute;
    top: 45%;
    left: 35%;
  }
  .operate_box{
    position: absolute;
    bottom: 20px;
    left: 50%;
    transform: translateX(-50%);
    &>img{
      width: 30px;
      height: 30px;
      margin-right: 10px;
    }
  }
}
.facing_room{
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  @include roomBg($body_bg_dark);
  padding-top: 120px;
  overflow: auto;
  // 文字聊天区
  .comment_box{
    .title_box{
      text-align: center;
      font-weight: bold;
      font-size: 18px;
    }
    .comment_display{
      width: 100%;
      max-height: 360px;
      overflow: auto;
      margin: 20px -20px;
      padding: 0 20px;
      .s_comment{
        margin-bottom: 10px;
        .user_name{
          color: #fff;
          font-weight: bold;
          margin-right: 10px;
        }
      }
    }
    .input_box{
      position: absolute;
      width: 100%;
      bottom: 10px;
      input{
        box-sizing: border-box;
        display: inline-block;
        padding: 0 10px;
        width: 90%;
        height: 30px;
        border-top-right-radius: 15px;
        border-bottom-right-radius: 15px;
        @include inputBg($dark_mainfont, $side_block_border_dark);
      }
      &>img{
          width: 28px;
          height: 28px;
          margin-left: -40px;
          margin-top: 2px;
          cursor: pointer;
        }
    } 
  }
  // 待邀请列表
  .other_userlist{
    .s_user_card{
      box-sizing: border-box;
      width: 100%;
      height: auto;
      margin-bottom: 10px;
      background: rgba($color: #fff, $alpha: .5);
      border-radius: 2px;
      padding: 10px;
      position: relative;
      &::after{
        content: '';
        display: block;
        clear: both;
      }
      .profile_pic{
        float: left;
        width: 60px;
        height: 60px;
        border-radius: 30px;
        overflow: hidden;
        text-align: center;
        &>img{
          width: 100%;
        }
      }
      .right{
        float: right;
        margin-left: 20px;
        width: calc(100% - 90px);
        color: $body_bg_dark;
        font-weight: bold;
        .btn{
          @include tokenBtn($font_color_dark, $body_bg_dark);
          padding: 5px 10px;
          font-size: 12px;
          position: absolute;
          right: 10px;
          bottom: 10px;
          border-bottom-right-radius: 13px;
          border-top-left-radius: 13px;
          cursor: pointer;
          &:hover{
            color: #fff;
          }
        }
      }
    }
  }
  header.with_bg{
    @include headerShadow($header_shadow_dark);
  }
  .ing_box{
    width: 750px;
    height: 500px;
    background-size: 100%;
    margin: 0 auto;
    position: relative;
    .ing_video{
      width: 100%;
      height: 100%;
      overflow: hidden;
      background: rgba($color: $light_mainfont, $alpha: .5);
      background-size: 100%;
      margin: 0 auto;
      display: flex;
      justify-content: space-between;
      align-items: center;
      flex-wrap: wrap;
      position: relative;
      .video_box{
        width:100%;
        height: 100%;
        overflow: hidden;
        position: relative;
          &:hover{
            .ing_modal{
              display: block;
            }
          }
          .ing_modal{
            display: none;
            width: 100%;
            height: 15%;
            background: linear-gradient(to top, #666464 0%,transparent 100%);
            position: absolute;
            bottom: 0px;
            left: 0;
            .namebox{
              color: $dark_mainfont;
              font-weight: bold;
              position: absolute;
              bottom: 10px;
              left: 20px;
            }
            .operate_box{
              position: absolute;
              right: 0;
              bottom: 10px;
              &>img{
                width: 40px;
                height: 40px;
                margin-right: 10px;
                cursor: pointer;
              }
            }
          }
          .img_box{
            width: 100%;
            margin-bottom: 10px;
            img{
              display: inline-block;
              width: 100%;
              height: auto;
            }
          }
        }
    }
    .comment_box{
      right: -310px;
      @include sideBlockStyle;
    }
    .other_userlist{
      left: -310px;
      @include sideBlockStyle;
    }
  }
  
  .video_list_container{
    box-sizing: border-box;
    margin-top: 50px;
    width: 100%;
    box-sizing: border-box;
    padding: 30px 10px 30px 30px;
    display: flex;
    justify-content: flex-start;
    flex-wrap: wrap;
    .s_video_box{
      width: 160px;
      height: 160px;
      margin-right: 50px;
      margin-bottom: 20px;
      @include sVideoShadow($dark_mainfont);
      background-size: cover;
      border-radius: 80px;
      overflow: hidden;
      animation: planetMove 3s ease-in-out infinite;
      transition: all .5s;
      position: relative;
      cursor: pointer;
      &:hover{
        animation: none;
        .s_video_modal{
          display: block;
        }
      }
      .img_box{
        img{
          width: 100%;
          height: 100%;
        }
      }
    }
  }
}
@keyframes planetMove {
  0%, 100% {
    transform: translate(0, -10px) rotate(6deg);
  }
  50% {
    transform: translate(-20px, 10px) rotate(-8deg);
  }
}
@media screen and (min-width: 580px) and (max-width: 750px) {
  .hover_btn{
    display: block;
  }
  .facing_room{
    .ing_box{
      width: 580px;
      height: 386px;
      .other_userlist, .comment_box{
        position: relative;
        width: 580px;
        left: 0;
        margin-top: 20px;
      }
      .ing_video{
        .video_box{
          &.single{
            max-height: 48%;
          }
          margin-bottom: 5px;
          .img_box{
            width: 100%;
            height: 100%;
            img{
              width: 100%;
              height: 100%;
              object-fit: cover;
            }
          }
        }
      }
    }
    .video_list_container{
      display: none;
      .operate_box{
        &>img{
          width: 20px !important;
          height: 20px !important;
          margin-right: 5px;
        }
      }
    }
  }
}
@media screen and (max-width: 580px) {
  .hover_btn{
    display: block;
  }
  .facing_room{
    .ing_box{
      width: 100%;
      min-width: 370px;
      height: 240px;
      padding-bottom: 66.7%;
      margin-bottom: 40px;
      .other_userlist, .comment_box{
        position: relative;
        width: 100%;
        left: 0;
        margin-top: 60px;
      }
      .ing_video{
        .video_box{
          &.single{
            max-height: 48%;
          }
          margin-bottom: 5px;
          .img_box{
            width: 100%;
            height: 100%;
            img{
              width: 100%;
              height: 100%;
              object-fit: cover;
            }
          }
        }
      }
    }
    .video_list_container{
      display: none;
      .namebox{
        position: absolute;
        top: 15px;
        left: 5px;
        font-size: 12px;
      }
      .operate_box{
        width: 50px;
        white-space: nowrap;
        position: absolute;
        bottom: 10px;
        &>img{
          width: 20px !important;
          height: 20px !important;
        }
      }
      .fullscreen_btn{
        position: absolute;
        right: 10px;
        &>img{
          width: 20px !important;
          height: 20px !important;
        }
      }
    }
  }
}
</style>