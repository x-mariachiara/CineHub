package com.unisa.cinehub.model.service;

import com.unisa.cinehub.data.entity.Cast;
import com.unisa.cinehub.data.repository.CastRepository;
import com.unisa.cinehub.model.exception.BeanNotExsistException;
import com.unisa.cinehub.model.exception.InvalidBeanException;
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

    public Cast addCast(Cast cast) throws InvalidBeanException{
        if(cast != null && !cast.getNome().isBlank() && !cast.getCognome().isBlank()) {
            return castRepository.save(new Cast(cast.getNome(), cast.getCognome()));
        } else {
            throw new InvalidBeanException("Cast " + cast + " non è valido");
        }

    }

    public void removeCast(Long id) throws BeanNotExsistException, InvalidBeanException {
        if(id != null) {
            if(!castRepository.existsById(id)) {
                castRepository.deleteById(id);
            }
            else throw new BeanNotExsistException("nessun cast con id: " + id);
        }
        else throw new InvalidBeanException("id non può essere null");

    }

    public List<Cast> retrieveAll() { return castRepository.findAll(); }

    public Cast retrieveByKey(Long id) throws BeanNotExsistException, InvalidBeanException {
        if(id != null) {
            if (castRepository.findById(id).isPresent()) {
                return castRepository.findById(id).get();
            }
            else throw new BeanNotExsistException("nessun cast con id: " + id);
        }
        else throw new InvalidBeanException("id non può essere null");
    }

    public Cast mergeCast(Cast cast) throws InvalidBeanException, BeanNotExsistException {
        if (cast != null && !cast.getNome().isBlank() && !cast.getCognome().isBlank()) {
            if (castRepository.existsById(cast.getId())) {
                return castRepository.save(cast);
            }
            else throw new BeanNotExsistException("Cast " + cast + " non esiste");
        }
        else throw new InvalidBeanException("Cast " + cast + " non è valido");
    }

}
