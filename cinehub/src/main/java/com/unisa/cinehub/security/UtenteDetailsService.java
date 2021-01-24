package com.unisa.cinehub.security;

import com.unisa.cinehub.data.entity.Utente;
import com.unisa.cinehub.model.utente.RecensoreRepository;
import com.unisa.cinehub.model.utente.UtenteRepository;
import com.unisa.cinehub.model.exception.BeanNotExsistException;
import com.unisa.cinehub.model.exception.InvalidBeanException;
import com.unisa.cinehub.model.utente.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpSession;

@Service
public class UtenteDetailsService implements UserDetailsService {
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private RecensoreRepository recensoreRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utente utente = null;
        try {
            utente = utenteService.findByEmail(email);
        } catch (InvalidBeanException | BeanNotExsistException e) {
            throw new UsernameNotFoundException(email);
        }
        //Utente utente = recensoreRepository.findById(email).get();
        System.out.println(utente);
        if(utente == null){
            throw new UsernameNotFoundException(email);
        }
        if(utente.getBannato() || !utente.getActive()) {
            throw new UsernameNotFoundException(email);
        }


        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession httpSession = servletRequestAttributes.getRequest().getSession(true);
        httpSession.setAttribute("utente", utente);

        return User.withUsername(utente.getEmail()).password(utente.getPassword()).roles("test").build();
    }
}
