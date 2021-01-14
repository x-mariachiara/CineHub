package com.unisa.cinehub.model.service;

import com.unisa.cinehub.data.entity.MiPiace;
import com.unisa.cinehub.data.entity.Recensione;
import com.unisa.cinehub.data.entity.Recensore;
import com.unisa.cinehub.data.repository.MiPiaceRepository;
import com.unisa.cinehub.data.repository.RecensioneRepository;
import com.unisa.cinehub.data.repository.RecensoreRepository;
import com.unisa.cinehub.data.repository.SegnalazioneRepository;
import com.unisa.cinehub.model.exception.BeanNotExsistException;
import com.unisa.cinehub.model.exception.InvalidBeanException;
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

    public MiPiace handleMiPiace(boolean b, Recensione recensione, Recensore recensore) throws InvalidBeanException {
        if(recensione != null && recensore != null) {
            MiPiace miPiace = new MiPiace(b);
            miPiace.setRecensione(recensione);
            miPiace.setRecensore(recensore);

            if(miPiaceRepository.existsById(new MiPiace.MiPiaceID(recensore.getEmail(), recensione.getId()))){
                MiPiace daDatabase = miPiaceRepository.findById(new MiPiace.MiPiaceID(recensore.getEmail(), recensione.getId())).orElse(null);
                if(daDatabase.isTipo() == b) {
                    return togliMiPiace(recensione, recensore, daDatabase);
                } else {
                    return modificaMiPiace(b, daDatabase);
                }
            } else {
                return aggiungiMiPiace(recensione, recensore, miPiace);
            }
        } else throw new InvalidBeanException();
    }

    private MiPiace aggiungiMiPiace(Recensione recensione, Recensore recensore, MiPiace miPiace) {
        MiPiace salvato = miPiaceRepository.save(miPiace);
        recensore.getListaMiPiace().add(miPiace);
        recensoreRepository.save(recensore);
        recensione.getListaMiPiace().add(miPiace);
        recensioneRepository.save(recensione);
        return salvato;
    }

    private MiPiace modificaMiPiace(boolean b, MiPiace daDatabase) {
        daDatabase.setTipo(b);
        miPiaceRepository.saveAndFlush(daDatabase);
        return daDatabase;
    }

    private MiPiace togliMiPiace(Recensione recensione, Recensore recensore, MiPiace daDatabase) {
        recensore = recensoreRepository.findById(recensore.getEmail()).orElse(null);
        recensore.getListaMiPiace().remove(daDatabase);
        recensione = recensioneRepository.findById(recensione.getId()).orElse(null);
        recensioneRepository.saveAndFlush(recensione);
        recensoreRepository.saveAndFlush(recensore);
        miPiaceRepository.delete(daDatabase);
        miPiaceRepository.flush();
        return daDatabase;
    }

    public MiPiace findMiPiaceById(Recensore recensore, Recensione recensione) throws BeanNotExsistException, InvalidBeanException {
        if(recensore != null && recensione != null) {
            MiPiace.MiPiaceID id = new MiPiace.MiPiaceID(recensore.getEmail(), recensione.getId());
            MiPiace miPiace = miPiaceRepository.findById(id).orElse(null);
            if (miPiace != null) {
                return miPiace;
            } else {
                throw new BeanNotExsistException();
            }
        } else {
            throw new InvalidBeanException();
        }
    }

    public Integer getNumeroMiPiaceOfRecensione(Recensione recensione) {
        List<MiPiace> miPiace = miPiaceRepository.getNumMiPiace(recensione.getId());
        return miPiace.size();
    }

    public Integer getNumeroNonMiPiaceOfRecensione(Recensione recensione) {
        List<MiPiace> miPiace = miPiaceRepository.getNumNonMiPiace(recensione.getId());
        return miPiace.size();
    }

}
