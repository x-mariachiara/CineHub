package com.unisa.cinehub.security;

import com.unisa.cinehub.data.entity.Utente;
import com.unisa.cinehub.data.repository.UtenteRepository;
import com.unisa.cinehub.model.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

public class UtenteDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    @Autowired
    private UtenteService utenteService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utente utente = utenteService.findByEmail(email);
        if(utente == null){
            throw new UsernameNotFoundException(email);
        }

        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession httpSession = servletRequestAttributes.getRequest().getSession(true);
        httpSession.setAttribute("utente", utente);

        return User.withUsername(utente.getEmail()).password(utente.getPassword()).roles("test").build();
    }
}
