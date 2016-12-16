package com.darren.personal.action;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.darren.personal.entity.Mail;
@Controller
public class MailSenderAction {

    @ResponseBody
    @RequestMapping(value = "/sendmail.do")
    public String sendmail(ModelMap model, Mail mail) {

       System.out.println(mail.getTo());
       
        return "success.success";
    }
}
