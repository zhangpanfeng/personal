package com.darren.personal.task;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import com.darren.personal.entity.Customer;
import com.darren.personal.entity.Mail;
import com.darren.personal.job.EmailScheduleJob;
import com.darren.personal.util.MailUtil;
import com.darren.personal.util.PropertiesUtil;

public class EmailTask implements Runnable {
    private static final Logger LOG = Logger.getLogger(EmailTask.class);
    private static final String TABLE_STYLE = "style='border:solid #ADD9C0; border-width:1px 0px 0px 1px;'";
    private static final String TD_STYLE = "style='border:solid #add9c0; border-width:0px 1px 1px 0px;'";
    private static final Mail MAIL = new Mail();
    private static final String MAIL_SCHEDULE_TO = "mail.schedule.to";
    private static final String MAIL_SCHEDULE_SUBJECT = "mail.schedule.subject";
    private static final String MAIL_TASK_PATH = "mail.task.path";
    private List<Customer> customerList;
    private Future<?> future;
    private String configPath;
    private String taskId;
    private Properties props;

    static {
        MAIL.setType(Mail.Type.HTML.toString());
    }

    public EmailTask(List<Customer> customerList, String configPath, String taskId) {
        this.customerList = customerList;
        this.configPath = configPath;
        this.taskId = taskId;

        props = PropertiesUtil.readProperties(configPath);
        MAIL.setTo(props.getProperty(MAIL_SCHEDULE_TO));
        MAIL.setSubject(props.getProperty(MAIL_SCHEDULE_SUBJECT));
    }

    @Override
    public void run() {
        LOG.info("run email task");
        StringBuilder builder = new StringBuilder();
        builder.append("<table " + TABLE_STYLE + ">");
        builder.append("<thead>");
        builder.append("<tr>");
        builder.append("<th width='100' " + TD_STYLE + ">").append("手机号").append("</th>");
        builder.append("<th width='100' " + TD_STYLE + ">").append("姓名").append("</th>");
        builder.append("<th width='500' " + TD_STYLE + ">").append("备注").append("</th>");
        builder.append("</tr>");
        builder.append("</thead>");
        builder.append("<tbody>");
        for (Customer customer : customerList) {
            builder.append("<tr>");
            builder.append("<td " + TD_STYLE + ">").append(customer.getPhone()).append("</td>");
            builder.append("<td " + TD_STYLE + ">").append(customer.getName()).append("</td>");
            builder.append("<td " + TD_STYLE + ">").append(customer.getComment()).append("</td>");
            builder.append("</tr>");
        }
        builder.append("</tbody>");
        builder.append("</table>");
        MAIL.setText(builder.toString());
        boolean result = MailUtil.send(MAIL, configPath);
        if (result) {
            // task finish, cancel schedule
            future.cancel(true);
            // task finish, remove task
            EmailScheduleJob.removeTask(taskId);
            // remove task list
            PropertiesUtil.delete(props.getProperty(MAIL_TASK_PATH), taskId);
        }
    }

    public void setFuture(Future<?> future) {
        this.future = future;
    }

    public Future<?> getFuture() {
        return future;
    }

}
