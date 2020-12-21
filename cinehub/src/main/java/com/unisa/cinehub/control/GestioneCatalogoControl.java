package com.unisa.cinehub.control;

import com.unisa.cinehub.data.entity.Film;
import com.unisa.cinehub.data.entity.Genere;
import com.unisa.cinehub.model.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("api/gestionecatalogo")
public class GestioneCatalogoControl {
    @Autowired
    private FilmService filmService;

    public GestioneCatalogoControl(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping("add/film")
    public void addFilm(@RequestBody Film film) {
        filmService.addFilm(film);
    }

    @GetMapping("request/all/film")
    public List<Film> findAllFilm() {
        return filmService.retrieveAll();
    }

    @PostMapping("addGeneri/film")
    public void addGeneriFilm(@RequestBody Collection<Genere> generi, @RequestParam("id") Long id) {
        System.out.println(generi + " " + id + "");
        filmService.addGeneri(generi, id);
    }

}