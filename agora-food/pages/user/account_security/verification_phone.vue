<template>
    <view class="code-box">
		<hx-navbar left-text="" defaultBackUrl="update_phone" transparent="hidden" >
			<view slot="right" class="navRight">
				<button class="nextBtn" size="mini" @click="formSubmit">完成</button>
			</view>
		</hx-navbar>
		<view class="mRL">
			<view class="title">
				输入短信验证码
			</view>
			<view class="content">
				验证码已发送至{{mobile}}，请在下方输入框内输入4位数字验证码。
			</view>
			
			<view class="flex-box margin-top">
			    <input
			        type="number"
			        focus="true"
			        :maxlength="maxlength"
			        class="hide-input"
			        @input="getVal"
			    />
			    <block v-for="(item, index) in ranges" :key="index">
			        <view :class="['item', { active: codeIndex === item, middle: type === 'middle', bottom: type === 'bottom', box: type === 'box' }]">
			            <view class="line" v-if="type !== 'middle'"></view>
			            <view v-if="type === 'middle' && codeIndex <= item" class="bottom-line"></view>
			            <block v-if="isPwd && codeArr.length >= item">
			                <text class="dot">.</text>
			            </block>
			            <block v-else>  {{ codeArr[index] ? codeArr[index] : ''}}</block>
			        </view>
			    </block>
				
			</view>
			
			<view class="timing-box margin-top">
				
				
				<block v-if="timing>0">
					<block v-if="postStatus"><text>正在发送短信验证...</text></block>
					<block v-else><text>{{timing}}秒后重新发送</text></block>
				</block>
				<block v-else>
					<view class="color-green" @click="resend">
						重新发送
					</view>
				</block>
				
				
			</view>
			
		</view>
        
    </view>
</template>

<script>
	export default {
		
	    watch: {
	        maxlength: {
	            immediate: true,
	            handler: function(newV) {
	                if (newV === 6) {
	                    this.ranges = [1, 2, 3, 4, 5, 6]
	                } else {
	                    this.ranges = [1, 2, 3, 4]
	                }
	            }
	        }
	    },
	    data() {
	        return {
	            codeIndex: 1,
				mobile: '',
	            codeArr: [],
	            ranges: [1, 2, 3, 4],
				maxlength:4,
				isPwd: false,
				type: "bottom",
				timing: 0,
				tNumber: 90,
				postStatus: false
	        }
	    },
		onLoad(option) {
			let reg =/^1[3-9]\d{9}$/;
			let that = this;
			
			if(!reg.exec(option.mobile)){
				uni.showToast({
					icon:'none',
					title: '手机号格式不正确！',
					duration: 2000
				})
			}else{
				this.mobile = option.mobile;
				that.resend();
			}
			
			
		},
	    methods: {
	        getVal(e) {
	            let { value } = e.detail
	            let arr = value.split('')
	            this.codeIndex = arr.length + 1
	            this.codeArr = arr
	            if (this.codeIndex > Number(this.maxlength)) {
					//验证码输入完成以后的操作
					let code =this.codeArr.join('');
	                console.log(code);
					this.updatePhone(code);
	            }
	        },
			//验证手机号
			updatePhone(c){
				let that = this;
				uni.showLoading({mask:true});
				
				this.$api.user.updatePhone({
					phone: that.mobile,
					code: c
				}).then((res)=>{
					uni.hideLoading();
					if(res.data.code == '0'){
						uni.showToast({
						    title: '完成！',
							icon: 'success',
						    duration: 2000,
							success:function(){
								//完成后返回至安全设置
								if(getCurrentPages() >= 3){
									uni.navigateBack({
									    delta: 3
									});
								}else{
									uni.reLaunch({
									    url: 'security'
									});
								}
								
							}
						});
					}
				}).catch((err)=>{
					uni.hideLoading();
				})
			},
			
			
			//重新发送验证码
			resend(){
				let that = this;
				if(!that.mobile){
					uni.showToast({
						icon:'none',
						title: '手机号格式不正确！',
						duration: 2000
					})
					return;
				}
				uni.showLoading({mask:true});
				this.postStatus = true
				this.$api.code.updatePhone({
					phone: that.mobile
				}).then((res)=>{
					uni.hideLoading();
					that.postStatus = false;
					if(res.data.code != '0'){
						uni.showToast({
						    title: '获取验证码失败！',
						    duration: 2000
						});
					}
				}).catch((e)=>{ 
					that.postStatus = false
					uni.hideLoading();
					that.timing = 0
					console.log("catch",e);
				})
				that.timing = that.tNumber;
				let t = setInterval(function(){
					that.timing = that.timing - 1
					if(that.timing <= 0){
						clearTimeout(t);
					}
				},1000)
			}
		
	    }
	}
</script>


<style scoped>
    @keyframes twinkling {
        0% {
            opacity: 0.2;
        }
        50% {
            opacity: 0.5;
        }
        100% {
            opacity: 0.2;
        }
    }
	.navRight{
		height: 100%;
		display: flex;
		justify-content: center;
		align-items: center;
		margin-right: 10upx;
	}
	.nextBtn{
		background: #07c160;
		color: #ffffff;
		border-color: #07c160;
	}
	.nextBtn:after{
		border-color: #07c160;
	}
	.color-green{
		 color: #00C777;
	}
	.mRL{
		margin: 30upx;
	}
	.margin-top{
		margin-top: 30upx;
	}
	.title {
		font-size: 50upx;
		color: #555555;
		margin-bottom: 24upx;
	}
	.content {
		font-size: 32upx;
		
		color: #999999;
	}
	.timing-box{
		color: #aaaaaa;
		text-align: center;
	}
    .code-box {
        text-align: left;
    }
    .flex-box {
        display: flex;
        justify-content: center;
        flex-wrap: wrap;
        position: relative;
		 text-align: center;
    }
    .flex-box .hide-input {
        position: absolute;
        top: 0;
        left: -100%;
        width: 200%;
        height: 100%;
        text-align: left;
        z-index: 9;
        opacity: 1;
    }
    .flex-box .item {
        position: relative;
        width: 100upx;
        height: 100upx;
        margin-right: 18upx;
        font-size: 70upx;
        color: #555555;
        line-height: 100upx;
    }
    .flex-box .item:last-child {
        margin-right: 0;
    }
    .flex-box .middle {
        border: none;
    }
    .flex-box .box {
        box-sizing: border-box;
        border: 2upx solid #cccccc;
        border-radius: 6upx;
    }
    .flex-box .bottom {
        box-sizing: border-box;
        border-bottom: 4upx solid #d2d2d2;
    }
    .flex-box .active {
        border-color: #00C777;
    }
    .flex-box .active .line {
        display: block;
    }
    .flex-box .line {
        display: none;
        position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);
        width: 2upx;
        height: 40upx;
        background: #333333;
        animation: twinkling 1s infinite ease;
    }
    .flex-box .dot{
        font-size: 80upx;
        line-height: 40upx;
    }
    .flex-box .bottom-line {
        height: 4px;
        background: #000000;
        width: 80%;
        position: absolute;
        border-radius: 2px;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
    }
</style>