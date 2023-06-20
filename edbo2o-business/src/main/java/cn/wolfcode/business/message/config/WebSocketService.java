package cn.wolfcode.business.message.config;

import cn.wolfcode.common.utils.StringUtils;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/imserver/{userId}")
@Component
public class WebSocketService {
    private static int onlineCount=0;
    private static ConcurrentHashMap<String,WebSocketService> webSocketMap = new ConcurrentHashMap<>();
    private Session session;
    private String userId = "";

    @OnOpen
    public void onPone(Session session, @PathParam("userId") String userId) {
        this.session = session;
        this.userId = userId;
        if(webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
            webSocketMap.put(userId,this);
        } else {
            webSocketMap.put(userId,this);
            addOnlineCount();
        }
        System.out.println("在线人数:" + getOnlineCount());
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("用户:" + userId + ",连接失败");
        }
    }

    @OnClose
    public void onClose() {
        if(webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
            subOnlineCount();
        }
        System.out.println("在线人数:" + getOnlineCount());
    }

    @OnMessage
    public void onMessage(String message,Session session) {
        System.out.println("用户:" + userId + ",报文:" + message);
        if(!StringUtils.isEmpty(message)) {
            try {
                JSONObject jsonObject = JSON.parseObject(message);
                jsonObject.put("fromUserId",this.userId);
                String toUserId = jsonObject.getString("toUserId");
                if(!StringUtils.isEmpty(toUserId) && webSocketMap.containsKey(toUserId)) {
                    webSocketMap.get(toUserId).sendMessage(jsonObject.toJSONString());
                } else {
                    System.out.println("用户:" + toUserId + "不在服务器上");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 发送自定义消息
    public static void sendInfo(String message,@PathParam("userId") String userId) throws IOException {
        System.out.println("用户:" + userId + ",报文:" + message);
        if (!StringUtils.isEmpty(userId) && webSocketMap.containsKey(userId)) {
            webSocketMap.get(userId).sendMessage(message);
        } else {
            System.out.println("用户:" + userId + ",连接失败");
        }
    }


    public static synchronized int getOnlineCount(){
        return onlineCount;
    }

    private static synchronized void addOnlineCount() {
        WebSocketService.onlineCount++;
    }

    private static synchronized void subOnlineCount() {
        WebSocketService.onlineCount--;
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }
}