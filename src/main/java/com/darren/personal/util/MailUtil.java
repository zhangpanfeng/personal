package com.darren.personal.util;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.log4j.Logger;

import com.darren.personal.entity.Mail;

public class MailUtil {
    private static final Logger LOG = Logger.getLogger(MailUtil.class);
    private static final String PROTOCOL = "smtp";
    private static final String HOST = "mail.smtp.host";
    private static final String UAERNAME = "mail.username";
    private static final String PASSWORD = "mail.password";
    private static final String CHARSET = "utf-8";
    private static final String MAIL_FROM = "mail.username";

    public static boolean send(Mail mail, String configPath) {
        Properties props = PropertiesUtil.readProperties(configPath);
        // create a session using Properties attributes
        Session session = Session.getDefaultInstance(props);
        // debug mode, can see the log information in console
        session.setDebug(true);
        Message message = new MimeMessage(session);

        try {
            // configure sender email address
            message.setFrom(new InternetAddress(props.getProperty(MAIL_FROM)));
            // configure receiver email address
            message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getTo()));
            // configure title
            message.setSubject(mail.getSubject());
            // create MimeMultipart object to add every part of email
            Multipart multipart = new MimeMultipart();

            // set email content
            MimeBodyPart contentPart = new MimeBodyPart();
            contentPart.setText(mail.getText(), CHARSET, mail.getType().toLowerCase());
            multipart.addBodyPart(contentPart);

            // add attachment
            for (String filePath : mail.getFileList()) {
                String fileName = "";
                BodyPart fileBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(filePath);

                // add attachment content
                fileBodyPart.setDataHandler(new DataHandler(source));
                // add attachment title
                fileBodyPart.setFileName(MimeUtility.encodeText(fileName));
                multipart.addBodyPart(fileBodyPart);
            }

            // add MimeMultipart objec to message
            message.setContent(multipart);
            // message.setContent(content.toString(),
            // "text/html;charset=utf-8");
            // save email
            message.saveChanges();
            // configure email protocol
            Transport transport = session.getTransport(PROTOCOL);
            // connect email server
            transport.connect(props.getProperty(HOST), props.getProperty(UAERNAME), props.getProperty(PASSWORD));
            // send the message out
            transport.sendMessage(message, message.getAllRecipients());
            LOG.info("Mail send success!");
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }

        return true;
    }
}
