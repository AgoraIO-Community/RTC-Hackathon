package com.dzw.web;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.dzw.dao.UserMapper;
import com.dzw.entity.User;
import com.dzw.service.ChatService;
import com.dzw.util.JSONChange;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@ServerEndpoint(value = "/websocket/{user}",encoders = { ServerEncoder.class })
@Component
public class WebSocketServer {

    private final static Logger logger = Logger.getLogger(WebSocketServer.class);

    static ChatService chatService;

    static UserMapper userMapper;

    @Autowired
    public void setChatService(ChatService chatService) {
        WebSocketServer.chatService = chatService;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        WebSocketServer.userMapper = userMapper;
    }

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        logger.info("有新连接加入！当前在线人数为" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        logger.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("user")String userId) throws IOException, EncodeException {
        logger.info("来自客户端的消息:" + message);
        LinkedHashMap<String,Object> mesMap = (LinkedHashMap<String,Object>) JSONChange.jsonToObj(new Object(),message);
        Integer sentOtherId =Integer.valueOf((String) mesMap.get("otherId")) ;
        String mes = (String) mesMap.get("context");
        Boolean videoFlag = (Boolean) mesMap.get("videoFlag");
        String channel = (String) mesMap.get("channel");
        for (WebSocketServer item : webSocketSet) {
                Integer otherId = null;
                List<String> listother = item.session.getRequestParameterMap().get("user");
                if (listother!= null) {
                    otherId =Integer.valueOf(listother.get(0)) ;
                }
                if(otherId==sentOtherId) {
                    if (videoFlag!=null) {
                        String username = userMapper.selectByPrimaryKey(Integer.valueOf(userId)).getUsername();
                        MessagePojo messagePojo = new MessagePojo(username,String.valueOf(otherId),videoFlag,channel);
                        item.sendObjectToUser(messagePojo);
                    }else {
                        MessagePojo messagePojo = new MessagePojo(userId,mes,String.valueOf(otherId));
                        item.sendObjectToUser(messagePojo);
                        chatService.sentMessage(mes,Integer.valueOf(userId),sentOtherId);
                    }
                }
        }

    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("发生错误");
        error.printStackTrace();
    }


    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public void sendObjectToUser(Object object) throws IOException, EncodeException {
        this.session.getBasicRemote().sendObject(object);
    }

    /**
     * 群发自定义消息
     * */
    public static void sendInfo(String message) throws IOException {
        logger.info(message);
        for (WebSocketServer item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}


