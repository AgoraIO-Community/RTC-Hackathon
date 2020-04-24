<template>
    <view>
        <hx-navbar :status-bar="true" :fixed="true" defaultBackUrl="security" left-text="设置密码">
			<view slot="right" class="navRight">
				<button class="nextBtn" size="mini" @click="formSubmit">完成</button>
			</view>
		</hx-navbar>
        
		<view class="content mRL">
			<view class="mt">
				<text class="text-gray ">
					请设置{{appName}}密码，你可以用{{appName}}绑定的账号+{{appName}}密码登录，比如使用手机号+{{appName}}密码登录{{appName}}，更快捷。
				</text>
			</view>
			<view class="pwsBox">
				<view class="listItem">
					<view class="listItem-left">
						原密码
					</view>
					<view class="listItem-right" :class="[setActive1 && 'active']">
						<input type="text" :password="true" placeholder-style="color:#b5b5b5;" value="" 
						:v-model="oldPwd"
						:focus="setActive1" 
						placeholder="填写原密码"  
						@focus="setActive1 = true" 
						@blur="setActive1 = false"
						@input="setOldPwd"
						@confirm="formNext1"
						confirm-type="next"/>
					</view>
				</view>
				<view class="listItem">
					<view class="listItem-left">
						新密码
					</view>
					<view class="listItem-right" :class="[setActive2 && 'active']">
						<input type="text" :password="true" placeholder-style="color:#b5b5b5;" 
						value="" 
						:focus="setActive2" 
						placeholder="填写新密码" 
						@focus="setActive2 = true" 
						@blur="setActive2 = false"
						@input="setNewPwd"
						@confirm="formNext2"
						confirm-type="next"/>
					</view>
				</view>
				<view class="listItem">
					<view class="listItem-left">
						确认密码
					</view>
					<view class="listItem-right" :class="[setActive3 && 'active']">
						<input type="text" :password="true" placeholder-style="color:#b5b5b5;" value=""  
						placeholder="再次填写确认" 
						:focus="setActive3" 
						@focus="setActive3 = true" 
						@blur="setActive3 = false"
						@input="setReNewPwd"
						@confirm="formNext3"
						confirm-type="done"/>
					</view>
				</view>
			</view>
			<view class="margin-top caption">
				密码必须是8-16位的数字、字母组合（不能是纯数字）
			</view>
			<navigator url="forget_password" class="forget">
				忘记原密码？
			</navigator>
		</view>
    </view>
</template>

<script>
    
    export default {
        data() {
            return {
				currentPhone: '',
				appName: this.$conf.appName,
				
				setActive1: true,
				setActive2: false,
				setActive3: false,
				
				oldPwd: '',
				newPwd: '',
				reNewPwd: '',
				
            }
        },
        methods: {
			setOldPwd(event){
				this.oldPwd = event.target.value;
			},
			setNewPwd(event){
				this.newPwd = event.target.value;
			},
			setReNewPwd(event){
				this.reNewPwd = event.target.value;
			},
			formNext1(value){
				this.oldPwd = value;
				setActive2 = true;
			},
			formNext2(value){
				this.newPwd = value;
				setActive3 = true;
			},
			formNext3(value){
				this.reNewPwd = value,
				this.formSubmit();
			},
			formSubmit(){
				let that = this;
				if(!this.oldPwd){
					uni.showModal({
					    title: '提示',
					    content: '请先填写原密码',
						confirmColor: '#3CC51F',
						showCancel: false,
					    success: function (res) {
					        if (res.confirm) {
					            that.setActive1 = true
					        }
					    }
					});
					return;
				}
				if(that.oldPwd.length<6){
					uni.showToast({
						icon: 'none',
						position: 'bottom',
						title: '原密码不正确！'
					});
					return;
				}
				
				
				
				uni.showLoading({mask:true});
				//验证原密码
				that.$api.user.passwordValidation({
					'str': that.$strEncode({
						oldPwd: that.oldPwd
					})
				}).then((res) => {
					
					let code = res.data.code;
					if(res.data.code != '0'){
						uni.showToast({
							icon: 'none',
							position: 'bottom',
							title: '原密码不正确！'
						});
						uni.hideLoading();
						that.setActive1 = true;
						return;
					}else{
						
						if(!this.newPwd || !this.reNewPwd || this.newPwd != this.reNewPwd){
							uni.showModal({
							    title: '提示',
							    content: '两次填写的密码不一致',
								confirmColor: '#3CC51F',
								showCancel: false,
							    success: function (res) {
							        if (res.confirm) {
										if(!that.newPwd){
											 that.setActive2 = true
										}else{
											 that.setActive3 = true
										}
							           
							        }
							    }
							});
							uni.hideLoading();
							return;
						}
						var patrn=/^[0-9]{1,20}$/;
						if (patrn.exec(this.newPwd)){
							uni.showToast({
								icon: 'none',
								position: 'bottom',
								title: '密码不能为纯数字！'
							});
							return;
						}
						if(this.newPwd.length<8){
							uni.showToast({
								icon: 'none',
								position: 'bottom',
								title: '密码长度为8-20位！'
							});
							return;
						}
						
						//新密码不能和旧密码相同
						if(this.newPwd == this.oldPwd){
							uni.showModal({
							    title: '提示',
							    content: '新密码不能和原密码相同',
								confirmColor: '#3CC51F',
								showCancel: false,
							    success: function (res) {
							        if (res.confirm) {
							            that.setActive1 = true
							        }
							    }
							});
							uni.hideLoading();
							return;
						}
						
						that.$api.user.updatePassword({
							'str': that.$strEncode({
								oldPwd: that.oldPwd,
								newPwd: that.newPwd
							})
						}).then((res) => {
							uni.hideLoading();
							let code = res.data.code;
							if(code != '0'){
								uni.showToast({
									icon: 'none',
									position: 'bottom',
									title: res.data.msg
								});
								return;
							}
							uni.showToast({
							    title: '完成',
								icon: 'success',
							    duration: 2000,
								success:function (res){
									//修改成功后返回上一级
									setTimeout(function() {
										if(getCurrentPages().length>1){
											uni.navigateBack();
										}else{
											uni.redirectTo({
												url:'security'
											});
										}
									}, 2000);
								}
							});
						}).catch((err) =>{
							uni.hideLoading();
						})
						
					}
				}).catch((err)=>{
					uni.hideLoading();
				});
				
				
			},
        },
        
    }
</script>

<style lang="scss">
	.navRight{
		height: 100%;
		display: flex;
		justify-content: center;
		align-items: center;
		margin-right: 10upx;
	}
	.content{
		margin-left: 40upx;
		margin-right: 40upx;
	}
	.mt{
		margin-top: 56upx;
	}
	.pwsBox{
		display: flex;
		flex-direction:column;
		justify-content: center;
		margin-top: 40upx;
	}
	.listItem{
		display: flex;
		flex-direction:row;
		justify-content: center;
		align-items: flex-end;
		height: 90upx;
		
	}
	.listItem-left{
		
		margin-right: 20upx;
		width: 74px;
		padding-bottom: 14upx;
		padding-top: 12upx;
		font-size: 34upx;
		height: 72upx;
	}
	.listItem-right{
		border-bottom: 1upx solid #d6d6d6;
		padding: 12upx;
		font-size: 34upx;
		flex: 1;
		height: 72upx;
	}
	.listItem-right.active{
		border-bottom-color: $base-color;
	}
	.forget{
		color: #0077AA;
		font-size: 26upx;
		display: inline;
		
	}
	.caption{
		font-size: 26upx;
		color: #666666;
		margin-bottom: 8upx;
	}
	.nextBtn{
		background:  $base-color;
		color: #ffffff;
		border-color:  $base-color;
	}
	.nextBtn:after{
		border-color:  $base-color;
	}
</style>