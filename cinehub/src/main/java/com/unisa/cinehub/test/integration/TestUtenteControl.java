package com.unisa.cinehub.test.integration;

import com.unisa.cinehub.Application;
import com.unisa.cinehub.control.UtenteControl;
import com.unisa.cinehub.data.entity.Moderatore;
import com.unisa.cinehub.data.entity.Recensore;
import com.unisa.cinehub.data.entity.VerificationToken;
import com.unisa.cinehub.data.repository.UtenteRepository;
import com.unisa.cinehub.data.repository.VerificationTokenRepository;
import com.unisa.cinehub.model.exception.*;
import com.unisa.cinehub.test.PSQLTestJPAConfig;
import com.unisa.cinehub.test.SecurityTestConfig;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = {Application.class, PSQLTestJPAConfig.class, SecurityTestConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class TestUtenteControl {

    @Autowired
    private UtenteControl utenteControl;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @MockBean
    private HttpServletRequest httpRequest;

    @MockBean
    private WebRequest webRequest;

    @MockBean
    private Calendar cal;


    private Recensore r1 =  new Recensore("r1@gmail.com", "a", "a", LocalDate.of(1978, 5, 6), "a", "a", false, true);
    private Recensore r2 =  new Recensore("r2@gmail.com", "b", "b", LocalDate.of(1978, 5, 6), "b", "b", false, true);
    private Recensore r3 =  new Recensore("r3@gmail.com", "c", "c", LocalDate.of(1978, 5, 6), "c", "c", true, true);
    private Recensore recensoreConAccount = new Recensore("recensore@gmail.com", "Recen", "Sore", LocalDate.of(1996, 2, 4),"recy", "pass", false, true);
    private Moderatore moderatoreAccount = new Moderatore("account@gmail.com", "Acc", "Ount", LocalDate.of(1996, 5, 4),"accy", "pass", false, true, Moderatore.Tipo.MODACCOUNT);
    private Moderatore moderatoreRecensioni = new Moderatore("recensioni@gmail.com", "Recen", "Sioni", LocalDate.of(1997, 5, 4),"reccy", "pass", false, true, Moderatore.Tipo.MODCOMMENTI);

    @BeforeEach
    public void dinosauri() {
        //salvo gli utenti
        utenteRepository.saveAll(Arrays.asList(r1, r2, r3, recensoreConAccount, moderatoreAccount, moderatoreRecensioni));

    }

    @Test
    public void signup_valid() throws Exception, UserUnderAgeException, AlreadyExsistsException, BannedException {
        Recensore recensore = new Recensore("r4@cinehub.com", "d", "d", LocalDate.of(1978, 5, 6), "d", "d", false, true);
        when(httpRequest.getContextPath()).thenReturn("");
        utenteControl.registrazioneUtente(recensore, httpRequest);
        assertTrue(utenteRepository.findAll().contains(recensore));
        assertFalse(utenteRepository.findById("r4@cinehub.com").get().getActive());
    }

    @Test
    public void signup_UtenteBannato() {
        when(httpRequest.getContextPath()).thenReturn("");
        assertThrows(BannedException.class, () ->  utenteControl.registrazioneUtente(r3, httpRequest));
    }


    @Test
    public void signup_UtenteGiaRegistrato() {
        when(httpRequest.getContextPath()).thenReturn("");
        assertThrows(AlreadyExsistsException.class, () ->  utenteControl.registrazioneUtente(r1, httpRequest));
    }


    @Test
    public void confermaRegistrazione_valid() throws UserUnderAgeException, AlreadyExsistsException, BannedException, InvalidBeanException, BeanNotExsistException {
        Recensore recensore = new Recensore("r4@cinehub.com", "d", "d", LocalDate.of(1978, 5, 6), "d", "d", false, true);
//        when(httpRequest.getContextPath()).thenReturn("");
//        utenteControl.registrazioneUtente(recensore, httpRequest);

        assertFalse(utenteRepository.findById("r4@cinehub.com").get().getActive());

        when(webRequest.getLocale()).thenReturn(Locale.getDefault());
        VerificationToken verificationToken = verificationTokenRepository.findByUtente(recensore);
        utenteControl.confermaRegistrazione(webRequest, verificationToken.getToken());

        assertTrue(utenteRepository.findById("r4@cinehub.com").get().getActive());
    }

    @Test
    @Transactional
    public void confermaRegistrazione_ExpiryDateSuperata() throws UserUnderAgeException, AlreadyExsistsException, BannedException, InvalidBeanException, BeanNotExsistException {
        Recensore recensore = new Recensore("r4@cinehub.com", "d", "d", LocalDate.of(1978, 5, 6), "d", "d", false, true);
//        when(httpRequest.getContextPath()).thenReturn("");
//        utenteControl.registrazioneUtente(recensore, httpRequest);

        assertFalse(utenteRepository.findById("r4@cinehub.com").get().getActive());

        when(webRequest.getLocale()).thenReturn(Locale.getDefault());
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 2);

        when(cal.getTime().getTime()).thenReturn(c.getTime().getTime());
        VerificationToken verificationToken = verificationTokenRepository.findByUtente(recensore);

        utenteControl.confermaRegistrazione(webRequest, verificationToken.getToken());

        assertFalse(utenteRepository.existsById("r4@cinehub.com"));
    }



}
