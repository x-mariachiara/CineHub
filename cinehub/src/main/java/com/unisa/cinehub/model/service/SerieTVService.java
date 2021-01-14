package com.unisa.cinehub.model.service;

import com.unisa.cinehub.data.entity.*;
import com.unisa.cinehub.data.repository.GenereRepository;
import com.unisa.cinehub.data.repository.SerieTVRepository;
import com.unisa.cinehub.data.repository.StagioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

@Service
public class SerieTVService {

    private static Logger logger = Logger.getLogger("SerieTVService");

    @Autowired
    private SerieTVRepository serieTVRepository;
    @Autowired
    private GenereRepository genereRepository;
    @Autowired
    private StagioneRepository stagioneRepository;

    public SerieTVService(SerieTVRepository serieTVRepository, GenereRepository genereRepository, StagioneRepository stagioneRepository) {
        this.serieTVRepository = serieTVRepository;
        this.genereRepository = genereRepository;
        this.stagioneRepository = stagioneRepository;
    }

    public SerieTv addSerieTV(SerieTv serieTv) {
        return serieTVRepository.save(serieTv);
    }

    public void removeSerieTV(Long id) {
        if(id != null && serieTVRepository.existsById(id)) {
            serieTVRepository.delete(serieTVRepository.findById(id).get());
        }
    }

    public List<SerieTv> retrieveAll() {
        return serieTVRepository.findAll();
    }

    public SerieTv retrieveByKey(Long id) {
        Optional<SerieTv> filmOptional = serieTVRepository.findById(id);
        //TODO per ora ritorna null se serieTV non lo trova
        return filmOptional.orElse(null);
    }

    /**
     * Questo metodo consente di aggiungere dei metodi a una serie tv
     * @param generi collezione di generi da aggiungere
     * @param id id della serie a cui aggiungerli
     */
    public void addGeneri(Collection<Genere> generi, Long id) {

        SerieTv serieTv;
        Optional<SerieTv> fromDB = serieTVRepository.findById(id);
        if (!fromDB.isPresent()) {
            logger.severe("Film non trovato. Annulata operazione");
            return ;
        } else {
            serieTv = fromDB.get();
        }

        //Controlla se i generi sono già presenti sul DB, in caso negativo li aggiunge
        for(Genere g : generi) {
            if (!genereRepository.existsById(g.getNomeGenere())) {
                logger.info("Genere " + g.getNomeGenere() + " mai inserito prima. Aggiungo.");
                genereRepository.save(g);
            }
        }


        serieTv.getGeneri().addAll(generi);
        serieTVRepository.save(serieTv);
    }

    /**
     * Modifica una serie tv esistente
     * @param serieTv serieTV esistente con attributi modificati da salvare
     */
    public void mergeSerieTV(SerieTv serieTv) {
        if (serieTv.getId() != null && serieTVRepository.existsById(serieTv.getId())) {
            serieTVRepository.save(serieTv);
            logger.info("SerieTV: " + serieTv + " modificato correttamente");
        }
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
        return null;
    }

    /**
     * Permette di cercare una serie tv tramite genere
     * @param nomiGeneri collezione di generi per effettuare la ricerca
     * @return una lista di serie tv
     */
    public Collection<SerieTv> searchByGenere(Collection<Genere> nomiGeneri) {
        HashSet<SerieTv> risultati = new HashSet<>();
        if(nomiGeneri != null && !nomiGeneri.isEmpty()) {

            List<Genere> generi = new ArrayList<>();
            for(Genere g : nomiGeneri) {
                generi.add(genereRepository.findById(g.getNomeGenere()).get());
            }

            for(Genere g : generi) {
                Set<Media> media = g.getMediaCollegati();
                for(Media m : media) {
                    if(m instanceof SerieTv)
                        risultati.add((SerieTv) m);
                }
            }
            return risultati;
        }
        return null;
    }

    /**
     * Permette di aggiungere una stagione ad una serie tv
     * @param serieTv serieTv a cui aggiungere la stagione
     * @param stagione stagione da aggiungere alla serietv
     */
    public void addStagione(SerieTv serieTv, Stagione stagione) {
        serieTv.getStagioni().add(stagione);
        serieTVRepository.save(serieTv);
    }

    /**
     * Permette di rimuovere una stagione da una serie tv
     * @param serieTv serieTV a cui rimuovere la stagione
     * @param numeroStagione numero della stagione da eliminare
     */
    public void removeStagione(SerieTv serieTv, Integer numeroStagione) {
        Stagione stagione = new Stagione(numeroStagione);
        serieTv.getStagioni().remove(stagione);
        serieTVRepository.save(serieTv);
    }


    /**
     * Ritorna la stagione di una serie tv dato il suo numero
     * @param serieTv serieTv da cui estrarre la stagione
     * @param numeroStagione numero della stagione da estrarre
     * @return la stagione corrispondente a quel numero se  esiste
     */
    public Optional<Stagione> getStagione(SerieTv serieTv, Integer numeroStagione) {
        Collection<Stagione> stagioni = serieTv.getStagioni();
        for (Stagione s : stagioni) {
            if (s.getNumeroStagione().equals(numeroStagione)) {
                return Optional.of(s);
            }
        }
        return Optional.empty();
    }

    /**
     * Ritorna la stagione di una serie tv dato il suo numero
     * @param idSerieTv id della serieTv da cui estrarre la stagione
     * @param numeroStagione numero della stagione da estrarre
     * @return la stagione corrispondente a quel numero se  esiste
     */
    public Optional<Stagione> getStagione(Long idSerieTv, Integer numeroStagione) {
        SerieTv serieTv = retrieveByKey(idSerieTv);
        Collection<Stagione> stagioni = serieTv.getStagioni();
        for (Stagione s : stagioni) {
            if (s.getNumeroStagione().equals(numeroStagione)) {
                return Optional.of(s);
            }
        }
        return Optional.empty();
    }

    public void aggiornaStagione(Stagione stagione) {
        stagioneRepository.save(stagione);
    }

    public List<SerieTv> findMostRecentSerieTv(Integer howMany) {
        List<SerieTv> mostRecentSerieTv = new ArrayList<>();
        List<SerieTv> serieTv = serieTVRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        int size = serieTv.size();
        if(size <= howMany) {
            mostRecentSerieTv.addAll(serieTv);
        } else {
            for(int i = 1; i <= howMany; i++) {
                mostRecentSerieTv.add(serieTv.get(i));
            }
        }
        return mostRecentSerieTv;
    }
}
