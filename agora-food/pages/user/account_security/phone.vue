<template>
	<view>
		<hx-navbar left-text="绑定手机号" defaultBackUrl="security"></hx-navbar>
		
		<view class="content margin-top" v-if="currentPhone.length">
			
			<view class="flex justify-center">
				<image class="" src="/static/img/user/phone2.png" mode=""></image>
			</view>
			
			<view class="margin-top">
				<text class="text-black">
					绑定的手机号：{{currentPhone}}
				</text>
			</view>
			<!-- #ifdef APP-PLUS -->
			<view class="margin-top">				<text class="text-gray ">
					已启用手机通讯录匹配，点击下方按钮可查看手机通讯录中哪些朋友注册了{{appName}}
				</text>
			</view>
			<button class="cu-btn lg bg-green text-white margin-top" style="width: 100%;" @click="hrefTo('address_book')">查看手机通讯录</button>
			<!-- #endif -->
			
			<button class="cu-btn bg-white lg text-green margin-top" style="width: 100%;"  @click="hrefTo('update_phone')">更换手机号</button>
			
		</view>
		
		<view class="content margin-top" v-else>
			<view class="flex justify-center">
				<image class="" src="/static/img/user/phone.png" mode=""></image>
			</view>
			<view class="margin-top">
				<text class="text-black">
					你还没有绑定手机
				</text>
			</view>
			<!-- #ifdef APP-PLUS -->
			<view class="margin-top">				<text class="text-gray ">
					已启用手机通讯录匹配，点击下方按钮可查看手机通讯录中哪些朋友注册了{{appName}}
				</text>
			</view>
			<button class="cu-btn lg bg-yellow text-white margin-top" style="width: 100%;" @click="hrefTo('address_book')">查看手机通讯录</button>
			<!-- #endif -->
			<button class="cu-btn bg-yellow lg text-white margin-top" style="width: 100%;" @click="hrefTo('bind_phone')">绑定手机号</button>
			
			
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
				appName: '',
				currentPhone: '',
			}
		},
		onLoad(){
			let that = this;
			that.appName = that.$conf.appName;
			that.currentPhone = this.userData.member.mobile
		},
		computed:{
			...mapState(['userData'])
		},
		methods: {
			hrefTo(url){
				if(url.length>0){
					uni.navigateTo({
						url: url
					});	
				}
				
			}
		}
	}
</script>

<style>
	.content{
		padding: 24upx; 
		text-align: center;
	}
	.content image{
		text-align: center;
		width: 240upx;
		height: 240upx;
		margin-top: 30upx;
		margin-bottom: 30upx;
	}
</style>
