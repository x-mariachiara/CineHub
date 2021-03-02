package com.unisa.cinehub.control;

import com.unisa.cinehub.data.entity.Film;
import com.unisa.cinehub.model.exception.BeanNotExsistException;
import com.unisa.cinehub.model.exception.InvalidBeanException;
import com.unisa.cinehub.model.utente.UtenteService;
import com.unisa.cinehub.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
public class IAControl {
    @Autowired
    private UtenteService service;
    
    public Film consigliaFilm(){
        if(SecurityUtils.isUserLoggedIn()) {
            try {
                return service.filmConsgiliato(SecurityUtils.getLoggedIn());
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (InvalidBeanException e) {
                e.printStackTrace();
                return null;
            } catch (BeanNotExsistException e) {
                e.printStackTrace();
                return null;
            }
        } else return null;
    }


}
