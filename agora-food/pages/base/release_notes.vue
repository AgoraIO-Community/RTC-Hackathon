<template>
	<view>
		<hx-navbar left-text="功能介绍" :border="true" defaultBackUrl="features"></hx-navbar>
		<view class="content">
			{{content}} 
		</view>
	</view>
	
</template>

<script>
	export default {
		data() {
			return {
				content: '',
			}
		},
		onLoad(option) {
			this.getUpdateInfo(option)
		},
		methods: {
			getUpdateInfo(option){
				this.content = option.v + "的更新内容"
			}
		}
	}
</script>

<style>
	page{
		background: #fff;
	}
	.content{
		margin: 24upx;
	}
</style>
