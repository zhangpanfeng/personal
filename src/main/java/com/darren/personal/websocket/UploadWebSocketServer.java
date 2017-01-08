package com.darren.personal.websocket;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;
import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;

import com.darren.personal.config.HttpSessionConfigurator;

@ServerEndpoint(value = "/uploadserver", configurator = HttpSessionConfigurator.class)
public class UploadWebSocketServer {
    private static final Logger LOG = Logger.getLogger(UploadWebSocketServer.class);
    private static final ConcurrentHashMap<String, UploadWebSocketServer> SOCKET_MAP = new ConcurrentHashMap<String, UploadWebSocketServer>();
    private Session session;
    private HttpSession httpSession;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        this.session = session;
        this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        SOCKET_MAP.put(this.httpSession.getId(), this);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("onMessage ");
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("onError ");
        error.printStackTrace();
    }

    @OnClose
    public void onClose(CloseReason closeReason) {
        System.out.println("onClose ");
        System.out.println(closeReason.getReasonPhrase());
        if(this.httpSession.getId() != null){
            SOCKET_MAP.remove(this.httpSession.getId());
        }
    }

    public void sendMessage(String message) {
        if (this.session.isOpen()) {
            try {
                this.session.getBasicRemote().sendText(message);
                // this.session.setMaxTextMessageBufferSize(102400);
            } catch (IOException e) {
                LOG.error(e.getMessage());
                e.printStackTrace();
            }

        }
    }

    /**
     * get UploadWebSocketServer
     * 
     * @param key
     *            HttpSession Id
     * @return UploadWebSocketServer
     */
    public static UploadWebSocketServer getSocketServer(String key) {
        return SOCKET_MAP.get(key);
    }

    public static CloseReason getDefaultCloseReason() {
        CloseReason closeReason = new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Task finished successfully");

        return closeReason;
    }
}
