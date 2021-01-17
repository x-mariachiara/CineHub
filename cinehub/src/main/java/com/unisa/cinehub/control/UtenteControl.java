package com.unisa.cinehub.control;

import java.util.*;
import com.unisa.cinehub.data.entity.Recensore;
import com.unisa.cinehub.data.entity.Utente;
import com.unisa.cinehub.data.entity.VerificationToken;
import com.unisa.cinehub.model.exception.AlreadyExsistsException;
import com.unisa.cinehub.model.exception.BannedException;
import com.unisa.cinehub.model.exception.InvalidBeanException;
import com.unisa.cinehub.model.exception.UserUnderAgeException;
import com.unisa.cinehub.model.registration.OnRegistrationCompleteEvent;
import com.unisa.cinehub.model.service.RecensoreService;
import com.unisa.cinehub.model.service.UtenteService;
import com.unisa.cinehub.views.login.SuccessRegister;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.charts.model.Navigator;
import org.h2.engine.Mode;
import org.h2.mvstore.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @GetMapping("/getAllNotBannedRecensori")
    public List<Recensore> getAllNotBannedRecensori() { return recensoreService.finAllNotBanned();}



    @PostMapping("/signup")
    public void registrazioneUtente(@ModelAttribute("utente") @Valid Utente utente, HttpServletRequest request) throws UserUnderAgeException, AlreadyExsistsException, BannedException {
        Utente utenteRegistrato = utenteService.signup(utente);
        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(utenteRegistrato, request.getLocale(), appUrl));
    }

    @GetMapping("/registrationConfirm")
    public ModelAndView confermaRegistrazione(WebRequest request, @RequestParam("token") String token) throws InvalidBeanException {
        Locale locale = request.getLocale();

        VerificationToken verificationToken = utenteService.getVerificationToken(token);
        System.out.println(token);
        if (verificationToken == null) {
            return new ModelAndView("redirect:/register");
        }

        Utente utente = utenteService.getUtenteByVerificationToken(token);
        Calendar cal = Calendar.getInstance();

        if((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            utenteService.deleteUtente(utente);
            return new ModelAndView("redirect:/register");

        }
        System.out.println("Utente attivato: " + utente);
        utente.setActive(true);
        utenteService.saveRegisteredUser(utente);


        return new ModelAndView("redirect:/successRegister");


    }
}
