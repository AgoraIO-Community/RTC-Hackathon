// pages/meeting/meeting.js
const app = getApp()
const Utils = require('../../utils/util.js')
const AgoraMiniappSDK = require("../../lib/mini-app-sdk-production.js");
const max_user = 10;
const Layouter = require("../../utils/layout.js");
const APPID = require("../../utils/config.js").APPID;

/**
 * log relevant, remove these part and relevant code if not needed
 */
const Uploader = require("../../utils/uploader.js")
const LogUploader = Uploader.LogUploader;
const LogUploaderTask = Uploader.LogUploaderTask;

Page({

  /**
   * 页面的初始数据
   */
  data: {
    /**
     * media objects array
     * this involves both player & pusher data
     * we use type to distinguish
     * a sample media object
     * {
     *   key: **important, change this key only when you want to completely refresh your dom**,
     *   type: 0 - pusher, 1 - player,
     *   uid: uid of stream,
     *   holding: when set to true, the block will stay while native control hidden, used when needs a placeholder for media block,
     *   url: url of pusher/player
     *   left: x of pusher/player
     *   top: y of pusher/player
     *   width: width of pusher/player
     *   height: height of pusher/player
     * }
     */
    media: [],
    /**
     * muted
     */
    muted: false,
    /**
     * beauty 0 - 10
     */
    beauty: 0,
    totalUser: 1,
    /**
     * debug
     */
    debug: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    Utils.log(`onLoad`);
    // get channel from page query param
    this.channel = options.channel;
    // default role to broadcaster
    this.role = options.role || "broadcaster";
    // get pre-gened uid, this uid will be different every time the app is started
    this.uid = Utils.getUid();
    // store agora client
    this.client = null;
    // store layouter control
    this.layouter = null;
    // prevent user from clicking leave too fast
    this.leaving = false;

    // page setup
    wx.setNavigationBarTitle({
       //title: `${this.channel}(${this.uid})`
       title: '启明直播间'
    });
    wx.setKeepScreenOn({
      keepScreenOn: true
    });

    /**
     * please remove this part in your production environment
     */
    if (/^sdktest.*$/.test(this.channel)) {
      this.testEnv = true
      wx.showModal({
        title: '提示',
        content: '您正处于测试环境',
        showCancel: false
      })
    }
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {
    let channel = this.channel;
    let uid = this.uid;
    Utils.log(`onReady`);

    // schedule log auto update, remove this if this is not needed
    this.logTimer = setInterval(() => {
      this.uploadLogs();
    }, 60 * 60 * 1000);

    // init layouter control
    this.initLayouter();

    // init agora channel
    this.initAgoraChannel(uid, channel).then(url => {
      Utils.log(`channel: ${channel}, uid: ${uid}`);
      Utils.log(`pushing ${url}`);
      let ts = new Date().getTime();

      if (this.isBroadcaster()) {
        // first time init, add pusher media to view
        this.addMedia(0, this.uid, url, {
          key: ts
        });
      }
    }).catch(e => {
      Utils.log(`init agora client failed: ${e}`);
      wx.showToast({
        title: `客户端初始化失败`,
        icon: 'none',
        duration: 5000
      });
    });
  },

  /**
   * 只有提供了该回调才会出现转发选项
   */
  onShareAppMessage() {

  },

  /**
   * calculate size based on current media length
   * sync the layout info into each media object
   */
  syncLayout(media) {
    let sizes = this.layouter.getSize(media.length);
    for (let i = 0; i < sizes.length; i++) {
      let size = sizes[i];
      let item = media[i];

      if (item.holding) {
        //skip holding item
        continue;
      }

      item.left = parseFloat(size.x).toFixed(2);
      item.top = parseFloat(size.y).toFixed(2);
      item.width = parseFloat(size.width).toFixed(2);
      item.height = parseFloat(size.height).toFixed(2);
    }
    return media;
  },

  /**
   * check if current media list has specified uid & mediaType component
   */
  hasMedia(mediaType, uid) {
    let media = this.data.media || [];
    return media.filter(item => {
      return item.type === mediaType && `${item.uid}` === `${uid}`
    }).length > 0
  },

  /**
   * add media to view
   * type: 0 - pusher, 1 - player
   * *important* here we use ts as key, when the key changes
   * the media component will be COMPLETELY refreshed
   * this is useful when your live-player or live-pusher
   * are in a bad status - say -1307. In this case, update the key
   * property of media object to fully refresh it. The old media
   * component life cycle event detached will be called, and
   * new media component life cycle event ready will then be called
   */
  addMedia(mediaType, uid, url, options) {
    Utils.log(`add media ${mediaType} ${uid} ${url}`);
    let media = this.data.media || [];

    if (mediaType === 0) {
      //pusher
      media.splice(0, 0, {
        key: options.key,
        type: mediaType,
        uid: `${uid}`,
        holding: false,
        url: url,
        left: 0,
        top: 0,
        width: 0,
        height: 0
      });
    } else {
      //player
      media.push({
        key: options.key,
        rotation: options.rotation,
        type: mediaType,
        uid: `${uid}`,
        holding: false,
        url: url,
        left: 0,
        top: 0,
        width: 0,
        height: 0
      });
    }

    media = this.syncLayout(media);
    return this.refreshMedia(media);
  },

  /**
   * remove media from view
   */
  removeMedia: function(uid) {
    Utils.log(`remove media ${uid}`);
    let media = this.data.media || [];
    media = media.filter(item => {
      return `${item.uid}` !== `${uid}`
    });

    if (media.length !== this.data.media.length) {
      media = this.syncLayout(media);
      this.refreshMedia(media);
    } else {
      Utils.log(`media not changed: ${JSON.stringify(media)}`)
      return Promise.resolve();
    }
  },

  /**
   * update media object
   * the media component will be fully refreshed if you try to update key
   * property.
   */
  updateMedia: function(uid, options) {
    Utils.log(`update media ${uid} ${JSON.stringify(options)}`);
    let media = this.data.media || [];
    let changed = false;
    for (let i = 0; i < media.length; i++) {
      let item = media[i];
      if (`${item.uid}` === `${uid}`) {
        media[i] = Object.assign(item, options);
        changed = true;
        Utils.log(`after update media ${uid} ${JSON.stringify(item)}`)
        break;
      }
    }

    if (changed) {
      return this.refreshMedia(media);
    } else {
      Utils.log(`media not changed: ${JSON.stringify(media)}`)
      return Promise.resolve();
    }
  },

  /**
   * call setData to update a list of media to this.data.media
   * this will trigger UI re-rendering
   */
  refreshMedia: function(media) {
    return new Promise((resolve) => {
      for (let i = 0; i < media.length; i++) {
        if (i < max_user) {
          //show
          media[i].holding = false;
        } else {
          //hide 
          media[i].holding = true;
        }
      }

      if (media.length > max_user) {
        wx.showToast({
          title: '由于房内人数超过7人，部分视频未被加载显示',
        });
      }

      Utils.log(`updating media: ${JSON.stringify(media)}`);
      this.setData({
        media: media
      }, () => {
        resolve();
      });
    });
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    let media = this.data.media || [];
    media.forEach(item => {
      if (item.type === 0) {
        //return for pusher
        return;
      }
      let player = this.getPlayerComponent(item.uid);
      if (!player) {
        Utils.log(`player ${item.uid} component no longer exists`, "error");
      } else {
        // while in background, the player maybe added but not starting
        // in this case we need to start it once come back
        player.start();
      }
    });
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {},

  onError: function(e) {
    Utils.log(`error: ${JSON.stringify(e)}`);
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {
    Utils.log(`onUnload`);
    clearInterval(this.logTimer);
    clearTimeout(this.reconnectTimer);
    this.logTimer = null;
    this.reconnectTimer = null;

    // unlock index page join button
    let pages = getCurrentPages();
    if (pages.length > 1) {
      //unlock join
      let indexPage = pages[0];
      indexPage.unlockJoin();
    }

    // unpublish sdk and leave channel
    if (this.isBroadcaster()) {
      try {
        this.client && this.client.unpublish();
      } catch (e) {
        Utils.log(`unpublish failed ${e}`);
      }
    }
    this.client && this.client.leave();
  },

  /**
   * callback when leave button called
   */
  onLeave: function() {
    if (!this.leaving) {
      this.leaving = true;
      this.navigateBack();
    }
  },


  /**
   * navigate to previous page
   * if started from shared link, it's possible that
   * we have no page to go back, in this case just redirect
   * to index page
   */
  navigateBack: function() {
    Utils.log(`attemps to navigate back`);
    if (getCurrentPages().length > 1) {
      //have pages on stack
      wx.navigateBack({});
    } else {
      //no page on stack, usually means start from shared links
      wx.redirectTo({
        url: '../index/index',
      });
    }
  },

  /**
   * 推流状态更新回调
   */
  onPusherFailed: function() {
    Utils.log('pusher failed completely', "error");
    wx.showModal({
      title: '发生错误',
      content: '推流发生错误，请重新进入房间重试',
      showCancel: false,
      success: () => {
        this.navigateBack();
      }
    })
  },

  /**
   * 静音回调
   */
  onMute: function() {
    if (!this.data.muted) {
      this.client.muteLocal('audio', () => {
        console.log('muteLocal success')
      }, err => {
        console.log(err)
      });
    } else {
      this.client.unmuteLocal('audio', () => {
        console.log('unmuteLocal success')
      }, err => {
        console.log(err)
      });
    }
    this.setData({
      muted: !this.data.muted
    })
  },

  /**
   * 摄像头方向切换回调
   */
  onSwitchCamera: function() {
    Utils.log(`switching camera`);
    // get pusher component via id
    const agoraPusher = this.getPusherComponent();
    agoraPusher && agoraPusher.switchCamera();
  },

  /**
   * 美颜回调
   */
  onMakeup: function() {
    let beauty = this.data.beauty == 5 ? 0 : 5;
    this.setData({
      beauty: beauty
    })
  },

  /**
   * 上传日志
   */
  uploadLogs: function() {
    let logs = Utils.getLogs();
    Utils.clearLogs();

    let totalLogs = logs.length;
    let tasks = [];
    let part = 0;
    let ts = new Date().getTime();
    // 1w logs per task slice
    const sliceSize = 500;
    do {
      let content = logs.splice(0, sliceSize);
      tasks.push(new LogUploaderTask(content, this.channel, part++, ts, this.uid));
    } while (logs.length > sliceSize)
    wx.showLoading({
      title: '0%',
      mask: true
    })
    LogUploader.off("progress").on("progress", e => {
      let remain = e.remain;
      let total = e.total;
      Utils.log(`log upload progress ${total - remain}/${total}`);
      if (remain === 0) {
        wx.hideLoading();
        wx.showToast({
          title: `上传成功`,
        });
      } else {
        wx.showLoading({
          mask: true,
          title: `${((total - remain) / total * 100).toFixed(2)}%`,
        })
      }
    });
    LogUploader.on("error"), e => {
      wx.hideLoading();
      wx.showToast({
        title: `上传失败: ${e}`,
      });
    }
    LogUploader.scheduleTasks(tasks);
  },

  /**
   * 上传日志回调
   */
  onSubmitLog: function() {
    let page = this;
    let mediaAction = this.isBroadcaster() ? "下麦" : "上麦"
    wx.showActionSheet({
      itemList: [mediaAction, "上传日志"],
      success: res => {
        let tapIndex = res.tapIndex;
        if (tapIndex == 0) {
          if (this.isBroadcaster()) {
            this.becomeAudience().then(() => {
              this.removeMedia(this.uid);
            }).catch(e => {
              Utils.log(`switch to audience failed ${e.stack}`);
            })
          } else {
            let ts = new Date().getTime();
            this.becomeBroadcaster().then(url => {
              this.addMedia(0, this.uid, url, {
                key: ts
              });
            }).catch(e => {
              Utils.log(`switch to broadcaster failed ${e.stack}`);
            })
          }
        } else if (tapIndex === 1) {
          this.setData({
            debug: !this.data.debug
          })
          wx.showModal({
            title: '遇到使用问题?',
            content: '点击确定可以上传日志，帮助我们了解您在使用过程中的问题',
            success: function(res) {
              if (res.confirm) {
                console.log('用户点击确定')
                page.uploadLogs();
              } else if (res.cancel) {
                console.log('用户点击取消')
              }
            }
          })
        }
      }
    })
  },

  /**
   * 获取屏幕尺寸以用于之后的视窗计算
   */
  initLayouter: function() {
    // get window size info from systemInfo
    const systemInfo = app.globalData.systemInfo;
    // 64 is the height of bottom toolbar
    this.layouter = new Layouter(systemInfo.windowWidth, systemInfo.windowHeight - 64);
  },

  /**
   * 初始化sdk推流
   */
  initAgoraChannel: function(uid, channel) {
    return new Promise((resolve, reject) => {
      let client = {}
      if (this.testEnv) {
        client = new AgoraMiniappSDK.Client({
          servers: ["wss://miniapp.agoraio.cn/120-131-14-112/api"]
        });
      } else {
        client = new AgoraMiniappSDK.Client()
      }
      //subscribe stream events
      this.subscribeEvents(client);
      AgoraMiniappSDK.LOG.onLog = (text) => {
        // callback to expose sdk logs
        Utils.log(text);
      };
      AgoraMiniappSDK.LOG.setLogLevel(-1);
      this.client = client;
      client.setRole(this.role);
      client.init(APPID, () => {
        Utils.log(`client init success`);
        // pass key instead of undefined if certificate is enabled
        client.join(undefined, channel, uid, () => {
          Utils.log(`client join channel success`);
          //and get my stream publish url
          if (this.isBroadcaster()) {
            client.publish(url => {
              Utils.log(`client publish success`);
              resolve(url);
            }, e => {
              Utils.log(`client publish failed: ${e.code} ${e.reason}`);
              reject(e)
            });
          } else {
            resolve();
          }
        }, e => {
          Utils.log(`client join channel failed: ${e.code} ${e.reason}`);
          reject(e)
        })
      }, e => {
        Utils.log(`client init failed: ${e} ${e.code} ${e.reason}`);
        reject(e);
      });
    });
  },

  reinitAgoraChannel: function(uid, channel) {
    return new Promise((resolve, reject) => {
      let client = {}
      if (this.testEnv) {
        client = new AgoraMiniappSDK.Client({
          servers: ["wss://miniapp.agoraio.cn/120-131-14-112/api"]
        });
      } else {
        client = new AgoraMiniappSDK.Client()
      }
      //subscribe stream events
      this.subscribeEvents(client);
      AgoraMiniappSDK.LOG.onLog = (text) => {
        // callback to expose sdk logs
        Utils.log(text);
      };
      AgoraMiniappSDK.LOG.setLogLevel(-1);
      let uids = this.data.media.map(item => {
        return item.uid;
      });
      this.client = client;
      client.setRole(this.role);
      client.init(APPID, () => {
        Utils.log(`client init success`);
        // pass key instead of undefined if certificate is enabled
        Utils.log(`rejoin with uids: ${JSON.stringify(uids)}`);
        client.rejoin(undefined, channel, uid, uids, () => {
          Utils.log(`client join channel success`);
          if (this.isBroadcaster()) {
            client.publish(url => {
              Utils.log(`client publish success`);
              resolve(url);
            }, e => {
              Utils.log(`client publish failed: ${e.code} ${e.reason}`);
              reject(e)
            });
          } else {
            resolve();
          }
        }, e => {
          Utils.log(`client join channel failed: ${e.code} ${e.reason}`);
          reject(e)
        })
      }, e => {
        Utils.log(`client init failed: ${e} ${e.code} ${e.reason}`);
        reject(e);
      });
    });
  },

  /**
   * return player component via uid
   */
  getPlayerComponent: function(uid) {
    const agoraPlayer = this.selectComponent(`#rtc-player-${uid}`);
    return agoraPlayer;
  },

  /**
   * return pusher component
   */
  getPusherComponent: function() {
    const agorapusher = this.selectComponent(`#rtc-pusher`);
    return agorapusher;
  },

  becomeBroadcaster: function() {
    return new Promise((resolve, reject) => {
      if (!this.client) {
        return reject(new Error("no client available"))
      }
      let client = this.client
      this.role = "broadcaster"
      client.setRole(this.role)
      Utils.log(`client switching role to ${this.role}`);
      client.publish(url => {
        Utils.log(`client publish success`);
        resolve(url);
      }, e => {
        Utils.log(`client publish failed: ${e.code} ${e.reason}`);
        reject(e)
      });
    })
  },

  becomeAudience: function() {
    return new Promise((resolve, reject) => {
      if (!this.client) {
        return reject(new Error("no client available"))
      }

      let client = this.client
      client.unpublish(() => {
        Utils.log(`client unpublish success`);
        this.role = "audience"
        Utils.log(`client switching role to ${this.role}`);
        client.setRole(this.role)
        resolve();
      }, e => {
        Utils.log(`client unpublish failed: ${e.code} ${e.reason}`);
        reject(e)
      });
    })
  },

  /**
   * reconnect when bad things happens...
   */
  reconnect: function() {
    wx.showToast({
      title: `尝试恢复链接...`,
      icon: 'none',
      duration: 5000
    });
    // always destroy client first
    // *important* miniapp supports 2 websockets maximum at same time
    // do remember to destroy old client first before creating new ones
    this.client && this.client.destroy();
    this.reconnectTimer = setTimeout(() => {
      let uid = this.uid;
      let channel = this.channel;
      this.reinitAgoraChannel(uid, channel).then(url => {
        Utils.log(`channel: ${channel}, uid: ${uid}`);
        Utils.log(`pushing ${url}`);
        let ts = new Date().getTime();

        if (this.isBroadcaster()) {
          if (this.hasMedia(0, this.uid)) {
            // pusher already exists in media list
            this.updateMedia(this.uid, {
              url: url,
              key: ts,
            });
          } else {
            // pusher not exists in media list
            Utils.log(`pusher not yet exists when rejoin...adding`);
            this.addMedia(0, this.uid, url, {
              key: ts
            });
          }
        }
      }).catch(e => {
        Utils.log(`reconnect failed: ${e}`);
        return this.reconnect();
      });
    }, 1 * 1000);
  },

  /**
   * 如果
   */
  isBroadcaster: function() {
    return this.role === "broadcaster";
  },

  /**
   * 注册stream事件
   */
  subscribeEvents: function(client) {
    /**
     * sometimes the video could be rotated
     * this event will be fired with ratotion
     * angle so that we can rotate the video
     * NOTE video only supportes vertical or horizontal
     * in case of 270 degrees, the video could be
     * up side down
     */
    client.on("video-rotation", (e) => {
      Utils.log(`video rotated: ${e.rotation} ${e.uid}`)
      setTimeout(() => {
        const player = this.getPlayerComponent(e.uid);
        player && player.rotate(e.rotation);
      }, 1000);
    });
    /**
     * fired when new stream join the channel
     */
    client.on("stream-added", e => {
      let uid = e.uid;
      const ts = new Date().getTime();
      Utils.log(`stream ${uid} added`);
      /**
       * subscribe to get corresponding url
       */
      client.subscribe(uid, (url, rotation) => {
        Utils.log(`stream ${uid} subscribed successful`);
        let media = this.data.media || [];
        let matchItem = null;
        for (let i = 0; i < media.length; i++) {
          let item = this.data.media[i];
          if (`${item.uid}` === `${uid}`) {
            //if existing, record this as matchItem and break
            matchItem = item;
            break;
          }
        }

        if (!matchItem) {
          //if not existing, add new media
          this.addMedia(1, uid, url, {
            key: ts,
            rotation: rotation
          })
        } else {
          // if existing, update property
          // change key property to refresh live-player
          this.updateMedia(matchItem.uid, {
            url: url,
            key: ts,
          });
        }
      }, e => {
        Utils.log(`stream subscribed failed ${e} ${e.code} ${e.reason}`);
      });
    });

    /**
     * remove stream when it leaves the channel
     */
    client.on("stream-removed", e => {
      let uid = e.uid;
      Utils.log(`stream ${uid} removed`);
      this.removeMedia(uid);
    });

    /**
     * when bad thing happens - we recommend you to do a 
     * full reconnect when meeting such error
     * it's also recommended to wait for few seconds before
     * reconnect attempt
     */
    client.on("error", err => {
      let errObj = err || {};
      let code = errObj.code || 0;
      let reason = errObj.reason || "";
      Utils.log(`error: ${code}, reason: ${reason}`);
      let ts = new Date().getTime();
      if (code === 501 || code === 904) {
        this.reconnect();
      }
    });

    /**
     * there are cases when server require you to update
     * player url, when receiving such event, update url into
     * corresponding live-player, REMEMBER to update key property
     * so that live-player is properly refreshed
     * NOTE you can ignore such event if it's for pusher or happens before
     * stream-added
     */
    client.on('update-url', e => {
      Utils.log(`update-url: ${JSON.stringify(e)}`);
      let uid = e.uid;
      let url = e.url;
      let ts = new Date().getTime();
      if (`${uid}` === `${this.uid}`) {
        // if it's not pusher url, update
        Utils.log(`ignore update-url`);
      } else {
        this.updateMedia(uid, {
          url: url,
          key: ts,
        });
      }
    });
  }
})