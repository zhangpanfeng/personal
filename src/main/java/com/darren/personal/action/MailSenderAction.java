package com.darren.personal.action;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class MailSenderAction {

    @RequestMapping(value = "/sendmail.do")
    public String sendmail(ModelMap model) {

        return "contact";
    }
}
