package com.unisa.cinehub.model.service;

import com.unisa.cinehub.data.entity.MiPiace;
import com.unisa.cinehub.data.entity.Recensione;
import com.unisa.cinehub.data.entity.Recensore;
import com.unisa.cinehub.data.repository.MiPiaceRepository;
import com.unisa.cinehub.data.repository.RecensioneRepository;
import com.unisa.cinehub.data.repository.RecensoreRepository;
import com.unisa.cinehub.data.repository.SegnalazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
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

            if(miPiaceRepository.existsById(new MiPiace.MiPiaceID(recensore.getEmail(), recensione.getId()))){
                MiPiace daDatabase = miPiaceRepository.findById(new MiPiace.MiPiaceID(recensore.getEmail(), recensione.getId())).orElse(null);
                if(daDatabase.isTipo() == b) {
                    logger.severe("Sto togliendo il mi piace: " + daDatabase);
                    recensore = recensoreRepository.findById(recensore.getEmail()).orElse(null);
                    recensore.getListaMiPiace().remove(daDatabase);
                    recensione = recensioneRepository.findById(recensione.getId()).orElse(null);
                    logger.info("madonna: " + recensione.getListaMiPiace().remove(daDatabase));
                    recensioneRepository.saveAndFlush(recensione);
                    recensoreRepository.saveAndFlush(recensore);
                    miPiaceRepository.delete(daDatabase);
                    miPiaceRepository.flush();


                } else {
                    logger.severe("Cambio il mi piace da: " + daDatabase.isTipo() + " a :" + b);
                    daDatabase.setTipo(b);
                    miPiaceRepository.saveAndFlush(daDatabase);
                }
            } else {
                logger.severe("Aggiungendo miPiace: " + miPiace);
                miPiaceRepository.save(miPiace);
                recensore.getListaMiPiace().add(miPiace);
                recensoreRepository.save(recensore);
                recensione.getListaMiPiace().add(miPiace);
                recensioneRepository.save(recensione);
            }
        }
    }

    public MiPiace findMiPiaceById(Recensore recensore, Recensione recensione) {
        MiPiace.MiPiaceID id = new MiPiace.MiPiaceID(recensore.getEmail(), recensione.getId());
        System.out.println(id);
        MiPiace miPiace = miPiaceRepository.findById(id).orElse(null);
        System.out.println(miPiace);
        return miPiace;
    }

    public Integer getNumeroMiPiaceOfRecensione(Recensione recensione) {
        System.out.println("Cotenggio mi piace - " + recensione);
        List<MiPiace> miPiace = miPiaceRepository.getNumMiPiace(recensione.getId());
        return miPiace.size();
    }

    public Integer getNumeroNonMiPiaceOfRecensione(Recensione recensione) {
        System.out.println("Cotenggio mi piace - " + recensione);
        List<MiPiace> miPiace = miPiaceRepository.getNumNonMiPiace(recensione.getId());
        return miPiace.size();
    }

}
