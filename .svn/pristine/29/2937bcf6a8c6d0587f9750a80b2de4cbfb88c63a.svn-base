package com.wanmi.sbc.websocket;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.order.bean.vo.GrouponInstanceVO;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.codec.SerializationCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * @description
 * @author edz
 * @date 2021/12/31 16:49
 */
@Component
@Slf4j
public class MessageHandler extends TextWebSocketHandler {

    @Autowired private RedissonClient redissonClient;

    @PostConstruct
    public void redisSub() {
        RTopic topic = redissonClient.getTopic("GROUPON_MESSAGE", new SerializationCodec());
        topic.addListener(GrouponInstanceVO.class, (charSequence, grouponInstanceVO) -> {
            if (log.isDebugEnabled()) {
                log.debug("grouponInstanceVO:{}", grouponInstanceVO.toString());
            }
            WebSocketSessionManager.get().forEach(webSocketSession -> {
                try {
                    webSocketSession.sendMessage(new TextMessage(JSONObject.toJSONString(grouponInstanceVO)));
                } catch (IOException e) {
                    log.error("socket文本信息IO异常", e);
                }
            });
        });
    }

    /**
     * @description 建立成功事件
     * @author edz
     * @date: 2021/12/31 16:49
     * @param session
     * @return void
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("session.getId() = " + session.getId());
        // 用户连接成功，放入在线用户缓存
        WebSocketSessionManager.add(session.getId(), session);
    }

    /**
     * @description 断开连接时
     * @author edz
     * @date: 2021/12/31 16:51
     * @param session
     * @param status
     * @return void
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        // 用户退出，移除缓存
        WebSocketSessionManager.remove(session.getId());
        System.out.println("移除成功");
    }
}
