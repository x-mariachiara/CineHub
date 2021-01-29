package com.unisa.cinehub.model.media.serietv;

import com.unisa.cinehub.data.entity.*;
import com.unisa.cinehub.model.exception.AlreadyExsistsException;
import com.unisa.cinehub.model.exception.BeanNotExsistException;
import com.unisa.cinehub.model.exception.InvalidBeanException;
import com.unisa.cinehub.model.media.GenereRepository;
import com.unisa.cinehub.model.media.RuoloService;
import com.unisa.cinehub.model.utente.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class SerieTVService {

    private static Logger logger = Logger.getLogger("SerieTVService");

    @Autowired
    private SerieTVRepository serieTVRepository;
    @Autowired
    private GenereRepository genereRepository;
    @Autowired
    private StagioneRepository stagioneRepository;
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private PuntataRepository puntataRepository;
    @Autowired
    private RuoloService ruoloService;


    public SerieTVService(SerieTVRepository serieTVRepository, GenereRepository genereRepository, StagioneRepository stagioneRepository, UtenteRepository utenteRepository, PuntataRepository puntataRepository) {
        this.serieTVRepository = serieTVRepository;
        this.genereRepository = genereRepository;
        this.stagioneRepository = stagioneRepository;
        this.utenteRepository = utenteRepository;
        this.puntataRepository = puntataRepository;
    }

    public SerieTv addSerieTV(SerieTv serieTv) throws AlreadyExsistsException, InvalidBeanException {
        if(Media.checkMedia(serieTv)) {
            logger.info("Una serie tv simile è già presente: " + serieTVRepository.existsByTitleAnnoUscita(serieTv.getTitolo(), serieTv.getAnnoUscita()));
            if(!serieTVRepository.existsByTitleAnnoUscita(serieTv.getTitolo(), serieTv.getAnnoUscita()) && serieTv.getId() == null) {
                serieTv.getGeneri().clear();
                return serieTVRepository.save(serieTv);
            }
            else throw  new AlreadyExsistsException("Esiste già una serie tv con titolo: " + serieTv.getTitolo() + " e anno di uscita: " + serieTv.getAnnoUscita());
        }
        else throw new InvalidBeanException("Formato valori di: " + serieTv + "non validi");
    }

    public void removeSerieTV(Long id) throws InvalidBeanException, BeanNotExsistException {
        if (id != null) {
            if (serieTVRepository.existsById(id)) {
                SerieTv daRimuovere = serieTVRepository.getOne(id);
                daRimuovere.setVisibile(false);
                serieTVRepository.save(daRimuovere);
                for(Stagione s : daRimuovere.getStagioni()) {
                    for(Puntata p : s.getPuntate()) {
                        p.setVisibile(false);
                        puntataRepository.save(p);
                    }
                }
            }
            else throw  new BeanNotExsistException("Non esiste una serietv con id + " + id);
        }
        else throw new InvalidBeanException("id null non valido");
    }

    public List<SerieTv> retrieveAll() {
        return serieTVRepository.findAll();
    }

    public SerieTv retrieveByKey(Long id) throws InvalidBeanException, BeanNotExsistException {
        if(id != null) {
            if(serieTVRepository.existsById(id) && serieTVRepository.findById(id).get().getVisibile()) {
                return serieTVRepository.findById(id).get();
            }
            else throw new BeanNotExsistException("Non esiste una serietv con id + " + id);
        }
        else throw new InvalidBeanException("id null non valido");
    }

    /**
     * Questo metodo consente di aggiungere dei metodi a una serie tv
     * @param generi collezione di generi da aggiungere
     * @param id id della serie a cui aggiungerli
     */
    public SerieTv addGeneri(Collection<Genere> generi, Long id) throws BeanNotExsistException, InvalidBeanException {

        if (!generi.isEmpty() && id != null) {
            SerieTv serieTv;
            Optional<SerieTv> fromDB = serieTVRepository.findById(id);
            if (!fromDB.isPresent()) {
                logger.severe("Film non trovato. Annulata operazione");
                throw new BeanNotExsistException("Non esiste una serietv con id + " + id);
            } else {
                serieTv = fromDB.get();
            }

            //Controlla se i generi sono già presenti sul DB, in caso negativo li aggiunge
            for (Genere g : generi) {
                if (!genereRepository.existsById(g.getNomeGenere())) {
                    logger.info("Genere " + g.getNomeGenere() + " mai inserito prima. Aggiungo.");
                    genereRepository.save(g);
                }
            }

            HashSet<Genere> daAggiungere = new HashSet<>(generi);
            serieTv.setGeneri(daAggiungere);
            return serieTVRepository.save(serieTv);
        }
        else throw new InvalidBeanException();
    }

    public SerieTv addCast(Collection<Ruolo> ruoli, Long id) throws InvalidBeanException, BeanNotExsistException {
        if (ruoli != null && id != null) {
            SerieTv serietv = serieTVRepository.findById(id).orElse(null);
            if (serietv != null) {
                ruoloService.cleanRuolo(id);
                for (Ruolo r : ruoli) {
                    r.setMedia(serietv);
                    ruoloService.addRuolo(r, r.getCastId(), id);
                }
                serietv.setRuoli(ruoli);
                logger.info("Ruoli: " + ruoli + " aggiunti alla serie tv: " + serietv.getRuoli());
                return serieTVRepository.saveAndFlush(serietv);
            } else {
                throw new BeanNotExsistException("Serie Tv con id = " + id + "non esistente");
            }
        } else {
            throw new InvalidBeanException("Aggiunta cast non valida per generi: " + ruoli + "e id=" + id);
        }
    }

    /**
     * Modifica una serie tv esistente
     * @param serieTv serieTV esistente con attributi modificati da salvare
     */
    public SerieTv mergeSerieTV(SerieTv serieTv) throws InvalidBeanException, BeanNotExsistException {
        if (Media.checkMedia(serieTv) && serieTv.getId() != null) {
            if(serieTVRepository.existsById(serieTv.getId())) {
                logger.info("SerieTV: " + serieTv + " modificato correttamente");
                return serieTVRepository.save(serieTv);
            }
            else throw new  BeanNotExsistException("Non esiste una serietv con id + " + serieTv.getId());
        }
        else throw new InvalidBeanException("Sono io:" + Media.checkMedia(serieTv) + "  " + (serieTv.getId()!=null) );
    }

    /**
     * Permete di ricercare una serie tv per titolo
     * @param titolo parametro di ricerca
     * @return lista di serie tv con titolo corrispondente al parametro titolo
     */
    public List<SerieTv> searchByTitle(String titolo) {
        List<SerieTv> risultati = new ArrayList<>();
        if(!titolo.isBlank()) {
            titolo = titolo.trim();
            logger.info("Ricerca per titolo avviata con titolo: " + titolo);
            risultati.addAll(serieTVRepository.findSerieTVByTitle(titolo));
            return risultati;
        }
        return risultati;
    }


    /**
     * Permette di cercare una serie tv tramite genere
     * @param nomiGeneri collezione di generi per effettuare la ricerca
     * @return una lista di serie tv
     */
    public Collection<SerieTv> searchByGenere(Collection<Genere> nomiGeneri) throws InvalidBeanException {
        HashSet<SerieTv> risultati = new HashSet<>();
        if(nomiGeneri != null && !nomiGeneri.isEmpty()) {
            List<Genere> generi = new ArrayList<>();
            for (Genere g : nomiGeneri) {
                if(genereRepository.existsById(g.getNomeGenere())) {
                    generi.add(genereRepository.findById(g.getNomeGenere()).get());
                }
            }
            for (Genere g : generi) {
                Set<Media> media = g.getMediaCollegati().stream().filter(media1 -> media1.getVisibile()).collect(Collectors.toSet());
                for (Media m : media) {
                    if (m instanceof SerieTv)
                        risultati.add((SerieTv) m);
                }
            }
            return risultati;
        }
        return risultati;
    }

    /**
     * Permette di aggiungere una stagione ad una serie tv
     * @param serieTv serieTv a cui aggiungere la stagione
     * @param stagione stagione da aggiungere alla serietv
     */
    public SerieTv addStagione(SerieTv serieTv, Stagione stagione) throws InvalidBeanException, BeanNotExsistException {
        if(Media.checkMedia(serieTv) && stagione.getNumeroStagione() >= 1) {
            if(serieTVRepository.existsById(serieTv.getId())) {
                serieTv.getStagioni().add(stagione);
                return serieTVRepository.save(serieTv);
            }
            else throw new BeanNotExsistException("Non esiste una serietv con id + " + serieTv.getId());
        }
        else throw new InvalidBeanException("serie tv: "+ serieTv + "numero stagione: " + stagione.getNumeroStagione() + "non validi");
    }

    /**
     * Permette di rimuovere una stagione da una serie tv
     * @param serieTv serieTV a cui rimuovere la stagione
     * @param numeroStagione numero della stagione da eliminare
     */
    public SerieTv removeStagione(SerieTv serieTv, Integer numeroStagione) throws BeanNotExsistException, InvalidBeanException {
        if(Media.checkMedia(serieTv) && numeroStagione >= 1 && numeroStagione <= ((Stagione)((ArrayList) serieTv.getStagioni()).get(serieTv.getStagioni().size()-1)).getNumeroStagione()) {
            Stagione stagione = new Stagione(numeroStagione);
            stagione.setSerieTv(serieTv);
            if(serieTVRepository.existsById(serieTv.getId()) && serieTv.getStagioni().contains(stagione)) {
                serieTv.getStagioni().remove(stagione);
                return serieTVRepository.save(serieTv);
            }
            else throw new BeanNotExsistException("Non esiste una serietv con id + " + serieTv.getId() + "e/o non contiene la stagione numero: " + numeroStagione);
        }
        else throw new InvalidBeanException();
    }


    /**
     * Ritorna la stagione di una serie tv dato il suo numero
     * @param serieTv serieTv da cui estrarre la stagione
     * @param numeroStagione numero della stagione da estrarre
     * @return la stagione corrispondente a quel numero se  esiste
     */
    public Stagione getStagione(SerieTv serieTv, Integer numeroStagione) throws BeanNotExsistException, InvalidBeanException {
        if(serieTv != null && numeroStagione != null) {
            Collection<Stagione> stagioni = serieTv.getStagioni();
            for (Stagione s : stagioni) {
                if (s.getNumeroStagione().equals(numeroStagione)) {
                    return s;
                }
            }
            throw new BeanNotExsistException("La serietv non ha stagioni o la stagione cercata non esiste");
        }
        else throw new InvalidBeanException("Dati invalidi per serietv: " + serieTv + " numero stagione: " + numeroStagione);
    }

    /**
     * Ritorna la stagione di una serie tv dato il suo numero
     * @param idSerieTv id della serieTv da cui estrarre la stagione
     * @param numeroStagione numero della stagione da estrarre
     * @return la stagione corrispondente a quel numero se  esiste
     */
    public Stagione getStagione(Long idSerieTv, Integer numeroStagione) throws InvalidBeanException, BeanNotExsistException {
        if(serieTVRepository.existsById(idSerieTv)) {
            SerieTv serieTv = serieTVRepository.findById(idSerieTv).get();
            return getStagione(serieTv, numeroStagione);
        }
        throw new BeanNotExsistException("La serietv con id: " + idSerieTv + "non esiste");

    }

    public Stagione aggiornaStagione(Stagione stagione) throws InvalidBeanException {
        if(stagione != null) {
            return stagioneRepository.save(stagione);
        }
        else throw new InvalidBeanException();
    }

    public List<SerieTv> findMostRecentSerieTv(Integer howMany) {
        List<SerieTv> mostRecentSerieTv = new ArrayList<>();
        List<SerieTv> serieTv = serieTVRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).stream().filter(serietv1 -> serietv1.getVisibile()).collect(Collectors.toList());
        int size = serieTv.size();
        if(howMany >= 0) {
            if (size <= howMany) {
                mostRecentSerieTv.addAll(serieTv);
            } else {
                for (int i = 0; i < howMany; i++) {
                    mostRecentSerieTv.add(serieTv.get(i));
                }
            }
            return mostRecentSerieTv;
        }
        return mostRecentSerieTv;
    }
}
