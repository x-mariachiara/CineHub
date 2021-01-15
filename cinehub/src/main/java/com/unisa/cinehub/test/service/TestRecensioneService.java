package com.unisa.cinehub.test.service;

import com.unisa.cinehub.data.entity.*;
import com.unisa.cinehub.data.repository.RecensioneRepository;
import com.unisa.cinehub.model.exception.BeanNotExsistException;
import com.unisa.cinehub.model.exception.InvalidBeanException;
import com.unisa.cinehub.model.service.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TestRecensioneService {

    @Autowired
    private RecensioneService recensioneService;

    @MockBean
    private RecensioneRepository recensioneRepository;

    @MockBean
    private PuntataService puntataService;

    @MockBean
    private FilmService filmService;

    @MockBean
    private UtenteService utenteService;

    @MockBean
    private SerieTVService serieTVService;

    @Test
    public void addRecensioneFilm_valid(){
        Film film = new Film("Baby Driver", 2017, "Un giovane pilota è costretto a lavorare per un boss del crimine e deve usare tutta la propria abilità quando una rapina, destinata a fallire, minaccia la sua vita e la sua libertà.", "https://www.youtube.com/embed/oFiLrgCuFXo", "https://pad.mymovies.it/filmclub/2015/09/049/locandina.jpg");
        film.setId(1L);
        Recensione recensione = new Recensione("Bel film", 5);
        Recensore recensore = new Recensore("mariachiaranasto1@gmail.com", "Maria Chiara", "Nasto", LocalDate.of(2000, 2, 7), "xmariachiara", new BCryptPasswordEncoder().encode("ciao"), false, true);
        recensione.setFilm(film);
        when(filmService.retrieveByKey(anyLong())).thenReturn(film);
        when(recensioneRepository.save(any(Recensione.class))).thenAnswer(i -> i.getArgument(0, Recensione.class));
        when(utenteService.saveRegisteredUser(any(Utente.class))).thenAnswer(i -> i.getArgument(0, Utente.class));
        doNothing().when(filmService).mergeFilm(any(Film.class));
        try {
          recensioneService.addRecensione(recensione, recensore);
          assert true;
        } catch (InvalidBeanException | BeanNotExsistException e) {
            assert false;
        }
    }

    @Test
    public void addRecensionePuntata_valid() throws BeanNotExsistException, InvalidBeanException {
        SerieTv serieTv = new SerieTv("La Casa di Carta", 2017,"La storia narra gli sviluppi di una rapina estremamente ambiziosa e originale: irrompere nella Fábrica Nacional de Moneda y Timbre, a Madrid, far stampare migliaia di milioni di banconote e scappare con il bottino. L'ideatore di questa impresa è un uomo conosciuto come \"il Professore\". Il reclutamento di ogni singolo membro della squadra non è affatto casuale: il Professore, infatti, seleziona attentamente un gruppo di individui con precedenti penali, i quali, per motivi di estrazione sociale, non hanno nulla da perdere. Ciascun membro durante la rapina agisce vestito di rosso con una maschera del pittore spagnolo Salvador Dalí.","https://www.youtube.com/embed/bNWnxJBFlDQ","https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fi2.wp.com%2Ftv.badtaste.it%2Fwp%2Fwp-content%2Fuploads-badtv%2F2019%2F01%2Fcasa-di-carta.jpg%3Ffit%3D300%252C450%26quality%3D85%26strip%3Dall%26ssl%3D1&f=1&nofb=1");
        serieTv.setId(1L);
        Stagione stagione = new Stagione(1);
        stagione.setSerieTv(serieTv);
        Puntata puntata = new Puntata("Imprudencias letales ", 2, "Río è vivo, è solo ferito alla testa. Berlino, al telefono con il Professore, gli rivela che Tokyo e Río hanno una relazione, ma lei smentisce tutto. Río, invece, quando sono soli, conferma a Berlino che ama Tokyo. Cominciano a esserci dei flashback ambientati nei cinque mesi di addestramento con il Professore. Nel frattempo la polizia prende le dovute contromisure e invia sul posto come negoziatrice l’ispettrice Raquel Murillo, una donna con problemi professionali e personali; vive con l'anziana madre e con sua figlia Paula, è separata da poco e ha denunciato per maltrattamenti l'ex marito, genio della Scientifica, che ora ha una relazione con sua sorella. Il braccio destro dell'ispettrice è Angel Rubio, segretamente innamorato di lei; inoltre la donna deve sottostare agli ordini del colonnello Prieto, a capo dei servizi segreti. ");
        puntata.setStagione(stagione);

        Recensione recensione = new Recensione("Bel film", 5);
        Recensore recensore = new Recensore("mariachiaranasto1@gmail.com", "Maria Chiara", "Nasto", LocalDate.of(2000, 2, 7), "xmariachiara", new BCryptPasswordEncoder().encode("ciao"), false, true);
        recensione.setPuntata(puntata);
        when(puntataService.retrievePuntataByKey(any(Puntata.PuntataID.class))).thenReturn(puntata);
        when(recensioneRepository.save(any(Recensione.class))).thenAnswer(i -> i.getArgument(0, Recensione.class));
        when(utenteService.saveRegisteredUser(any(Utente.class))).thenAnswer(i -> i.getArgument(0, Utente.class));
        when(serieTVService.retrieveByKey(anyLong())).thenReturn(serieTv);
        doNothing().when(serieTVService).mergeSerieTV(any(SerieTv.class));
        try {
            recensioneService.addRecensione(recensione, recensore);
            assert true;
        } catch (InvalidBeanException | BeanNotExsistException e) {
            assert false;
        }
    }

    @Test
    public void addRecensione_recensioneNull(){
        Recensore recensore = new Recensore("mariachiaranasto1@gmail.com", "Maria Chiara", "Nasto", LocalDate.of(2000, 2, 7), "xmariachiara", new BCryptPasswordEncoder().encode("ciao"), false, true);
        assertThrows(InvalidBeanException.class, () -> recensioneService.addRecensione(null, recensore));
    }
    @Test
    public void removeRecensioneFilm_valid() {
        Film film = new Film("Baby Driver", 2017, "Un giovane pilota è costretto a lavorare per un boss del crimine e deve usare tutta la propria abilità quando una rapina, destinata a fallire, minaccia la sua vita e la sua libertà.", "https://www.youtube.com/embed/oFiLrgCuFXo", "https://pad.mymovies.it/filmclub/2015/09/049/locandina.jpg");
        film.setId(1L);
        Recensione recensione = new Recensione("Bel film", 5);

        Recensore recensore = new Recensore("mariachiaranasto1@gmail.com", "Maria Chiara", "Nasto", LocalDate.of(2000, 2, 7), "xmariachiara", new BCryptPasswordEncoder().encode("ciao"), false, true);
        recensore.getListaRecensioni().add(recensione);
        recensione.setRecensore(recensore);
        recensione.setFilm(film);
        doNothing().when(filmService).mergeFilm(any(Film.class));
        when(utenteService.saveRegisteredUser(any(Utente.class))).thenAnswer(i -> i.getArgument(0, Utente.class));
        doNothing().when(recensioneRepository).delete(any(Recensione.class));
        try {
            recensioneService.removeRecensione(recensione);
            assert true;
        } catch (InvalidBeanException e){
            assert false;
        }
    }

    @Test
    public void removeRecensionePuntata_valid() throws InvalidBeanException {
        SerieTv serieTv = new SerieTv("La Casa di Carta", 2017,"La storia narra gli sviluppi di una rapina estremamente ambiziosa e originale: irrompere nella Fábrica Nacional de Moneda y Timbre, a Madrid, far stampare migliaia di milioni di banconote e scappare con il bottino. L'ideatore di questa impresa è un uomo conosciuto come \"il Professore\". Il reclutamento di ogni singolo membro della squadra non è affatto casuale: il Professore, infatti, seleziona attentamente un gruppo di individui con precedenti penali, i quali, per motivi di estrazione sociale, non hanno nulla da perdere. Ciascun membro durante la rapina agisce vestito di rosso con una maschera del pittore spagnolo Salvador Dalí.","https://www.youtube.com/embed/bNWnxJBFlDQ","https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fi2.wp.com%2Ftv.badtaste.it%2Fwp%2Fwp-content%2Fuploads-badtv%2F2019%2F01%2Fcasa-di-carta.jpg%3Ffit%3D300%252C450%26quality%3D85%26strip%3Dall%26ssl%3D1&f=1&nofb=1");
        serieTv.setId(1L);
        Stagione stagione = new Stagione(1);
        stagione.setSerieTv(serieTv);
        Puntata puntata = new Puntata("Imprudencias letales ", 2, "Río è vivo, è solo ferito alla testa. Berlino, al telefono con il Professore, gli rivela che Tokyo e Río hanno una relazione, ma lei smentisce tutto. Río, invece, quando sono soli, conferma a Berlino che ama Tokyo. Cominciano a esserci dei flashback ambientati nei cinque mesi di addestramento con il Professore. Nel frattempo la polizia prende le dovute contromisure e invia sul posto come negoziatrice l’ispettrice Raquel Murillo, una donna con problemi professionali e personali; vive con l'anziana madre e con sua figlia Paula, è separata da poco e ha denunciato per maltrattamenti l'ex marito, genio della Scientifica, che ora ha una relazione con sua sorella. Il braccio destro dell'ispettrice è Angel Rubio, segretamente innamorato di lei; inoltre la donna deve sottostare agli ordini del colonnello Prieto, a capo dei servizi segreti. ");
        puntata.setStagione(stagione);

        Recensione recensione = new Recensione("Bel film", 5);
        Recensore recensore = new Recensore("mariachiaranasto1@gmail.com", "Maria Chiara", "Nasto", LocalDate.of(2000, 2, 7), "xmariachiara", new BCryptPasswordEncoder().encode("ciao"), false, true);
        recensore.getListaRecensioni().add(recensione);
        recensione.setPuntata(puntata);
        recensione.setRecensore(recensore);

        when(puntataService.mergePuntata(any(Puntata.class))).thenAnswer(i -> i.getArgument(0, Puntata.class));
        when(utenteService.saveRegisteredUser(any(Utente.class))).thenAnswer(i -> i.getArgument(0, Utente.class));
        doNothing().when(recensioneRepository).delete(any(Recensione.class));

        try {
            recensioneService.removeRecensione(recensione);
            assert true;
        } catch (InvalidBeanException e) {
            assert false;
        }
    }

    @Test
    public void removeRecensione_recensioneNull() {
        assertThrows(InvalidBeanException.class, () -> recensioneService.removeRecensione(null));
    }

    @Test
    public void addRisposta_valid() {

    }

    @Test
    public void addRisposta_beanNotExist() {
        Recensione recensione = new Recensione("Bel film", 5);
        Recensore recensore = new Recensore("mariachiaranasto1@gmail.com", "Maria Chiara", "Nasto", LocalDate.of(2000, 2, 7), "xmariachiara", new BCryptPasswordEncoder().encode("ciao"), false, true);
        when(recensioneRepository.existsById(1L)).thenReturn(false);
        assertThrows(BeanNotExsistException.class, () -> recensioneService.addRisposta());
    }

}
