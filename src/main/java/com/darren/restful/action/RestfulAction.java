package com.darren.restful.action;


import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/restful")
public class RestfulAction {
    private static final Logger LOG = Logger.getLogger(RestfulAction.class);

    @ResponseBody
    @RequestMapping(value = "/receiveMapAsBody.do", method = RequestMethod.POST)
    public String receiveMapAsBody(HttpServletRequest req) {
        System.out.println("xxx");
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "/receiveStringAsBody.do", method = RequestMethod.POST)
    public String receiveStringAsBody(HttpServletRequest req, String body) {
        LOG.info(body);
        return "receiveStringAsBody.do";
    }

    @ResponseBody
    @RequestMapping(value = "/test.do")
    public String test(HttpServletRequest req) {
        System.out.println("test");
        return "test";
    }
}
