package com.darren.personal.action;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.darren.personal.entity.Mail;
@Controller
public class MailSenderAction {

    @RequestMapping(value = "/sendmail.do")
    public String sendmail(ModelMap model, Mail mail) {

       System.out.println(mail.getTo());
       
        return "contact";
    }
}
