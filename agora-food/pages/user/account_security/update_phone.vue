

<template>
    <view>
        <hx-navbar :status-bar="true" defaultBackUrl="phone" :fixed="true" left-icon="back" left-text="验证手机号" @click-right="next" right-text="下一步"></hx-navbar>
        <view class=" msgBox mRL">
        	<text class="text-gray ">
        		更换手机号后，下次登录可使用新手机号登录。当前手机号：{{mobile}}
        	</text>
        </view>
		<view class="content mRL">
			<view class="areaCodeBox ">
				<text class="areaCode">+86</text>
				<text class="areaText">中国大陆</text>
			</view>
			<view class="phoneBox">
				<input type="number" placeholder="你本人手机号" maxlength="11" @blur="inputBlur" @confirm="submit"/>
			</view>
		</view>
    </view>
</template>

<script>
    import {
        mapState 
    } from 'vuex';
    export default {
        data() {
            return {
				mobile: '',
				newMobile: '',
				appName: '',
				
			
            }
        },
		onLoad() {
			this.appName = this.$conf.appName;
			this.mobile = this.userData.member.mobile;
		},
		
		computed: { 
			...mapState(['hasLogin','userData'])
		},
        methods: {
			submit(even){
				this.newMobile = even.detail.value;
				this.next();
			},
			inputBlur(even){
				this.newMobile = even.detail.value;
			},
			next(){
				let that = this;
				let reg =/^1[3-9]\d{9}$/; 
				if(!reg.exec(that.newMobile)){
					uni.showToast({
						icon:'none',
						title: '手机号格式不正确！',
						duration: 2000
					})
					return;
				}
				if(that.newMobile ==  that.mobile){
					uni.showToast({
						icon:'none',
						title: '不能重复绑定该手机号！',
						duration: 2000
					})
					return;
				}
				uni.navigateTo({
					url: 'verification_phone?mobile=' + that.newMobile
				});
			}
        },
        
    }
</script>

<style>
	
   page{
	  
   }
	.content{
		display: flex;
		flex-direction:row;
		justify-content: center;
		align-items: flex-end;
		height: 116upx;
		margin-top: 50upx;
	}
	.areaCodeBox{
		width: 160upx;
		display: flex;
		justify-content: center;
		align-items: center;
		flex-direction:column;
		border-bottom: 1px solid #e1e1e1;
		padding: 8upx 0;
		margin-right: 24upx;
	}
	.mRL{
		margin-left: 40upx;
		margin-right: 40upx;
	}
	.msgBox{
		margin-top: 56upx;
	}
	.areaCode{
		font-size: 30upx;
		color: #333333;
	}
	.areaText{
		font-size: 16upx;
		color: #444444;
	}
	.phoneBox{
		width: 100%;
		border-bottom: 1px solid #1AAD19;
		font-size: 38upx;
		padding: 0  12upx 16upx  12upx;
	}
</style>