package com.eastwest.factory;

import com.eastwest.model.enums.MailType;
import com.eastwest.service.EmailService2;
import com.eastwest.service.MailService;
import com.eastwest.service.SendgridService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MailServiceFactory {
    @Value("${mail.type:SMTP}")
    private MailType mailType;

    private final EmailService2 emailService2;
    private final SendgridService sendgridService;

    public MailService getMailService() {
        switch (mailType) {
            case SENDGRID:
                return sendgridService;
            default:
                return emailService2;
        }
    }
}
