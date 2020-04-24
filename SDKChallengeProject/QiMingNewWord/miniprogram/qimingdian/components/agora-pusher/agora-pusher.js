// components/agora-pusher.js
const Utils = require("../../utils/util.js")

Component({
  /**
   * 组件的属性列表
   */
  properties: {
    minBitrate: {
      type: Number,
      value: 200
    },
    maxBitrate: {
      type: Number,
      value: 500
    },
    width: {
      type: Number,
      value: 0
    },
    height: {
      type: Number,
      value: 0
    },
    x: {
      type: Number,
      value: 0
    },
    y: {
      type: Number,
      value: 0
    },
    muted: {
      type: Boolean,
      value: !1
    },
    debug: {
      type: Boolean,
      value: !1
    },
    beauty: {
      type: String,
      value: 0
    },
    aspect: {
      type: String,
      value: "3:4"
    },
    /**
     * 0 - loading, 1 - ok, 2 - error
     */
    status: {
      type: String,
      value: "loading",
      observer: function (newVal, oldVal, changedPath) {
        Utils.log(`player status changed from ${oldVal} to ${newVal}`);
      }
    },
    url: {
      type: String,
      value: "",
      observer: function (newVal, oldVal, changedPath) {
        // 属性被改变时执行的函数（可选），也可以写成在methods段中定义的方法名字符串, 如：'_propertyChange'
        // 通常 newVal 就是新设置的数据， oldVal 是旧数据
        Utils.log(`pusher url changed from ${oldVal} to ${newVal}, path: ${changedPath}`);
      }
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    pusherContext: null,
    detached: false
  },

  /**
   * 组件的方法列表
   */
  methods: {
    /**
     * start live pusher via context
     * in most cases you should not call this manually in your page
     * as this will be automatically called in component ready method
     */
    start() {
      Utils.log(`starting pusher`);
      this.data.pusherContext.stop();
      if (this.data.detached) {
        Utils.log(`try to start pusher while component already detached`);
        return;
      }
      this.data.pusherContext.start();
    },

    /**
     * stop live pusher context
     */
    stop() {
      Utils.log(`stopping pusher`);
      this.data.pusherContext.stop();
    },

    /**
     * switch camera direction
     */
    switchCamera() {
      this.data.pusherContext.switchCamera();
    },

    /**
     * 推流状态更新回调
     */
    recorderStateChange: function (e) {
      Utils.log(`live-pusher code: ${e.detail.code} - ${e.detail.message}`)
      if (e.detail.code === -1307) {
        //re-push
        Utils.log('live-pusher stopped', "error");
        this.setData({
          status: "error"
        })
        //emit event
        this.triggerEvent('pushfailed');
      }

      if (e.detail.code === 1008) {
        //started
        Utils.log(`live-pusher started`);
        if(this.data.status === "loading") {
          this.setData({
            status: "ok"
          })
        }
      }
    },
    recorderNetChange: function(e) {
      Utils.log(`network: ${JSON.stringify(e.detail)}`);
    }
  },

  /**
   * 组件生命周期
   */
  ready: function () {
    Utils.log("pusher ready");
    this.data.pusherContext || (this.data.pusherContext = wx.createLivePusherContext(this));
  },
  moved: function () {
    Utils.log("pusher moved");
   },
  detached: function () {
    Utils.log("pusher detached");
    // auto stop pusher when detached
    this.data.pusherContext && this.data.pusherContext.stop();
    this.data.detached = true;
  }
})
