package com.murtaza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    MailSender mailSender;

    public boolean sendMail(String to, String subject, String body) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setSubject(subject);
        mail.setTo(to);
        mail.setText(body);
        mail.setFrom("rizvi2793@gmail.com");

        mailSender.send(mail);

        return true;
    }
}
