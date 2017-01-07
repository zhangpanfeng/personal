package com.darren.personal.action;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.darren.personal.constant.StateCode;
import com.darren.personal.entity.User;
import com.darren.personal.util.JSONResponseUtil;
import com.darren.personal.util.PropertiesUtil;

@Controller
public class LoginAction {
    private static final Logger LOG = Logger.getLogger(LoginAction.class);
    private static final String SECURITY_CONFIG = "securityConfig";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";

    @ResponseBody
    @RequestMapping(value = "/login.do")
    public String login(ModelMap model, HttpServletRequest request, User user) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", StateCode.FAILURE);
        String securityConfig = request.getServletContext().getInitParameter(SECURITY_CONFIG);
        Properties properties = null;
        try {
            properties = PropertiesUtil.readProperties(ResourceUtils.getFile(securityConfig).getAbsolutePath());
            if (properties.getProperty(USERNAME).equals(user.getUserName())
                    && properties.getProperty(PASSWORD).equals(user.getPassword())) {
                user.setName(properties.getProperty(NAME));
                request.getSession().setAttribute("user", user);
                map.put("name", user.getName());
                map.put("result", StateCode.SUCCESS);
            }
        } catch (Exception e) {
            LOG.info(e.getMessage());
            e.printStackTrace();
        }

        return JSONResponseUtil.getResult(map);
    }

    @ResponseBody
    @RequestMapping(value = "/logout.do")
    public String logout(ModelMap model, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        request.getSession().removeAttribute("user");
        map.put("result", StateCode.SUCCESS);

        return JSONResponseUtil.getResult(map);
    }
}
