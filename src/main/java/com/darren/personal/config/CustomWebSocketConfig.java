//package com.darren.personal.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.config.annotation.EnableWebSocket;
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//import org.springframework.web.socket.server.HandshakeInterceptor;
//
//import com.darren.personal.handler.CustomTextWebSocketHandler;
//import com.darren.personal.interceptor.CustomHandshakeInterceptor;
//
//@Configuration
//@EnableWebSocket
//public class CustomWebSocketConfig implements WebSocketConfigurer {
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        //setAllowedOrigins方法用来设置来自那些域名的请求可访问，默认为localhost
//        registry.addHandler(getWebSocketHandler(), "/websocket2").addInterceptors(getInterceptors())
//                .setAllowedOrigins("*");
//
//    }
//
//    @Bean
//    public WebSocketHandler getWebSocketHandler() {
//        return new CustomTextWebSocketHandler();
//    }
//
//    @Bean
//    public HandshakeInterceptor getInterceptors() {
//        return new CustomHandshakeInterceptor();
//    }
//}
