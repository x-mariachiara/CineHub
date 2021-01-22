package com.unisa.cinehub.test.unit;

import com.unisa.cinehub.data.entity.Recensore;
import com.unisa.cinehub.data.entity.Utente;
import com.unisa.cinehub.model.registration.ConfirmRegistration;
import com.unisa.cinehub.model.registration.OnRegistrationCompleteEvent;
import com.unisa.cinehub.model.service.UtenteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDate;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
public class TestOnRegistrationCompleteEvent {

    @MockBean
    private JavaMailSender javaMailSender;

    @MockBean
    private UtenteService utenteService;

    @Autowired
    private ConfirmRegistration confirmRegistration;

    @Test
    public void launchEvent_valid() {

        doNothing().when(utenteService).createVerificationToken(any(Utente.class), anyString());
        Utente utente = new Recensore("r1@gmail.com", "a", "a", LocalDate.of(1978, 5, 6), "a", "a", false, true);
        OnRegistrationCompleteEvent event = new OnRegistrationCompleteEvent(utente, Locale.getDefault(), "");
        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));
        SimpleMailMessage oracolo = new SimpleMailMessage();
        oracolo.setTo(utente.getEmail());
        oracolo.setText("http://localhost:8080" + event.getAppUrl() + "/api/utentecontrol/registrationConfirm?token=");
        oracolo.setSubject("Conferma la registrazione");
        SimpleMailMessage ritornato = confirmRegistration.confirmRegistration(event);
        ritornato.setText(ritornato.getText().substring(0, ritornato.getText().length() - 36));
        Assertions.assertEquals(oracolo, ritornato);
    }



}
