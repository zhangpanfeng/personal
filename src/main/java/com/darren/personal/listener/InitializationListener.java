package com.darren.personal.listener;

import java.io.File;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.WebApplicationContext;

import com.darren.personal.constant.Constant;
import com.darren.personal.util.PropertiesUtil;

@Component
public class InitializationListener implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOG = Logger.getLogger(InitializationListener.class);
    private static final String MAIL_CONFIG = "mailConfig";
    private static final String MAIL_TASK_PATH = "mail.task.path";

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            LOG.info("init");
            ApplicationContext applicationContext = event.getApplicationContext();
            WebApplicationContext webApplicationContext = (WebApplicationContext) applicationContext;
            ServletContext servletContext = webApplicationContext.getServletContext();
            String mailConfig = servletContext.getInitParameter(MAIL_CONFIG);
            try {
                String emailConfigPath = ResourceUtils.getFile(mailConfig).getAbsolutePath();
                servletContext.setAttribute(Constant.EMAIL_CONFIG_PATH, emailConfigPath);
                
                Properties props = PropertiesUtil.readProperties(emailConfigPath);
                File file = new File(props.getProperty(MAIL_TASK_PATH));
                if(!file.exists()){
                    file.createNewFile();
                }
            } catch (Exception e) {
                LOG.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }

}
