package com.darren.personal.action;

import java.io.FileNotFoundException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.darren.personal.entity.Mail;
import com.darren.personal.util.DateUtil;
import com.darren.personal.util.MailUtil;
import com.darren.personal.util.PropertiesUtil;

@Controller
public class MailSenderAction {
    private static final Logger LOG = Logger.getLogger(MailSenderAction.class);
    private static final String MAIL_CONFIG = "mailConfig";
    private static final String REPLY_TEXT = "mail.others.text";
    private static final String MAIL_FROM = "mail.username";
    private static final String MAIL_SUBJECT = "mail.me.subject";
    private static final String TEXT_SUFFIX = "mail.me.textSuffix";

    @ResponseBody
    @RequestMapping(value = "/sendmail.do")
    public String sendmail(ModelMap model, HttpServletRequest request, Mail mail) {
        String text = mail.getText();
        String message = "failure";
        String mailConfig = request.getServletContext().getInitParameter(MAIL_CONFIG);
        String configPath = null;
        try {
            configPath = ResourceUtils.getFile(mailConfig).getAbsolutePath();
        } catch (FileNotFoundException e) {
            LOG.info(e.getMessage());
            e.printStackTrace();
        }

        Properties properties = PropertiesUtil.readProperties(configPath);
        // send to others
        mail.setFrom(properties.getProperty(MAIL_FROM));
        mail.setText(MessageFormat.format(properties.getProperty(REPLY_TEXT), mail.getName()));
        mail.setType(Mail.Type.HTML.toString());
        boolean result = MailUtil.send(mail, configPath);

        if (result) {
            // send to me
            mail.setSubject(MessageFormat.format(properties.getProperty(MAIL_SUBJECT), mail.getName(),
                    DateUtil.getString(new Date())));
            mail.setType(Mail.Type.PLAIN.toString());
            mail.setText(text + MessageFormat.format(properties.getProperty(TEXT_SUFFIX), mail.getTo()));
            result = MailUtil.send(mail, configPath);
        }

        if (result) {
            message = "success";
        }

        return message;
    }
}
