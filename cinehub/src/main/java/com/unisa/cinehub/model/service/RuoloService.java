package com.unisa.cinehub.model.service;

import com.unisa.cinehub.data.entity.*;
import com.unisa.cinehub.data.repository.CastRepository;
import com.unisa.cinehub.data.repository.FilmRepository;
import com.unisa.cinehub.data.repository.RuoloRepository;
import com.unisa.cinehub.data.repository.SerieTVRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuoloService {

    @Autowired
    private RuoloRepository ruoloRepository;
    @Autowired
    private CastRepository castRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private SerieTVRepository serieTVRepository;

    public RuoloService(RuoloRepository ruoloRepository, CastRepository castRepository, FilmRepository filmRepository, SerieTVRepository serieTVRepository) {
        this.ruoloRepository = ruoloRepository;
        this.castRepository = castRepository;
        this.filmRepository = filmRepository;
        this.serieTVRepository = serieTVRepository;
    }

    public void addRuolo(Ruolo ruolo, Long castId, Long mediaId) {
        Cast cast = castRepository.findById(castId).orElse(null);
        Media media = filmRepository.findById(mediaId).orElse(null);
        Ruolo daAggiungere = new Ruolo(ruolo.getTipo());
        if(media == null) {
            media = serieTVRepository.findById(mediaId).orElse(null);
        }
        if(media != null && cast != null) {
            daAggiungere.setCast(cast);
            daAggiungere.setMedia(media);
            ruoloRepository.save(daAggiungere);
            media.getRuoli().add(daAggiungere);
            if(media instanceof Film) {
                filmRepository.save((Film) media);
            } else {
                serieTVRepository.save((SerieTv) media);
            }
        }
    }
}
