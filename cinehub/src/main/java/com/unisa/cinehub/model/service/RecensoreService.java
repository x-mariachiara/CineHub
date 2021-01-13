package com.unisa.cinehub.model.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import com.unisa.cinehub.data.entity.Recensore;
import com.unisa.cinehub.data.repository.RecensoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RecensoreService {

    @Autowired
    private RecensoreRepository recensoreRepository;

    public RecensoreService(RecensoreRepository recensoreRepository) {
        this.recensoreRepository = recensoreRepository;
    }

    public List<Recensore> findAll(){
        return recensoreRepository.findAll();
    }

    public List<Recensore> finAllNotBanned() {
        return recensoreRepository.findNotBanned();
    }

    public void save(){
        Recensore recensore = new Recensore("edrioe@gmail.com", "Andrea", "Ercolino", LocalDate.of(1999, 07, 22), "Piccibu di Maria Chiara", new BCryptPasswordEncoder().encode("ciao"), false, true);
        recensoreRepository.save(recensore);
    }


}
