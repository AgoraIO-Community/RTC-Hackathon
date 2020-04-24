<template>
	<view>
		<hx-navbar left-text="设置" backTabbarUrl="/pages/user/center/center"></hx-navbar>
		
		<view class="cu-list menu sm-border margin-top">
			
			<navigator class="cu-item arrow " url="../account_security/security" open-type="navigate">
				<view class="content">
					<text class="text-black">安全设置</text>
				</view>
				<view class="action">
					<text class="text-grey text-sm">手机号、密码</text>
				</view>
			</navigator>
			<view class="cu-item arrow">
				<view class="content">
					<text class="text-black">通用</text>
				</view>
				<view class="action">
					<text class="text-grey text-sm"></text>
				</view>
			</view>
			<view class="cu-item arrow">
				<view class="content">
					<text class="text-black">隐私</text>
				</view>
				<view class="action">
					<text class="text-grey text-sm"></text>
				</view>
			</view>
			
			<navigator class="cu-item arrow margin-top" url="../../base/about" open-type="navigate">
				<view class="content">
					<text class="text-black">关于</text>
				</view>
				<view class="action">
					<text class="text-grey text-sm">版本号 {{version}}</text>
				</view>
			</navigator>
			<navigator class="cu-item arrow" url="../../base/help_feedback" open-type="navigate">
				<view class="content">
					<text class="text-black">帮助与反馈</text>
				</view>
				<view class="action">
					<text class="text-grey text-sm"></text>
				</view>
			</navigator>
			
			<!-- <view class="cu-item margin-top">
				<view class="content" style="text-align: center;">
					<text class="text-black">切换账号</text>
				</view>
			</view> -->
			<view class="cu-item margin-top" @click="logOut">
				<view class="content" style="text-align: center;">
					<text class="text-grey" style="color: #ffc107">退出登录</text>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	var that;
	export default {
		data() {
			return {
				version: this.$conf.version
			}
		},
		onLoad(){
			var that = this;
		},
		mounted() {
			that= this;
		},
		methods: {
			logOut(){
				that.$store.dispatch("logout");
				that.$api.user.logout();
				
				that.back();
			},
			back(){
				uni.redirectTo({
				    url: '/pages/user/login/login'
				});
			}
		}
	}
</script>

<style>

</style>
