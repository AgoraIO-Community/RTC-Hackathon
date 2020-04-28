# hx-jump-ball 用于加入购物车的跳跃效果

特效组件

本组件目前兼容H5、5+APP。

 ![image](https://raw.githubusercontent.com/Asuancaiyu/uniapp/master/components/hx-jump-ball/demo.gif)
### 使用案例：
[使用案例：HX商城。模版地址：https://ext.dcloud.net.cn/plugin?id=1352](https://ext.dcloud.net.cn/plugin?id=1352)

### 本组件支持模式：
1. 主要应用在购物车添加商品时的动态效果  


### 使用前提

注意HBuilder X 2.4.7版本以下 H5 是没有效果的，app有；所以赶紧升级编辑器！！！

### 使用方式	
页面使用需在 ``` script ``` 中引用组件
``` javascript
import hxJumpBall from "@/components/hx-jump-ball/hx-jump-ball.vue"
export default {
    components: {hxJumpBall}
}
```


### 属性
#### 基本属性 
| 名称                         | 类型            | 默认值                | 描述                                               |
| ----------------------------|---------------  | ---------------------- | ---------------------------------------------------|
| element                     | Array           | []                     | `[起跳元素，终点元素]`必填项，可以动态改变起跳元素，使用方式看demo     |
| start                       | Number          | 1                      | 想要执行一次动画，需要改变该值（每次加一或减一）						|
| ballWidth                   | Number          | 15                     | 小球宽度                                           |
| ballHeight                  | Number          | 15                     | 小球高度                                           |
| backgroundColor             | String          | reg                    | 小球颜色                                           |
| backgroundImage             | String          | ''                     | 小球图片                                          |
| index                       | Number          | 1                      | 堆叠顺序（z-index 参数）                                     |
| bezier                      | String          | cubic-bezier(.6,-0.63,.94,.71)          | 贝塞尔曲线，如果你想调整小球跳跃高度只需调整第二个参数 （-1 ~ 1）                             |
| speed                       | Number          | 800                    | 下落速度（毫秒）                             |


### 事件
| 名称             | 参数              | 描述                      |
| -----------------|------------------| --------------------------|
| @msg             | res              | 执行成功返回{code:0} ，失败返回{code:1,error:'info'}         |

## 使用例子

### html
``` html
<template>
	<view>
		<jumpBall :start.sync="num" :element.sync="element" @msg="jbMsg" />
		  
		<view class="add" @click="anima()" ><button>起跳1</button></view>
		<view class="add2" @click="anima2()"><button>起跳2</button></view>  
		<view class="foot"><view class="cart">测试位置</view></view>
	</view>
</template>
```

### javacript
``` javacript
<script>
	import jumpBall from '@/components/hx-jump-ball/hx-jump-ball.vue';
	export default {
		components: {
		  jumpBall
		},
		data() {
			return {
				num:1,
				element: [],
			}
		},
		methods: {
			anima(){
				this.element = ['.add','.cart'];
				this.num ++; 
			},
			anima2(){
				this.element = ['.add2','.cart'];
				this.num ++;
			}, 
			jbMsg(res){
				//执行加入购物车的逻辑
				console.log("执行回调",res.code);
			}
		}
	}
</script>
```

### css
```
<style>
	.add{
		position: fixed;
		right:30px;
		top: 150px;
	}
	.add2{
		position: fixed;
		right:30px;
		top: 250px;
	}
	.foot{
		position: fixed;
		bottom: 0;
		height: 90upx;
		background: #e1e1e1;
		width: 100%;
	}
	.foot .cart{
		width: 180upx;
		margin-left: 60upx;
		height: 90upx;
		background: #999999;
		line-height: 90upx;
		text-align: center;
	}
</style>
```