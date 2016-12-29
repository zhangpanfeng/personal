package com.darren.personal.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class TestAction {

    @RequestMapping(value = "/test.do")
    public String test(HttpServletRequest request) {
        return "test";
    }
}
