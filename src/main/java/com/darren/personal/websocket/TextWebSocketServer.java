package com.darren.personal.websocket;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;

@ServerEndpoint("/textserver")
public class TextWebSocketServer {
    private static final Logger LOG = Logger.getLogger(TextWebSocketServer.class);
    private static final ConcurrentHashMap<String, TextWebSocketServer> SOCKET_MAP = new ConcurrentHashMap<String, TextWebSocketServer>();
    private static final String TOKEN = "token:";
    private String token;
    private Session session;
    private boolean isConnected;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        this.isConnected = true;
        token = UUID.randomUUID().toString();
        SOCKET_MAP.put(token, this);
        this.onMessage(TOKEN + token);

        System.out.println("Client connected");
    }

    @OnMessage
    public void onMessage(String message) {
        if (this.session != null) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                LOG.info(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @OnError
    public void onError(Throwable t) {
        System.out.println("onError ");
    }

    @OnClose
    public void onClose() {
        this.isConnected = false;
        SOCKET_MAP.remove(token);
        if (session != null) {
            try {
                session.close();
            } catch (IOException e) {
                LOG.info(e.getMessage());
                e.printStackTrace();
            }
        }
        System.out.println("Connection closed");
    }

    public boolean isConnected() {
        return isConnected;
    }

    /**
     * get WebSocketServer
     * 
     * @param key WebSocket Session Id
     * @return WebSocketServer
     */
    public static TextWebSocketServer getSocketServer(String key) {
        return SOCKET_MAP.get(key);
    }

}
