package com.unisa.cinehub.model.service;

import com.unisa.cinehub.data.entity.Cast;
import com.unisa.cinehub.data.repository.CastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        if(cast != null && !cast.getNome().isBlank() && !cast.getCognome().isBlank())
            castRepository.save(cast);
    }

    public void removeCast(Long id) {
        if(id != null && castRepository.existsById(id)) {
            castRepository.deleteById(id);
        }
    }

    public List<Cast> retrieveAll() { return castRepository.findAll(); }

    public Cast retrieveByKey(Long id) {
        if(id != null) {
            return castRepository.findById(id).orElse(null);
        }
        return null;
    }

    public void mergeCast(Cast cast) {
        if(cast != null && !cast.getNome().isBlank() && !cast.getCognome().isBlank())
            castRepository.save(cast);
    }
}
