export default {

    // 正常模式的tab title配置
    tabTitles: [
      {
        title: '今日',
        icon: 'https://s1.ax1x.com/2020/04/19/JuR8Rf.png',
        activeIcon: 'https://s1.ax1x.com/2020/04/19/JuRDJ0.png'
      },
      {
        title: '最新',
        icon: 'https://s1.ax1x.com/2020/04/19/JuRcyF.png',
        activeIcon: 'https://s1.ax1x.com/2020/04/19/JuRgL4.png',
      },

      {
        title: '我的',
        icon: 'https://s1.ax1x.com/2020/04/19/JuRReJ.png',
        activeIcon: 'https://s1.ax1x.com/2020/04/19/JuRWw9.png',
      }
    ],
    tabStyles: {
      bgColor: '#FFFFFF',
      titleColor: '#666666',
      activeTitleColor: '#027DB4',
      activeBgColor: '#FFFFFF',
      isActiveTitleBold: true,
      iconWidth: 60,
      iconHeight: 60,
      width: 160,
      height: 120,
      fontSize: 24,
      textPaddingLeft: 10,
      textPaddingRight: 10
    },
  
    // 使用 iconFont 模式的tab title配置
    tabIconFontTitles: [
      {
        title: '首页',
        codePoint: '&#xe601'
      },
      {
        title: '特别推荐',
        codePoint: '\ue608'
      },
      {
        title: '消息中心',
        codePoint: '\ue752',
      },
      {
        title: '我的主页',
        codePoint: '\ue601',
        dot: true
      }
    ],
    tabIconFontStyles: {
      bgColor: '#FFFFFF',
      titleColor: '#666666',
      activeTitleColor: '#3D3D3D',
      activeBgColor: '#FFFFFF',
      isActiveTitleBold: true,
      width: 160,
      height: 120,
      fontSize: 24,
      textPaddingLeft: 10,
      textPaddingRight: 10,
      iconFontSize: 50,
      iconFontMarginBottom: 8,
      iconFontColor: '#333333',
      activeIconFontColor: 'red',
      iconFontUrl: '//at.alicdn.com/t/font_1765018_myt0srnitv7.css'
    }
  }