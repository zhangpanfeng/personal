package com.darren.personal.websocket;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.CloseReason;
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
    private Session session;
    private boolean isConnected;
    private String key;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        this.isConnected = true;
        key = UUID.randomUUID().toString();
        SOCKET_MAP.put(key, this);
        this.onMessage(key);

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
    public void onClose(Session session, CloseReason reason) {
        this.isConnected = false;
        SOCKET_MAP.remove(key);
        System.out.println("Connection closed");
    }

    public boolean isConnected() {
        return isConnected;
    }

    /**
     * get WebSocketServer
     * @param key 
     * @return WebSocketServer
     */
    public static TextWebSocketServer getSocketServer(String key) {
        return SOCKET_MAP.get(key);
    }

}
