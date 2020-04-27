import Vue from 'vue';
import Vuex from 'vuex';
import Message from '@/components/Message'

Vue.use(Vuex);

const state = {
    username:'',
    userid : '',
    socket:{},
};

//读取cookie，需要注意的是cookie是不能存中文的，如果需要存中文，解决方法是后端先进行编码encode()，前端取出来之后用decodeURI('string')解码。（安卓可以取中文cookie，IOS不行）
function getCookie(name) {
    var arr;
    var reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg)){
        return arr[2];
    }else{
      return null;
    }
 };
 
 //设置cookie   name为cookie的名字，value是值，expiredays为过期时间（天数）
function setCookie (name, value, expiredays) {
    var exdate = new Date();
    exdate.setDate(exdate.getDate() + expiredays);
    document.cookie = name + "=" + escape(value) + ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString());
};
 
  //删除cookie
function delCookie (name){
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval = getCookie(name);
    if (cval != null)
    document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
};

function createSocket(userid,state){
    var socket;
        if(typeof(WebSocket) == "undefined") {
            console.log("您的浏览器不支持WebSocket");
        }else{
            console.log("您的浏览器支持WebSocket");
            //实现化WebSocket对象，指定要连接的服务器地址与端口  建立连接
            let socketUrl = 'ws://localhost:8081/websocket/'+userid;
            socket = new WebSocket(socketUrl);
            state.socket = socket;
            //打开事件
            socket.onopen = function() {
                console.log("Socket 已打开");
            };
            // 获得消息事件
            // socket.onmessage = function(msg) {
            //     console.log(msg.data);
            //     return msg.data;
            // };
            //关闭事件
            socket.onclose = function() {
                console.log("Socket已关闭");
            };
            //发生了错误事件
            socket.onerror = function() {
                console.log("Socket发生了错误");
            }
    
            //关闭连接
            function closeWebSocket() {
                socket.close();
            }
    
            //发送消息
            function send(message) {
                socket.send(message);
            }
    
        }    
};

const mutations={
    setUser(state){
        state.username = getCookie('username');
        state.userid  = getCookie('userid');
    },
    newSocket(state){
        let userid = state.userid;
        if(userid!=null&&userid!=''){
            createSocket(userid,state); 
        }  
    }
}

export default new Vuex.Store({
   state,mutations
})



