package com.darren.personal.listener;

import java.io.FileNotFoundException;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.WebApplicationContext;

import com.darren.personal.constant.Constant;

@Component
public class InitializationListener implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOG = Logger.getLogger(InitializationListener.class);
    private static final String MAIL_CONFIG = "mailConfig";

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            System.out.println("init");
            ApplicationContext applicationContext = event.getApplicationContext();
            WebApplicationContext webApplicationContext = (WebApplicationContext) applicationContext;
            ServletContext servletContext = webApplicationContext.getServletContext();
            String mailConfig = servletContext.getInitParameter(MAIL_CONFIG);
            try {
                String emailConfigPath = ResourceUtils.getFile(mailConfig).getAbsolutePath();
                servletContext.setAttribute(Constant.EMAIL_CONFIG_PATH, emailConfigPath);
            } catch (FileNotFoundException e) {
                LOG.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }

}
