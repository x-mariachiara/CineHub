package com.unisa.cinehub.model.service;

import com.unisa.cinehub.data.entity.MiPiace;
import com.unisa.cinehub.data.entity.Recensione;
import com.unisa.cinehub.data.entity.Recensore;
import com.unisa.cinehub.data.repository.MiPiaceRepository;
import com.unisa.cinehub.data.repository.RecensioneRepository;
import com.unisa.cinehub.data.repository.RecensoreRepository;
import com.unisa.cinehub.data.repository.SegnalazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class MiPiaceService {

    private static Logger logger = Logger.getLogger("MiPiaceService");

    @Autowired
    MiPiaceRepository miPiaceRepository;

    @Autowired
    RecensioneRepository recensioneRepository;

    @Autowired
    RecensoreRepository recensoreRepository;

    public MiPiaceService(MiPiaceRepository miPiaceRepository, RecensioneRepository recensioneRepository, RecensoreRepository recensoreRepository) {
        this.miPiaceRepository = miPiaceRepository;
        this.recensioneRepository = recensioneRepository;
        this.recensoreRepository = recensoreRepository;
    }

    public void addMiPiace(boolean b, Recensione recensione, Recensore recensore) {
        if(recensione != null && recensore != null) {
            MiPiace miPiace = new MiPiace(b);
            miPiace.setRecensione(recensione);
            miPiace.setRecensore(recensore);
            miPiaceRepository.save(miPiace);
            recensore.getListaMiPiace().add(miPiace);
            recensoreRepository.save(recensore);
            recensione.getListaMiPiace().add(miPiace);
            recensioneRepository.save(recensione);
        }
    }

    public MiPiace findMiPiaceById(Recensore recensore, Recensione recensione) {
        MiPiace.MiPiaceID id = new MiPiace.MiPiaceID(recensore, recensione);
        System.out.println(id);
        MiPiace miPiace = miPiaceRepository.findById(id).orElse(null);
        System.out.println(miPiace);
        return miPiace;
    }

    public void changeMiPiace(Recensore recensore, Recensione recensione) {
        if(recensore != null && recensione != null) {
            MiPiace.MiPiaceID id = new MiPiace.MiPiaceID(recensore, recensione);
            MiPiace toChange = miPiaceRepository.findById(id).orElse(null);
            if(toChange != null) {
                toChange.setTipo(!toChange.isTipo());
                miPiaceRepository.save(toChange);
            }
        }
    }
}
