package com.unisa.cinehub.model.utente;

import java.util.List;
import com.unisa.cinehub.data.entity.Recensore;
import org.springframework.beans.factory.annotation.Autowired;
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
