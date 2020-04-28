<template>
    <view>
        <hx-navbar :status-bar="true" defaultBackUrl="phone" :fixed="true" left-icon="back" left-text="验证手机号" right-text="下一步"></hx-navbar>
        <view class=" msgBox mRL">
        	<text class="text-gray ">
        		验证你的手机号码，{{appName}}会向你发送一条验证短信，并且会自动完成验证，整个过程你只需要耐心等待。{{appName}}不会公开你的手机号码给其他用户。
        	</text>
        </view>
		<view class="content mRL">
			<view class="areaCodeBox ">
				<text class="areaCode">+86</text>
				<text class="areaText">中国大陆</text>
			</view>
			<view class="phoneBox">
				<input type="text" :value="phone" placeholder="你本人手机号"/>
			</view>
		</view>
    </view>
</template>

<script>
    import {
        mapState 
    } from 'vuex';
    export default {
        data() {
            return {
				phone: '',
				appName: '',
            }
        },
		onLoad() {
			this.appName = this.$conf.appName;
		},
		computed:{
			...mapState(['userData'])
		},
        methods: {

        },
        
    }
</script>

<style lang="scss">
	
   page{
	  
   }
	.content{
		display: flex;
		flex-direction:row;
		justify-content: center;
		align-items: flex-end;
		height: 116upx;
		margin-top: 50upx;
	}
	.areaCodeBox{
		width: 160upx;
		display: flex;
		justify-content: center;
		align-items: center;
		flex-direction:column;
		border-bottom: 1px solid #e1e1e1;
		padding: 8upx 0;
		margin-right: 24upx;
	}
	.mRL{
		margin-left: 40upx;
		margin-right: 40upx;
	}
	.msgBox{
		margin-top: 56upx;
	}
	.areaCode{
		font-size: 30upx;
		color: #333333;
	}
	.areaText{
		font-size: 16upx;
		color: #444444;
	}
	.phoneBox{
		width: 100%;
		border-bottom: 1px solid $base-color;
		font-size: 38upx;
		padding: 0  12upx 16upx  12upx;
	}
</style>