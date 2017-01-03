package com.darren.personal.job;

import java.util.List;
import java.util.concurrent.Future;
import com.darren.personal.entity.Customer;
import com.darren.personal.entity.Mail;
import com.darren.personal.util.MailUtil;

public class EmailTask implements Runnable {
    private static final String TABLE_STYLE = "style='border:solid #ADD9C0; border-width:1px 0px 0px 1px;'";
    private static final String TD_STYLE = "style='border:solid #add9c0; border-width:0px 1px 1px 0px;'";
    private static final Mail MAIL = new Mail();
    private List<Customer> customerList;
    private Future<?> future;
    private String configPath;
    private String taskId;

    static {
        MAIL.setTo("sf0902@163.com");
        MAIL.setSubject("重要提醒");
        MAIL.setType(Mail.Type.HTML.toString());
    }

    public EmailTask(List<Customer> customerList, String configPath, String taskId) {
        this.customerList = customerList;
        this.configPath = configPath;
        this.taskId = taskId;
    }

    @Override
    public void run() {
        System.out.println("run email task");
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
        }
    }

    public void setFuture(Future<?> future) {
        this.future = future;
    }

    public Future<?> getFuture() {
        return future;
    }

}
