package com.example.demo.controller;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.domain.Message;
import org.apache.commons.lang3.StringUtils;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


//@ServerEndpoint("/listenServer/{userId}")
//public class WebSocketServer {
//    static Log log=LogFactory.getLog(WebSocketServer.class);
//    private static int onlineCount = 0;
//    private  static ConcurrentHashMap<String,WebSocketServer> webSocketMap =new ConcurrentHashMap<>();
//    private Session session;
//    private String userId;
//    @OnOpen
//    public void onOpen(Session session, @Param("userId") String userId){
//        this.session=session;
//        this.userId=userId;
//        if (webSocketMap.contains(userId)) {
//            webSocketMap.remove(userId);
//            webSocketMap.put(userId, this);
//        }
//        else{
//            webSocketMap.put(userId,this);
//            addOnlineCount();
//        }
//        log.info("用户连接数："+userId+"，当前在线人数："+getOnlineCount());
//        try {
//            sendMessage("连接成功");
//        }catch (Exception e){
//            log.error("用户："+userId+"网络异常");
//        }
//    }
//    @OnClose
//    public void onClose(){
//        if (webSocketMap.contains(userId)){
//            webSocketMap.remove(userId);
//            subOnlineCount();
//        }
//
//    }
//    @OnMessage
//    public void onMessage(String message, Session session){
//        log.info("用户消息："+userId+"，报文"+message.toString());
//        if(message!=null){
//            try {
////                //解析发送的报文
////                JSONObject jsonObject = JSON.parseObject(message);
////                //追加发送人(防止串改)
////                jsonObject.put("fromUserId",this.userId);
////                String toUserId=jsonObject.getString("toUserId");
//                //传送给对应toUserId用户的websocket
//                JSONObject jsonObject = JSON.parseObject(message);
////                if(StringUtils.isNotBlank(toUserId)&&webSocketMap.containsKey(toUserId)){
////                    webSocketMap.get(toUserId).sendMessage(jsonObject.toJSONString());
////                }else{
////                    log.error("请求的userId:"+toUserId+"不在该服务器上");
////                    //否则不在这个服务器上，发送到mysql或者redis
////                }
//                for (WebSocketServer webSocketServer: webSocketMap.values()) {
//                            webSocketServer.sendMessage(jsonObject.toJSONString());
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//    }
//    @OnError
//    public void onError(Session session, Throwable error) {
//        log.error("用户错误:"+this.userId+",原因:"+error.getMessage());
//        error.printStackTrace();
//    }
//
//
//
//    private void sendMessage(String message)throws IOException {
//        this.session.getBasicRemote().sendText(message);
//    }
//
//    public static void sendInfo(String message) throws IOException {
//        //log.info("发送消息到:" + userId + "，报文:" + message);
//        if (true) {
//            for (WebSocketServer s:webSocketMap.values()) {
//                    s.sendMessage(message);
//            }
////            webSocketMap.get(userId).sendMessage(message);
//        } else {
//            log.error("用户"  + ",不在线！");
//        }
//    }
//
//    public static synchronized int getOnlineCount() {
//        return onlineCount;
//    }
//
//    public static synchronized void addOnlineCount() {
//        WebSocketServer.onlineCount++;
//    }
//
//    public static synchronized void subOnlineCount() {
//        WebSocketServer.onlineCount--;
//    }
//
//
//}
@ServerEndpoint("/test")
@Component
public class WebSocketServer {
    /**
     * 存放所有在线的客户端
     */
    static Log log=LogFactory.getLog(WebSocketServer.class);
    private static Map<String, Session> clients = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        log.info("有新的客户端连接了: {}"+session.getId());
        //将新用户存入在线的组
        clients.put(session.getId(), session);
    }

    /**
     * 客户端关闭
     * @param session session
     */
    @OnClose
    public void onClose(Session session) {
        log.info("有用户断开了, id为:{}"+session.getId());
        //将掉线的用户移除在线的组里
        clients.remove(session.getId());
    }

    /**
     * 发生错误
     * @param throwable e
     */
    @OnError
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    /**
     * 收到客户端发来消息
     * @param message  消息对象
     */
    @OnMessage
    public void onMessage(String message) {
        log.info("服务端收到客户端发来的消息: {}"+ message);
        this.sendAll(message);
    }

    /**
     * 群发消息
     * @param message 消息内容
     */
    static void sendAll(String message) {
        for (Map.Entry<String, Session> sessionEntry : clients.entrySet()) {
            sessionEntry.getValue().getAsyncRemote().sendText(message);
        }
    }
}
