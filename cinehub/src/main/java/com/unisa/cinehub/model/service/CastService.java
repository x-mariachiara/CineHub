package com.unisa.cinehub.model.service;

import com.unisa.cinehub.data.entity.*;
import com.unisa.cinehub.data.repository.CastRepository;
import com.unisa.cinehub.data.repository.FilmRepository;
import com.unisa.cinehub.data.repository.RuoloRepository;
import com.unisa.cinehub.data.repository.SerieTVRepository;
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

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private SerieTVRepository serieTVRepository;

    @Autowired
    private RuoloRepository ruoloRepository;

    public CastService(CastRepository castRepository, FilmRepository filmRepository, SerieTVRepository serieTVRepository, RuoloRepository ruoloRepository) {
        this.castRepository = castRepository;
        this.filmRepository = filmRepository;
        this.serieTVRepository = serieTVRepository;
        this.ruoloRepository = ruoloRepository;
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
            if(castRepository.existsById(id)) {
                Cast toRemove = castRepository.getOne(id);
                for(Ruolo r : toRemove.getRuoli()) {
                    Media m = r.getMedia();
                    m.getRuoli().remove(r);
                    if(m instanceof Film) {
                        filmRepository.save((Film) m);
                    } else {
                        serieTVRepository.save((SerieTv) m);
                    }
                }
                toRemove.getRuoli().clear();
                castRepository.save(toRemove);
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
