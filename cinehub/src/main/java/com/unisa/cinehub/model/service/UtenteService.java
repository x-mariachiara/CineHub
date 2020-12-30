package com.unisa.cinehub.model.service;

import com.unisa.cinehub.data.entity.Utente;
import com.unisa.cinehub.data.repository.RecensoreRepository;
import com.unisa.cinehub.data.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    public UtenteService(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }


    public void signup(Utente utente) {
        utente.setActive(false);
        utente.setBannato(false);

        //vari controlli di business sui parametri
        utenteRepository.save(utente);
    }

    private void sendMail() {

    }

    public Utente findByEmail(String email){
        if (email != null && !email.isBlank()){
            return utenteRepository.findById(email).orElse(null);
        }
        return null;
    }
}
