<template>
	<view>
		<hx-navbar title="支付订单" :backgroundColor="[241,241,241]"></hx-navbar>
		<view class="head">
			<view class="row" >
				<text v-if="countdownStr">支付剩余时间 {{countdownStr}}</text>
				<text v-else>重新提交订单</text>
			</view>
			<view class="row pricebox pt-8">
				<text class="fh">￥</text>
				<text class="txt1">{{orderInfo.money}}</text>
			</view>
			<view class="row pt-4" @click="doOrder">
				<text>{{orderInfo.store_name}}</text>
				<text v-if="orderInfo.store_community">（{{orderInfo.store_community}}）</text>
				<text>订单详情</text>
				<i class="hxicon-right"></i>
			</view>
		</view>
		<view class="container">
			<radio-group @change="payChange" class="list-box">
			<block v-for="(item,i) in payData" :key="i">
				<view class="flex-rl list-item">
					<view class="left">
						<view class="iconbox" :style="'background-color:' + item.icon_color">
							<i :class="item.icon" ></i>
						</view>
						
					</view>
					<view class="b-b right">
						<view class="tit-box">
							<text class="tit">{{item.name}}</text>
						</view>
						<radio :value="item.id" color="#ffc107" :checked="item.default == 1" />
					</view>
				</view>
			</block>
			</radio-group>
		</view>
		<view class="btn" @click="submitPay">
			<text>确认支付</text>
		</view>
	</view>
</template>

<script>
	export default {
		onLoad(option) {
			let that = this
			//获取订单id
			if(option.id){
				
			}
		},
		onReady(){
			let that = this
			that.orderInfo = {
				//订单id
				order_id: "LQL20200410134",
				//金额
				money: 206.2,
				//门店名称
				store_name: "小太阳红旗商店",
				//门店地标
				store_community: "玫瑰园店",
				//订单创建时间
				create_date: "2020-04-10 18:08:56",
				//订单有效期
				expiration_date: "2021-04-12 20:06:56"
			}
			that.countdown();
			
			for (let i in this.payData){
				if(this.payData[i].default == 1){
					this.payType = this.payData[i].id
				}
			}
		},
		data() {
			return {
				countdownStr: "00:00",
				//订单信息
				orderInfo:{},
				//默认支付方式
				payType:"wxpay",
				// 支付方式
				payData: [{
						id: "wxpay",
						name: "微信支付",
						icon: "hxicon-weixinzhifu",
						icon_color: "#09bb07",
						default: 0,
					},
					{
						id: "alipay",
						name: "支付宝支付",
						icon: "hxicon-zhifubaozhifu",
						icon_color: "#08a0e9",
						default: 1
					}],

			}
		},
		methods: {
			
			payChange(event){
				
				this.payType = event.target.value
			},
			doOrder(){
				//查看订单
				uni.showModal({
				    content: '这是查看订单的事件',
					showCancel: false,
					confirmColor: "#999",
				    success: function (res) {
				       
				    }
				});
			},
			
			//倒计时
			countdown(){
				var go_time=new Date((this.orderInfo.expiration_date).replace(/-/g, '/'));
				var now_time=new Date();
				if(!go_time.getTime()){
					return
				}
				var alltime =go_time.getTime() -now_time.getTime ();  //剩余的时间（毫秒）
				if(alltime <= 0){
					this.countdownStr = ""
					this.showPrompt()
					return
				}
				 var haoscend =alltime%1000;  //毫秒
				//console.log(haoscend);
				var scend = parseInt ((alltime/1000)%60  )
				//console.log(scend);
				var minute =parseInt((alltime/1000/60)%60  )
			   // console.log(minute);
				var hour =parseInt((alltime/1000/60/60)%24 )
				if(scend<10){
					scend = "0" + scend
				}
				if(minute<10){
					minute = "0" + minute
				}
				let str = minute +":"+scend;
				if(hour>0){
					str = hour + ':' + str
				}
				this.countdownStr = str
				setTimeout (this.countdown ,1000); 
			},
			//显示弹窗
			showPrompt(){
				uni.showModal({
				    content: '支付超时，请重新下单',
					showCancel: false,
					confirmText: "重新下单",
					confirmColor: "#999",
				    success: function (res) {
				        if (res.confirm) {
				           
				        }
				    }
				});
			},
			//确认支付
			submitPay(){
				if(this.countdownStr == ''){
					this.showPrompt()
					return
				}
				// #ifdef APP-PLUS
					
					uni.requestPayment({
					    provider: this.payType,
					    orderInfo: 'orderInfo', //微信、支付宝订单数据
					    success: function (res) {
					        console.log('success:' + JSON.stringify(res));
					    },
					    fail: function (err) {
					        console.log('fail:' + JSON.stringify(err));
					    }
					});
				//#endif
			
				// #ifdef MP-WEIXIN
				//微信小程序
				uni.requestPayment({
					provider: 'wxpay',
					timeStamp: String(Date.now()),
					nonceStr: 'A1B2C3D4E5',
					package: 'prepay_id=wx20180101abcdefg',
					signType: 'MD5',
					paySign: '',
					success: function (res) {
						console.log('success:' + JSON.stringify(res));
					},
					fail: function (err) {
						console.log('fail:' + JSON.stringify(err));
					}
				});
				// #endif
				
			}
		   
		}
	}
</script>

<style lang="scss">
	page{
		background-color: #f1f1f1;
	}
	.pt-8{
		margin-top: 8px;
	}
	.pt-4{
		margin-top: 4px;
	}
	.flex-rl{
		display: flex;
		flex-direction: row;
		justify-content: space-between;
	}
	.head{
		display: flex;
		flex-direction: column;
		justify-content: center;
		padding: 30px 15px;
		.row{
			display: flex;
			flex-direction: row;
			justify-content: center;
			font-size: 12px;
			color: #888;
			.fh{
				font-size: 16px;
				font-weight: bold;
				color: #000;
			}
			.txt1{
				font-size: 34px;
				font-weight: bold;
				color: #000;
			}
			[class*="hxicon-"]{
				position: relative;
				top: 3px;
				margin-left: 3px;
			}
		}
		.pricebox{
			display: flex;
			flex-direction: row;
			align-items: baseline;
		}
		
	}
	.container{
		margin: 15px;
		border-radius: 10px;
		background-color: #fff;
		overflow: hidden;
		padding: 0 8px 0 14px;
		.list-box{
			display: flex;
			flex-direction: column;
			.list-item{
				display: flex;
				flex-direction: row;
				
				align-items: center;
				height: 50px;
				
				
				.left{
					display: flex;
					flex-direction: row;
					align-items: center;
					.iconbox{
						display: flex;
						flex-direction: row;
						justify-content: center;
						align-items: center;
						height: 24px;
						width: 24px;
						border-radius: 4px;
						[class*="hxicon-"]{
							font-size: 22px;
							color: #fff;
						}
						
					}
					
					
				}
				.right{
					display: flex;
					flex-direction: row;
					align-items: center;
					flex: 1;
					height: 100%;
					.tit-box{
						flex: 1;
						.tit{
							font-size: 16px;
							color: #333;
							margin-left: 6px;
						}
					}
					
				}
			}
		}
	}
	.btn{
		position: fixed;
		display: flex;
		flex-direction: row;
		justify-content: center;
		align-items: center;
		border-radius: 10px;
		overflow: hidden;
		bottom: 15px;
		left: 15px;
		right: 15px;
		height: 45px;
		font-size: 16px;
		font-weight: bold;
		color: #333;
		background: linear-gradient(45deg, #ffd900, #ffc107);		
	}
</style>
