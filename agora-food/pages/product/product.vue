<template>
	<view class="container" id="productPage">
		<jumpBall  :start.sync="num" :element.sync="element" @msg="jbMsg" :speed="animaTime" :index="9999"/>
		<hx-navbar
		    :fixed="true"
		    :color="['#ffffff','#888888']"
		    barPlaceholder="hidden"
		    transparent="auto"
			:back="false" 
			:rightSlot="false"
		    :background-color="[255,255,255]"
			:pageScroll.sync="pageScroll">
			<block slot="left">
				<view class="ring" style="margin-left: 6px;font-weight: bold;" :style="showRing ? '' : 'background-color:#ffffff'" @click="backPage">
					<i class="hxicon-unfold"></i>
				</view>
				 
			</block>
			<view class="ctn">
				<view class="tabs-box" style="background: none; width: auto;" :style="!showRing ? '' : 'display:none;'">
					<view class="" style="width: 210px;height: 100%;">
						<view class="hx-tabs">
							<view class="hx-tabs-item" v-for="(item,i) in tabs" :key="i" :class="{'hx-tabs-active': swiperCurrent == i}" @click="swiperChange(i,item.id)" >
								<text>{{item.name}}</text>
							</view>
							<view class="hx-tabs-slider-box" :style="{left:swiperCurrentSliderLeft + 'px'}">
								<view class="hx-tabs-slider"></view>
							</view>
						</view>
					</view>
				</view>
				
				<view class="leftBox">
					<view class="ring" :style="showRing ? '' : 'background-color:#ffffff'">
						<i class="hxicon-cart_fill_light"></i>
					</view>
					<view class="ring" :style="showRing ? '' : 'background-color:#ffffff'" @click="share">
						<i class="hxicon-more"></i>
					</view>
					
				</view>
				<view class="jrNull"></view>
			</view>
		</hx-navbar>
		
		<!-- 滑动图片 -->
		<view class="carousel">
			<swiper indicator-dots circular=true duration="400">
				<swiper-item class="swiper-item" v-for="(item,index) in goodsInfoData.banner_img" :key="index" @click="previewImg(item,goodsInfoData.banner_img)">
					<view class="image-wrapper">
						<image
							:src="item" 
							class="loaded" 
							mode="aspectFill"
						></image>
					</view>
				</swiper-item>
			</swiper>
		</view>
		
		<view class="container-box introduce-section" >
			<text class="title">{{ goodsInfoData.name }}</text>
			<view class="tag-box">
				<block v-for="(item,i) in goodsInfoData.goods_tag" :key="i">
					<text class="tag-item" >{{item}}</text>
				</block>
				
				<!-- <text class="tag-item">香辣</text>
				<text class="tag-item">好吃</text> -->
			</view>
			<view class="bot-row">
				<text>月售{{ goodsInfoData.monthly_sales ? goodsInfoData.monthly_sales : 0 }}</text>
				<!-- <text>库存: 4690</text>
				<text>浏览量: 768</text> -->
			</view>
			
		</view>
		<view class="container-box shopping-cart" :class="{'barBorder':showBorder}">
			<view class="price-box" >
				<text class="price-tip">¥</text>
				<text class="price">{{ goodsInfoData.price }}</text>
				<block v-if="goodsInfoData.oldprice != 0 && goodsInfoData.oldprice > goodsInfoData.price">
					<text class="m-price" >¥{{ goodsInfoData.oldprice }}</text>
					<text class="coupon-tip" >{{(goodsInfoData.price/goodsInfoData.oldprice * 10).toFixed(1)}}折</text>
				</block>
			</view>
			
			<block v-if="goodsInfoData.form">
				<view class="add-btn" @click="showForm(goodsInfoData)">
					<i class="hxicon-add" id="eleAdd"></i>
					<text>选规格</text>
					<view class="num"  v-if="getCartGoodsNum(currentGoodsData)">
						{{getCartGoodsNum(currentGoodsData)}}
					</view>
				</view>
			</block>
			<block v-else>
				
				<block v-if="!currentGoodsData.number">
					<view class="add-btn" @click="formFirstAddGoods()">
						<i class="hxicon-add" id="eleAdd"></i>
						<text>加入购物车</text>
					</view>
				</block>
				<block v-else>
					<view class="btn-box">
						<view id="eleAdd" class="addEle_gg jumpPosition"></view>
						<hx-number-box @change="formAddGoodsChange" :value="currentGoodsData.number" :rowData="currentGoodsData" :clickTime="animaTime + 200" @addChange="touchOnAddGoods('#eleAdd',currentGoodsData)"></hx-number-box>
					</view>
				</block>
			</block>
		</view>
		<view class="line-weight"></view>
		
		<!--  分享 -->
		<view class="share-section" @click="share">
			<view class="share-icon">
				<text class="hxicon-favor_fill_light"></text>返
			</view>
			<text class="tit">该商品分享可领49减10红包</text>
			<text class="hxicon-mark"></text>
			<view class="share-btn">
				立即分享
				<text class="hxicon-right"></text>
			</view>
		</view> 
		<!-- <view class="c-list">
			<view class="c-row b-b" @click="toggleSpec">
				<text class="tit">购买类型</text>
				<view class="con">
					<text class="selected-text" v-for="(sItem, sIndex) in specSelected" :key="sIndex">
						{{sItem.name}}
					</text>
				</view>
				<text class="hxicon-right"></text>
			</view>
			
		</view>
		<view class="line-weight"></view> -->
		
		<!-- 详情 -->
		<view class="container-box">
			<view class="tabs-box" >
				<view class="" style="width: 210px;height: 100%;">
						
					<view class="hx-tabs">
						<view class="hx-tabs-item" v-for="(item,i) in tabs" :key="i" :class="{'hx-tabs-active': swiperCurrent == i}" @click="swiperChange(i,item.id)" >
							<text>{{item.name}}</text>
						</view>
						<view class="hx-tabs-slider-box" :style="{left:swiperCurrentSliderLeft + 'px'}">
							<view class="hx-tabs-slider"></view>
						</view>
					</view>
				</view>
			</view>
			<view class="info-box" id="detail">
				<view class="info-item" v-for="(item,i) in goodsInfoData.detail" :key="i">
					<text class="left">{{item.tit}}：</text>
					<text class="right">{{item.txt}}</text>
				</view>
			</view>
			
		</view>
		<view class="line-weight"></view>
		
		<!-- 评价 -->
		<view class="container-box eva-section" id="eva">
			<view class="e-header">
				<text class="tit">评价</text>
				<text class="hpd" v-if="goodsEvaData.praise/goodsEvaData.sum * 100 > 0">({{Math.round(goodsEvaData.praise/goodsEvaData.sum * 100)}}%好评度)</text>
				<text class="tip">{{goodsEvaData.sum ? goodsEvaData.sum : 0}}条评论</text>
				<text class="hxicon-right"></text>
			</view> 
			<view class="eva-tag-box" v-if="goodsEvaData.eva_tag">
				<!-- 标签自定义 ；“有图 ，赞，踩” 三种标签一般评价都有-->
				<view class="tag-item" v-if="goodsEvaData.eva_tag.exist_pic > 0">有图 <text class="num">{{goodsEvaData.eva_tag.exist_pic}}</text></view>
				<view class="tag-item" v-if="goodsEvaData.eva_tag.appreciate > 0"><i class="hxicon-appreciate_fill_light"></i> <text class="num">{{goodsEvaData.eva_tag.appreciate}}</text></view>
				<view class="tag-item tread-bg" v-if="goodsEvaData.eva_tag.oppose > 0"><i class="hxicon-oppose_fill_light tread"></i> <text class="num">{{goodsEvaData.eva_tag.oppose}}</text></view>
				<view class="tag-item" v-for="(item,i) in goodsEvaData.eva_tag.other" :key="i">{{item}}</view>
				
			</view>
			<!-- 最好不要超过两条评价 -->
			<view class="eva-box" v-for="(item, i) in goodsEvaData.eva_list" :key="i">
				<image class="portrait" :src="item.avatar" mode="aspectFill"></image>
				<view class="right">
					<view class="bot">
						<text class="attr">{{item.name}}</text>
						<text class="time">{{item.time}}</text>
					</view>
					<view class="praise-box" v-if="item.point == 1">
						<i class="hxicon-appreciate_fill_light" ></i>
						<text class="praise-txt">攒了该商品</text>
					</view>
					<view class="praise-box" v-else-if="item.point == 2">
						<i class="hxicon-oppose_fill_light " :class="{tread:true}"></i>
						<text class="praise-txt">踩了该商品</text>
					</view>
					<text class="con">{{item.content}}</text>
					<view class="imgs" v-if="item.pic">
						<block v-for="(imgurl, index) in item.pic" :key="index">
							<view class="imgs-box"><image :src="imgurl" mode="widthFix" style="width: 100%;" @click="previewImg(imgurl,item.pic)"></image></view>
						</block>
					</view>
				</view>
				
			</view>
			
			
			<view class="eva-btn">
				<view class="avatar-box">
					<view class="avatar-item" v-for="(item, i) in goodsEvaData.eva_list" :key="i">
						<image :src="item.avatar" mode=""></image>
					</view>
					
					<view class="avatar-item">
						<i class="hxicon-more"></i>
					</view>
				</view>
				<text>{{goodsEvaData.sum}}条评价</text>
				<i class="hxicon-right"></i>
			</view>
		</view>
		<view class="line-weight"></view>
		<view class="detail-desc" id="coll">
			<view class="d-header">
				<text>图文详情</text>
			</view>
			<rich-text :nodes="goodsInfoData.desc"></rich-text>
		</view>
		
		<!-- 底部操作菜单 -->
		<!-- <view class="page-bottom">
			<navigator url="/pages/index/index" open-type="switchTab" class="p-b-btn">
				<text class="hxicon-home_fill_light"></text>
				<text>首页</text>
			</navigator>
			<navigator url="/pages/cart/cart" open-type="switchTab" class="p-b-btn">
				<text class="hxicon-cartfill"></text>
				<text>购物车</text>
			</navigator>
			<view class="p-b-btn" :class="{active: favorite}" @click="toFavorite">
				<text class="hxicon-likefill"></text>
				<text>收藏</text>
			</view>
			
			<view class="action-btn-group">
				<button type="primary" class=" action-btn no-border buy-now-btn" @click="buy">立即购买</button>
				<button type="primary" class=" action-btn no-border add-cart-btn">加入购物车</button>
			</view>
		</view> -->
		
		<!-- 购物车 -->
		<view class="foot"  @touchmove.stop.prevent="mpClear" :style="{height: footHeight}" v-if="showFoot">
			<view class="zz" @click="hideShoppingCar"></view>
			<view class="btn-box">
				<view class="btn-box-left" @click="contact">
					<view class="imgBox">
						<image src="../../static/store/contact.png" mode=""></image>
					</view>
					<text>联系商家</text>
				</view>
				<view class="btn-box-line"></view>
				<view class="btn-box-center" @click="showShoppingCar">
					<view class="cart" :animation="cartAnimationData">
						<view class="tag cartNum" v-if="goodsTotalNumber>0">{{goodsTotalNumber}}</view>
						<image :src="goodsTotalNumber ? '/static/store/cart.png' : '/static/store/cart2.png'" mode=""></image>
					</view>
					<view class="priceBox">
						<view class="hx-txt-18 hx-color-white" v-if="goodsTotalPrice>0">
							￥{{goodsTotalPrice}}
						</view>
						<view class="hx-txt-10 hx-color-gray">
							另需配送费￥{{shippingDees}}
						</view>
					</view>
				</view>
				<view class="btn-box-right">
					<view class="jiesuan"  v-if="goodsTotalPrice>0 && goodsTotalPrice >= startingPrice" @click="jiesuan">
						去结算
					</view>
					<view class="pscj hx-txt-10 hx-color-gray" v-else>
						<text v-if="startingPrice>0">差￥{{-(goodsTotalPrice-startingPrice)}}起送</text>
					</view>
					
				</view>
			</view>
			<view class="cart-box" :style="{display: showCar ? 'flex' : 'none'}">
				<view class="box-container rebate-box" v-if="showDiscount">
					<text>已享100减25</text>
				</view>
				<view class="box-container operating-box">
					<view class="operating-box_right">
						
					</view>
					<view class="operating-box_left clear" @click="clearShoppingCart">
						<i class="hxicon-delete"></i>
						<text>清空购物车</text>
					</view>
				</view>
				<view class=" goods-box">
					<view class="" style="flex: 1;">
						<scroll-view scroll-y="true" class="goods-list-scroll" :scroll-top="carGoodsScrollTop">
							<view class="goods-list">
								<view class="box" v-for="(rowData,i) in shoppCart" :key="i" v-if="rowData.number>0">
									
									<view class="m-store-item">
										<view class="m-img">
											<image style="width: 100%;height: 100%;" :src="rowData.img" mode="aspectFit"></image>
										</view>
										<view class="m-text">
											<view class="m-title">
												{{rowData.name}}
											</view>
											<view class="m-descripe">
												
												{{rowData.current_form ? rowData.form.name + "：" + rowData.current_form.name : rowData.descripe}}
												
											</view>
											<view class="m-price-box" >
												<view class="symbol">￥</view>
												<view class="m-price">{{rowData.price}}</view>
												<view class="m-old-price" v-if="rowData.oldprice">
													<text>￥{{rowData.oldprice}}</text>
												</view>
											</view>
											<view class="m-distance" > 
											<view :class="'addEle2_' + i" class="jumpPosition">
											</view>
												<hx-number-box @change="addGoodsChange" :value="rowData.number" :rowData="rowData" :clickTime="400" @addChange="touchOnAddGoods('.addEle2_' + i,rowData)"></hx-number-box>
												
											</view>
										</view>
										
									</view>
								</view>
							</view>
						</scroll-view>
					</view>
					
				</view>
			</view>
			
		</view>
		

		
		<!-- 多规格 -->
		<view class="goods-form" :animation="formAnimationData" @click="hiddenForm()" @touchmove.stop.prevent="mpClear">
			<view class="form-main" v-if="currentGoodsData.form">
				<view class="form-main_ctn" @click.stop.prevent="mpClear" >
					<view class="godos_tit"><text>{{currentGoodsData.name}}</text></view>
					
					<view class="gg_tit">
						<text>{{currentGoodsData.form.name}}</text>
					</view>
					<view class="gg_box">
						<block v-for="(form_child,j) in currentGoodsData.form_child" :key="j" v-if="form_child.pid == currentGoodsData.form.id">
							<view class="item" :class="{'active': form_child.select}" @click="selectGoodsForm(currentGoodsData,form_child)">{{form_child.name}}</view>
						</block>
					</view>
					
					<view class="select_gg">
						<text class="lable">已选规格：</text>
						<view class="select_gg_box">
							<block v-for="(form_child,j) in currentGoodsData.form_child" :key="j" v-if="form_child.select == true">
								<view class="gg-item">
									<text>{{form_child.name}} </text>
									<text class="gg-item-cut">,</text>
								</view>
							</block>
						</view>
					</view>
					
					<view class="bottom">
						<view class="price_box">
							<text>￥</text>
							<block v-for="(form_child,j) in currentGoodsData.form_child" :key="j" v-if="form_child.select == true">
								<text class="price">{{form_child.price}}</text>
							</block>
						</view>
						<view class="form-btn-box">
							<block v-if="!currentGoodsData.number">
								<view id="ggAddBtn" class="add-btn" @click="formFirstAddGoods()">
									<i class="hxicon-add" id="eleAdd"></i>
									<text>加入购物车</text>
								</view>
							</block>
							<block v-else>
								<view class="addEle_gg jumpPosition"></view>
								<hx-number-box @change="formAddGoodsChange" :value="currentGoodsData.number" :rowData="currentGoodsData" :clickTime="animaTime + 200" @addChange="touchOnAddGoods('.addEle_gg',currentGoodsData)"></hx-number-box>
							</block>
						</view>
						
					</view>
				</view>
				<view class="close" @click="hiddenForm()">
					<i class="hxicon-close"></i> 
				</view>
			</view>
			
		</view>
		
		<!-- 分享 -->
		<share 
			ref="share" 
			:contentHeight="580"
			:shareList="shareList"
		></share>
		
	</view>
</template>

<script>
	import share from '@/components/hx-share/hx-share';
	import hxNavbar from '@/components/hx-navbar/hx-navbar.vue';
	import jumpBall from '@/components/hx-jump-ball/hx-jump-ball.vue';
	import hxNumberBox from "@/components/uni-number-box/uni-number-box.vue";

	//引入测试数据
	import testData from "@/common/testdata.js";
	
	var statusBarHeight = uni.getSystemInfoSync().statusBarHeight;
	export default{
		components: {
			share,
			hxNumberBox,
			jumpBall,
			hxNavbar
		},
		data() {
			return {
				pageScroll: {},
				
				specClass: 'none',
				specSelected:[],
				
				//tabs
				tabs: [
					{name:'详情',id: 'detail'},
					{name:'评价',id: 'eva'},
					{name:'图文',id: 'coll'},
				],
				swiperCurrent: 0,
				swiperCurrentSliderLeft: 0,
				showRing: true,
				showTabs: true,
				showBorder: false,
				
				favorite: true,
				
				//小球动画
				num:1,
				element: [],
				animaTime: 300,
				cartAnimation: {},
				cartAnimationData: {},
				
				//购物车
				shoppingCartAll:[],
				//商家信息
				storeData: [],
				//显示购物车
				showFoot:true,
				//配送费
				shippingDees: 0,
				//配送起步价
				startingPrice:10,
				//购物车商品价格合计
				goodsTotalPrice: 0,
				//购物车商品数量合计
				goodsTotalNumber: 0,
				//购物车
				shoppCart: [],
				//foot 高度
				footHeight: '0',
				//显示购物车
				showCar: false,
				//购物车中商品滚动条位置
				carGoodsScrollTop: 0,
				//购物车缓存 Storage 名称
				shoppingCartStorageName: 'shopping_cart',
				//购物车显示折扣
				showDiscount:true,
				
				//商品信息
				goodsInfoData: {},
				//商品评价
				goodsEvaData: {},
				
				
			
				//多规格当前产品
				currentGoodsData: {},
				//显示规格容器
				showFormBox: false,
				//显示规格动画
				formAnimationData:{},
			
				
				//分享
				shareList: [{
					type: 1,
					icon: '/static/shop/share_wechat.png',
					text: '微信好友'
				},
				{
					type: 2,
					icon: '/static/shop/share_moment.png',
					text: '朋友圈'
				},
				{
					type: 3,
					icon: '/static/shop/share_qq.png',
					text: 'QQ好友'
				},
				{
					type: 4,
					icon: '/static/shop/share_qqzone.png',
					text: 'QQ空间'
				}],
				
				
				specList: [
					{id: 1, name: '尺寸',},
					{id: 2, name: '颜色',},
				],
				specChildList: [
					{id: 1, pid: 1, name: 'XS',},
					{id: 2, pid: 1, name: 'S',},
					{id: 3, pid: 1, name: 'M',},
					{id: 4,pid: 1,name: 'L',},
					{id: 5,pid: 1,name: 'XL',},
					{id: 6,pid: 1,name: 'XXL',},
					{id: 7,pid: 2,name: '白色',},
					{id: 8,pid: 2,name: '珊瑚粉',},
					{id: 9,pid: 2,name: '草木绿',},
				]
			};
		},
		async onLoad(options){
			let that = this;
			//接收传值,id里面放的是商品id
			let id = options.id;
			if(id){
				
			}
			//模拟请求数据
			uni.showLoading({
			    title: '加载中'
			});
			setTimeout(()=>{
				//商家信息
				that.storeData = testData.storeData
				//商品信息
				that.goodsInfoData = testData.goodsInfo
				that.currentGoodsData = testData.goodsInfo
				let cartGoods = that.getStoreCart()
				//普通商品直接可以显示数量
				if(!that.currentGoodsData.form){
					for(let i in cartGoods){
						if(cartGoods[i].id == that.currentGoodsData.id){
							that.currentGoodsData.number = cartGoods[i].number
							break
						}
					}
				}
				//商品评价
				that.goodsEvaData = testData.goodsEva
				setTimeout(()=>{
					that.init();
					uni.hideLoading();
				},500)
			},1000)
			
			
			//规格 默认选中第一条
			this.specList.forEach(item=>{
				for(let cItem of this.specChildList){
					if(cItem.pid === item.id){
						this.$set(cItem, 'selected', true);
						this.specSelected.push(cItem);
						break; //forEach不能使用break
					}
				}
			})
			
			this.cartAnimation = uni.createAnimation({
				duration: 800,
				timingFunction: "ease",
				delay: 0
			}); 
			
		
		},
		//监听返回
		onBackPress(e) {  
			// 这里可以自定义返回逻辑，比如下面跳转其他页面
			// return true 表示禁止默认返回
			
		}, 
		//导航栏滑动时的操作
		onPageScroll(e) {
			let top = e.scrollTop
			let that = this
			that.pageScroll =e
			let view = uni.createSelectorQuery().select(".shopping-cart");
			view.fields({
				rect: true
			}, data => {
				if(data.top - statusBarHeight<= 44){
					that.showBorder = true;
				}else{
					that.showBorder = false;
				}
			}).exec();
			if(top < 120){
				that.showRing = true;
			}else{
				that.showRing = false;
				this.navSearchBgOpacity = 1
				this.navSearchColor = '#888888'
			}
		},
		methods:{
			init(){
				let that = this;
				
				let carts = uni.getStorageSync(that.shoppingCartStorageName) || [];
				//根据缓存数据 获取购物车中属于本商店的商品
				for(let i in carts){
					if(carts[i].store_idd == that.storeDatastore_idid){
						that.shoppCart = carts[i].shopping_cart;
						break;
					}
				}
				
				for(let i in that.shoppCart){
					
					//计算商品总价
					that.goodsTotalPrice += that.shoppCart[i].total
					//商品总数量
					that.goodsTotalNumber += that.shoppCart[i].number
				}
				//初始化起步价和配送费
				that.starting_price = that.storeData.starting_price;
				that.shipping_dees = that.storeData.shipping_dees;
				
				that.setLabelNumber();
			},
			//返回
			backPage(){
				var pages = getCurrentPages().length;
				console.log(pages);
				if(pages == 1){
					// uni.switchTab({
					//     url: '/pages/index/index'
					// });
					uni.redirectTo({
					   url: '/pages/index/index'
					});
				}else{
					uni.navigateBack({
						animationType:"zoom-in"
					});
				}
			},
			//tab 事件
			swiperChange(index,idName) {
				this.swiperCurrent = index;
				this.swiperCurrentSliderLeft= 70 * index;
				
				//滚动至指定位置
				//最终位置 = 目标节点坐标 - 目标节点坐标 - 状态栏高度 - 导航栏高度44 - 价格栏高度
				let view = uni.createSelectorQuery().select("#productPage");
				view.boundingClientRect(data => {
					let view2 = uni.createSelectorQuery().select("#" + idName);
					view2.boundingClientRect(data2 => {
						let view3 = uni.createSelectorQuery().select(".shopping-cart");
						view3.fields({
							size: true
						}, res => {
							uni.pageScrollTo({
								duration:300,
								scrollTop:data2.top - data.top - statusBarHeight - 44 - res.height
							})
							
						}).exec();
						
						
					}).exec();
					
				}).exec();
			},
		
			mpClear(e) {
				// TODO nvue 取消冒泡
				e.stopPropagation()
			},
			//显示购物车
			showShoppingCar(){
				if(this.goodsTotalNumber == 0){
					return;
				}
				this.footHeight = '100%';
				this.showCar = true;
				this.carGoodsScrollTop = 0;
			},
			//隐藏购物车
			hideShoppingCar(){
				this.footHeight = '0';
				this.showCar = false;
			},
			//清空该门店的购物车
			clearShoppingCart(){
				let that = this;
				that.shoppCart = [];
				that.storeData.shopping_cart = [];
				for(let i in that.shoppingCartAll){
					if(that.shoppingCartAll[i].store_idd == that.storeDatastore_idid){
						that.shoppingCartAll[i] = that.storeData
					}
				}
				uni.setStorageSync(that.shoppingCartStorageName,that.shoppingCartAll);
				
				for(let i in that.goodsList){
					that.goodsList[i].number = 0;
				}
				for(let i in that.categoryList){
					that.categoryList[i].number = 0;
				}
				//购物车商品价格合计
				that.goodsTotalPrice = 0;
				//购物车商品数量合计
				that.goodsTotalNumber = 0;
				that.hideShoppingCar();
			},
			//增加商品后的回调事件
			touchOnAddGoods(ele,data){
				
				this.element = [ele,'.cart'];
				this.num ++; 
			},
			
			//新增商品
			storeAddGoods(number,rowData){
				var that = this;
				let shoppCart = [];
				let totalPrice = 0;
				let totalNumber = 0;
				let existedGoods = false;
				number = Number(number)
				
				let carts = uni.getStorageSync(that.shoppingCartStorageName) || [];
				
				//根据缓存数据 获取购物车中属于本商店的商品
				for(let i in carts){
					if(carts[i].store_id == that.storeData.store_id){
						shoppCart = carts[i].shopping_cart;
						break;
					}
				}
				
				//是否为有规格的商品
				let isFormGoods = false
				if(rowData.current_form){
					isFormGoods = true
				}
				let deleteGoods = null
				//检查该商品是否为第一次添加，
				for(var i in shoppCart){
					if(shoppCart[i].id == rowData.id){
						if(isFormGoods){
							//相同商品比较规格是否也相同
							if(shoppCart[i].current_form.id == rowData.current_form.id){
								existedGoods = true;
							}
						}else{
							existedGoods = true;
						}
						if(existedGoods){
							//在购物车中移除该商品
							if(number <= 0){
								deleteGoods = shoppCart[i];
								break;
							}
							if(isFormGoods){
								shoppCart[i].price = rowData.current_form.price
								shoppCart[i].oldprice = rowData.current_form.oldprice
								shoppCart[i].total = number *  rowData.current_form.price
							}else{
								shoppCart[i].price = rowData.price
								shoppCart[i].total = number *  rowData.price
								shoppCart[i].oldprice = rowData.oldprice
							}
							//非第一次添加，直接修改商品数量，并计算出单品合计
							
							shoppCart[i].number = rowData.number = number
							break;
						}
					}
				}
				//在购物车中移除该商品
				if(deleteGoods != null){
					
					if(!carts){
						return [];
					}
					//根据缓存数据 获取购物车中属于本商店的商品
					for(let i in carts){
						if(carts[i].store_id == that.storeData.store_id){
							var index = shoppCart.indexOf(deleteGoods);
						
							if (index > -1) { 
								shoppCart.splice(index, 1); 
							} 
							carts[i].shopping_cart = shoppCart
							
							//计算总商品数量和总价
							for(var j in shoppCart){
								//总价
								totalPrice += shoppCart[j].total
								totalNumber += shoppCart[j].number
							}
							//更改商品列表中的已购买数量
							for(let j in that.goodsList){
								if(that.goodsList[j].id == rowData.id){
									that.goodsList[j] = rowData
									break;
								}
							}
							break;
						}
					}
					
				}else{
					//第一次添加
					if(!existedGoods){
						if(rowData.form){
							rowData.price = rowData.current_form.price
							rowData.oldprice = rowData.current_form.oldprice
							rowData.total = number *  rowData.current_form.price
						}else{
							rowData.total = number *  rowData.price
						}
						rowData.number = number;
						shoppCart.push(rowData);
					}
					
					//计算总商品数量和总价
					for(var i in shoppCart){
						//总价
						totalPrice += shoppCart[i].total
						totalNumber += shoppCart[i].number
					}
					
					that.storeData.shopping_cart = shoppCart;
					//门店第一次添加商品
					let isFirstAddGoods = true;
					for(let i in carts){
						if(carts[i].store_id == that.storeData.store_id){
							carts[i] = that.storeData
							isFirstAddGoods = false
						}
					}
					if(isFirstAddGoods === true){
						carts.push(that.storeData)
					}
				}
				that.shoppCart = shoppCart
				that.shoppingCartAll = carts
				that.currentGoodsData.number = number
				//购物车商品数据缓存至本地
				uni.setStorageSync(that.shoppingCartStorageName,carts);
				return {
					shoppCart: shoppCart,
					totalPrice: totalPrice,
					totalNumber: totalNumber
				}
			},
			
			//新增商品计算价格
			addGoodsChange(number,dataArr){
				number = Number(number)
				var that = this;
				let rowData = {
					id: dataArr.id,
					type_id: dataArr.type_id,
					name: dataArr.name,
					descripe: dataArr.descripe,
					img: dataArr.img,
					price: dataArr.price,
					oldprice: dataArr.oldprice,
					number: number
				}
				if(dataArr.form){
					rowData.form = dataArr.form
					rowData.form_child = dataArr.form_child
					rowData.current_form =  dataArr.current_form
				}
				
				let res = that.storeAddGoods(number,rowData)
				console.log(res.totalPrice)
				if(res.totalPrice){
					if(that.goodsTotalPrice < res.totalPrice){
						//更具小球动画延时更改数据
						setTimeout(function(){
							that.goodsTotalPrice = res.totalPrice
							that.goodsTotalNumber =  res.totalNumber
						},that.animaTime + 150);
					}else{
						that.goodsTotalPrice = res.totalPrice
						that.goodsTotalNumber = res.totalNumber
					}
					if(that.goodsTotalNumber == 0){
						that.hideShoppingCar();
					}
					
				}
				
				return 
				let shoppCart = this.shoppCart;
				let totalPrice = 0;
				let totalNumber = 0;
				let existedGoods = false;
				
				
				// //点击添加购物车时生效
				// if(status){
				// 	that.element = ['#eleAdd','.cart'];
				// 	that.num ++; 
				// }
				//检查该商品是否为第一次添加，如果为第二次添加，则直接修改商品数量，并计算出合计
				for(var i in shoppCart){
					//非第一次添加
					if(shoppCart[i].id == rowData.id){
						//如果为加入购物车则为叠加
						if(status){
							number = shoppCart[i].number + number
						}
						shoppCart[i].number = rowData.number = number;
						shoppCart[i].total = number * shoppCart[i].price
						existedGoods = true;
						break;
					}
				}
				//第一次添加
				if(!existedGoods){
					rowData.number = number;
					rowData.total = number * rowData.price
					shoppCart.push(rowData);
				}
				
				//计算总商品数量和总价
				for(var i in shoppCart){
					//总价
					totalPrice += shoppCart[i].total
					totalNumber += shoppCart[i].number
					
				}
				//更改商品列表中的已购买数量
				for(let i in that.goodsList){
					if(that.goodsList[i].id == rowData.id){
						that.goodsList[i] = rowData
						break;
					}
				}
				
				that.shoppCart = shoppCart;
				if(that.goodsTotalPrice < totalPrice){
					//更具小球动画延时更改数据
					setTimeout(function(){
						that.goodsTotalPrice = totalPrice
						that.goodsTotalNumber = totalNumber
					},that.animaTime + 150);
				}else{
					that.goodsTotalPrice = totalPrice
					that.goodsTotalNumber = totalNumber
				}
				
				//门店第一次添加该商品
				let isFirstAddGoods = true;
				that.storeData.shopping_cart = that.shoppCart;
				
				for(let i in that.shoppingCartAll){
					if(that.shoppingCartAll[i].store_id == that.storeData.store_id){
						that.shoppingCartAll[i] = that.storeData
						isFirstAddGoods = false
					}
				}
				
				that.setLabelNumber();
				if(isFirstAddGoods === true){
					that.shoppingCartAll.push(that.storeData)
				}
				if(that.goodsTotalNumber == 0){
					that.hideShoppingCar();
				}
				
				//购物车商品数据缓存至本地
				uni.setStorageSync(that.shoppingCartStorageName,that.shoppingCartAll);
			},
			//计算每类商品购买的总数
			setLabelNumber(){
				let that = this;
				//计算每一类购买商品的总数量
				for(let j in that.categoryList){
					let n = 0;
					for(var i in that.shoppCart){
						if(that.shoppCart[i].type_id ==  that.categoryList[j].id){
							n += that.shoppCart[i].number;
						}
					}
					that.categoryList[j].number = n;
				}
			},
			
			//去结算
			jiesuan(){
				
				let that = this;
				uni.navigateTo({
					url:'/pages/order/preview'
				})
			},
			//联系商家
			contact(){
				uni.showModal({
					title:"",
					content:"联系商家"
				})
			},
			//小球回调
			jbMsg(res){
				let that = this;
				
				
				that.cartAnimation.matrix(1, 0, 0, 0.7, 0, 7).step({duration: 100})
				that.cartAnimation.matrix(1, 0, 0, 1, 0, 0).step({duration: 150});
				that.cartAnimationData = that.cartAnimation.export();
				// setTimeout(()=>{
				// 	that.cartAnimation.matrix(1, 0, 0, 0.7, 0, 7).step({duration: 100})
				// 	that.cartAnimation.matrix(1, 0, 0, 1, 0, 0).step({duration: 150});
				// 	that.cartAnimationData = that.cartAnimation.export();
				// },50)
				
			},
			
			//预览图片
			previewImg(img,imgList){
				
				// 预览图片
				uni.previewImage({
					current: img,
					urls: imgList,
					indicator: "default",
					// longPressActions: {
					// 	itemList: ['发送给朋友', '保存图片', '收藏'],
					// 	success: function(data) {
					// 		//console.log('选中了第' + (data.tapIndex + 1) + '个按钮,第' + (data.index + 1) + '张图片');
					// 	},
					// 	fail: function(err) {
					// 		//console.log(err.errMsg);
					// 	}
					// }
				});
			},
			
			//-----------------------------------------------------------------------------------
			//显示 规格
			showForm(goods){
				var that = this;
				let goodsCart = that.getStoreCart();
			
				if(goodsCart){
					let currentGoods = null
					for(let i in goodsCart){
						if(goodsCart[i].id == goods.id){
							currentGoods = goodsCart[i]
							break
						}
					}
					if(currentGoods){
						let selectStatus = false
						for (let i in goods.form_child){
							if(goods.form_child[i].id == currentGoods.current_form.id){
								if(!selectStatus){
									goods.form_child[i].select = true
									goods.number = currentGoods.number
									selectStatus = true
								}else{
									goods.form_child[i].select = false
								}
							}else{
								goods.form_child[i].select = false
							}
						}
					}
					
				}
				var animation = uni.createAnimation({timingFunction: 'ease'})
				
				that.currentGoodsData = goods
				this.animation = animation
				animation.top(0).bottom(0).left(0).right(0).step({ duration: 0 })
				animation.opacity(1).step({ duration: 300 })
				this.formAnimationData = animation.export()
				that.showFormBox =true;
			},
			//隐藏规格
			hiddenForm(){
				var animation = uni.createAnimation({
				  //duration: 1000,
					timingFunction: 'ease',
				})
				
				this.animation = animation
				animation.scale(0,0).opacity(0).step({ duration: 300 })
				animation.top(0).bottom(0).left("-100%").right("100%").step({ duration: 0 })
				this.formAnimationData = animation.export()
				var that = this;
				that.showFormBox =false;
			},
			formFirstAddGoods(){
				let that = this
				that.formAddGoodsChange(1,that.currentGoodsData)
				that.touchOnAddGoods('#eleAdd',that.currentGoodsData)
			},
			//加入购物车
			formAddGoodsChange(number,goodsData){
				let that = this;
				if(number == 1){
					for(let i in goodsData.form_child){
						if(goodsData.form_child[i].select == true ){
							that.currentGoodsData.current_form = goodsData.form_child[i]
							break
						}
					}
				}
				that.addGoodsChange(number,that.currentGoodsData)
				
				
			},
			//选择规格
			selectGoodsForm(goods,formChild){
				let that = this
				let data = goods.form_child
				let n = 0
				for (var i in data){
					if(data[i].id == formChild.id){
						data[i].select = true
					}else{
						data[i].select = false
					}
				}
				let goodsCart = that.getStoreCart();	
				if(goodsCart){
					for(let i in goodsCart){
						if(goodsCart[i].id == goods.id && goodsCart[i].current_form.id == formChild.id){
							n = goodsCart[i].number
							break;
						}
					}
				}
				
				goods.number = n
				this.currentGoodsData = goods
				
			},
			
			//获取该商品在购物车的数量
			getCartGoodsNum(goods){
				let cart = this.getStoreCart()
				let n = 0
				if(cart){
					for(let i in cart){
						if(cart[i].id == goods.id){
							n += cart[i].number
						}
					}
				}
				return n
			},
			//-----------------------------------------------------------------------------------
			//获取门店购物车
			getStoreCart(){
				let that = this
				let shoppCart = {}
				let carts = uni.getStorageSync(that.shoppingCartStorageName) || [];
				
				if(carts){
					//根据缓存数据 获取购物车中属于本商店的商品
					for(let i in carts){
						if(carts[i].store_id == that.storeData.store_id){
							shoppCart = carts[i].shopping_cart;
							break;
						}
					}
				}else{
					that.storeData.shopping_cart = []
					uni.setStorageSync(that.shoppingCartStorageName,that.storeData);
				}
				return shoppCart
			},
			
			
			
			//分享
			share(){
				this.$refs.share.toggleMask();	
			},
			//收藏
			toFavorite(){
				this.favorite = !this.favorite;
			},
			buy(){
				uni.navigateTo({
					url: `/pages/shop/order/createOrder`
				})
			},
			stopPrevent(){}
		},

	}
</script>

<style lang='scss'>
	//主题颜色
	$hx-theme-color: #FFC107;
	$hx-theme-color-light: #FFEB3B;
	page{
		background: #ffffff;
		padding-bottom: 160upx;
	}
	/*边框*/
	.b-b:after,
	.b-t:after {
		position: absolute;
		z-index: 3;
		left: 0;
		right: 0;
		height: 0;
		content: '';
		transform: scaleY(.5);
		border-bottom: 1px solid #E4E7ED;
	}
	
	.b-b:after {
		bottom: 0;
	}
	
	.b-t:after {
		top: 0;
	}
	.hx-txt-10{
		font-size: 10px;
	}
	.hx-txt-12{
		font-size: 12px;
	}
	.hx-txt-14{
		font-size: 14px;
	}
	.hx-txt-16{
		font-size: 16px;
	}
	.hx-txt-18{
		font-size: 18px;
	}
	.hx-txt-22{
		font-size: 22px;
	}
	.hx-color-gray{
		color: #bbbbbb;
	}
	.hx-color-white{
		color: #FFFFFF;
	}
	.hx-color-black{
		color: #333333;
	}
	.hx-txt-weigth{
		font-weight: bold;
	}
	.hx-mb-10{
		margin-bottom: 10px;
	}
	.hx-mt-15{
		margin-top: 15px;
	}
	.hx-shadow{
		box-shadow: 0px 6upx 16upx rgba(173, 173, 173, 0.2);
	}
	.ring{
		background-color: rgba(0, 0, 0, 0.3);
		width: 28px;
		height: 28px;
		border-radius: 100%;
		display: flex;
		align-items: center;
		justify-content: center;
		font-size: 16px;
	}
	.ctn{
		
		/* border: 1px solid #e3e3e3; */
		width: 100%;
		display: flex;
		justify-content: flex-end;
		overflow: hidden;
		align-items: center;
		.leftBox{
			display: flex;
			width: 60px;
			justify-content: space-between;
			flex: none;
			margin: 0 8px;
			align-items: center;
		}
		.jrNull{
			/* #ifdef MP */
			width: 95px;
			flex: none;
			/* #endif */
		}
	}
	.barBorder{
		box-shadow: 0 1px 6px #ccc;
		border-top: 1px solid #f1f1f1;
	}
	.icon-right{
		font-size: 16px;
		color: #888;
	}
	.carousel {
		height: 722upx;
		position:relative;
		swiper{
			height: 100%;
		}
		.image-wrapper{
			width: 100%;
			height: 100%;
		}
		.swiper-item {
			display: flex;
			justify-content: center;
			align-content: center;
			height: 750upx;
			overflow: hidden;
			image {
				width: 100%;
				height: 100%;
			}
		}
		
	}
	.line-weight{
		background-color: #efefef;
		height: 10px;
	}
	.container-box{
		background: #fff;
		padding: 10px 15px;
	}
	/* 标题简介 */
	.introduce-section{
		padding-bottom: 0;
		.title{
			font-size: 32upx;
			color: #303133;
			height: 50upx;
			line-height: 50upx;
			font-weight: bold;
		}
		.tag-box{
			display: flex;
			align-items:baseline;
			margin: 6px 0 4px;
			flex-wrap: wrap;
			.tag-item{
				align-items: center;
				padding: 2px 5px;
				background: #efefef;
				margin-bottom: 8px;
				font-size: 10px;
				margin-right: 10px;
				border-radius: 3px;
				color: #555555;
			}
		}
		.bot-row{
			display:flex;
			align-items:center;
			height: 20px;
			font-size: 12px;
			color: #909399;
			text{
				flex: 1;
			}
		}
	}
	/* 价格 */
	.shopping-cart{
		
		position: sticky;
		top: calc(44px + var(--status-bar-height));
		padding-top: 10px;
		display: flex;
		justify-content: space-between;
		background: #ffffff;
		
		z-index: 10;
		.price-box{
			display:flex;
			align-items:baseline;
			font-size: 12px;
			color:#ff582b;
			flex: 1;
			.price{
				font-size: 20px;
			}
			.m-price{
				margin:0 6px;
				color: #909399;
				text-decoration: line-through;
				font-size: 12px;
			}
			.coupon-tip{
				align-items: center;
				padding: 4upx 10upx;
				background: #FF5722;
				font-size: 12px;
				color: #fff;
				border-radius: 3px;
				line-height: 1;
				transform: translateY(-4upx); 
			}
		}
		.add-btn{
			position: relative;
			border-radius: 50px;
			background-color: #ffce3c; 
			padding: 3px 10px;
			display: flex;
			height: 30px;
			align-items: center;
			i{
				font-weight: bold;
				margin-left: -4px;
				margin-right: 3px;
			}
			text{
				
				font-size: 14px;
				font-weight: bold;
				color: #363636;
			}
			.jumpPosition{
				position: absolute;
				right: 6px;
				top: 11px;
			}
			.num{
				position: absolute;
				right: 0;
				top: -10px;
				width: 18px;
				height: 18px;
				line-height: 18px;
				font-size: 10px;
				color: #fff;
				background-color: #ff5722;
				text-align: center;
				border-radius: 50%;
				
			}
		}
		.btn-box{
			position: relative;
			border-radius: 50px;
			padding: 3px 10px;
			display: flex;
			height: 30px;
			align-items: center;
			.jumpPosition{
				position: absolute;
				right: 16px;
				top: 11px;
			}
		}
		
	}
	
	/* 分享 */
	.share-section{
		display:flex;
		align-items:center;
		color: #606266;
		background: linear-gradient(to right, #fff8c3, #ffcc33);
		padding: 12upx 30upx;
		.share-icon{
			display:flex;
			align-items: center;
			width: 38px;
			height: 16px;
			line-height: 1;
			border: 1px solid #FF9800;
			border-radius: 2px;
			position: relative;
			overflow: hidden;
			font-size: 12px;
			color: #FF5722;
			&:after{
				content: '';
				width: 50upx;
				height: 50upx;
				border-radius: 50%;
				left: -20upx;
				top: -12upx;
				position:absolute;
				background: #FF9800;
			}
		}
		.icon-favor_fill_light{
			position:relative;
			z-index: 1;
			font-size: 24upx;
			margin-left: 2upx;
			margin-right: 10upx;
			color: #fff;
			line-height: 1;
		}
		.tit{
			font-size: 14px;
			margin-left:10upx;
		}
		.icon-mark{
			padding: 10upx;
			font-size: 30upx;
			line-height: 1;
		}
		.share-btn{
			flex: 1;
			text-align: right;
			font-size: 12px;
			color: #FF5722;
		}
		.icon-right{
			font-size: 12px;
			margin-left: 4upx;
			color: #fa436a;
			
		}
	}
	
	.tabs-box{
		width: 100%;
		z-index: 10;
		background-color: white;
		height: 40px;
		.hx-tabs{
			position: relative;
			display: flex;
			height:100%;
			&-item{
				display: flex;
				flex-direction: row;
				justify-content: left;
				align-items: center;
				width: 70px;
				color:#666666;
				text{
					font-size: 16px;
				}
				
			}
			&-active{
				color:#333333;
				font-weight: bold;
			}
			&-slider-box{
				position: absolute;
				display: flex;
				justify-content: left;
				bottom: 0;
				width: 70px;
			}
			&-slider{
				display: flex;
				background: #FFC107;
				width: 20px;
				height: 2px;
				margin-left: 6px;
			}
		}
	}
	/* 详情 */
	.info-box{
		display: flex;
		flex-direction: column;
		padding-top: 12px;
		.info-item{
			display: flex;
			flex-direction: row;
			align-items: flex-start;
			font-size: 14px;
			margin-bottom: 6px;
			.left{
				color: #909399;
				width: 84px;
			}
			.right{
				color: #000000;
				flex: 1;
			}
		}
	}
	
	.c-list{
		font-size: 13px;
		color: #606266;
		background: #fff;
		.c-row{
			display:flex;
			align-items:center;
			padding: 20upx 30upx;
			position:relative;
		}
		.tit{
			width: 140upx;
		}
		.con{
			flex: 1;
			color: #303133;
			.selected-text{
				margin-right: 10upx;
			}
		}
		.bz-list{
			height: 40upx;
			font-size: 13px;
			color: #303133;
			text{
				display: inline-block;
				margin-right: 30upx;
			}
		}
		.con-list{
			flex: 1;
			display:flex;
			flex-direction: column;
			color: #303133;
			line-height: 40upx; 
		}
		.red{
			color: #fa436a;
		}
	}
	
	/* 评价 */
	.eva-section{
		display: flex;
		flex-direction: column;
		
		.e-header{
			display: flex;
			align-items: center;
			height: 70upx;
			font-size: 13px ;
			color: #909399;
			.tit{
				font-size: 16px;
				color: #303133;
				margin-right: 6px;
				font-weight: bold;
			}
			.hpd{
				font-size: 12px;
				transform: translateY(2px);
			}
			.tip{
				flex: 1;
				text-align: right;
			}
			.icon-right{
				margin-left: 10upx;
			}
			
		}
		.eva-tag-box{
			display: flex;
			align-items:baseline;
			margin: 6px 0 6px;
			flex-wrap: wrap;
			.tag-item{
				align-items: center;
				padding: 4px 8px;
				background: #fae196;
				font-size: 10px;
				margin-right: 10px;
				margin-bottom: 10px;
				border-radius: 3px;
				color: #555555;
				display: flex;
				flex-direction: row;
				i{
					color: #F0AD4E;
					font-size: 12px;
				}
				.tread{
					color: #909399
				}
				
				.num{
					margin-left: 6px;
				}
			}
			.tread-bg{
				background-color: #eeeeee !important;
			}
		}
		.eva-btn{
			margin:16px 0 6px;
			background: #f6f6f6;
			border-radius: 4px;
			display: flex;
			height: 36px;
			justify-content: center;
			align-items: center;
			flex-direction: row;
			font-size: 14px;
			text{
				margin-left: 10px;
				margin-right: 6px;
			}
			.avatar-box{
				display: flex;
				flex-direction: row;
				.avatar-item{
					width: 20px;
					height: 20px;
					overflow: hidden;
					border-radius: 100%;
					background-color:#cfcfcf;
					border: 0.5px solid #cccccc;
					display: flex;
					justify-content: center;
					align-items: center;
					margin-left: -5px;
					z-index: 1;
					image{
						width: 20px;
						height: 20px;
						
					}
					i{
						color: #ffffff;
					}
				}
			}
		}
	}
	.eva-box{
		display: flex;
		padding: 10px 0;
		border-bottom: 1px solid #f5f5f5;
		.portrait{
			flex-shrink: 0;
			width: 80upx;
			height: 80upx;
			border-radius: 100px;
		}
		.right{
			flex: 1;
			display: flex;
			flex-direction: column;
			font-size: 14px;
			color: #606266;
			padding-left: 26upx;
			.con{
				font-size: 14px;
				color: #303133;
				padding: 10px 0 6px;
			}
			.bot{
				display: flex;
				justify-content: space-between;
				font-size: 12px;
				color:#909399;
			}
			.praise-box{
				margin-top: 4px;
				display: flex;
				flex-direction: row;
				[class*="hxicon-"]{
					color: #F0AD4E;
					margin-right: 6px;
				}
				.tread{
					color: #909399;
				}
				
				.praise-txt{
					color:#909399;
					font-size: 12px;
				}
			}
			.imgs {
				display: flex;
				flex-wrap: wrap;
				padding-top: 4px;
				.imgs-box {
					width: 25%;
					padding-right: 5px;
					box-sizing: border-box;
					image{
						border-radius: 4px;
					}
				}
			}
		}
	}
	/*  详情 */
	.detail-desc{
		background: #fff;
		margin-top: 16upx;
		.d-header{
			display: flex;
			justify-content: center;
			align-items: center;
			height: 80upx;
			font-size: 15px;
			color: #303133;
			position: relative;
				
			text{
				padding: 0 20upx;
				background: #fff;
				position: relative;
				z-index: 1;
			}
			&:after{
				position: absolute;
				left: 50%;
				top: 50%;
				transform: translateX(-50%);
				width: 300upx;
				height: 0;
				content: '';
				border-bottom: 1px solid #ccc; 
			}
		}
	}
	
	
	
	/*  弹出层 */
	.popup {
		position: fixed;
		left: 0;
		top: 0;
		right: 0;
		bottom: 0;
		z-index: 99;
		
		&.show {
			display: block;
			.mask{
				animation: showPopup 0.2s linear both;
			}
			.layer {
				animation: showLayer 0.2s linear both;
			}
		}
		&.hide {
			.mask{
				animation: hidePopup 0.2s linear both;
			}
			.layer {
				animation: hideLayer 0.2s linear both;
			}
		}
		&.none {
			display: none;
		}
		.mask{
			position: fixed;
			top: 0;
			width: 100%;
			height: 100%;
			z-index: 1;
			background-color: rgba(0, 0, 0, 0.4);
		}
		.layer {
			position: fixed;
			z-index: 99;
			bottom: 0;
			width: 100%;
			min-height: 40vh;
			border-radius: 10upx 10upx 0 0;
			background-color: #fff;
			.btn{
				height: 66upx;
				line-height: 66upx;
				border-radius: 100upx;
				background: #fa436a;
				font-size: 15px;
				color: #fff;
				margin: 30upx auto 20upx;
			}
		}
		@keyframes showPopup {
			0% {
				opacity: 0;
			}
			100% {
				opacity: 1;
			}
		}
		@keyframes hidePopup {
			0% {
				opacity: 1;
			}
			100% {
				opacity: 0;
			}
		}
		@keyframes showLayer {
			0% {
				transform: translateY(120%);
			}
			100% {
				transform: translateY(0%);
			}
		}
		@keyframes hideLayer {
			0% {
				transform: translateY(0);
			}
			100% {
				transform: translateY(120%);
			}
		}
	}
	
	/* 底部操作菜单 */
	.page-bottom{
		position:fixed;
		left: 30upx;
		bottom:30upx;
		z-index: 95;
		display: flex;
		justify-content: center;
		align-items: center;
		width: 690upx;
		height: 100upx;
		background: rgba(255,255,255,.9);
		box-shadow: 0 0 20upx 0 rgba(0,0,0,.5);
		border-radius: 16upx;
		
		.p-b-btn{
			display:flex;
			flex-direction: column;
			align-items: center;
			justify-content: center;
			font-size: 12px;
			color: #606266;
			width: 96upx;
			height: 80upx;
			[class*="hxicon-"]{
				font-size: 40upx;
				line-height: 48upx;
				color: #909399;
			}
			&.active, &.active [class*="hxicon-"]{
				color: #fa436a;
			}
			.icon-fenxiang2{
				font-size: 42upx;
				transform: translateY(-2upx);
			}
			.icon-likefill{
				font-size: 46upx;
			}
		}
		.action-btn-group{
			display: flex;
			height: 76upx;
			border-radius: 100px;
			overflow: hidden;
			box-shadow: 0 20upx 40upx -16upx #fa436a;
			box-shadow: 1px 2px 5px rgba(219, 63, 96, 0.4);
			background: linear-gradient(to right, #ffac30,#fa436a,#F56C6C);
			margin-left: 20upx;
			position:relative;
			&:after{
				content: '';
				position:absolute;
				top: 50%;
				right: 50%;
				transform: translateY(-50%);
				height: 28upx;
				width: 0;
				border-right: 1px solid rgba(255,255,255,.5);
			}
			.action-btn{
				display:flex;
				align-items: center;
				justify-content: center;
				width: 180upx;
				height: 100%;
				font-size: 14px ;
				padding: 0;
				border-radius: 0;
				background: transparent;
			}
		}
	}
	
	/* foot */
	.foot{
		position: fixed;
		display: flex;
		z-index: 12;
		
		justify-content:center;
		align-items : center; 
		bottom: 0;
		height: 100%;
		width: 100%;
		
		/*width: calc(100% - 32px);*/
		.btn-box{
			position:absolute;
			display: flex;
			bottom: 6px;
			justify-content:center;
			align-items : center; 
			margin:0;
			height: 45px;
			width: calc(100% - 32px);
			z-index: 9;
			&-left{
				background: #222222;
				border-top-left-radius:50px;
				border-top-right-radius:9px;
				border-bottom-left-radius:50px;
				border-bottom-right-radius:9px;
				padding-left: 6upx;
				display: flex;
				flex-direction:column;
				justify-content:center;
				align-self: center;
				width: 70px;
				height: 100%;
				color: #f6d200;
				text-align: center;
				.imgBox{
					display: flex;
					text-align: center;
					justify-content:center;
					image{
						width: 20px;
						height: 20px;
					}
				}
				text{
					font-size: 20upx;
				}
			}
			&-line{
				background: #ffffff;
				width: 2px;
				height: 100%;
			}
			&-center{
				height: 100%;
				flex: auto;
				display: flex;
				justify-content:flex-start;
				align-self: center;
				align-items: center;
				background: #222222;
				border-top-left-radius:8upx;
				border-bottom-left-radius:8upx;
				padding-left: 10upx;
				.cart{
					position: relative;
					width: 36px;
					height: 36px;
					image{
						width: 100%;
						height: 100%;
					}
					.tag{
						position: absolute;
						right: 12upx;
						top: 16upx;
						height: 18px;
						width: 18px;
						background-color: #ff4000;
						color: #ffffff;
						border-radius: 50%;
						z-index: 1;
						font-size: 10px;
						text-align: center;
						line-height: 18px;
					}
				}
				.priceBox{
					flex: auto;
				}
				
			}
			&-right{
				width: 70px;
				height: 100%;
				position: relative;
				display: flex;
				justify-content:flex-start;
				align-self: center;
				align-items: center;
				
				
				.pscj{
					width: 100%;
					height: 100%;
					border-top-right-radius:100upx;
					border-bottom-right-radius:100upx;
					background: #222222;
					text-align: center;
					display: flex;
					justify-content:center;
					align-self: center;
					align-items: center;  
				}
				.jiesuan{
					width: 100%;
					height: 100%;
					font-size: 28upx;
					border-top-right-radius:100upx;
					border-bottom-right-radius:100upx;
					text-align: center;
					display: flex;
					justify-content:center;
					align-self: center;
					align-items: center;    
					background: linear-gradient(45deg, $hx-theme-color-light, $hx-theme-color); 
					font-weight: bold;
					color: #222222;
				}
			}
		}
		.zz{
			position: absolute;
			top: 0;
			left: 0;
			right: 0;
			bottom: 0;
			background-color: rgba(0,0,0,.7);
			z-index: 1;
		}
		.cart-box{
			display: flex;
			justify-content: flex-start;
			flex-flow: column;
			background: #ffffff;
			position: absolute;
			bottom: 0; 
			z-index: 2;
			max-height: 66%;
			
			padding-bottom: 62px;
			border-top-left-radius: 16px;
			border-top-right-radius: 16px;
			overflow: hidden;
			width: 100%;
			.rebate-box{
				height: 30px;
				background: #FFC107;
				color: #FF9900;
				text-align: center;
				line-height: 30px;
				font-size: 14px;
			}
			.box-container{
				box-sizing: border-box;
				padding:0 16px;
			}
			.operating-box{
				font-size: 12px;
				line-height: 40px;
				height: 40px;
				border-bottom: 1px solid #f6f6f6;
				color: #666666;
				display: flex;
				flex-direction: row;
				&_right{
					flex: 1;
				}
				&_left{
					display: flex;
					flex-direction: row;
				}
				
			}
			.goods-box{
				height: 100%;
			    overflow: hidden;
				flex: 1;
				display: flex;
				.goods-list-scroll{
					height: 100%;
					
					.goods-list{
						
						width: 100%;
						display: flex;
						flex-wrap: wrap;
						
						.box{
							width: 100%;
							border-bottom: 1px solid #f6f6f6;
							box-sizing: border-box;
							padding: 0 16px;
							.m-store-item{
								display: flex;
								flex-direction: row;
								width: 100%;
								justify-content: space-between;
								align-items: flex-end;
								padding-top: 15px;
								padding-bottom: 15px;
								.m-img{
									flex: 0 0 85px;
									height: 85px;
									background: #eee;
									border-radius: 4px;
								}
								.m-text{
									flex: 1;
									position: relative;
									height: 85px;
									padding: 0 6px;
									display: flex;
									align-content: space-between;
									flex-direction: column;
									.m-title{
										font-size: 16px;
										color:#555555;
										height: 21px;
										font-weight: bold;
									}
									.m-descripe{
										font-size: 12px;
										color:#999999;
										margin-top: 5px;
										height: 35px;
									}
									.m-price-box{
										height: 24px;
										font-weight: bold;
										display: flex;
										flex-direction: row;
										align-items: flex-end;
										.symbol{
											color:#ff582b;
											font-size: 12px;
										}
										.m-price{
											position: relative;
											top: 2px;
											font-size: 18px;
											color:#ff582b;
										}
										.m-old-price{
											margin-left: 3px;
											display: flex;
											flex-direction: row;
											font-size: 10px;
											color:#999999;
											margin-top: 5upx;
											text-decoration: line-through;
											font-weight: normal;
										}
									}
									.m-distance{
										position: absolute;
										right: 0;
										bottom: -4px;
										z-index: 16;
										color:#b2b2b2;
										font-size: 20upx;
										text-align: right;
										.jumpPosition{
											position: absolute; 
											bottom: 23px;
											right: 0;
											z-index: 2;
											width: 28px;
											height: 28px;
										}
									}
									
								}
								
							}
						}
					}
				}
			}
		}
	}
	/*多规格*/
	.goods-form{
		opacity: 0;
		background-color: rgba(0,0,0,.6);
		position: fixed;
		top: 0;
		right: 100%;
		left: -100%;
		bottom: 0;
		
		z-index: 1000;
		display: flex;
		//flex-direction: column;
		align-items: center;
		
		.form-main{
			position: absolute;
			display: flex;
			flex-direction: column;
			justify-content: left;
			left: 24px;
			right: 24px;
			
			background-color: #fff;
			border-radius: 8px;
			.form-main_ctn{
				display: flex;
				flex-direction: column;
				justify-content: left;
				padding: 8px 12px 12px 12px;
				.godos_tit{
					margin-top: 4px;
					margin-bottom: 6px;
					font-size: 18px;
					font-weight: bold;
					color: #333;
				}
				.gg_tit{
					margin-top: 8px;
					font-size: 14px;
					font-weight: bold;
					color: #555;
				}
				.gg_box{
					margin-top: 8px;
					display: flex;
					flex-direction: row;
					flex-wrap: wrap;
					font-size: 12;
					color: #333;
					.item{
						margin-right: 14px;
						margin-bottom: 14px;
						border: 1px solid #f1f1f1;
						border-radius: 4px;
						padding: 4px 6px;
					}
					.item.active{
						border-color: #ffe081;
						background-color: #fff0b7;
					}
					
				}
				.select_gg{
					margin: 26px -12px 0;
					padding: 6px 12px;
					color: #999;
					background-color: #f9f9f9;
					display: flex;
					flex-direction: row;
					.lable{
						
					}
					.select_gg_box{
						flex: 1;
						display: flex;
						flex-direction: row;
						flex-wrap: wrap;
						.gg-item{
							color: #333;
							.gg-item-cut{
								margin-left: 3px;
								margin-right: 3px;
							}
						}
						.gg-item:last-child{
							.gg-item-cut{
								display: none;
							}
						}
					}
					
				}
				.bottom{
					position: relative;
					display: flex;
					flex-direction: row;
					margin-top: 12px;
					.price_box{
						flex: 1;
						lign-items: baseline;
						color: #ff582b;
						font-size: 14px;
						position: relative;
						top: 4px;
						.price{
							font-size:  24px;
						}
					}
					.jumpPosition{
						position: absolute;
						right: 16px;
						top: 2px;
					}
				}
				
			}
			
			
		}
		.close{
			position: absolute;
			left: 50%;
			margin-left: -20px;
			bottom: -70px;
			border-radius: 50%;
			height: 40px;
			width: 40px;
			background-color: #fff;
			opacity: 0.7;
			text-align: center;
			line-height: 43px;
			i{
				font-weight: bold;
				font-size: 22px;
			}
		}
		.form-btn-box{
			height: 30px;
			line-height: 30px;
			.add-btn{
				border-radius: 50px;
				background-color: #ffce3c; 
				padding: 0 12px;
				display: flex;
				height: 30px;
				line-height: 30px;
				align-items: center;
				i{
					font-weight: bold;
					font-size: 18px;
					margin-right: 3px;
					margin-left: -4px;
				}
				text{
					
					font-size: 14px;
					font-weight: bold;
					color: #363636;
				}
			}
		}
		
	}
</style>
