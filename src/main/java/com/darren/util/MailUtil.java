package com.darren.util;

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

import com.darren.entity.Mail;

public class MailUtil {
    private static final Logger LOG = Logger.getLogger(MailUtil.class);
    private static final String PROTOCOL = "smtp";
    private static final String HOST = "mail.smtp.host";
    private static final String UAERNAME = "mail.username";
    private static final String PASSWORD = "mail.password";
    private static final String CHARSET = "utf-8";

    public static void send(Mail mail, String configPath) {
        Properties props = PropertiesUtil.readProperties(configPath);
        // 用刚刚设置好的props对象构建一个session
        Session session = Session.getDefaultInstance(props);
        // 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
        // 用（你可以在控制台（console)上看到发送邮件的过程）
        session.setDebug(true);
        Message message = new MimeMessage(session);

        try {
            // 加载发件人地址
            message.setFrom(new InternetAddress(mail.getFrom()));
            // 加载收件人地址
            message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getTo()));
            // 加载标题
            message.setSubject(mail.getSubject());

            // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
            Multipart multipart = new MimeMultipart();

            // 设置邮件的文本内容
            MimeBodyPart contentPart = new MimeBodyPart();
            contentPart.setText(mail.getText(), CHARSET, mail.getType().toLowerCase());
            multipart.addBodyPart(contentPart);

            // 添加附件
            for (String filePath : mail.getFileList()) {
                String fileName = "";
                BodyPart fileBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(filePath);

                // 添加附件的内容
                fileBodyPart.setDataHandler(new DataHandler(source));
                // 添加附件的标题
                fileBodyPart.setFileName(MimeUtility.encodeText(fileName));
                multipart.addBodyPart(fileBodyPart);
            }

            // 将multipart对象放到message中
            message.setContent(multipart);
            // message.setContent(content.toString(),
            // "text/html;charset=utf-8");
            // 保存邮件
            message.saveChanges();
            // 发送邮件
            Transport transport = session.getTransport(PROTOCOL);
            // 连接服务器的邮箱
            transport.connect(props.getProperty(HOST), props.getProperty(UAERNAME), props.getProperty(PASSWORD));
            // 把邮件发送出去
            transport.sendMessage(message, message.getAllRecipients());
            LOG.info("Mail send success!");
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
