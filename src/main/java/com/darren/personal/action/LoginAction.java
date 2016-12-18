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
//import org.codehaus.jackson.map.ObjectMapper;
import com.darren.personal.constant.StateCode;
import com.darren.personal.entity.User;
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
    public Object login(ModelMap model, HttpServletRequest request, User user) {
        LOG.info("hello");
        Map<String, String> result = new HashMap<String, String>();
        result.put("result", String.valueOf(StateCode.FAILURE));
        String securityConfig = request.getServletContext().getInitParameter(SECURITY_CONFIG);
        Properties properties = null;
        try {
            properties = PropertiesUtil.readProperties(ResourceUtils.getFile(securityConfig).getAbsolutePath());
        } catch (Exception e) {
            LOG.info(e.getMessage());
            e.printStackTrace();
        }

        if (properties.getProperty(USERNAME).equals(user.getUserName())
                && properties.getProperty(PASSWORD).equals(user.getPassword())) {
            result.put("name", properties.getProperty(NAME));
        }
//        private ObjectMapper jsonMapper = new ObjectMapper();
//        String responseStr = jsonMapper.writeValueAsString(json);
        return result;
    }
}
