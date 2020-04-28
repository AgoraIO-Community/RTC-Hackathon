<template>
	<view>
		<hx-navbar title="搜索页面"></hx-navbar>
		<view class="contianer">
			<text class="txt">{{dataType}}</text>
		</view>
		<view class="contianer">
			<text class="txt">待开发</text>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				dataType: null
			}
		},
		onLoad(option){
			if(option.type){
				switch(option.type){
					case 'home':
						this.dataType = "来自首页的搜索";
						break
					case 'store':
						this.dataType = "来自商户页的搜索"
						break
					case 'order':
						this.dataType = "来自订单页的搜索"
						break
				}
			}
		},
		methods: {
			
		}
	}
</script>

<style>
.contianer{
	margin: 15px;
}
.txt{
	font-size: 16px;
	color: #888888;
}
</style>
