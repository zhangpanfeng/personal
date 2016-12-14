package com.darren.personal.util;

import com.darren.personal.entity.Mail;

public class MailTest {

    public static void main(String[] args) {
        Mail mail = new Mail();
        mail.setFrom("sf0902@163.com");
        mail.setTo("pf0902@126.com");
        mail.setSubject("test mail sender");
        mail.setText("<h1>Hello World!</h1><br><h2>title title title</h2>");
        mail.setType(Mail.Type.HTML.toString());
        
        String configPath = System.getProperty("user.dir") + "\\src\\main\\resources\\mail.properties";
        MailUtil.send(mail, configPath);

    }

}
