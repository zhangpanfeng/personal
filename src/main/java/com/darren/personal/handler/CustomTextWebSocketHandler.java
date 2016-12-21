//package com.darren.personal.handler;
//
//import org.apache.log4j.Logger;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//
//public class CustomTextWebSocketHandler extends TextWebSocketHandler {
//    private static final Logger LOG = Logger.getLogger(CustomTextWebSocketHandler.class);
//
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//
//        super.handleTextMessage(session, message);
//        LOG.info("GOMA === > WebSocketEndPoint.handlerTextMessage...");
//
//        TextMessage returnMessage = new TextMessage(message.getPayload() + " received at server");
//        session.sendMessage(returnMessage);
//    }
//}