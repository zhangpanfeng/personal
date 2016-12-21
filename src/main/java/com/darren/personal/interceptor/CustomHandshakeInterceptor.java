//package com.darren.personal.interceptor;
//
//import java.util.Map;
//
//import org.apache.log4j.Logger;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
//
//public class CustomHandshakeInterceptor extends HttpSessionHandshakeInterceptor {
//    private static final Logger LOG = Logger.getLogger(CustomHandshakeInterceptor.class);
//
//    @Override
//    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
//            Map<String, Object> attributes) throws Exception {
//        LOG.info("beforeHandshake");
//        return super.beforeHandshake(request, response, wsHandler, attributes);
//    }
//
//    @Override
//    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
//            Exception ex) {
//        LOG.info("afterHandshake");
//        super.afterHandshake(request, response, wsHandler, ex);
//    }
//
//}
