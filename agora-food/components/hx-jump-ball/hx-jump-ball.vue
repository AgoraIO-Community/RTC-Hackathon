<template>
	<view class="hx-jump-ball">
		<view class="ballBox" :animation="ballBoxAnimationData" :style="{'z-index':index}">
			<view class="ballOuter" 
			:animation="ballOuterAnimationData" 
			:style="{width:ballWidth*2 + 'upx',height:ballHeight*2 + 'upx','background-color':backgroundColor,'background-image': backgroundImage ?`url(${backgroundImage})`: ''}">
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		name: "hx-jump-ball",
		data() {
			return {
				flag: false,
				ballBoxAnimation: null,
				ballOuterAnimation: null,
				ballBoxAnimationData: {},
				ballOuterAnimationData: {},
			};
		},
		props: {
			//小球宽度
			ballWidth: {
			    type: Number,
				default: 15
			},
			//小球高度
			ballHeight: {
			    type: Number,
				default: 15
			},
			//小球颜色
			backgroundColor: {
			    type: String,
				default: "reg"
			},
			//图片
			backgroundImage: {
			    type: String,
				default: ""
			},
			//小球的堆叠顺序
			index: {
			    type: Number,
				default: 0
			},
			//开始动画
			start: {
			    type: Number,
				default: 1
			},
			//html元素class名称，[起点元素，终点元素]
			element:{
				type: Array,
				default(){
					return []
				}
			},
			//下落速度 ms毫秒
			speed:{
				type: Number,
				default: 800
			},
			//贝塞尔曲线
			bezier:{
				type: String,
				default: "cubic-bezier(.6,-0.63,.94,.71)"
			}
		},
		
		watch:{
			start(val,oldVal) {
				var that = this;
				if(!that.element){
					return;
				}
				if(this.flag){
					return;
				}
				that.flag = !that.flag;
				that.getElementCoordinate(that.element[0],that.element[1]);
				
			}
		},
		created() {
			this.ballBoxAnimation = uni.createAnimation({
				duration: 0,
				timingFunction: this.bezier,
				delay: 0
			}); 
			this.ballOuterAnimation = uni.createAnimation({
				duration: 0,
				timingFunction: "linear",
				delay: 0
			});
			this.setEnd();
			//初始化位置
		},
		methods:{
			//获取元素坐标
			getElementCoordinate(startElement,endElement){
				let that = this;
				const query = uni.createSelectorQuery();  
				let nodesRef = query.select(startElement); 
				nodesRef.fields({  
				      id: true,  
				      rect: true,
					  size: true
				}, res => {
					  if(!res){
						  that.flag = !that.flag;
						  that.$emit("msg",{code: 100, error: '未获取到起始元素位置'})
						  return ;
					  }
					  const SLeft = res.left  + ((res.width + that.ballWidth ) / 2  - that.ballWidth);
					  const STop = res.bottom - ((res.height - that.ballHeight ) / 2  + that.ballHeight);
					  
					  let nodesRef2 = uni.createSelectorQuery().select(endElement); 
					  nodesRef2.fields({  
					        id: true,  
					        rect: true,
					  	  size: true
					  }, res2 => {  
					  	  if(!res2){
							  that.$emit("msg",{code: 101, error: '未获取到结束元素位置'})
							  return ;
					  	  }
						  //计算出元素的中心坐标
					  	  let ELeft = res2.left + ((res2.width + that.ballWidth ) / 2  - that.ballWidth);
					  	  let ETop = res2.bottom - ((res2.height - that.ballHeight ) / 2  + that.ballHeight);
						 
						  
					  	  that.startAnimation(SLeft,STop,ELeft,ETop);
					  }).exec()  
					  
				}).exec()  
			},
			
			
			//开始动画
			startAnimation(SLeft,STop,ELeft,ETop){
				let that = this;
				
				let jumpDistance = SLeft - ELeft;
				
			
				
			
			//  暂时注释掉，待uniapp修复bug后再调整
				//根坐标
			// 	this.ballBoxAnimation.translate3d(ELeft,STop,0).step({duration: 800});
			// 	this.ballBoxAnimation.translate3d(ELeft,ETop,0).step({duration: 800});
			// 	this.ballBoxAnimationData = this.ballBoxAnimation.export();
			
			// 	console.log('根坐标执行玩');
			// 	//相对根的坐标
			// 	this.ballOuterAnimation.translate3d(jumpDistance,0,0).step({duration: 800});
			// 	this.ballOuterAnimation.translate3d(0,0,0).step({duration: 800});
			// 	this.ballOuterAnimationData = this.ballOuterAnimation.export();
			// 	console.log('相对根的坐标');
			// 	setTimeout(function() {
			// 		console.log("动画完成");
			// 		that.flag = !that.flag;
			// 	}, 800);
				//初始化位置
				//that.setStart(SLeft,STop,ELeft,ETop);
				//因为uniapp  step()有bug，所以必须要延时执行
				that.ballBoxAnimation = uni.createAnimation({
					duration: 0,
					timingFunction: that.bezier,
					delay: 0
				}); 
				that.ballOuterAnimation = uni.createAnimation({
					duration: 0,
					timingFunction: "linear",
					delay: 0
				});
				//根坐标
				that.ballBoxAnimation.translate3d(ELeft,STop,0).opacity(1).step({duration: 0});
				that.ballBoxAnimation.opacity(1).translate3d(ELeft,ETop,0).step({duration: that.speed});
				that.ballBoxAnimation.opacity(0).step({duration: 0});
				that.ballBoxAnimationData = that.ballBoxAnimation.export();
				
				//相对根的坐标
				that.ballOuterAnimation.translate3d(SLeft - ELeft,0,0).opacity(1).step({duration: 0});
				that.ballOuterAnimation.opacity(1).translate3d(0,0,0).step({duration: that.speed});
				that.ballOuterAnimation.opacity(0).step({duration: 0});
				that.ballOuterAnimationData = that.ballOuterAnimation.export();
				
				setTimeout(function() {
					that.flag = !that.flag;
					that.$emit("msg",{code:0,status:true});
				},  that.speed);
				
				
			},
			 //动画开始前初始化小球位置并显示小球
			setStart(SLeft,STop,ELeft,ETop){
				this.ballBoxAnimation.translate3d(ELeft,STop,0).opacity(1).step({duration: 0});
				this.ballBoxAnimationData = this.ballBoxAnimation.export();
				
				this.ballOuterAnimation.translate3d(SLeft - ELeft,0,0).opacity(1).step({duration: 0});
				this.ballOuterAnimationData = this.ballOuterAnimation.export();
			},
			
			//隐藏小球
			setEnd(){
				this.ballBoxAnimation.opacity(0).step({duration: 0});
				this.ballBoxAnimationData = this.ballBoxAnimation.export();
				
				this.ballOuterAnimation.opacity(0).step({duration: 0});
				this.ballOuterAnimationData = this.ballOuterAnimation.export();
			}
		}
	}
</script>

<style>
	.ballBox{
		position: fixed;
		left: 0;
		top: 0;
		z-index: 9;
		/*  用颜色来演示用原理 */
		/*background-color: #4CD964*/;
		height:30rpx;
		width:30rpx;
	}
	.ballOuter {
	   background:red;
	   height:100%;
	   width:100%;
	   border-radius: 50%;
	   background-size: 100% 100%;
	   background-position: center;
	}
	
</style>
