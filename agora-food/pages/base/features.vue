<template>
	<view>
		<hx-navbar left-text="功能介绍" :border="true" defaultBackUrl="about"></hx-navbar>
		<view class="cu-list menu sm-border">
			
			<navigator class="cu-item arrow " url="release_notes?v=202" open-type="navigate">
				<view class="content">
					<text class="text-black">1.0.1主要更新</text>
				</view>
				<view class="action">
					<text class="text-black text-sm">11月11日</text>
				</view>
			</navigator>
			<navigator class="cu-item arrow " url="release_notes?v=201" open-type="navigate">
				<view class="content">
					<text class="text-black">1.0.0主要更新</text>
				</view>
				<view class="action">
					<text class="text-black text-sm">10月8日</text>
				</view>
			</navigator>
			
			
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			}
		},
		methods: {
			
		}
	}
</script>

<style>
page{
	background: #fff;
}
</style>
