package com.unisa.cinehub.model.service;

import com.unisa.cinehub.data.entity.Puntata;
import com.unisa.cinehub.data.entity.SerieTv;
import com.unisa.cinehub.data.entity.Stagione;
import com.unisa.cinehub.data.repository.PuntataRepository;
import com.unisa.cinehub.data.repository.SerieTVRepository;
import com.unisa.cinehub.data.repository.StagioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service
public class PuntataService {
    @Autowired
    private StagioneRepository stagioneRepository;
    @Autowired
    private PuntataRepository puntataRepository;
    @Autowired
    private SerieTVService serieTVService;

    public PuntataService(SerieTVService serieTVService, StagioneRepository stagioneRepository, PuntataRepository puntataRepository) {
        this.serieTVService = serieTVService;
        this.stagioneRepository = stagioneRepository;
        this.puntataRepository = puntataRepository;
    }

    public void addPuntata(Puntata puntata, Integer numeroStagione, Long idSerieTv) {
        SerieTv serieTv = serieTVService.retrieveByKey(idSerieTv);
        if(serieTv != null) {
            Stagione stagione = new Stagione(numeroStagione);
            stagione.setSerieTv(serieTv);
            if(!serieTv.getStagioni().contains(stagione)) {
                serieTVService.addStagione(serieTv, numeroStagione);
            }
            puntata.setStagione(stagione);
            puntataRepository.save(puntata);
        }
    }

    public void removePuntata(Puntata daEliminare) {
        if(daEliminare != null) {
            Puntata.PuntataID id = new Puntata.PuntataID(daEliminare.getNumeroPuntata(), daEliminare.getStagione());
            if(puntataRepository.existsById(id)) {
                Puntata puntata = puntataRepository.findById(id).get();
                puntataRepository.delete(puntata);
                Stagione stagione = puntata.getStagione();
                if(stagione.getPuntate().isEmpty()) {
                    serieTVService.removeStagione(stagione.getSerieTv(), stagione.getNumeroStagione());
                }
            }
        }
    }

    public Collection<Puntata> retrieveBySerieTV(Long idSerieTv) {
        List<Puntata> puntate = new ArrayList<>();
        if(idSerieTv != null) {
            SerieTv serieTv = serieTVService.retrieveByKey(idSerieTv);
            for(Stagione s : serieTv.getStagioni()) {
                puntate.addAll(s.getPuntate());
            }
            return puntate;
        }

        return null;
    }

    public Collection<Puntata> retrieveByStagione(Long idSerieTv, Integer numeroStagione) {
        if(idSerieTv != null && numeroStagione != null) {
            SerieTv serieTv = serieTVService.retrieveByKey(idSerieTv);
            Stagione stagione = serieTVService.getStagione(serieTv, numeroStagione).orElse(null);
            if (stagione != null) {
                return stagione.getPuntate();
            }
        }
        return null;
    }
}
