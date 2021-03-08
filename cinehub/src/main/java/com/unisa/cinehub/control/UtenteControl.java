package com.unisa.cinehub.control;

import com.unisa.cinehub.data.entity.Recensore;
import com.unisa.cinehub.data.entity.Utente;
import com.unisa.cinehub.data.entity.VerificationToken;
import com.unisa.cinehub.model.exception.*;
import com.unisa.cinehub.model.registration.OnRegistrationCompleteEvent;
import com.unisa.cinehub.model.utente.RecensoreService;
import com.unisa.cinehub.model.utente.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

@RestController
@RequestMapping("api/utentecontrol")
public class UtenteControl {

    /* Altri Service */

    private static  Logger log = Logger.getLogger("UtenteControl");

    @Autowired
    private RecensoreService recensoreService;
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    private Calendar cal;

    public UtenteControl(RecensoreService recensoreService, ApplicationEventPublisher eventPublisher) {
        this.recensoreService = recensoreService;
        this.eventPublisher = eventPublisher;
        cal = Calendar.getInstance();
    }

    @GetMapping("/getAllRecensori")
    public List<Recensore> getAllRecensori() {
        return recensoreService.findAll();
    }

    @GetMapping("/getAllNotBannedRecensori")
    public List<Recensore> getAllNotBannedRecensori() { return recensoreService.finAllNotBanned();}



    @GetMapping("/signup")
    public void registrazioneUtente(@ModelAttribute("utente") @Valid Utente utente, HttpServletRequest request) throws UserUnderAgeException, AlreadyExsistsException, BannedException {
        Utente utenteRegistrato = utenteService.signup(utente);
        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(utenteRegistrato, request.getLocale(), appUrl));
    }

    @GetMapping("/registrationConfirm")
    public ModelAndView confermaRegistrazione(WebRequest request, @RequestParam("token") String token) throws InvalidBeanException, BeanNotExsistException {
        Locale locale = request.getLocale();

        VerificationToken verificationToken = utenteService.getVerificationToken(token);
        System.out.println(token);
        if (verificationToken == null) {
            return new ModelAndView("redirect:/register");
        }

        Utente utente = utenteService.getUtenteByVerificationToken(token);

        if((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {

            utenteService.deleteUtente(utente, verificationToken);
            return new ModelAndView("redirect:/register");

        }
        System.out.println("Utente attivato: " + utente);
        utente.setActive(true);
        utenteService.saveRegisteredUser(utente);


        return new ModelAndView("redirect:/successRegister");

    }

    @GetMapping("/exportData")
    public String exportData() {
        try {
            return utenteService.exportData();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
