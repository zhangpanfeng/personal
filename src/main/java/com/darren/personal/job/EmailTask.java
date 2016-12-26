package com.darren.personal.job;

import java.util.List;
import java.util.concurrent.Future;

import org.codehaus.jackson.map.DeserializationConfig.Feature;

import com.darren.personal.entity.Customer;
import com.darren.personal.entity.Mail;
import com.darren.personal.util.MailUtil;

public class EmailTask implements Runnable {
    private static final Mail MAIL = new Mail();
    private List<Customer> customerList;
    private Future<?> future;
    
    static {
        MAIL.setTo("sf0902@163.com");
        MAIL.setSubject("Please contact these people as soon as posible");
    }

    public EmailTask(List<Customer> customerList) {
        this.customerList = customerList;
    }

    @Override
    public void run() {
        StringBuilder builder = new StringBuilder();
        for(Customer customer : customerList){
            builder.append("Name: ").append(customer.getName()).append(" ");
            builder.append("MobilePhone: ").append(customer.getPhone()).append(" ");
            builder.append("Comment: ").append(customer.getComment()).append("\n");
        }
        MAIL.setText(builder.toString());
//        MailUtil.send(mail, configPath)
        System.out.println("Hello");
        future.cancel(true);
    }

    public void setFuture(Future<?> future) {
        this.future = future;
    }

}
