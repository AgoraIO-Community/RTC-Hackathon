**插件使用说明**

- 基于 Promise 对象实现更简单的 request 使用方式，支持请求和响应拦截
- 下载后把 vmeitime-http 文件夹 copy 到项目 common/ 目录下

## 1. 配置 

### 1.1 全局配置修改（修改vmeitime-http/interface.js中config和interceptor）
``` javascript 
	config: {
		baseUrl: "https://api.com/api/",
		header: {
			'Content-Type':'application/json;charset=UTF-8',
			'Content-Type':'application/x-www-form-urlencoded'
		},    
		dataType: "json",  
		responseType: "text"
	},
	interceptor: {
		request: null,
		response: null
	}
```
	
### 1.2 具体接口调用时修改（在vmeitime-http/index.js文件中具体业务接口中配置)
``` javascript
//设置baseUrl
http.config.baseUrl = "http://localhost:8080/api/"
//设置请求前拦截器
http.interceptor.request = (config) => {
    //添加通用参数
    config.header = {
        "token": "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
    }
}
//设置请求结束后拦截器
http.interceptor.response = (response) => {
    //判断返回状态 执行相应操作
    return response;
}
```	


## 2. 使用

### 2.1 全局使用(在main.js注册)

``` //  main.js
  import api from '@/common/vmeitime-http/'
	
	// 全局挂载后使用
	Vue.prototype.$api = api
```

``` // pages/index/index.vue

<template>
	<view class="content">
		
		<view class="uni-padding-wrap uni-common-mt">
			<view class="uni-textarea uni-common-mt">
				<textarea :value="res"></textarea>
			</view>
			<view class="uni-btn-v uni-common-mt">
				<button type="primary" @click="sendRequest" :loading="loading">发起请求</button>
				<button type="default" @click="sendRequest1" :loading="loading">发起请求(async/await)</button>
			</view>
		</view>
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				loading: false,
				res: ''
			}
		},
		onLoad(option) {
			//this.sendRequest();
			//this.sendRequest1();
		},
		methods: {
			// 方式一
			sendRequest: function() {
				this.loading = true
				this.$api.test({noncestr: Date.now()}).then((res)=>{
					this.loading = false;
					console.log('request success', res)
					uni.showToast({
						title: '请求成功',
						icon: 'success',
						mask: true
					});
					this.res = '请求结果 : ' + JSON.stringify(res);
				}).catch((err)=>{
					this.loading = false;
					console.log('request fail', err);
				})
			},
			
			//方式二  https://segmentfault.com/a/1190000013292562?utm_source=channel-newest
			async sendRequest1() {
				this.loading = true
				let res = await this.$api.test({noncestr: Date.now()});
				this.loading = false;
				this.res = '请求结果 : ' + JSON.stringify(res);
			}
		}
	}
</script>
```


### 2.2 局部使用(局部使用，不需要在 main.js 中注册) 

``` // pages/index/index.vue

<template>
	<view class="content">
		
		<view class="uni-padding-wrap uni-common-mt">
			<view class="uni-textarea uni-common-mt">
				<textarea :value="res"></textarea>
			</view>
			<view class="uni-btn-v uni-common-mt">
				<button type="primary" @click="sendRequest" :loading="loading">发起请求</button>
				<button type="default" @click="sendRequest1" :loading="loading">发起请求(async/await)</button>
			</view>
		</view>
		
	</view>
</template>

<script>
    import api from '@/common/vmeitime-http/'

    export default {
        data() {
            return {
				loading: false,
				res: ''
			}
        },
        onLoad(option) {
        	//this.sendRequest();
        	//this.sendRequest1();
        },
        methods: {
        	// 方式一
        	sendRequest: function() {
        		this.loading = true
        		api.test({noncestr: Date.now()}).then((res)=>{
        			this.loading = false;
        			console.log('request success', res)
        			uni.showToast({
        				title: '请求成功',
        				icon: 'success',
        				mask: true
        			});
        			this.res = '请求结果 : ' + JSON.stringify(res);
        		}).catch((err)=>{
        			this.loading = false;
        			console.log('request fail', err);
        		})
        	},
        	
        	//方式二
        	async sendRequest1() {
        		this.loading = true
        		let res = await this.api.test({noncestr: Date.now()});
        		this.loading = false;
        		this.res = '请求结果 : ' + JSON.stringify(res);
        	}
        }
    }
</script>

```


## 3. 接口数据加密、接口签名核验

在vmeitime-http/interface.js文件中的request(Object)方法中补充修改相应的代码

## 4. 接口请求/响应日志记录

在vmeitime-http/interface.js文件中的request(Object)方法中补充修改相应的代码

## 5. 业务相关接口编写

在vmeitime-http/index.js文件中的编写具体业务相关的接口,参考test()方法



	