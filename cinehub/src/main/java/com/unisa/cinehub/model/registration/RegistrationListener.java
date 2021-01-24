package com.unisa.cinehub.model.registration;

import com.unisa.cinehub.data.entity.Utente;
import com.unisa.cinehub.model.utente.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    @Autowired
    private UtenteService service;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ConfirmRegistration confirmRegistration;


    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        confirmRegistration.confirmRegistration(event);
    }

    private static final String HOST = "http://localhost:8080";


    @Bean
    public ConfirmRegistration confirmBean() {
        return new ConfirmRegistration() {
            @Override
            public SimpleMailMessage confirmRegistration(OnRegistrationCompleteEvent event) {
                return inviaMailConferma(event);
            }
        };
    }

    private SimpleMailMessage inviaMailConferma(OnRegistrationCompleteEvent event) {
        Utente utente = event.getUtente();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(utente, token);

        String emailDestinatario = utente.getEmail();
        String oggetto = "Conferma la registrazione";
        String linkDiConferma = event.getAppUrl() + "/api/utentecontrol/registrationConfirm?token=" + token;
        //String messaggio = messageSource.getMessage("message.regSuc", null, event.getLocale());

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(emailDestinatario);
        email.setSubject(oggetto);
        email.setText(HOST + linkDiConferma);
        mailSender.send(email);
        return email;
    }



    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("cinehubconfirm@gmail.com");
        mailSender.setPassword("U9Lh8VBpQpz7");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

}
