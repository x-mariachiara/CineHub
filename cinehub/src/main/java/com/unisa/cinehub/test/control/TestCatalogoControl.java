package com.unisa.cinehub.test.control;

import com.unisa.cinehub.Application;
import com.unisa.cinehub.control.CatalogoControl;
import com.unisa.cinehub.data.entity.*;
import com.unisa.cinehub.data.repository.*;
import com.unisa.cinehub.model.exception.BeanNotExsistException;
import com.unisa.cinehub.model.exception.InvalidBeanException;
import com.unisa.cinehub.model.exception.NotAuthorizedException;
import com.unisa.cinehub.model.exception.NotLoggedException;
import com.unisa.cinehub.test.PSQLTestJPAConfig;
import com.unisa.cinehub.test.SecurityTestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.Assert.*;

@SpringBootTest(classes = {Application.class, PSQLTestJPAConfig.class, SecurityTestConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class TestCatalogoControl {

    /* Classe sotto test */
    @Autowired
    private CatalogoControl catalogoControl;

    /* Repository per popolare il db di test */
    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private RecensioneRepository recensioneRepository;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private GenereRepository genereRepository;

    @Autowired
    private RecensoreRepository recensoreRepository;

    /* Media */
    private Film babyDriver = new Film("Baby Driver", 2017, "Un giovane pilota è costretto a lavorare per un boss del crimine e deve usare tutta la propria abilità quando una rapina, destinata a fallire, minaccia la sua vita e la sua libertà.", "https://www.youtube.com/embed/oFiLrgCuFXo", "https://pad.mymovies.it/filmclub/2015/09/049/locandina.jpg");
    private Film johnWick = new Film("John Wick", 2015, "Il leggendario assassino John Wick si è allontanato dal mondo della violenza dopo aver sposato l'amore della propria vita. Quando la donna muore improvvisamente, il giovane cade nello sconforto più profondo. Il perfido criminale Iosef Tarasov decide di tormentarlo rubandogli l'automobile ed uccidendogli il cane. Per l'uomo è l'ora della vendetta.", "https://www.youtube.com/embed/N_ZPL3hmFEo?controls=0", "https://upload.wikimedia.org/wikipedia/en/9/98/John_Wick_TeaserPoster.jpg");

    /* Generi */
    private Genere azione = new Genere(Genere.NomeGenere.AZIONE);
    private Genere drammatico = new Genere(Genere.NomeGenere.DRAMMATICI);
    private Genere romantico = new Genere(Genere.NomeGenere.ROMANTICO);
    private Genere commedia = new Genere(Genere.NomeGenere.COMMEDIA);


    /* Utenti */
    private Recensore r1 =  new Recensore("r1@gmail.com", "a", "a", LocalDate.of(1978, 5, 6), "a", "a", false, true);
    private Recensore r2 =  new Recensore("r2@gmail.com", "b", "b", LocalDate.of(1978, 5, 6), "b", "b", false, true);
    private Recensore r3 =  new Recensore("r3@gmail.com", "c", "c", LocalDate.of(1978, 5, 6), "c", "c", false, true);
    private Recensore recensoreConAccount = new Recensore("recensore@gmail.com", "Recen", "Sore", LocalDate.of(1996, 2, 4),"recy", "pass", false, true);
    private Moderatore moderatoreAccount = new Moderatore("account@gmail.com", "Acc", "Ount", LocalDate.of(1996, 5, 4),"accy", "pass", false, true, Moderatore.Tipo.MODACCOUNT);
    private Moderatore moderatoreRecensioni = new Moderatore("recensioni@gmail.com", "Recen", "Sioni", LocalDate.of(1997, 5, 4),"reccy", "pass", false, true, Moderatore.Tipo.MODCOMMENTI);

    /* Recensioni */
    private Recensione recensioneR1JohnWick = new Recensione("Bel film", 4);
    private Recensione recensioneR2JohnWick = new Recensione("così così", 2);
    private Recensione recensioneR2BabyDriver = new Recensione("Nice! :)", 3);
    private Recensione recensioneR1BabyDriver = new Recensione("Mi piacciono le macchine", 5);

    @BeforeEach
    @Transactional
    public void dinosauri(){

        //Salvo le entità padre
        genereRepository.saveAll(Arrays.asList(azione, drammatico, romantico, commedia));
        utenteRepository.saveAll(Arrays.asList(r1, r2, r3, recensoreConAccount, moderatoreAccount, moderatoreRecensioni));

        // relazioni film - genere
        johnWick.getGeneri().addAll(Arrays.asList(azione, drammatico));
        babyDriver.getGeneri().addAll(Arrays.asList(romantico, azione, drammatico));

        azione.getMediaCollegati().addAll(Arrays.asList(johnWick, babyDriver));
        drammatico.getMediaCollegati().addAll(Arrays.asList(johnWick, babyDriver));
        romantico.getMediaCollegati().add(babyDriver);

        filmRepository.saveAll(Arrays.asList(johnWick, babyDriver));
        genereRepository.saveAll(Arrays.asList(azione, drammatico, romantico, commedia));

        // recensioni
        recensioneR1BabyDriver.setFilm(babyDriver);
        recensioneR1BabyDriver.setRecensore(r1);
        recensioneR2BabyDriver.setFilm(babyDriver);
        recensioneR2BabyDriver.setRecensore(r2);
        recensioneR1JohnWick.setFilm(johnWick);
        recensioneR1JohnWick.setRecensore(r1);
        recensioneR2JohnWick.setFilm(johnWick);
        recensioneR2JohnWick.setRecensore(r2);

        babyDriver.getListaRecensioni().addAll(Arrays.asList(recensioneR1BabyDriver, recensioneR2BabyDriver));
        johnWick.getListaRecensioni().addAll(Arrays.asList(recensioneR1JohnWick, recensioneR2JohnWick));
        r1.getListaRecensioni().addAll(Arrays.asList(recensioneR1BabyDriver, recensioneR1JohnWick));
        r2.getListaRecensioni().addAll(Arrays.asList(recensioneR2JohnWick, recensioneR2BabyDriver));

        recensioneRepository.saveAll(Arrays.asList(recensioneR2JohnWick, recensioneR2BabyDriver, recensioneR1JohnWick, recensioneR1BabyDriver));
        utenteRepository.saveAll(Arrays.asList(r1, r2, r3));
        filmRepository.saveAll(Arrays.asList(johnWick, babyDriver));
    }

    @Test
    @WithUserDetails("recensore@gmail.com")
    @Transactional
    public void addRecensione() {
        Recensione oracolo = new Recensione("Bello", 5);
        oracolo.setId(7l);
        oracolo.setFilm(babyDriver);
        oracolo.setRecensore(recensoreConAccount);

        try {
            catalogoControl.addRecensione(oracolo);
            assertTrue(recensioneRepository.existsById(oracolo.getId()));
            assertTrue(((filmRepository.findById(2l).get()).getListaRecensioni()).contains(oracolo));
            assertTrue(recensoreRepository.findById(recensoreConAccount.getEmail()).get().getListaRecensioni().contains(oracolo));
        } catch (NotLoggedException | NotAuthorizedException | InvalidBeanException | BeanNotExsistException e) {
            assert false;
        }
    }


}
