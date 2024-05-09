 package com.deepak.mailsender;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    private final JavaMailSender javaMailSender;

    public ContactService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void contactus(String queryMessage){
        
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("deepaksharma948270@gmail.com");
        message.setTo("ds948270@gmail.com");
        message.setText(queryMessage);
        message.setSubject("Contact From College Bus Tracking System");

        // sending result to the email
        javaMailSender.send(message);

        
    }
}
    


