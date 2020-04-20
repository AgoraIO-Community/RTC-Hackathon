<template>
  <div class="agora-theme">
    <leftNav></leftNav>
    <!-- <div class="navbar-fixed">
       
    </div> -->
    <form id="form" class="row col l12 s12">
        <div class="row container col l12 s12">
        <div class="col" style="min-width: 433px; max-width: 443px" >
            <div class="card" style="margin-top: 0px; margin-bottom: 0px;">
            <div class="row card-content" style="margin-bottom: 0px;">
                <div class="input-field" style="display:none">
                    <label for="appID" class="active">App ID</label>
                    <input type="text" placeholder="App ID" name="appID" v-model="appid">
                </div>
                <div class="input-field" style="display:none">
                    <label for="channel" class="active">Channel</label>
                    <input type="text" placeholder="channel" name="channel" v-model="channel">
                </div>
                <div class="input-field" style="display:none">
                    <label for="token" class="active">Token</label>
                    <input type="text" placeholder="token" name="token">
                </div>
                <div class="row" style="margin: 0">
                    <div class="col s12">
                    <button class="btn btn-raised btn-primary waves-effect waves-light" id="join" @click="joinEv($event)">连接</button>
                    <button class="btn btn-raised btn-primary waves-effect waves-light" id="leave" @click="leaveEv($event)">离开</button>
                    <button class="btn btn-raised btn-primary waves-effect waves-light" id="publish"  @click="publishEv($event)">推送</button>
                    <button class="btn btn-raised btn-primary waves-effect waves-light" id="unpublish"  @click="unpublishEv($event)">不推送</button>
                    </div>
                </div>
            </div>
            </div>
            <ul class="collapsible card agora-secondary-border" style="margin-top: .4rem; border: 1px ">
            <li>
                <div class="collapsible-header agora-secondary-bg">
                </div>
                <div class="collapsible-body card-content">
                <div class="row">
                    <div class="col s12">
                    <div class="input-field">
                        <label for="UID" class="active">UID</label>
                        <input type="number" placeholder="UID" name="uid">
                    </div>
                    <div class="input-field">
                        <label for="cameraId" class="active">CAMERA</label>
                        <select name="cameraId" id="cameraId"></select>
                    </div>
                    <div class="input-field">
                        <label for="microphoneId" class="active">MICROPHONE</label>
                        <select name="microphoneId" id="microphoneId"></select>
                    </div>
                    <div class="input-field">
                        <label for="cameraResolution" class="active">CAMERA RESOLUTION</label>
                        <select name="cameraResolution" id="cameraResolution"></select>
                    </div>
                    <div class="row col s12">
                        <div class="row">
                        <label for="mode" class="active">MODE</label>
                        </div>
                        <div class="row">
                        <label>
                            <input type="radio" class="with-gap" name="mode" value="live" checked />
                            <span>live</span>
                        </label>

                        <label>
                            <input type="radio" class="with-gap" name="mode" value="rtc" />
                            <span>rtc</span>
                        </label>
                        </div>
                    </div>
                    <div class="row col s12">
                        <div class="row">
                        <label for="codec" class="active">CODEC</label>
                        </div>
                        <div class="row">
                        <label>
                            <input type="radio" class="with-gap" name="codec" value="h264" checked />
                            <span>h264</span>
                        </label>

                        <label>
                            <input type="radio" class="with-gap" name="codec" value="vp8" />
                            <span>vp8</span>
                        </label>
                        </div>
                    </div>
                    </div>
                </div>
                </div>
            </li>
            </ul>
        </div>
        <div class="col s7">
            <div class="video-grid" id="video">
            <div class="video-view">
                <div id="local_stream" class="video-placeholder" ></div>
                <div id="local_video_info" class="video-profile hide"></div>
                <div id="video_autoplay_local" class="autoplay-fallback hide"></div>
            </div>
            </div>
        </div>
        </div>
    </form>
  </div>
</template>

<script>
  import  "@/vendor/jquery.min.js"
  import  "@/vendor/materialize.min.js"
  import  "@/AgoraRTCSDK-3.0.2.js"
  import LeftNav from '@/components/LeftNav'
  import axios from 'axios'
  import store from '@/vuex/store'
export default {
    components:{
        LeftNav
    },
    data() {
        return {
            appid:'5a2b09687d6748559b9f0b5319b1bf54',
            channel:'demo',
            rece:false,
            resolutions : [
                {
                    name: 'default',
                    value: 'default',
                },
                {
                    name: '480p',
                    value: '480p',
                },
                {
                    name: '720p',
                    value: '720p',
                },
                {
                    name: '1080p',
                    value: '1080p'
                }
            ],

            Toast : {
                info: (msg) => {
                    this.Toastify({
                    text: msg,
                    classes: "info-toast"
                    })
                },
                notice: (msg) => {
                    this.Toastify({
                    text: msg,
                    classes: "notice-toast"
                    })
                },
                error: (msg) => {
                    this.Toastify({
                    text: msg,
                    classes: "error-toast"
                    })
                }
            },

            rtc : {
                client: null,
                joined: false,
                published: false,
                localStream: null,
                remoteStreams: [],
                params: {}
            },

            fields : [
                'appID', 'channel'
            ],
          }  
        },

        
    created() {
        console.log("agora sdk version: " + AgoraRTC.VERSION + " compatible: " + AgoraRTC.checkSystemRequirements());
        if(this.$route.params.channel){
            this.channel = this.$route.params.channel;
            this.rece=true;
        };
        if(this.$route.params.username){
            alert(this.$route.params.username+"邀请你视频聊天，请点击连接");
        }
    },  


    methods: {
     
      getDevices(devices) {
            devices.audios.forEach(function (audio) {
            $('<option/>', {
                value: audio.value,
                text: audio.name,
            }).appendTo("#microphoneId");
            })
            devices.videos.forEach(function (video) {
            $('<option/>', {
                value: video.value,
                text: video.name,
            }).appendTo("#cameraId");
            })
            resolutions.forEach(function (resolution) {
            $('<option/>', {
                value: resolution.value,
                text: resolution.name
            }).appendTo("#cameraResolution");
            })
            M.AutoInit();
      },  

      joinEv(e) {
        console.log("join");
        if(!this.rece){
            let channel = "demo"+Math.round(Math.random()*1000);
            this.channel = channel;
        }
        console.log("当前频道是："+this.channel);
        if(this.$route.params.username==undefined){
           var mes = {
                otherId:this.$route.params.id.toString(),
                videoFlag:true,
                channel:this.channel
            }
            var json = JSON.stringify(mes);
            //发送
            this.$store.state.socket.send(json);
        }
  
        e.preventDefault();
        var params = this.serializeformData();
        if (this.validator(params, this.fields)) {
          this.join(this.rtc, params);
        }
      },

      publishEv(e) {
        console.log("publish")
        e.preventDefault();
        var params = this.serializeformData();
        if (this.validator(params, this.fields)) {
          this.publish(this.rtc);
        }
      },

      unpublishEv(e) {
        console.log("unpublish")
        e.preventDefault();
        var params = this.serializeformData();
        if (this.validator(params, this.fields)) {
          this.unpublish(this.rtc);
        }
      },

     leaveEv(e) {
        console.log("leave")
        e.preventDefault();
        var params = this.serializeformData();
        if (this.validator(params, this.fields)) {
          this.leave(this.rtc);
        }
      },
   
        Toastify (options) {
            M.toast({html: options.text, classes: options.classes});
        },

        validator(formData, fields) {
            var keys = Object.keys(formData);
            for (let key of keys) {
                if (fields.indexOf(key) != -1) {
                    if (!formData[key]) {
                        this.Toast.error("Please Enter " + key);
                        return false;
                    }
                }
            }
            return true;
        },

        serializeformData() {
            var formData = $("#form").serializeArray();
            formData[1].value = this.channel;
            var obj = {}
            for (var item of formData) {
                var key = item.name;
                var val = item.value;
                obj[key] = val;
            }
            return obj;
        },  

        addView (id, show) {
            if (!$("#" + id)[0]) {
                $("<div/>", {
                id: "remote_video_panel_" + id,
                class: "video-view",
                }).appendTo("#video");

                $("<div/>", {
                id: "remote_video_" + id,
                class: "video-placeholder",
                }).appendTo("#remote_video_panel_" + id);

                $("<div/>", {
                id: "remote_video_info_" + id,
                class: "video-profile " + (show ? "" :  "hide"),
                }).appendTo("#remote_video_panel_" + id);

                $("<div/>", {
                id: "video_autoplay_"+ id,
                class: "autoplay-fallback hide",
                }).appendTo("#remote_video_panel_" + id);
            }
        },

        removeView (id) {
            if ($("#remote_video_panel_" + id)[0]) {
                $("#remote_video_panel_"+id).remove();
            }
        },

        getDevices (next) {
            AgoraRTC.getDevices(function (items) {
                items.filter(function (item) {
                    return ['audioinput', 'videoinput'].indexOf(item.kind) !== -1
                })
                .map(function (item) {
                    return {
                        name: item.label,
                        value: item.deviceId,
                        kind: item.kind,
                    }
                });
                var videos = [];
                var audios = [];
                for (var i = 0; i < items.length; i++) {
                var item = items[i];
                if ('videoinput' == item.kind) {
                    var name = item.label;
                    var value = item.deviceId;
                    if (!name) {
                        name = "camera-" + videos.length;
                    }
                    videos.push({
                        name: name,
                        value: value,
                        kind: item.kind
                    });
                }
                if ('audioinput' == item.kind) {
                    var name = item.label;
                    var value = item.deviceId;
                    if (!name) {
                        name = "microphone-" + audios.length;
                    }
                    audios.push({
                        name: name,
                        value: value,
                        kind: item.kind
                    });
                }
                }
                next({videos: videos, audios: audios});
            });
        },


        handleEvents (rtc) {
            let _this = this;
            // Occurs when an error message is reported and requires error handling.
            rtc.client.on("error", (err) => {
                console.log(err)
            })
            // Occurs when the peer user leaves the channel; for example, the peer user calls Client.leave.
            rtc.client.on("peer-leave", function (evt) {
                var id = evt.uid;
                console.log("id", evt);
                if (id != rtc.params.uid) {
                _this.removeView(id);
                }
                _this.Toast.notice("peer leave")
                console.log('peer-leave', id);
            })
            // Occurs when the local stream is published.
            rtc.client.on("stream-published", function (evt) {
                _this.Toast.notice("stream published success")
                console.log("stream-published");
            })
            // Occurs when the remote stream is added.
            rtc.client.on("stream-added", function (evt) {  
                var remoteStream = evt.stream;
                var id = remoteStream.getId();
                _this.Toast.info("stream-added uid: " + id)
                if (id !== rtc.params.uid) {
                rtc.client.subscribe(remoteStream, function (err) {
                    console.log("stream subscribe failed", err);
                })
                }
                console.log('stream-added remote-uid: ', id);
            });
            // Occurs when a user subscribes to a remote stream.
            rtc.client.on("stream-subscribed", function (evt) {
                var remoteStream = evt.stream;
                var id = remoteStream.getId();
                rtc.remoteStreams.push(remoteStream);
                _this.addView(id);
                remoteStream.play("remote_video_" + id);
                var doc = document.getElementById("remote_video_" + id);
                    if(doc){
                        doc.style.position=''
                    }
                _this.Toast.info('stream-subscribed remote-uid: ' + id);
                console.log('stream-subscribed remote-uid: ', id);
            })
            // Occurs when the remote stream is removed; for example, a peer user calls Client.unpublish.
            rtc.client.on("stream-removed", function (evt) {
                var remoteStream = evt.stream;
                var id = remoteStream.getId();
                _this.Toast.info("stream-removed uid: " + id)
                remoteStream.stop("remote_video_" + id);
                rtc.remoteStreams = rtc.remoteStreams.filter(function (stream) {
                return stream.getId() !== id
                })
                _this.removeView(id);
                console.log('stream-removed remote-uid: ', id);
            })
            rtc.client.on("onTokenPrivilegeWillExpire", function(){
                // After requesting a new token
                // rtc.client.renewToken(token);
                _this.Toast.info("onTokenPrivilegeWillExpire")
                console.log("onTokenPrivilegeWillExpire")
            });
            rtc.client.on("onTokenPrivilegeDidExpire", function(){
                // After requesting a new token
                // client.renewToken(token);
                _this.Toast.info("onTokenPrivilegeDidExpire")
                console.log("onTokenPrivilegeDidExpire")
            })
        },

         /**
      * rtc: rtc object
      * option: {
      *  mode: string, 'live' | 'rtc'
      *  codec: string, 'h264' | 'vp8'
      *  appID: string
      *  channel: string, channel name
      *  uid: number
      *  token; string,
      * }
     **/
        join (rtc, option) {
            if (rtc.joined) {
                this.Toast.error("Your already joined");
                return;
            }

            /**
             * A class defining the properties of the config parameter in the createClient method.
             * Note:
             *    Ensure that you do not leave mode and codec as empty.
             *    Ensure that you set these properties before calling Client.join.
             *  You could find more detail here. https://docs.agora.io/en/Video/API%20Reference/web/interfaces/agorartc.clientconfig.html
             **/
            rtc.client = AgoraRTC.createClient({mode: option.mode, codec: option.codec});

            rtc.params = option;

            // handle AgoraRTC client event
            this.handleEvents(rtc);

            let _this = this;
            // init client
            rtc.client.init(option.appID, function () {             
                console.log("init success");

                /**
                 * Joins an AgoraRTC Channel
                 * This method joins an AgoraRTC channel.
                 * Parameters
                 * tokenOrKey: string | null
                 *    Low security requirements: Pass null as the parameter value.
                 *    High security requirements: Pass the string of the Token or Channel Key as the parameter value. See Use Security Keys for details.
                 *  channel: string
                 *    A string that provides a unique channel name for the Agora session. The length must be within 64 bytes. Supported character scopes:
                 *    26 lowercase English letters a-z
                 *    26 uppercase English letters A-Z
                 *    10 numbers 0-9
                 *    Space
                 *    "!", "#", "$", "%", "&", "(", ")", "+", "-", ":", ";", "<", "=", ".", ">", "?", "@", "[", "]", "^", "_", "{", "}", "|", "~", ","
                 *  uid: number | null
                 *    The user ID, an integer. Ensure this ID is unique. If you set the uid to null, the server assigns one and returns it in the onSuccess callback.
                 *   Note:
                 *      All users in the same channel should have the same type (number or string) of uid.
                 *      If you use a number as the user ID, it should be a 32-bit unsigned integer with a value ranging from 0 to (232-1).
                **/
                rtc.client.join(option.token ? option.token : null, option.channel, option.uid ? +option.uid : null, function (uid) {
                _this.Toast.notice("join channel: " + option.channel + " success, uid: " + uid);
                console.log("join channel: " + option.channel + " success, uid: " + uid);
                rtc.joined = true;

                rtc.params.uid = uid;

                // create local stream
                rtc.localStream = AgoraRTC.createStream({
                    streamID: rtc.params.uid,
                    audio: true,
                    video: true,
                    screen: false,
                    microphoneId: option.microphoneId,
                    cameraId: option.cameraId
                })

                // init local stream
                rtc.localStream.init(function () {
                    console.log("init local stream success");
                    // play stream with html element id "local_stream"
                    rtc.localStream.play("local_stream");
                    var doc = document.getElementById("video"+uid);
                    if(doc){
                        doc.style.position='relative'
                    }

                    // publish local stream
                    _this.publish(rtc);
                }, function (err)  {
                    _this.Toast.error("stream init failed, please open console see more detail")
                    console.error("init local stream failed ", err);
                })
                }, function(err) {
                _this.Toast.error("client join failed, please open console see more detail")
                console.error("client join failed", err)
                })
            }, (err) => {
                _this.Toast.error("client init failed, please open console see more detail")
                console.error(err);
            });

            
        },

       publish (rtc) {
            let _this = this;
            if (!rtc.client) {
                _this.Toast.error("Please Join Room First");
                return;
            }
            if (rtc.published) {
                _this.Toast.error("Your already published");
                return;
            }
            var oldState = rtc.published;

            // publish localStream
            rtc.client.publish(rtc.localStream, function (err) {
                rtc.published = oldState;
                console.log("publish failed");
                _this.Toast.error("publish failed")
                console.error(err);
            })
            _this.Toast.info("publish")
            rtc.published = true
        },

        unpublish (rtc) {
            let _this = this;
            if (!rtc.client) {
                _this.Toast.error("Please Join Room First");
                return;
            }
            if (!rtc.published) {
                _this.Toast.error("Your didn't publish");
                return;
            }
            var oldState = rtc.published;
            rtc.client.unpublish(rtc.localStream, function (err) {
                rtc.published = oldState;
                console.log("unpublish failed");
                _this.Toast.error("unpublish failed");
                console.error(err);
            })
            _this.Toast.info("unpublish")
            rtc.published = false;
        },

        leave (rtc) {
            if (!rtc.client) {
                this.Toast.error("Please Join First!");
                return;
            }
            if (!rtc.joined) {
                this.Toast.error("You are not in channel");
                return;
            }
            /**
             * Leaves an AgoraRTC Channel
             * This method enables a user to leave a channel.
             **/
            let _this = this;
            rtc.client.leave(function () {
                // stop stream
                rtc.localStream.stop();
                // close stream
                rtc.localStream.close();
                while (rtc.remoteStreams.length > 0) {
                var stream = rtc.remoteStreams.shift();
                var id = stream.getId();
                stream.stop();
                _this.removeView(id);
                }
                rtc.localStream = null;
                rtc.remoteStreams = [];
                rtc.client = null;
                console.log("client leaves channel success");
                rtc.published = false;
                rtc.joined = false;
                _this.Toast.notice("leave success");
            }, function (err) {
                console.log("channel leave failed");
                _this.Toast.error("leave success");
                console.error(err);
            })
        },

    


    //end method
    },
    
}


</script>

<style>
@import "../assets/common.css";


</style>