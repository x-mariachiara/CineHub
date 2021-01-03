package com.unisa.cinehub.model.service;

import com.unisa.cinehub.data.entity.Cast;
import com.unisa.cinehub.data.repository.CastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class CastService {

    private static Logger logger = Logger.getLogger("CastService");

    @Autowired
    private CastRepository castRepository;

    public CastService(CastRepository castRepository) {
        this.castRepository = castRepository;
    }

    public void addCast(Cast cast) {
        castRepository.save(cast);
    }
}
