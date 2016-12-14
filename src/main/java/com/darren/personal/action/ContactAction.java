package com.darren.personal.action;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContactAction {

    @RequestMapping(value = "/contact.do")
    public String contact(ModelMap model) {

        return "contact";
    }
}
