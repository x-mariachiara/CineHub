package com.unisa.cinehub.model.registration;

import org.springframework.mail.SimpleMailMessage;

public interface ConfirmRegistration {

    SimpleMailMessage confirmRegistration(OnRegistrationCompleteEvent event);
}
