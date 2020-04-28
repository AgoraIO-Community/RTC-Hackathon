<template>
	<view class="hx-comment">
		<view class="hx-comment_basic-info">
			<view class="hx-comment_basic-info_left">
				<view class="hx-comment_basic-info_left_top">
					<text>3.5</text>
				</view>
				<view class="hx-comment_basic-info_left_bottom">
					<text>商家评分</text>
				</view>
			</view>
			<view class="hx-comment_basic-info_center">
				<view class="hx-comment_basic-info_center_item" style="margin-bottom: 10px;">
					<view class="hx-comment_basic-info_center_item_left">
						<text>包装</text>
					</view>
					<view class="hx-comment_basic-info_center_item_center">
						<uni-rate size="14" value="3.6"></uni-rate>
					</view>
					<view class="hx-comment_basic-info_center_item_right">
						<text>3.6</text>
					</view>
				</view>
				<view class="hx-comment_basic-info_center_item">
					<view class="hx-comment_basic-info_center_item_left">
						<text>新鲜</text>
					</view>
					<view class="hx-comment_basic-info_center_item_center">
						<uni-rate size="14" value="4.5"></uni-rate>
					</view>
					<view class="hx-comment_basic-info_center_item_right">
						<text>4.5</text>
					</view>
				</view>
			</view>
			<view class="hx-comment_basic-info_right">
				
				<view class="hx-comment_basic-info_right_top">
					<view class="hx-comment_basic-info_right_top">
						<text>95%</text>
					</view>
					<view class="hx-comment_basic-info_right_bottom">
						<text>配送满意度</text>
					</view>
				</view>
			</view>
		</view>
		<view class="hx-comment_dividing-line15"></view>
		<view class="hx-comment_main-box">
			
			<view class="lists" v-if="listData.length > 0">
				<block v-for="(item, index_) in listData" :key="index_">
					<view class="item">
						<view class="icon"><image :src="item.header_img" mode="widthFix" style="width:100%" /></view>
						<view class="info">
							<view class="name-time">
								<text class="name">{{ item.user_name }}</text>
								<text class="time">{{ item.create_time }}</text>
							</view>
							<view class="stars">
								<text>评分</text> <uni-rate size="14" value="4.5"></uni-rate>
							</view>
							<view class="evaluate-content">
								<text>{{ item.content || '用户暂未评价' }}</text>
								<view class="imgs" v-if="item.imgs">
									<block v-for="(imgurl, index) in item.imgs" :key="index">
										<view class="imgs-box"><image :src="imgurl" mode="widthFix" style="width: 100%;" @click="previewImgs(imgurl,item.imgs)"></image></view>
									</block>
								</view>
							</view>
						</view>
					</view>
					<view class="hx-bb"></view>
				</block>
			</view>
			<view class="no-lists" v-else>暂无评论</view>
			
		</view>
	</view>
</template>

<script>
	import uniRate from '@/components/uni-rate/uni-rate.vue'
	
	export default {
		name: "hx-comment",
		components: {uniRate},
		props: {
			//评价列表数据
			listData: {
				type: Array,
				default: function(){
					return [];
				},
			},
		},
		data() {
			return {
				
			};
		},
		
		created() {
		},
		methods: {
			previewImgs(img,imgList){
				// 预览图片
				uni.previewImage({
					current: img,
					urls: imgList,
					indicator: "default",
					// longPressActions: {
					// 	itemList: ['发送给朋友', '保存图片', '收藏'],
					// 	success: function(data) {
					// 		//console.log('选中了第' + (data.tapIndex + 1) + '个按钮,第' + (data.index + 1) + '张图片');
					// 	},
					// 	fail: function(err) {
					// 		//console.log(err.errMsg);
					// 	}
					// }
				});
			}
		}
	}
</script>

<style lang="scss">
	
	$hx-color-main: #ff9800;
	$hx-color-gray: #999999;
	
	.hx-comment{
		
		position: relative;
		.hx-bb{
			margin-left: 11px;
			border-bottom: 1px solid #efefef;
			
		}
		&_dividing-line15{
			height: 15px;
			background: #efefef;
			width: 100%;
		}
		&_dividing-line{
			height: 2px;
			background: #efefef;
			width: 100%;
		}
		&_basic-info{
			height: 82px;
			display: flex;
			flex-direction: row;
			align-items: center;
			text-align: center;
		
			&_left{
				display: flex;
				flex-direction: column;
				width: 100px;
				&_top{
					font-size: 32px;
					margin-bottom: 2px;
					color: $hx-color-main;
				}
				&_bottom{
					font-size: 12px;
					 color: #555555;
				}
			}
			&_center{
				flex: 1;
				display: flex;
				align-items: center;
				flex-direction: column;
				
				&_item{
					font-size: 12px;
					display: flex;
					flex-direction: row;
					align-items: center;
					justify-content : center;
					&_left{
						color: #555555;
					}
					&_center{
						margin: 0 6px 0 8px;
						height: 0;
					}
					&_right{
						color: #FF9800;
					}
				}
			}
			&_right{
				width: 100px;
				display: flex;
				flex-direction: column;
				border-left: 1px solid #efefef;
				&_top{
					font-size: 26px;
					margin-bottom: 2px;
					 color: $hx-color-gray;
				}
				&_bottom{
					font-size: 12px;
					 color: $hx-color-gray;
				}
			}
		}
		&_main-box{
			
			.lists .item {
				padding: 20upx;
				display: flex;
				font-size: 22upx;
				color: #999;
				.icon {
					width: 60upx;
					height: 60upx;
					border-radius: 50%;
					overflow: hidden;
					margin-right: 26upx;
					border: 1px solid #efefef;
				}
			}
			.lists .item .info {
				flex: 1;
				font-size: 16px;
				color: #000;
				.name-time {
					display: flex;
					align-items: center;
					justify-content: space-between;
					.name{
						font-size: 16px;
						color: #000;
					}
					.time{
						font-size: 12px;
						color: #999;
					}
				}
				.stars {
					padding: 6px 0 10px;
					display: flex;
					flex-direction: row;
					align-items: center;
					position: relative;
					text{
						font-size: 12px;
						color: #999;
						margin-right: 6px;
					} 
					.uni-rate{
						height: 0;
					}
				}
				.evaluate-content {
					color: #555555;
					font-size: 14px;
					text-align: left;
					padding-top: 4px;
					.imgs {
						display: flex;
						flex-wrap: wrap;
						padding-top: 4px;
						.imgs-box {
							width: 25%;
							padding-right: 5px;
							box-sizing: border-box;
							image{
								border-radius: 4px;
							}
						}
					}
				}
			}
			.no-lists {
				padding: 20upx 0;
				text-align: center;
				font-size: 24upx;
				color: #666;
			}
		}
	}
</style>
