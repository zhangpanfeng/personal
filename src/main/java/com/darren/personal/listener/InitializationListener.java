package com.darren.personal.listener;

import java.io.File;
import java.util.Date;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.WebApplicationContext;

import com.darren.personal.constant.Constant;
import com.darren.personal.job.EmailScheduleJob;
import com.darren.personal.util.PropertiesUtil;
import com.darren.personal.util.SpringContextUtil;

@Component
public class InitializationListener implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOG = Logger.getLogger(InitializationListener.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            LOG.info("init");
            ApplicationContext applicationContext = event.getApplicationContext();
            //save applicationContext to static variable
            SpringContextUtil.setApplicationContext(applicationContext);
            WebApplicationContext webApplicationContext = (WebApplicationContext) applicationContext;
            ServletContext servletContext = webApplicationContext.getServletContext();
            String mailConfig = servletContext.getInitParameter(Constant.MAIL_CONFIG_NAME);
            try {
                String emailConfigPath = ResourceUtils.getFile(mailConfig).getAbsolutePath();
                servletContext.setAttribute(Constant.EMAIL_CONFIG_PATH, emailConfigPath);

                Properties props = PropertiesUtil.readProperties(emailConfigPath);
                File file = new File(props.getProperty(Constant.MAIL_TASK_PATH));
                if (!file.exists()) {
                    file.createNewFile();
                }

                props = PropertiesUtil.readProperties(file);
                Set<Object> keySet = props.keySet();
                for (Object key : keySet) {
                    String taskId = (String) key;
                    String emailContent = props.getProperty(taskId);
                    String[] array = emailContent.split(Constant.SPLIT_DELIMITER);
                    long localSendTime = Long.parseLong(array[0]);
                    long newLocalSendTime = localSendTime;
                    long now = new Date().getTime();
                    if(localSendTime <= now){
                        //if send time is time out, assign a new value = now + 5 minutes
                        newLocalSendTime = now + 5 * 60 * 1000;
                    }else{
                      //if send time is not time out, assign a new value = newLocalSendTime + 5 minutes, delay 5 minutes
                        newLocalSendTime = newLocalSendTime + 5 * 60 * 1000;
                    }
                    emailContent.replaceFirst(String.valueOf(localSendTime), String.valueOf(newLocalSendTime));
                    //reSchedule the unfinished task
                    EmailScheduleJob.scheduleTask(emailContent, emailConfigPath, taskId, newLocalSendTime);
                }
            } catch (Exception e) {
                LOG.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }

}
