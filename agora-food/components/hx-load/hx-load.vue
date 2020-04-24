<template>
	<view class="hx-load">
		<view class="style1" v-if="style == 1">
			<view class="">
				<view class="item bounce1" :style="color ? `background-color: ${color}` : '' "></view>
				<view class="item bounce2" :style="color ? `background-color: ${color}` : '' "></view>
				<view class="item bounce3" :style="color ? `background-color: ${color}` : '' "></view>
			</view>
			<text>{{txt}}</text>
		</view>
	</view>
</template>

<script>
	export default {
		name: 'hx-load',
		data() {
			return {
				
			};
		},
		props:{
			'color': {
				type: String,
				default: '',
			},
			'style': {
				type: Number,
				default: 1,
			},
			'txt': {
				type: String,
				default: '正在载入',
			}
		}
	}
</script>

<style lang="scss">
	.hx-load{
		.style1 {
		  margin: 20px auto;
		  width: 150px;
		  text-align: center;
		  .item {
		    width: 12px;
		    height: 12px;
		    background-color: #ffd11c;
		    margin: 0 3px;
		    border-radius: 100%;
		    display: inline-block;
		    -webkit-animation: bouncedelay 1.4s infinite ease-in-out;
		    animation: bouncedelay 1.4s infinite ease-in-out;
		    -webkit-animation-fill-mode: both;
		    animation-fill-mode: both;
		  }
		  .bounce1 {
		    -webkit-animation-delay: -0.32s;
		    animation-delay: -0.32s;
		  }
		  .bounce2 {
		    -webkit-animation-delay: -0.16s;
		    animation-delay: -0.16s;
		  }
		  text{
			  margin-top: 6px;
			  font-size: 12px;
			  color: #cccccc;
		  }
		}
		@-webkit-keyframes bouncedelay {
		  0%, 80%, 100% { -webkit-transform: scale(0.0) }
		  40% { -webkit-transform: scale(1.0) }
		}
		@keyframes bouncedelay {
		  0%, 80%, 100% {
		    transform: scale(0.0);
		    -webkit-transform: scale(0.0);
		  } 40% {
		    transform: scale(1.0);
		    -webkit-transform: scale(1.0);
		  }
		}
	}

</style>
