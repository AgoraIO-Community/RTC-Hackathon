<template>
	<view>
		<hx-navbar left-text="安全设置" defaultBackUrl="/pages/user/setting/setting"></hx-navbar>
	
		<view class="cu-list menu sm-border margin-top">
			
			<navigator class="cu-item arrow " url="phone" open-type="navigate">
				<view class="content">
					<text class="text-black">手机号</text>
				</view>
				<view class="action">
					<text class="text-grey text-sm">{{mobile}}</text>
				</view>
			</navigator>
			<navigator class="cu-item arrow " url="update_password" open-type="navigate">
				<view class="content">
					<text class="text-black">密码设置</text>
				</view>
				<view class="action">
					<text class="text-grey text-sm"></text>
				</view>
			</navigator>
			
			
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
			}
		},
		onReady() {
			
		},
		computed:{
			...mapState(['userData'])
		},
		mounted() {
			
			if(Object.keys(this.userData).length != 0){
				let str =this.userData.member.mobile;
				this.mobile = str.replace(/(\d{3})(\d{6})(\d{2})/g,'$1******$3');
			}
			
		},
		methods: {
			
		}
	}
</script>

<style>

</style>
