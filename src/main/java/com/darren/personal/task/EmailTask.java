package com.darren.personal.task;

import java.util.Properties;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import com.darren.personal.constant.Constant;
import com.darren.personal.entity.Mail;
import com.darren.personal.job.EmailScheduleJob;
import com.darren.personal.service.CustomerService;
import com.darren.personal.util.MailUtil;
import com.darren.personal.util.PropertiesUtil;
import com.darren.personal.util.SpringContextUtil;

public class EmailTask implements Runnable {
    private static final Logger LOG = Logger.getLogger(EmailTask.class);
    private static final Mail MAIL = new Mail();
    private static final String MAIL_SCHEDULE_TO = "mail.schedule.to";
    private static final String MAIL_SCHEDULE_SUBJECT = "mail.schedule.subject";
    private String emailContent;
    private Future<?> future;
    private String configPath;
    private String taskId;
    private Properties props;

    static {
        MAIL.setType(Mail.Type.HTML.toString());
    }

    public EmailTask(String emailContent, String configPath, String taskId) {
        this.emailContent = emailContent;
        this.configPath = configPath;
        this.taskId = taskId;

        props = PropertiesUtil.readProperties(configPath);
        MAIL.setTo(props.getProperty(MAIL_SCHEDULE_TO));
        MAIL.setSubject(props.getProperty(MAIL_SCHEDULE_SUBJECT));

        // write the schedule task to file
        PropertiesUtil.save(props.getProperty(Constant.MAIL_TASK_PATH), taskId, emailContent);
        LOG.info("Write the schedule task to file taskId: " + taskId);
    }

    @Override
    public void run() {
        LOG.info("Run the schedule task taskId: " + taskId);
        emailContent = emailContent.substring(emailContent.indexOf(Constant.DELIMITER) + 1);
        MAIL.setText(emailContent);
        boolean result = MailUtil.send(MAIL, configPath);
        if (result) {
            // task finish, cancel schedule
            future.cancel(true);
            // task finish, remove task
            EmailScheduleJob.removeTask(taskId);
            // remove task list
            PropertiesUtil.delete(props.getProperty(Constant.MAIL_TASK_PATH), taskId);
            LOG.info("Remove the finished schedule task from the file taskId: " + taskId);

            // update the customer, set the email state to Y
            CustomerService customerService = (CustomerService) SpringContextUtil.getBean(CustomerService.class);
            customerService.updateScheduledCustomer(taskId);
        }
    }

    public void setFuture(Future<?> future) {
        this.future = future;
    }

    public Future<?> getFuture() {
        return future;
    }

}
