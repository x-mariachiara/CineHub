package com.unisa.cinehub.control;

import java.util.*;
import com.unisa.cinehub.data.entity.Recensore;
import com.unisa.cinehub.data.entity.Utente;
import com.unisa.cinehub.data.entity.VerificationToken;
import com.unisa.cinehub.model.exception.AlreadyExsistsException;
import com.unisa.cinehub.model.exception.UserUnderAgeException;
import com.unisa.cinehub.model.registration.OnRegistrationCompleteEvent;
import com.unisa.cinehub.model.service.RecensoreService;
import com.unisa.cinehub.model.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("api/utentecontrol")
public class UtenteControl {

    /* Altri Service */
    @Autowired
    private RecensoreService recensoreService;
    @Autowired
    private UtenteService utenteService;
    @Autowired
    ApplicationEventPublisher eventPublisher;

    public UtenteControl(RecensoreService recensoreService, ApplicationEventPublisher eventPublisher) {
        this.recensoreService = recensoreService;
        this.eventPublisher = eventPublisher;
    }

    @GetMapping("/getAllRecensori")
    public List<Recensore> getAllRecensori() {
        return recensoreService.findAll();
    }

    @PostMapping("/signup")
    public String registrazioneUtente(@ModelAttribute("utente") @Valid Utente utente, HttpServletRequest request) {
        try {
            Utente utenteRegistrato = utenteService.signup(utente);
            String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(utenteRegistrato, request.getLocale(), appUrl));
        } catch (AlreadyExsistsException e) {
            return "Esiste già un utente con questa email.";
        } catch (UserUnderAgeException e) {
            return "Devi avere almeno tredici anni per registrarti";
        }

        return "È stata inviata una mail di conferma a " + utente.getEmail();
    }

    @GetMapping("/registrationConfirm")
    public String confermaRegistrazione(WebRequest request, @RequestParam("token") String token) {
        Locale locale = request.getLocale();

        VerificationToken verificationToken = utenteService.getVerificationToken(token);
        System.out.println(token);
        if (verificationToken == null) {
            return "Token non valido";
        }

        Utente utente = utenteService.getUtenteByVerificationToken(token);
        Calendar cal = Calendar.getInstance();

        if((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            utenteService.deleteUtente(utente);
            return "Token scaduto";
        }
        System.out.println("Utente attivato: " + utente);
        utente.setActive(true);
        utenteService.saveRegisteredUser(utente);
        return "Utente confermato";
    }

}
