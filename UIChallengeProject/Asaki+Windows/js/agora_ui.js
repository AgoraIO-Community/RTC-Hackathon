Vue.prototype.$ischangeLanguage=0;

let ui_nav = new Vue({
	el:"#agora_ui",
	data:{
		isBlue:true,
		isRed:false,
		isGreen:false,
		isYellow:false,
		UsingIsClicked:false,
		languageIsClicked:false,
		colorIsClicked:false,
		colorList:[
			{colorName:"红色",color:'#F57983'},
			{colorName:"黄色",color:'#F1C239'},
			{colorName:"绿色",color:'#00E078'},
			{colorName:"蓝色",color:'#177BB0'}
		],
		languageList:[
			"简体中文",
			"English"
		],
		language:{
			chinese:{
				title:'多人通话',
				usingTips:'使用说明',
				tipsTitle:'多人通话——使用说明:',
				tipsPrepare:'使用前准备:',
				tipsLink:'首先需要到声网，注册一个账号，这是声网的官网链接:',
				tipsGetAppID:'获取自己的AppID:',
				tipsAppIDText:'进入控制台，点击项目列表，若已有项目，进入项目，点击AppID旁边的眼睛图标，就会自动复制你的AppID。若没有项目的话，就需要新建一个项目，然后还是点进项目，这样就可以找到你的AppID了。',
				tipsGetToken:'生成临时Token:',
				tipsTokenText1:'在控制台中进入项目列表，找到旁边的功能下面的类似搜索图标，点击生成临时Token，并且进去之后，填写你频道的名字，OK之后，生成临时Token，之后将临时Token复制，发送给你的朋友家人。',
				tipsTokenText2:'在下方填入你的AppID，临时Token，刚刚的频道名，就可以进行多人视频通话啦~',
				tipsTokenText3:'快去与你的朋友家人一起使用见面聊天吧！',
				tipsReaded:'我已了解',
				changeLanguage:'切换语言',
				changeColor:'主题色',
				inAppID:'请输入您的App ID',
				inChannel:'请输入您的channel',
				inToken:'请输入您的token',
				join:'连接',
				leave:'断开',
				publish:'发布',
				unpublish:'取消发布',
				settings:'高级设置',
				close:'关闭',
				save:'保存'
			},
			english:{
				title:'Multi-person call',
				usingTips:'Instructions for use',
				tipsTitle:'Multi-person call-instructions:',
				tipsPrepare:'Preparation before use:',
				tipsLink:'First of all, you need to go to Shengwang and register an account, this is the official website link of Shengwang:',
				tipsGetAppID:'Get your own AppID:',
				tipsAppIDText:'Enter the console, click on the project list, if there is an existing project, enter the project, click the eye icon next to AppID, it will automatically copy your AppID. If there is no project, you need to create a new project, and then click into the project, so that you can find your AppID.',
				tipsGetToken:'Generate temporary token:',
				tipsTokenText1:'Enter the project list in the console, find the similar search icon under the function next to it, click generate temporary token, and after entering, fill in the name of your channel, after OK, generate temporary token, then copy the temporary token and send it to your friends family.',
				tipsTokenText2:'Fill in your AppID, temporary Token, and channel name just now, you can make a multi-person video call ~',
				tipsTokenText3:'Go to meet and chat with your friends and family!',
				tipsReaded:'I already understand',
				changeLanguage:'Switch language',
				changeColor:'Theme color',
				inAppID:'Please enter your App ID',
				inChannel:'Please enter your channel',
				inToken:'Please enter your token',
				join:'join',
				leave:'leave',
				publish:'publish',
				unpublish:'unpubish',
				settings:'advanced settings',
				close:'close',
				save:'save'
			}
		},
		controlIsHidden:false,
		settingIsShow:false,
		hiddenStyle:{},
	},
	methods:{
		toggleColor(n){
			this.colorIsClicked= !this.colorIsClicked
			let _this = this
			switch(n){
				case '绿色':
					_this.isBlue=false
					_this.isRed=false
					_this.isGreen=true
					_this.isYellow=false
					break
				case '红色':
					_this.isBlue=false
					_this.isRed=true
					_this.isGreen=false
					_this.isYellow=false
					break
				case '黄色':
					_this.isBlue=false
					_this.isRed=false
					_this.isGreen=false
					_this.isYellow=true
					break
				default: 
					_this.isBlue=true
					_this.isRed=false
					_this.isGreen=false
					_this.isYellow=false
			}
		},
		getLanguage(){
			let language
			if (this.$ischangeLanguage == 1) {
				language = this.language.english
				return language
			} else{
				language = this.language.chinese
				return language
			}
		},		
		toggleLanguage(l){
			this.languageIsClicked = !this.languageIsClicked
			this.$ischangeLanguage = l
			this.getLanguage()
		},
		close(){
			this.settingIsShow = false
			Toast.info("取消设置")
		},
		save(){
			this.settingIsShow = false
			Toast.notice("保存成功")
		}
	},
	watch:{
		'controlIsHidden':function(newVal){
			if (newVal) {
				this.hiddenStyle['borderRadius']=0,
				this.hiddenStyle['borderTopRightRadius']=10+'px',
				this.hiddenStyle['borderBottomRightRadius']=10+'px'			
			} else {
				for(let key in this.hiddenStyle){
					delete this.hiddenStyle[key]
				}
			}
		}
	}
})