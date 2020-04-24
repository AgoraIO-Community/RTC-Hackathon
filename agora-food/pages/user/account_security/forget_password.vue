<template>
	<view>
		<hx-navbar :status-bar="true" :fixed="true" defaultBackUrl="security" left-text="设置密码">
			
		</hx-navbar>
		<view class="" style="margin-top: 200upx;">
			
		</view>
		<input type="text" value="asdfaf" @blur="test" @confirm="suc"/>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			}
		},
		onLoad() {
			try{
				var powered = false;
				chrome.bluetooth.getAdapterState(function(adapter) {
				  powered = adapter.powered;
				});
				
				chrome.bluetooth.onAdapterStateChanged.addListener(
				  function(adapter) {
				    if (adapter.powered != powered) {
				      powered = adapter.powered;
				      if (powered) {
				        console.log("Adapter radio is on");
				      } else {
				        console.log("Adapter radio is off");
				      }
				    }
				  });
			}catch(e){
				//TODO handle the exception
				console.log(e)
			}
			
		},
		methods: {
			
		}
	}
</script>

<style>

</style>
