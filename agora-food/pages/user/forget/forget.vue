<template>
	<view class="forget">
		<uni-nav-bar
			:status-bar="true"
			:shadow="false"
			:border="false"
			background-color="#f1f1f1">
		  
		    <view slot="left">
				<view class="cu-bar">
					<view class="action">
						<text class="cuIcon-close" @click="closePage"></text> 
					</view>
					<view class="content text-bold">
						
					</view>
				</view> 
			</view>
		</uni-nav-bar>
		
		<view class="content">
			<!-- 主体 -->
			<view class="main">
				<view class="tips">若你忘记了密码，可在此重置新密码。</view>
				<wInput
					v-model="phoneData"
					type="text"
					maxlength="11"
					placeholder="请输入手机号码"
				></wInput>
				<wInput
					v-model="passData"
					type="password"
					maxlength="11"
					placeholder="请输入新密码"
					isShowPass
				></wInput>
				
				<wInput
					v-model="verCode"
					type="number"
					maxlength="4"
					placeholder="验证码"
					
					isShowCode
					codeText="获取重置码"
					setTime="30"
					ref="runCode"
					@setCode="getVerCode()"
				></wInput>
			</view>
			
			<wButton 
				text="重置密码"
				:rotate="isRotate" 
				@click.native="startRePass()"
			></wButton>

		</view>
	</view>
</template>

<script>
	var _this;
	import wInput from '@/components/watch-login/watch-input.vue' //input
	import wButton from '@/components/watch-login/watch-button.vue' //button
	export default {
		data() {
			return {
				phoneData: "", //电话
				passData: "", //密码
				verCode:"", //验证码
				isRotate: false, //是否加载旋转
			}
		},
		components:{
			wInput,
			wButton
		},
		mounted() {
			_this= this;
			console.log('hello');
		},
		methods: {
			getVerCode(){
				//获取验证码
				if (_this.phoneData.length != 11) {
				     uni.showToast({
				        icon: 'none',
						position: 'bottom',
				        title: '手机号不正确'
				    });
				    return false;
				}
				console.log("获取验证码")
				this.$refs.runCode.$emit('runCode'); //触发倒计时（一般用于请求成功验证码后调用）
				uni.showToast({
				    icon: 'none',
					position: 'bottom',
				    title: '模拟倒计时触发'
				});
				
				setTimeout(function(){
					_this.$refs.runCode.$emit('runCode',0); //假装模拟下需要 终止倒计时
					uni.showToast({
					    icon: 'none',
						position: 'bottom',
					    title: '模拟倒计时终止'
					});
				},3000)
			},
			startRePass() {
				//重置密码
				if(this.isRotate){
					//判断是否加载中，避免重复点击请求
					return false;
				}
				if (this.phoneData.length != 11) {
				    uni.showToast({
				        icon: 'none',
						position: 'bottom',
				        title: '手机号不正确'
				    });
				    return false;
				}
			    if (this.passData.length < 6) {
			        uni.showToast({
			            icon: 'none',
						position: 'bottom',
			            title: '密码不正确'
			        });
			        return false;
			    }
				if (this.verCode.length != 4) {
				    uni.showToast({
				        icon: 'none',
						position: 'bottom',
				        title: '验证码不正确'
				    });
				    return false;
				}
				console.log("重置密码成功")
				_this.isRotate=true
				setTimeout(function(){
					_this.isRotate=false
				},3000)
				
				
			}
		}
	}
</script>

<style>
	
	.content {
		display: flex;
		flex-direction: column;
		justify-content:center;
		/* margin-top: 128upx; */
	}
	
	/* 头部 logo */
	.header {
		width:161upx;
		height:161upx;
		box-shadow:0upx 0upx 60upx 0upx rgba(0,0,0,0.1);
		border-radius:50%;
		background-color: #000000; 
		margin-top: 128upx;
		margin-bottom: 72upx;
		margin-left: auto;
		margin-right: auto;
	}
	.header image{
		width:161upx;
		height:161upx;
		border-radius:50%;
	}
	
	/* 主体 */
	.main {
		display: flex;
		flex-direction: column;
		padding-left: 70upx;
		padding-right: 70upx;
	}
	.tips {
		color: #999999;
		font-size: 28upx;
		margin-top: 64upx;
		margin-left: 48upx;
	}
	
	/* 其他登录方式 */
	.other_login{
		display: flex;
		flex-direction: row;
		justify-content: center;
		align-items: center;
		margin-top: 256upx;
		text-align: center;
	}
	.login_icon{
		border: none;
		font-size: 64upx;
		margin: 0 64upx 0 64upx;
		color: rgba(0,0,0,0.7)
	}
	.wechat_color{
		color: #83DC42;
	}
	.weibo_color{
		color: #F9221D;
	}
	.github_color{
		color: #24292E;
	}
	
	/* 底部 */
	.footer{
		display: flex;
		flex-direction: row;
		justify-content: center;
		align-items: center;
		font-size: 28upx;
		margin-top: 64upx;
		color: rgba(0,0,0,0.7);
		text-align: center;
		height: 40upx;
		line-height: 40upx;
	}
	.footer text{
		font-size: 24upx;
		margin-left: 15upx;
		margin-right: 15upx;
	}
</style>

