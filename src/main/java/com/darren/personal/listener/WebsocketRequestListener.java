package com.darren.personal.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class WebsocketRequestListener implements ServletRequestListener{

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void requestInitialized(ServletRequestEvent requestEvent) {
        @SuppressWarnings("unused")
        HttpSession session = ((HttpServletRequest) requestEvent.getServletRequest()).getSession();
        
    }

}
