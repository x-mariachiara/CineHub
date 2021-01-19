package com.unisa.cinehub.test.control;

import com.unisa.cinehub.Application;
import com.unisa.cinehub.control.ModerazioneControl;
import com.unisa.cinehub.data.entity.Moderatore;
import com.unisa.cinehub.data.entity.Recensione;
import com.unisa.cinehub.data.entity.Recensore;
import com.unisa.cinehub.data.entity.Segnalazione;
import com.unisa.cinehub.data.repository.RecensioneRepository;
import com.unisa.cinehub.data.repository.UtenteRepository;
import com.unisa.cinehub.model.exception.BeanNotExsistException;
import com.unisa.cinehub.model.exception.InvalidBeanException;
import com.unisa.cinehub.model.exception.NotAuthorizedException;
import com.unisa.cinehub.model.service.SegnalazioneService;
import com.unisa.cinehub.test.PSQLTestJPAConfig;
import com.unisa.cinehub.test.SecurityTestConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {Application.class, PSQLTestJPAConfig.class, SecurityTestConfig.class})
@ActiveProfiles("test")
@Transactional
public class TestModerazioneControl {

    @Autowired
    private ModerazioneControl moderazioneControl;

    @Autowired
    private SegnalazioneService segnalazioneService;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private RecensioneRepository recensioneRepository;

    private Recensore r1 =  new Recensore("rec@gmail.com", "a", "a", LocalDate.of(1978, 5, 6), "a", "a", true, false);
    private Recensore recensore = new Recensore("recensore@gmail.com", "Recen", "Sore", LocalDate.of(1996, 2, 4),"recy", "pass", false, true);
    private Moderatore moderatoreAccount = new Moderatore("account@gmail.com", "Acc", "Ount", LocalDate.of(1996, 5, 4),"accy", "pass", false, true, Moderatore.Tipo.MODACCOUNT);
    private Moderatore moderatoreRecensioni = new Moderatore("recensioni@gmail.com", "Recen", "Sioni", LocalDate.of(1997, 5, 4),"reccy", "pass", false, true, Moderatore.Tipo.MODCOMMENTI);
    private Recensione rec = new Recensione("bell", 4);

    @BeforeEach
    public void dinosauri(){
        recensioneRepository.save(rec);
        rec.setRecensore(r1);
        r1.getListaRecensioni().add(rec);
        utenteRepository.save(r1);
        utenteRepository.save(recensore);
        utenteRepository.save(moderatoreAccount);
        utenteRepository.save(moderatoreRecensioni);
        recensioneRepository.save(rec);
    }

    @AfterEach
    public void asteroide(){
        utenteRepository.deleteAll();
        recensioneRepository.deleteAll();
    }


    @Test
    @WithUserDetails("recensore@gmail.com")
    public void addSegnalazione_valid() throws NotAuthorizedException, InvalidBeanException {
        Segnalazione perOracolo = new Segnalazione();
        perOracolo.setRecensore(r1);
        perOracolo.setRecensione(rec);
        perOracolo.setSegnalatoreId("recensore@gmail.com");
        List<Segnalazione> oracolo = new ArrayList<>(Arrays.asList(perOracolo));
        moderazioneControl.addSegnalazione(rec);
        assertEquals(oracolo, moderazioneControl.findAllSegnalazioni());
    }

    @Test
    @WithUserDetails("recensioni@gmail.com")
    public void addSegnalazione_notAuthorized() {
        Segnalazione perOracolo = new Segnalazione();
        perOracolo.setRecensore(r1);
        perOracolo.setRecensione(rec);
        perOracolo.setSegnalatoreId("recensore@gmail.com");
        List<Segnalazione> oracolo = new ArrayList<>(Arrays.asList(perOracolo));
        assertThrows(NotAuthorizedException.class, () -> moderazioneControl.addSegnalazione(rec));
    }

    @Test
    @WithUserDetails("account@gmail.com")
    public void bannaAccount_valid() throws NotAuthorizedException, InvalidBeanException, BeanNotExsistException {
        moderazioneControl.bannaRecensore("rec@gmail.com");
        Recensore recensoreSalvato = (Recensore) utenteRepository.findById("rec@gmail.com").get();
        assertTrue(recensoreSalvato.getBannato());
        assertTrue(recensoreSalvato.getListaRecensioni().isEmpty());
        assertFalse(recensioneRepository.findAll().contains(rec));
    }


}
