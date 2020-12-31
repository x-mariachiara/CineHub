package com.unisa.cinehub.model.service;

import com.unisa.cinehub.data.entity.Utente;
import com.unisa.cinehub.data.repository.RecensoreRepository;
import com.unisa.cinehub.data.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    public UtenteService(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }


    public boolean signup(Utente utente) {

        if (LocalDate.now().getYear() - utente.getDataNascita().getYear() < 13) {
            return false;
        } else if (utenteRepository.existsById(utente.getEmail())) {
            return false;
        } else {
            utente.setActive(false);
            utente.setBannato(false);
            utente.setPassword(new BCryptPasswordEncoder().encode(utente.getPassword()));
            utenteRepository.save(utente);
            return true;
        }
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
