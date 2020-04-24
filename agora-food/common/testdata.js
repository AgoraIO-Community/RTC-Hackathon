//商家信息
const storeData = {
		//商家唯一标识
		store_id: '168',
		//商家名称
		store_name: '小太阳商店',
		//头像
		avatar: '//imgs.1op.cn/i/hxshop/avatar/3.png',
		//横幅图片
		banner: '//imgs.1op.cn/i/hxshop/banner/banner.jpg',
		//商家住址
		address: '新疆阿克苏是他北路2号',
		//地标或社区
		community: '天府名城',
		//配送时间
		delivery_time: '11:00~20:30',
		//联系电话
		telephone: '18299989916',
		//商家购物车
		shopping_cart: [],
		//配送费
		shipping_dees: 0,
		//配送起步价
		starting_price:30
	};

//首页门店列表
const storeList = [
	{
		//商户标识
		store_id: '852',
		//店名
		name: '田东日式料理',
		//门头
		avatar: '//imgs.1op.cn/i/hxshop/goods/14.jpg',
		//小区
		community: '秦阳店',
		//评分
		mark: '4.8',
		//月售
		monthly_sales: 356,
		//门店距离，按米计算
		distance: 500,
		//起送价
		starting_price: 3,
		//配送费
		shipping_dees: 15,
		//商品列表
		goods:[{
				//id
				id: '235',
				//商品名称
				name: '画画酱酱面',
				//主图
				main_pic: '//imgs.1op.cn/i/hxshop/goods/10.jpg',
				//标签 【招牌，爆款，推荐】等等
				tag: '招牌',
				//现价
				price: 45,
				//原价
				old_price: 65,
			}
			,{id: '236', name: '白色米', main_pic: '//imgs.1op.cn/i/hxshop/goods/6.jpg', tag: '招牌', price: 45, old_price: 65,}
			,{id: '237', name: '小羊肉', main_pic: '//imgs.1op.cn/i/hxshop/goods/2.jpg', tag: '爆款', price: 45, old_price: 65,}
			,{id: '238', name: '烤鸡翅', main_pic: '//imgs.1op.cn/i/hxshop/goods/3.jpg', tag: '爆款', price: 45, old_price: 65,}
			,{id: '239', name: '爆爆鱼', main_pic: '//imgs.1op.cn/i/hxshop/goods/4.jpg', tag: '推荐', price: 45, old_price: 65,}
			,{id: '240', name: '生吃肉票', main_pic: '//imgs.1op.cn/i/hxshop/goods/5.jpg', tag: '推荐', price: 45, old_price: 65,}
			
		]
		
	}
	,{
		store_id: '853',
		name: '十里飘香烧烤',
		avatar: '//imgs.1op.cn/i/hxshop/goods/2.jpg',
		community: '',
		mark: '4.8',
		monthly_sales: 356,
		distance: 500,
		starting_price: 3,
		shipping_dees: 15,
		goods:[
			{id: '236', name: '可乐鸡翅buibui爽', main_pic: '//imgs.1op.cn/i/hxshop/goods/7.jpg', tag: '招牌', price: 45, old_price: 65,}
			,{id: '237', name: '百富汉堡', main_pic: '//imgs.1op.cn/i/hxshop/goods/8.jpg', tag: '爆款', price: 45, old_price: 65,}
			,{id: '238', name: '百富汉堡', main_pic: '//imgs.1op.cn/i/hxshop/goods/9.jpg', tag: '爆款', price: 45, old_price: 65,}
			,{id: '239', name: '百富汉堡', main_pic: '//imgs.1op.cn/i/hxshop/goods/10.jpg', tag: '推荐', price: 45, old_price: 65,}
			,{id: '240', name: '百富汉堡', main_pic: '//imgs.1op.cn/i/hxshop/goods/11.jpg', tag: '推荐', price: 45, old_price: 65,}
		]
		
	}
	,{
		store_id: '853',
		name: '德克士',
		avatar: '//imgs.1op.cn/i/hxshop/goods/3.jpg',
		community: '天府名城',
		mark: '4.8',
		monthly_sales: 356,
		distance: 500,
		starting_price: 3,
		shipping_dees: 15,
		goods:[
			{id: '236', name: '可乐鸡翅buibui爽', main_pic: '//imgs.1op.cn/i/hxshop/goods/12.jpg', tag: '招牌', price: 45, old_price: 65,}
			,{id: '237', name: '百富汉堡', main_pic: '//imgs.1op.cn/i/hxshop/goods/13.jpg', tag: '爆款', price: 45, old_price: 65,}
			,{id: '238', name: '百富汉堡', main_pic: '//imgs.1op.cn/i/hxshop/goods/14.jpg', tag: '爆款', price: 45, old_price: 65,}
			,{id: '239', name: '百富汉堡', main_pic: '//imgs.1op.cn/i/hxshop/goods/15.jpg', tag: '推荐', price: 45, old_price: 65,}
			,{id: '240', name: '百富汉堡', main_pic: '//imgs.1op.cn/i/hxshop/goods/16.jpg', tag: '推荐', price: 45, old_price: 65,}
		]
		
	}
	,{
		store_id: '854',
		name: '五点快餐',
		avatar: '//imgs.1op.cn/i/hxshop/goods/4.jpg',
		community: '天北花园',
		mark: '4.8',
		monthly_sales: 356,
		distance: 500,
		starting_price: 3,
		shipping_dees: 15,
		goods:[
			{id: '236', name: '可乐鸡翅buibui爽', main_pic: '//imgs.1op.cn/i/hxshop/goods/17.jpg', tag: '招牌', price: 45, old_price: 65,}
			,{id: '237', name: '百富汉堡', main_pic: '//imgs.1op.cn/i/hxshop/goods/1.jpg', tag: '爆款', price: 45, old_price: 65,}
			,{id: '238', name: '百富汉堡', main_pic: '//imgs.1op.cn/i/hxshop/goods/3.jpg', tag: '爆款', price: 45, old_price: 65,}
			,{id: '239', name: '百富汉堡', main_pic: '//imgs.1op.cn/i/hxshop/goods/5.jpg', tag: '推荐', price: 45, old_price: 65,}
			,{id: '240', name: '百富汉堡', main_pic: '//imgs.1op.cn/i/hxshop/goods/7.jpg', tag: '推荐', price: 45, old_price: 65,}
		]
		
	}]

//评论数据
const commentData = [{
		header_img: "//imgs.1op.cn/i/hxshop/avatar/3.png",
		user_name: "测试1",
		rate:5,
		create_time: "2019.04.12",
		content: "好评",
		imgs:[
			'//imgs.1op.cn/i/hxshop/goods/7.jpg',
			'//imgs.1op.cn/i/hxshop/goods/1.jpg',
			'//imgs.1op.cn/i/hxshop/goods/9.jpg',
			'//imgs.1op.cn/i/hxshop/goods/3.jpg'
		]
	},
	{
		content: "中评",
		create_time: "2019.04.12",
		header_img: "//imgs.1op.cn/i/hxshop/avatar/2.png",
		user_name: "测试2",
		rate:4,
		// imgs:[]
	},
	{
		content: "",
		create_time: "2019.04.12",
		header_img: "//imgs.1op.cn/i/hxshop/avatar/2.png",
		user_name: "测试3",
		rate:2,
		// imgs:[]
	},{
		content: "好评",
		create_time: "2019.04.12",
		header_img: "//imgs.1op.cn/i/hxshop/avatar/3.png",
		user_name: "小蚂蚁",
		rate:5,
		imgs:[
			'//imgs.1op.cn/i/hxshop/goods/9.jpg',
			'//imgs.1op.cn/i/hxshop/goods/12.jpg',
			'//imgs.1op.cn/i/hxshop/goods/16.jpg',
			'//imgs.1op.cn/i/hxshop/goods/7.jpg'
		]
	},
	{
		content: "中评",
		create_time: "2019.04.12",
		header_img: "//imgs.1op.cn/i/hxshop/avatar/4.png",
		user_name: "沙漠骆驼",
		rate:3.5,
		// imgs:[]
	},
	{
		content: "",
		create_time: "2019.04.12",
		header_img: "//imgs.1op.cn/i/hxshop/avatar/5.png",
		user_name: "莫思",
		rate:2.3,
		// imgs:[]
	}];

//商品数据
const goodsData= [{   
		id: 1,
		type_id:1,
		name:'白果王水果沙拉',
		descripe:"脆糯营养，口感好，健康绿色",
		img:'//imgs.1op.cn/i/hxshop/goods/14.jpg',
		price:"",
		oldprice:"",
		//规格
		form: {id:1,name:"尺寸"},
		form_child:[
			{id:81,pid:1,name:"8寸500g", price:"46", oldprice:"100", select:true},
			{id:82,pid:1,name:"10寸600g", price:"97", oldprice:"100",select:false},
			{id:83,pid:1,name:"12寸800g", price:"135", oldprice:"100",select:false},
			{id:84,pid:1,name:"四川麻辣", price:"12", oldprice:"100",select:false},
			{id:85,pid:1,name:"香辣", price:"20", oldprice:"100",select:false},
			{id:86,pid:1,name:"卤香", price:"90", oldprice:"100",select:false},
			{id:87,pid:1,name:"鲜甜广味", price:"80", oldprice:"100",select:false},
			{id:88,pid:1,name:"镇店茴香味", price:"100", oldprice:"100",select:false}
		]
	},
	{   
		id: 2,
		type_id:2,
		name:'精品烤山药',
		descripe:"脆糯营养，口感好，健康绿色",
		img: '//imgs.1op.cn/i/hxshop/goods/12.jpg',
		price:"",
		oldprice:"",
		//规格
		form: {id:1,name:"尺寸"},
		form_child:[
			{id:81,pid:1,name:"8寸500g", price:"78", oldprice:"100", select:true},
			{id:82,pid:1,name:"10寸600g", price:"97", oldprice:"100",select:false},
			{id:83,pid:1,name:"12寸800g", price:"135", oldprice:"100",select:false},
			{id:84,pid:1,name:"四川麻辣", price:"12", oldprice:"100",select:false},
			{id:85,pid:1,name:"香辣", price:"20", oldprice:"100",select:false},
			{id:86,pid:1,name:"卤香", price:"90", oldprice:"100",select:false},
			{id:87,pid:1,name:"鲜甜广味", price:"80", oldprice:"100",select:false},
			{id:88,pid:1,name:"镇店茴香味", price:"100", oldprice:"100",select:false}
		]
	},
	{   
		id: 3,
		type_id:2,
		name:'川味毛血旺',
		descripe:"脆糯营养，口感好，健康绿色",
		img: '//imgs.1op.cn/i/hxshop/goods/11.jpg',
		price:"4",
		oldprice:"",
		
	},
	{   
		id: 4,
		type_id:3,
		name:'吐鲁番烤全羊',
		descripe:"脆糯营养，口感好，健康绿色",
		img: '//imgs.1op.cn/i/hxshop/goods/10.jpg',
		price:"4",
		oldprice:""
	},
	{   
		id: 5,
		type_id:3,
		name:'红烧肉',
		descripe:"脆糯营养，口感好，健康绿色",
		img: '//imgs.1op.cn/i/hxshop/goods/9.jpg',
		price:"4",
		oldprice:""
	},
	{   
		id: 6,
		type_id:4,
		name:'新疆特色辣子鸡',
		descripe:"脆糯营养，口感好，健康绿色",
		img: '//imgs.1op.cn/i/hxshop/goods/8.jpg',
		price:"4",
		oldprice:""
	},
	{
		id: 106,
		type_id:4,
		name:'新疆特色羊排',
		descripe:"脆糯营养，口感好，健康绿色",
		img: '//imgs.1op.cn/i/hxshop/goods/9.jpg',
		price:"4",
		oldprice:""
	},
	{   
		id: 7,
		type_id:5,
		name:'绝味海鲜拼盘',
		descripe:"脆糯营养，口感好，健康绿色",
		img: '//imgs.1op.cn/i/hxshop/goods/7.jpg',
		price:"4",
		oldprice:""
	},
	{   
		id: 8,
		type_id:5,
		name:'金色香糯大粽子',
		descripe:"脆糯营养，口感好，健康绿色",
		img: '//imgs.1op.cn/i/hxshop/goods/6.jpg',
		price:"4",
		oldprice:""
	},
	{   
		id: 9,
		type_id:5,
		name:'马梓林香香鸡',
		descripe:"脆糯营养，口感好，健康绿色",
		img: '//imgs.1op.cn/i/hxshop/goods/5.jpg',
		price:"4",
		oldprice:""
	},
	{   
		id: 10,
		type_id:6,
		name:'草莓味莫普氏蛋糕',
		descripe:"脆糯营养，口感好，健康绿色",
		img: '//imgs.1op.cn/i/hxshop/goods/4.jpg',
		price:"4",
		oldprice:""
	},
	{   
		id: 23,
		type_id:6,
		name:'川味毛血旺',
		descripe:"脆糯营养，口感好，健康绿色",
		img: '//imgs.1op.cn/i/hxshop/goods/11.jpg',
		price:"4",
		oldprice:"",
		
	},
	{   
		id: 24,
		type_id:6,
		name:'吐鲁番烤全羊',
		descripe:"脆糯营养，口感好，健康绿色",
		img: '//imgs.1op.cn/i/hxshop/goods/10.jpg',
		price:"4",
		oldprice:""
	},
	{   
		id: 25,
		type_id:7,
		name:'红烧肉',
		descripe:"脆糯营养，口感好，健康绿色",
		img: '//imgs.1op.cn/i/hxshop/goods/9.jpg',
		price:"4",
		oldprice:""
	},
	{   
		id: 26,
		type_id:7,
		name:'新疆特色辣子鸡',
		descripe:"脆糯营养，口感好，健康绿色",
		img: '//imgs.1op.cn/i/hxshop/goods/8.jpg',
		price:"4",
		oldprice:""
	},
	{   
		id: 27,
		type_id:7,
		name:'绝味海鲜拼盘',
		descripe:"脆糯营养，口感好，健康绿色",
		img: '//imgs.1op.cn/i/hxshop/goods/7.jpg',
		price:"4",
		oldprice:""
	},
	{id: 28,type_id:8,name:'金色香糯大粽子',descripe:"脆糯营养，口感好，健康绿色",img: '//imgs.1op.cn/i/hxshop/goods/6.jpg',price:"4",oldprice:""},
	{id: 29,type_id:8,name:'马梓林香香鸡',descripe:"脆糯营养，口感好，健康绿色",img: '//imgs.1op.cn/i/hxshop/goods/5.jpg',price:"4",oldprice:""},
	{id: 30,type_id:8,name:'草莓味莫普氏蛋糕',descripe:"脆糯营养，口感好，健康绿色",img: '//imgs.1op.cn/i/hxshop/goods/4.jpg',price:"4",oldprice:""},
	{id: 31,type_id:9,name:'金色香糯大粽子',descripe:"脆糯营养，口感好，健康绿色",img: '//imgs.1op.cn/i/hxshop/goods/16.jpg',price:"4",oldprice:""},
	{id: 32,type_id:9,name:'马梓林香香鸡',descripe:"脆糯营养，口感好，健康绿色",img: '//imgs.1op.cn/i/hxshop/goods/17.jpg',price:"4",oldprice:""},
	{id: 33,type_id:9,name:'草莓味莫普氏蛋糕',descripe:"脆糯营养，口感好，健康绿色",img: '//imgs.1op.cn/i/hxshop/goods/15.jpg',price:"4",oldprice:""},
	{id: 46,type_id:8,name:'金色香糯大粽子',descripe:"脆糯营养，口感好，健康绿色",img: '//imgs.1op.cn/i/hxshop/goods/6.jpg',price:"4",oldprice:""},
	{id: 34,type_id:10,name:'马梓林香香鸡',descripe:"脆糯营养，口感好，健康绿色",img: '//imgs.1op.cn/i/hxshop/goods/14.jpg',price:"4",oldprice:""},
	{id: 35,type_id:10,name:'草莓味莫普氏蛋糕',descripe:"脆糯营养，口感好，健康绿色",img: '//imgs.1op.cn/i/hxshop/goods/13.jpg',price:"4",oldprice:""},
	{id: 45,type_id:8,name:'金色香糯大粽子',descripe:"脆糯营养，口感好，健康绿色",img: '//imgs.1op.cn/i/hxshop/goods/6.jpg',price:"4",oldprice:""},
	{id: 36,type_id:10,name:'马梓林香香鸡',descripe:"脆糯营养，口感好，健康绿色",img: '//imgs.1op.cn/i/hxshop/goods/12.jpg',price:"4",oldprice:""},
	{id: 37,type_id:10,name:'草莓味莫普氏蛋糕',descripe:"脆糯营养，口感好，健康绿色",img: '//imgs.1op.cn/i/hxshop/goods/11.jpg',price:"4",oldprice:""},
	{id: 38,type_id:11,name:'草莓味莫普氏蛋糕',descripe:"脆糯营养，口感好，健康绿色",img: '//imgs.1op.cn/i/hxshop/goods/10.jpg',price:"4",oldprice:""},
	{id: 44,type_id:8,name:'金色香糯大粽子',descripe:"脆糯营养，口感好，健康绿色",img: '//imgs.1op.cn/i/hxshop/goods/6.jpg',price:"4",oldprice:""},
	{id: 39,type_id:12,name:'马梓林香香鸡',descripe:"脆糯营养，口感好，健康绿色",img: '//imgs.1op.cn/i/hxshop/goods/9.jpg',price:"4",oldprice:""},
	{id: 40,type_id:12,name:'草莓味莫普氏蛋糕',descripe:"脆糯营养，口感好，健康绿色",img: '//imgs.1op.cn/i/hxshop/goods/8.jpg',price:"4",oldprice:""},
	{id: 13,type_id:13,name:'金色香糯大粽子',descripe:"脆糯营养，口感好，健康绿色",img: '//imgs.1op.cn/i/hxshop/goods/6.jpg',price:"4",oldprice:""},
	{id: 41,type_id:12,name:'马梓林香香鸡',descripe:"脆糯营养，口感好，健康绿色",img: '//imgs.1op.cn/i/hxshop/goods/7.jpg',price:"4",oldprice:""},
	{id: 42,type_id:13,name:'草莓味莫普氏蛋糕',descripe:"脆糯营养，口感好，健康绿色",img: '//imgs.1op.cn/i/hxshop/goods/1.jpg',price:"4",oldprice:""}
	];
	
//商品种类数据
const categoryData = [
		{id: 1,name: '烧烤'},
		{id: 2,name: '生鲜'},
		{id: 3,name: '绿蔬'},
		{id: 4,name: '肉类'},
		{id: 5,name: '川味'},
		{id: 6,name: '粤菜'},
		{id: 7,name: '湘菜'},
		{id: 8,name: '西餐'},
		{id: 9,name: '饮料'},
		{id: 10,name: '糕点'},
		{id: 11,name: '凉菜'},
		{id: 12,name: '火锅'},
		{id: 13,name: '干锅'}
	];

//商品详细
const goodsInfo = {
	//商品id
	id: 18,
	//商品类id
	type_id:1,
	//商品名称
	name:'新疆特色辣子鸡排饭',
	//商品描述-就是掌柜描述，
	descripe:"口味可以自己选，有香辣、蒜香、葱香、孜然、老麻口味",
	//主图
	img:'/static/face/face_5.jpg',
	//滚动图片
	banner_img:['//imgs.1op.cn/i/hxshop/goods/14.jpg','//imgs.1op.cn/i/hxshop/goods/7.jpg','//imgs.1op.cn/i/hxshop/goods/12.jpg'],
	//现价
	price:"23",
	//原价
	oldprice:"44",
	//月销售
	monthly_sales: "566",
	//商品标签
	goods_tag:['约800克','香辣','特色菜','营养美食'],
	//规格
	form: {id:1,name:"尺寸"},
	form_child:[
		{id:81,pid:1,name:"8寸500g", price:"46", oldprice:"100", select:true},
		{id:82,pid:1,name:"10寸600g", price:"97", oldprice:"100",select:false},
		{id:83,pid:1,name:"12寸800g", price:"135", oldprice:"100",select:false},
		{id:84,pid:1,name:"四川麻辣", price:"12", oldprice:"100",select:false},
		{id:85,pid:1,name:"香辣", price:"20", oldprice:"100",select:false},
		{id:86,pid:1,name:"卤香", price:"90", oldprice:"100",select:false},
		{id:87,pid:1,name:"鲜甜广味", price:"80", oldprice:"100",select:false},
		{id:88,pid:1,name:"镇店茴香味", price:"100", oldprice:"100",select:false}
	],
	//详情
	detail:[
		{
			tit:'掌柜描述',
			txt:'口味可以自己选，有香辣、蒜香、葱香、孜然、老麻口味',
		},{
			tit:'主料',
			txt:'鸡胸排，大葱',
		},{
			tit:'菜系',
			txt:'新疆特色菜',
		},{
			tit:'口味',
			txt:'香辣',
		}
		//......更多
	],
	//图文
	desc: `
		<view style="width:100%">
			<image style="width:100%;display:block;" src="//imgs.1op.cn/i/hxshop/goods/14.jpg" />
			<image style="width:100%;display:block;" src="//imgs.1op.cn/i/hxshop/goods/7.jpg" />
			<image style="width:100%;display:block;" src="//imgs.1op.cn/i/hxshop/goods/6.jpg" />
			<image style="width:100%;display:block;" src="//imgs.1op.cn/i/hxshop/goods/3.jpg" />
			<image style="width:100%;display:block;" src="//imgs.1op.cn/i/hxshop/goods/1.jpg" />
		</view>
	`,
};

//商品评价
const goodsEva = {
	//总评价数
	sum: '386',
	//好评
	praise: '306',
	//差评
	bad_review: '80',
	//评价标签
	eva_tag: {
		//有图
		'exist_pic': '62',
		//赞
		'appreciate': '96',
		//踩
		'oppose': '16',
		//其他标签
		'other': ['92%人口味满意','300人希望再次购买']
	},
	eva_list: [
		{
			//用户名
			name: '白色的太阳',
			//头像
			avatar: '//imgs.1op.cn/i/hxshop/avatar/4.png',
			//评论时间
			time: '2020.03.12',
			//点赞或踩商品,没有投票【0】、赞【1】、踩【2】
			point: 1,
			//评价内容
			content: "味道好极了，家里人超爱吃，希望下次能多放点辣椒，我们家吃辣",
			//上传的图片
			pic: ['//imgs.1op.cn/i/hxshop/goods/12.jpg','//imgs.1op.cn/i/hxshop/goods/13.jpg','//imgs.1op.cn/i/hxshop/goods/15.jpg']
		},
		{
			//用户名
			name: '匿名用户',
			//头像
			avatar: '//imgs.1op.cn/i/hxshop/avatar/2.png',
			//评论时间
			time: '2020.03.16',
			//点赞或踩商品,没有投票【0】、赞【1】、踩【2】
			point: 2,
			//评价内容
			content: "",
			//上传的图片
			pic: []
		},
		{
			//用户名
			name: '匿名用户',
			//头像
			avatar: '//imgs.1op.cn/i/hxshop/avatar/6.png',
			//评论时间
			time: '2020.03.16',
			//点赞或踩商品,没有投票【0】、赞【1】、踩【2】
			point: 0,
			//评价内容
			content: "一般般",
			//上传的图片
			pic: []
		},
	]
}

// 订单
const ordersData = [
	{
		id: 'MS2020041101',
		store_id: 168,
		tag:'../../static/img/index/yd.png',
		store_name: '肯德基',
		community: '阿克苏友好店',
		avatar: 'https://imgs.1op.cn/i/hxshop/avatar/2.png',
		create_time: '2020-4-11 19:51',
		total: 122.45,
		//订单状态 [1已取消，2待支付，3待收货，4待评价，5完成，6退款中，7退款完成]
		status: 3,
	},
	{
		id: 'MS2020041102',
		store_id: 186,
		tag:'../../static/img/index/sc.png',
		store_name: '美食大龙虾*烧烤虾尾',
		goods_name: '小龙虾+鸡翅+可乐+田螺',
		community: '',
		avatar: 'https://imgs.1op.cn/i/hxshop/avatar/5.png',
		create_time: '2020-4-11 19:51',
		total: 122.45,
		//订单状态 [1已取消，2待支付，3待收货，4待评价，5完成，6退款中，7退款完成]
		status: 2,
	},
	{
		id: 'MS2020041103',
		store_id: 183,
		tag:'../../static/img/index/sg.png',
		store_name: '辣椒小海鲜',
		community: '',
		avatar: 'https://imgs.1op.cn/i/hxshop/avatar/9.png',
		create_time: '2020-4-11 19:51',
		total: 122.45,
		//订单状态 [1已取消，2待支付，3待收货，4待评价，5完成，6退款中，7退款完成]
		status: 1,
	},
	{
		id: 'MS2020041104',
		store_id: 182,
		tag:'../../static/img/index/cs.png',
		store_name: '特色冒菜-四川爆啦',
		community: '特卖店',
		avatar: 'https://imgs.1op.cn/i/hxshop/avatar/9.png',
		create_time: '2020-4-11 19:51',
		total: 122.45,
		//订单状态 [1已取消，2待支付，3待收货，4待评价，5完成，6退款中，7退款完成]
		status: 4,
	},
	{
		id: 'MS2020041105',
		store_id: 181,
		tag:'../../static/img/index/yd.png',
		store_name: '绝味黑鸭脖',
		community: '',
		avatar: 'https://imgs.1op.cn/i/hxshop/avatar/9.png',
		create_time: '2020-4-11 19:51',
		total: 122.45,
		//订单状态 [1已取消，2待支付，3待收货，4待评价，5完成，6退款中，7退款完成]
		status: 5,
	},
	{
		id: 'MS2020041106',
		store_id: 180,
		tag:'../../static/img/index/yd.png',
		store_name: '天天来食府',
		community: '',
		avatar: 'https://imgs.1op.cn/i/hxshop/avatar/9.png',
		create_time: '2020-4-11 19:51',
		total: 122.45,
		//订单状态 [1已取消，2待支付，3待收货，4待评价，5完成，6退款中，7退款完成]
		status: 6,
	},
	{
		id: 'MS2020041107',
		store_id: 170,
		tag:'../../static/img/index/yd.png',
		store_name: '肯德基',
		community: '兴隆店',
		avatar: 'https://imgs.1op.cn/i/hxshop/avatar/10.png',
		create_time: '2020-4-11 19:51',
		total: 122.45,
		//订单状态 [1已取消，2待支付，3待收货，4待评价，5完成，6退款中，7退款完成]
		status: 7,
	}
]
	
export default {
	storeData,
	storeList,
	commentData,
	goodsData,
	categoryData,
	goodsInfo,
	goodsEva,
	ordersData
}
	