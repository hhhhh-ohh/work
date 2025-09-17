package com.wanmi.sbc.websocket;

import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author edz
 * @className WsSessionManager
 * @description TODO
 * @date 2021/12/30 19:14
 **/
public class WebSocketSessionManager {
    /**
     * 保存连接 session 的地方
     */
    private static final ConcurrentHashMap<String, WebSocketSession> SESSION_POOL = new ConcurrentHashMap<>();

    /**
     * @description 添加 session
     * @author  edz
     * @date: 2021/12/31 16:53
     * @param key
     * @param session
     * @return void
     **/
    public static void add(String key, WebSocketSession session) {
        // 添加 session
        SESSION_POOL.put(key, session);
    }

    /**
     * @description 删除 session,会返回删除的 session
     * @author  edz
     * @date: 2021/12/31 16:53
     * @param key
     * @return org.springframework.web.socket.WebSocketSession
     **/
    public static WebSocketSession remove(String key) {
        // 删除 session
        return SESSION_POOL.remove(key);
    }

    /**
     * @description 删除并同步关闭连接
     * @author  edz
     * @date: 2021/12/31 16:53
     * @param key
     * @return void
     **/
    public static void removeAndClose(String key) {
        WebSocketSession session = remove(key);
        if (session != null) {
            try {
                // 关闭连接
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @description 获得 session
     * @author  edz
     * @date: 2021/12/31 16:53
     * @return java.util.List<org.springframework.web.socket.WebSocketSession>
     **/
    public static List<WebSocketSession> get() {
        // 获得 session
        return new ArrayList<>(SESSION_POOL.values());
    }
}
