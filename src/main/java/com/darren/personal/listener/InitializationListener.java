package com.darren.personal.listener;

import java.io.FileNotFoundException;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.WebApplicationContext;

@Component  
public class InitializationListener implements ApplicationListener<ContextRefreshedEvent> {
    private static final String MAIL_CONFIG = "mailConfig";
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(event.getApplicationContext().getParent() == null){
            System.out.println("init");
            ApplicationContext applicationContext = event.getApplicationContext();
            WebApplicationContext webApplicationContext = (WebApplicationContext)applicationContext;
            ServletContext servletContext = webApplicationContext.getServletContext();
            String mailConfig = servletContext.getInitParameter(MAIL_CONFIG);
            System.out.println(servletContext.getContextPath());
            try {
            String  configPath = ResourceUtils.getFile("classpath:mail.properties").getAbsolutePath();
            System.out.println(configPath);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        }
    }

}



  