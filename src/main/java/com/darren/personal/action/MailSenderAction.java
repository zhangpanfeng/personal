package com.darren.personal.action;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.darren.personal.constant.Constant;
import com.darren.personal.entity.Mail;
import com.darren.personal.util.DateUtil;
import com.darren.personal.util.JSONResponseUtil;
import com.darren.personal.util.MailUtil;
import com.darren.personal.util.PropertiesUtil;

@Controller
public class MailSenderAction {
    private static final Logger LOG = Logger.getLogger(MailSenderAction.class);
    private static final String REPLY_TEXT = "mail.others.text";
    private static final String MAIL_SUBJECT = "mail.me.subject";
    private static final String TEXT_SUFFIX = "mail.me.textSuffix";
    private static final String TO_ME = "mail.me.to";
    

    @ResponseBody
    @RequestMapping(value = "/sendmail.do")
    public Object sendmail(ModelMap model, HttpServletRequest request, Mail mail) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", 0);
        String text = mail.getText();
        String configPath = (String) request.getServletContext().getAttribute(Constant.EMAIL_CONFIG_PATH);
        Properties properties = PropertiesUtil.readProperties(configPath);
        // send to others
        mail.setSubject("Re: " + mail.getSubject());
        mail.setText(MessageFormat.format(properties.getProperty(REPLY_TEXT), mail.getName()));
        mail.setType(Mail.Type.HTML.toString());
        boolean result = MailUtil.send(mail, configPath);

        if (result) {
            // send to me
            mail.setSubject(MessageFormat.format(properties.getProperty(MAIL_SUBJECT), mail.getName(),
                    DateUtil.getString(new Date())));
            mail.setType(Mail.Type.PLAIN.toString());
            mail.setText(text + MessageFormat.format(properties.getProperty(TEXT_SUFFIX), mail.getTo()));
            mail.setTo(properties.getProperty(TO_ME));
            result = MailUtil.send(mail, configPath);
            System.out.println("send result = " + result);
            if (result) {
                map.put("result", 1);
            }
        }

        return map;
    }
}
