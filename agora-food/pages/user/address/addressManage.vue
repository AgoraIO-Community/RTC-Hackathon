<template>
	<view class="content">
		<hx-navbar title="收货地址" backTabbarUrl="/pages/user/center/center" :backgroundColor="[241,241,241]"></hx-navbar>
		<view class="bb15">
			
		</view>
		<view class="list-box">
			<view class="row b-b">
				<text class="tit">联系人</text>
				<input class="input" type="text" v-model="addressData.name" placeholder="收货人姓名" placeholder-class="placeholder" />
			</view>
			<view class="row b-b">
				<text class="tit">手机号</text>
				<input class="input" type="number" v-model="addressData.mobile" placeholder="收货人手机号码" placeholder-class="placeholder" />
			</view>
			<view class="row b-b">
				<text class="tit">地址</text>
				<text @click="chooseLocation" class="input">
					{{addressData.addressName}}
				</text>
				<text class="hxicon-locationfill"></text>
			</view>
			<view class="row b-b"> 
				<text class="tit">门牌号</text>
				<input class="input" type="text" v-model="addressData.area" placeholder="楼号、门牌" placeholder-class="placeholder" />
			</view>
			
			<view class="row default-row">
				<text class="tit">设为默认</text>
				<switch :checked="addressData.defaule" color="#ffc107" @change="switchChange" />
			</view>
			</view>
		<view class="add-btn" @click="confirm">
			<text>提交</text>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				addressData: {
					name: '',
					mobile: '',
					addressName: '在地图选择',
					address: '',
					area: '',
					default: false
				}
			}
		},
		onLoad(option){
			let title = '新增收货地址';
			if(option.type==='edit'){
				title = '编辑收货地址'
				
				this.addressData = JSON.parse(option.data)
			}
			this.manageType = option.type;
			uni.setNavigationBarTitle({
				title
			})
		},
		methods: {
			switchChange(e){
				this.addressData.default = e.detail;
			},
			
			//地图选择地址
			chooseLocation(){
				uni.chooseLocation({
					success: (data)=> {
						this.addressData.addressName = data.name;
						this.addressData.address = data.name;
					}
				})
			},
			
			//提交
			confirm(){
				let data = this.addressData;
				if(!data.name){
					this.$api.msg('请填写收货人姓名');
					return;
				}
				if(!/(^1[3|4|5|7|8][0-9]{9}$)/.test(data.mobile)){
					this.$api.msg('请输入正确的手机号码');
					return;
				}
				if(!data.address){
					this.$api.msg('请在地图选择所在位置');
					return;
				}
				if(!data.area){
					this.$api.msg('请填写门牌号信息');
					return;
				}
				
				//this.$api.prePage()获取上一页实例，可直接调用上页所有数据和方法，在App.vue定义
				this.$api.prePage().refreshList(data, this.manageType);
				this.$api.msg(`地址${this.manageType=='edit' ? '修改': '添加'}成功`);
				setTimeout(()=>{
					uni.navigateBack()
				}, 800)
			},
		}
	}
</script>

<style lang="scss">
	.bb15{
		background: #f1f1f1;
		padding-top: 40upx;
	}
	.list-box{
		display: flex;
		flex-direction: column;
		background-color: #fff;
		border-radius: 10px;
		overflow: hidden;
		margin: 0 15px;
	}
	.row{
		display: flex;
		align-items: center;
		position: relative;
		padding:0 30upx;
		height: 110upx;
		background: #fff;
		
		.tit{
			flex-shrink: 0;
			width: 120upx;
			font-size: 30upx;
			color: $font-color-dark;
		}
		.input{
			flex: 1;
			font-size: 30upx;
			color: $font-color-dark;
		}
		.icon-locationfill{
			font-size: 36upx;
			color: $font-color-light;
		}
	}
	.default-row{
		margin-top: 16upx;
		.tit{
			flex: 1;
		}
		switch{
			transform: translateX(16upx) scale(.9);
		}
	}
	.add-btn{
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
