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


}
