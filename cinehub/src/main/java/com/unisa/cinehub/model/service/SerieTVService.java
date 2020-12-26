package com.unisa.cinehub.model.service;

import com.unisa.cinehub.data.entity.*;
import com.unisa.cinehub.data.repository.GenereRepository;
import com.unisa.cinehub.data.repository.SerieTVRepository;
import com.unisa.cinehub.data.repository.StagioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

@Service
public class SerieTVService {

    private static Logger logger = Logger.getLogger("StagioneService");

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

    public void addSerieTV(SerieTv serieTv) {
        serieTVRepository.save(serieTv);
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

    public void mergeSerieTV(SerieTv serieTv) {
        if (serieTv.getId() != null && serieTVRepository.existsById(serieTv.getId())) {
            serieTVRepository.save(serieTv);
            logger.info("SerieTV: " + serieTv + " modificato correttamente");
        }
    }

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

    public void addStagione(SerieTv serieTv, Integer numeroStagione) {
        //Creo la stagione
        Stagione stagione = new Stagione(numeroStagione);
        //la rendo persistente
        stagioneRepository.save(stagione);
        //la aggiungo alla serie tv
        serieTv.getStagioni().add(stagione);
        //aggiorno le modifiche
        serieTVRepository.save(serieTv);
    }

    public void removeStagione(SerieTv serieTv, Integer numeroStagione) {
        Stagione stagione = new Stagione(numeroStagione);
        serieTv.getStagioni().remove(stagione);
        serieTVRepository.save(serieTv);
    }

    public Optional<Stagione> getStagione(SerieTv serieTv, Integer numeroStagione) {
        Collection<Stagione> stagioni = serieTv.getStagioni();
        for (Stagione s : stagioni) {
            if (s.getNumeroStagione().equals(numeroStagione)) {
                return Optional.of(s);
            }
        }
        return Optional.empty();
    }

}
