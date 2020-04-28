<template>
	<view>
		<hx-navbar left-text="关于" defaultBackUrl="/pages/user/setting/setting"></hx-navbar>
		
		<view class="container">
			<image src="../../static/img/logo96x96.png" mode=""></image>
			<view class="title margin-top">
				<text>{{appName}}</text>
			</view>
			<text class="vsersion">版本号 {{version}}</text>
		</view>
	
		<view class="cu-list menu sm-border">
			
			<navigator class="cu-item arrow " url="features" open-type="navigate">
				<view class="content">
					<text class="text-black">功能介绍</text>
				</view>
				<view class="action">
					<text class="text-grey text-sm"></text>
				</view>
			</navigator>
			<!-- #ifdef APP-PLUS -->
			<view class="cu-item arrow " @click="upgrade">
				<view class="content">
					<text class="text-black">检测更新</text>
				</view>
				<view class="action">
					<text class="text-grey text-sm"></text>
				</view>
			</view>
			<!-- #endif -->
			<navigator class="cu-item arrow " url="protocol/protocol" open-type="navigate">
				<view class="content">
					<text class="text-black">服务协议</text>
				</view>
				<view class="action">
					<text class="text-grey text-sm"></text>
				</view>
			</navigator>
		</view>
		<view class="foot">
			<view class="solid-bottom text-xs padding justify-center">
				<text class="text-gray">{{company}} 版权所有</text>
				<view></view>
				<text class="text-gray">Copyright © 2019-{{nowYear}} {{companyEN}}</text>
				<view></view>
				<text class="text-gray">All Rights Reserved</text>
				
			</view>
		</view>
	</view>
</template>

<script>
	var that;
	export default {
		data() {
			return {
				appName: this.$conf.appName,
				version: this.$conf.version,
				company: this.$conf.company,
				companyEN: this.$conf.companyEN,
				nowYear: 2019,
				server: this.$conf.server
			}
		},
		onLoad(){
			
		},
		mounted() {
			that= this;
			var myDate = new Date();
			that.nowYear = myDate.getFullYear();
		},
		methods: {
			// #ifdef APP-PLUS
			upgrade(){
				let that = this;
				 plus.runtime.getProperty(plus.runtime.appid,function(inf){ 
				        that.$appUpgrade.checkUpgrade( inf.version); 
				        //sessionStorage.setItem('upgrade','1');
				 });  
				/* uni.showLoading({
				    title: '获取最新版本中'
				});
				setTimeout(function () {
				    uni.hideLoading();
					uni.showToast({
					    title: '已是最新版本',
						position: 'center',
					});
				}, 2000); */
			},
			
			//检查更新
			checkUpgrade(wgtVer){
				let that = this;
				plus.nativeUI.showWaiting("检测更新...");
				that.$api.app.upgrade({version:wgtVer}).then((res)=>{
					plus.nativeUI.closeWaiting();
					let resData = res.data;
					if( parseInt(resData.code) ==1 ){                           
						//可以升级
						plus.nativeUI.confirm("检查到当前版本有最新更新，下载升级？",
							function(event){
								if(event.index ==0){
									console.log('下载地址：'+ server + resData.url)
									downWgt(server + resData.url); //下载更新版的地址
								}                        
							} ,'系统消息',['马上升级','下次再说']);                
					} else{  
						plus.nativeUI.toast("无新版本可更新！");
					}
				}).catch((e)=>{
					plus.nativeUI.closeWaiting();
					 plus.nativeUI.toast('检测更新失败！') ;
				})
			},
			// 下载wgt文件
			downWgt(wgtUrl){
			    plus.nativeUI.showWaiting("下载更新文件...");
			    plus.downloader.createDownload( wgtUrl, {filename:"_doc/update/"}, function(d,status){
			        if ( status == 200 ) { 
			            //console.log("下载wgt成功："+d.filename);
			            installWgt(d.filename); // 安装wgt包
			        } else {
			            //console.log("下载wgt失败！");
			            plus.nativeUI.alert("下载更新失败！");
			        }
			        plus.nativeUI.closeWaiting();
			    }).start();
			},
			
			// 更新应用资源  
			installWgt(path){
			    plus.nativeUI.showWaiting("正在安装更新文件...");
			    plus.runtime.install(path,{},function(){
			        plus.nativeUI.closeWaiting();        
			        plus.nativeUI.alert("应用资源更新完成！",function(){
			            plus.runtime.restart();
			        });
			    },function(e){
			        plus.nativeUI.closeWaiting();        
			        plus.nativeUI.alert("安装更新文件失败["+e.code+"]："+e.message);
			    });
			}
			
			// #endif
		}
	}
</script>

<style>
	page{
		background: #ffffff;
	}
	.cu-list.menu {
	    margin-left:24upx;
		margin-right:24upx;
		border-top: 2upx solid #f1f1f1;
	    border-bottom: 2upx solid #f1f1f1;
	}
	.text-grey{
		color: #555555;
	}
	.container{
		margin-top: 60upx;
		margin-bottom: 60upx;
		text-align: center;
	}
	.container image{
		width: 192upx;
		height: 192upx;
	}
	.title{
		font-size: 40upx;
		font-weight: bold;
		color: #333333;
		text-align: center;
		margin-bottom: 8upx;
	}
	.version{
		font-size: 32upx;
		color: #333333;
	}
	.foot{
		position: absolute;
		bottom: 10upx;
		text-align: center;
		width: 100%;
	}
</style>
