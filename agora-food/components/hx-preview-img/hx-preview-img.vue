<template>
	<view class="previewImg"  @touchmove.stop.prevent="mpClear" :animation="formAnimationData">
		<view class="close" @click="close">
			<text>关闭</text>
		</view>
		<view class="mask" >
			
			<swiper @change="changeSwiper"  class="my_swiper" :current="currentIndex" :circular="circular" :indicator-dots="indicatorDots" :autoplay="autoplay" :duration="duration">
				<swiper-item v-for="(src, y) in picList" :key="y" >
					<!-- <movable-area class="movable_box" :scale-area="true" >
						<movable-view class="movable_view"  :scale="false" direction="all"  :scale-min="0.5" :scale-value="1" :scale-max="3" >
							
						</movable-view>
					</movable-area> -->
					<view  class="bg_img" :style="{ backgroundImage: 'url('+ src +')'}"></view>
				</swiper-item>
			</swiper>
		</view>
		<view class="pagebox" v-if="picList.length>0">{{ Number(currentIndex) + 1 }} / {{ picList.length }}</view>
	</view>
</template>

<script>
	export default {
		name: 'hx-preview-img',
		props: {
			current: {
				type: String,
				default: ''
			},
			list: {
				type: Array,
				default: function(){
					return new Array();
				}
			},
			//开始
			start: {
			    type: Number,
				default: 0
			},
			
		},
			
		data() {
			return {
				picList: [],
				indicatorDots: false,
				autoplay: false,
				duration: 50,
				circular: true,
				currentIndex: 0,
				isShowSwiper: false,
				animation:{},
				formAnimationData: {},
			};
		},
		watch: {
			list(val) {
				
			},
			current(val){
				
			},
			start(val,oldVal) {
				var that = this;
				if(val<=0){
					this.close()
				}else{
					//图片预览器为显示
					this.$emit("status",true)
					this.picListInit();
				}
				
			}
		},
		created() {
			
		},
		methods: {
			
			picListInit() {
				
				let that = this
				if(!this.current || !this.list){
					return;
				}
				for(var i in this.list){
					if(this.current == this.list[i]){
						this.currentIndex = i;
						break;
					}
				}
				this.picList=this.list;
				that.animation = uni.createAnimation({
				  //duration: 1000,
					timingFunction: 'ease',
				})
				that.animation.top(0).bottom(0).left(0).right(0).step({ duration: 0 })
				that.animation.opacity(1).step({ duration: 300 })
				that.formAnimationData = that.animation.export()
				
			},
			changeSwiper(e) {
				this.currentIndex = e.target.current;
			},
			mpClear(e) {
				// TODO nvue 取消冒泡
				e.stopPropagation()
			},
			close(){
				
				var that = this;
				//图片预览器为隐藏
				this.$emit("status",false)
				this.animation = uni.createAnimation({
				  //duration: 1000,
					timingFunction: 'ease',
				})
				this.animation.scale(0,0).opacity(0).step({ duration: 300 })
				this.animation.top(0).bottom(0).left("-100%").right("100%").step({ duration: 0 })
				this.formAnimationData = this.animation.export()
				
			}
			
		}
	}
</script>

<style lang="scss" scoped>
.previewImg {
	opacity: 0;
	position: fixed;
	top: 0;
	right: 100%;
	left: -100%;
	width: 100%;
	height: 100%;
	z-index: 99999999;
	
	.close{
		position: absolute;
		top: calc(var(--status-bar-height) + 15px);
		left: 15px;
		z-index: 6;
		color: #ffffff;
		font-size: 14px;
		background-color: rgba(255, 255, 255, 0.4);
		padding: 0 9px;
		border-radius: 4px;
		text-align: center;
		display: flex;
		align-items: center;
		height: 27px;

	}
	.pagebox{
		position: absolute;
		z-index: 6;
		color: #fff;
		bottom: 20rpx;
		text-align: center;
		width: 100%;
	}
	.mask {
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		display: flex;
		justify-content: center;
		align-items: center;
		background-color: #000;
		z-index: 5;
		.my_swiper {
			width: 100%;
			height: 60vh;
			.bg_img {
				background-size: 100% auto;
				background-repeat: no-repeat;
				background-position:center;
				width: 100%;
				height: 100%;
			}
			.movable_box{
				width: 100%;
				height: 60vh;
				.movable_view{
					width: 100%;
					height: 60vh;
					
				}
			
			}
			
		}
	}
	
	.pic_list {
		display: flex;
		flex-flow: row wrap;
		> view {
			flex: 0 0 33.3vw;
			height: 33.3vw;
			padding: 1vw;
			> image {
				width: 100%;
				height: 100%;
			}
		}
	}
}

</style>
