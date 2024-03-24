package com.foodDelivaryApp.userservice.utilImpl;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.foodDelivaryApp.userservice.util.IEmailSender;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailSendarUtilImpl implements IEmailSender {

    @Autowired
    private JavaMailSender javaMailSender;

    private void addToAttachment(MimeMessageHelper messageHelper, Map<String, String> attachment) throws MessagingException {
        String attachmentName = attachment.get(ATTACHMENT_NAME);
        String attachmentType = attachment.get(ATTACHMENT_TYPE);
        String attachmentPath = attachment.get(ATTACHMENT_PATH);
        File attachmentFile = new File(attachmentPath);
        if (StringUtils.isEmpty(attachmentName) ==  true) {
            attachmentName = attachmentFile.getName();
        }

        FileSystemResource fileSystemResource = new FileSystemResource(attachmentFile);
        messageHelper.addAttachment(attachmentName, fileSystemResource, attachmentType);
    }

    @Override
    public void sendEmail(String reciepent, String body, String subjects) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("yavarkhan892300@gmail.com");
            message.setTo(reciepent);
            message.setText(body);
            message.setSubject(subjects);
            javaMailSender.send(message);
            log.info("mail sent successfully to {}", reciepent);
        } catch (Exception ex) {
            log.error("Failed to send mail", ex);
        }
    }

    @Override
    public void sendEmail(String recipient, String body, String subject, List<Map<String, String>> attachments) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom("yavarkhan892300@gmail.com");
            messageHelper.setSubject(subject);
            messageHelper.setText(body, true);
            messageHelper.setTo(recipient);
            if (attachments != null) {
                for (Map<String, String> attachment: attachments) {
                    addToAttachment(messageHelper, attachment);
                }
            }

            javaMailSender.send(mimeMessage);
            log.info("email send successfully");
        } catch (Exception e) {
            log.info("failed to send email");
        }
    }

    @Override
    public void sendEmail(List<String> recipients, String body, String subject) {
        for (String recipient: recipients) {
            sendEmail(recipient, body, subject);
        }
    }

    @Override
    public void sendEmail(List<String> recipients, String body, String subject, List<Map<String, String>> attachments) {
        for (String recipient: recipients) {
            sendEmail(recipient, body, subject, attachments);
        }
    }

}
