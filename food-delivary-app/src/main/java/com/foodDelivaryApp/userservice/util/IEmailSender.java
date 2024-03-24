package com.foodDelivaryApp.userservice.util;

import java.util.List;
import java.util.Map;

public interface IEmailSender {

    String ATTACHMENT_NAME = "attachmentName";
    String ATTACHMENT_TYPE = "attachmentType";
    String ATTACHMENT_PATH = "attachmentPath";

    void sendEmail(String recipent , String body, String subject);

    void sendEmail(List<String> recipents, String body, String subject);

    void sendEmail(String recipent, String body, String subject, List<Map<String, String>> attachments);

    void sendEmail(List<String> recipents, String body, String subject, List<Map<String, String>> attachments);
}
