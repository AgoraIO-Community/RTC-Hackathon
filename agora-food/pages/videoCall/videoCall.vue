<template>
	<view class=" agora-bg">
	  <view class="content flex-center-column">
	    <view class="logo-section flex-center-column">
	      <image class="logo" style="width: 300rpx; height: 200rpx;" mode="aspectFit" src="../../images/logo.png"></image>
	      <text class="h1">agora视频客服</text>
	    </view>
	    <view class="user-section flex-center-column">
	      <image class="userinfo-avatar" src="{{userInfo.avatarUrl}}" background-size="cover"></image>
	      <text class="userinfo-nickname">{{userInfo.nickName}}</text>
	    </view>
	    <view class="form-section flex-center-column">
	      <view class="inputWrapper">
	        <input placeholder-style='color:#A3D1E0' class="channelInput" placeholder='输入桌位号' bindinput="onInputChannel" bindconfirm="onInputChannel" bindblur="onInputChannel"></input>
	      </view>
	      <button plain="true" open-type="getUserInfo" bindgetuserinfo="onGotUserInfo" disabled="{{disableJoin}}" class="joinBtn">联系客服</button>
	    </view>
	    <view class="footer flex-center-column">
	      <text>Powered by Agora. Build v1.1.1180907</text>
	    </view>
	  </view>
	</view>
</template>

<script>
	const Utils = require('../../utils/util.js')
	export default {
		data(){
			return {
				userInfo: {},
				    hasUserInfo: false,
				    // whether to disable join btn or not
				    disableJoin: false
			}
		},
		/**
		   * 生命周期函数--监听页面加载
		   */
		  onLoad: function (options) {
		    this.channel = "";
		    this.uid = Utils.getUid();
		    this.lock = false;
		    let userInfo = wx.getStorageSync("userInfo");
		    if (userInfo){
		      this.setData({
		        hasUserInfo: true,
		        userInfo: userInfo
		      });
		    }
		  },
		
		  /**
		   * 生命周期函数--监听页面初次渲染完成
		   */
		  onReady: function () {
		
		  },
		
		  /**
		   * 只有提供了该回调才会出现转发选项
		   */
		  onShareAppMessage() {
		
		  },
		
		  /**
		   * 生命周期函数--监听页面隐藏
		   */
		  onHide: function () {
		  },
		
		  /**
		   * 生命周期函数--监听页面卸载
		   */
		  onUnload: function () {
		
		  },
		  methods:{
			  
			    /**
			     * callback to get user info
			     * using wechat open-type
			     */
			    onGotUserInfo: function(e){
			      let userInfo = e.detail.userInfo || {};
			      // store data for next launch use
			      wx.setStorage({
			        key: 'userInfo',
			        data: userInfo,
			      })
			      this.onJoin(userInfo);
			    },
			    /**
			     * check if join is locked now, this is mainly to prevent from clicking join btn to start multiple new pages
			     */
			    checkJoinLock: function() {
			      return !(this.lock || false);
			    },
			    
			    lockJoin: function() {
			      this.lock = true;
			    },
			  
			    unlockJoin: function() {
			      this.lock = false;
			    },
			  
			    onJoin: function (userInfo) {
			      userInfo = userInfo || {};
			      let value = this.channel || "";
			  
			      let uid = this.uid;
			      if (!value) {
			        wx.showToast({
			          title: '请提供一个有效的房间名',
			          icon: 'none',
			          duration: 2000
			        })
			      } else {
			        if(this.checkJoinLock()) {
			          this.lockJoin();
			          if (value === "agora") {
			            // go to test page if channel name is agora
			            wx.navigateTo({
			              url: `../test/test`
			            });
			          } else if (value === "agora2") {
			            // go to test page if channel name is agora
			            wx.navigateTo({
			              url: `../test2/test2`
			            });
			          } else {
			            wx.showModal({
			              title: '是否推流',
			              content: '选择取消则作为观众加入，观众模式不推流',
			              showCancel: true,
			              success: function (res) {
			                let role = "audience";
			                if (res.confirm) {
			                  role = "broadcaster";
			                }
			  
			                wx.navigateTo({
			                  url: `../meeting/meeting?channel=${value}&uid=${uid}&role=${role}`
			                });
			              }
			            })
			          }
			        }
			      }
			    },
			    onInputChannel: function (e) {
			      let value = e.detail.value;
			      this.channel = value;
			    }
		  }

		
	}
</script>

<style>
	page {
	    height: 100%;
	}
	
	.content {
	  position: absolute;
	  left: 80rpx;
	  right: 80rpx;
	  width: auto;
	  height: 100%;
	}
	
	.content .logo-section .logo{
	  margin-bottom: 20rpx;
	}
	
	.content .logo-section .h1{
	  margin-bottom: 10rpx;
	}
	
	.content .user-section{
	  margin-bottom: 40rpx;
	}
	
	
	.content .userinfo-avatar {
	  width: 128rpx;
	  height: 128rpx;
	  margin: 40rpx 20rpx 20rpx 20rpx;
	  border-radius: 128rpx;
	  border: 2px solid white;
	  box-shadow: 1px 1px 1px rgba(0,0,0,0.3);
	}
	
	.content .userinfo-nickname {
	  color: #2F597A;
	  font-size: 28rpx;
	  text-align: center;
	}
	
	.content .form-section{
	  width: 100%;
	}
	
	.content .inputWrapper{
	  width: 100%;
	  border-radius: 10rpx;
	  background-color: rgba(255,255,255,0.4);
	  border: 1px solid #98BECA;
	}
	
	.content .channelInput{
	  font-size: 28rpx;
	  padding: 0 30rpx;
	  height: 80rpx;
	  color: #5083AA;
	}
	
	.content .joinBtn{
	  background-color: #FEFFFE;
	  color: #5083AA;
	  width: 100%;
	  height: 80rpx;
	  font-size: 28rpx;
	  margin-top: 32rpx;
	  line-height: 80rpx;
	  box-shadow: 0px 4px 4px rgba(84,163,186,0.15);  
	  font-weight: bold;
	  border: 0; 
	}
	
	.content .envBtn{
	  /* background-color: white; */
	  color: white;
	  height: 80rpx;
	  font-size: 28rpx;
	  margin-top: 32rpx;
	  line-height: 80rpx;
	  border: 0;
	  margin-bottom: -10rpx;
	}
	
	.content .footer{
	  justify-content: flex-end;
	  flex-grow: 1;
	  font-size: 24rpx;
	  margin-bottom: 32rpx;
	  color: #63C5E6;
	}
</style>
