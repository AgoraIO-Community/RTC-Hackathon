import api from '@/common/vmeitime-http/'
import conf from '@/config.js'

export default{
	checkUpgrade(wgtVer){
		let that = this;
		plus.nativeUI.showWaiting("检测更新...");
		api.app.upgrade({version:wgtVer}).then((res)=>{
			plus.nativeUI.closeWaiting();
			let resData = res.data;
			if( parseInt(resData.code) ==0 ){                           
				//可以升级
				plus.nativeUI.confirm("检查到当前版本有最新更新，下载升级？",
					function(event){
						if(event.index ==0){
							console.log('下载地址：'+ conf.server + resData.data.url)
							that.downWgt(conf.server + resData.data.url); //下载更新版的地址
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
		let that = this;
		plus.nativeUI.showWaiting("下载更新文件...");
		plus.downloader.createDownload( wgtUrl, {filename:"_downloads/update/"}, function(d,status){
			if ( status == 200 ) { 
				//console.log("下载wgt成功："+d.filename);
				that.installWgt(d.filename); // 安装wgt包
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
}