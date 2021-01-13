package com.unisa.cinehub.model.service;

import com.unisa.cinehub.data.entity.Recensore;
import com.unisa.cinehub.data.entity.Utente;
import com.unisa.cinehub.data.entity.VerificationToken;
import com.unisa.cinehub.data.repository.UtenteRepository;
import com.unisa.cinehub.data.repository.VerificationTokenRepository;
import com.unisa.cinehub.model.exception.AlreadyExsistsException;
import com.unisa.cinehub.model.exception.BannedException;
import com.unisa.cinehub.model.exception.UserUnderAgeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    public UtenteService(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }


    public Utente signup(Utente utente) throws UserUnderAgeException, AlreadyExsistsException, BannedException {
        if (LocalDate.now().getYear() - utente.getDataNascita().getYear() < 13) {
            throw new UserUnderAgeException();
        } else if (utenteRepository.existsById(utente.getEmail())) {
            Utente u = utenteRepository.findById(utente.getEmail()).get();
            if(u.getBannato()) {
                throw new BannedException();
            } else {
                throw new AlreadyExsistsException();
            }
        } else {
            utente.setActive(false);
            utente.setBannato(false);
            utente.setPassword(new BCryptPasswordEncoder().encode(utente.getPassword()));
            utenteRepository.save(utente);
            return utenteRepository.findById(utente.getEmail()).orElse(null);
        }
    }

    public Utente findByEmail(String email){
        if (email != null && !email.isBlank()){
            return utenteRepository.findById(email).orElse(null);
        }
        return null;
    }


    public UserDetails findUserDetailsByEmail(String email) throws UsernameNotFoundException {
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNotExpired = true;
        boolean accountNotBanned = true;

        try {
            Utente utente = utenteRepository.findById(email).orElse(null);
            if (utente == null) {
                throw  new UsernameNotFoundException("Nessun utente trovato con email " + email);
            }

            return new User(
                    utente.getEmail(),
                    utente.getPassword().toLowerCase(),
                    utente.getActive(),
                    accountNonExpired,
                    credentialsNotExpired,
                    accountNotBanned,
                    new ArrayList<GrantedAuthority>()
                    );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUtente(Utente utente) {
        utenteRepository.delete(utente);
    }

    public Utente saveRegisteredUser(Utente utente) {
        System.out.println("utente da salvare =" + utente);
        utenteRepository.save(utente);
        return utente;
    }

    public Utente registraNuovoUtente(Utente utente) throws AlreadyExsistsException{
        if (utenteRepository.existsById(utente.getEmail())) {
            throw new AlreadyExsistsException("Esiste un utente con questa email " + utente.getEmail());
        }

        Utente utenteDaRegistrare = new Recensore(utente.getEmail(), utente.getNome(), utente.getCognome(), utente.getDataNascita(), utente.getUsername(), utente.getPassword(), false, false);
        return utenteRepository.save(utenteDaRegistrare);
    }

    public Utente getUtenteByVerificationToken(String verificationToken) {
        Utente utente = verificationTokenRepository.findByToken(verificationToken).getUtente();
        return utente;
    }


    public VerificationToken getVerificationToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

    public void createVerificationToken(Utente utente, String token) {
        VerificationToken myToken = new VerificationToken();
        myToken.setToken(token);
        myToken.setUtente(utente);
        verificationTokenRepository.save(myToken);
    }


    public void bannaRecensore(String email) {
        if(email != null && !email.isBlank()) {
            Utente daBannare = utenteRepository.findById(email).orElse(null);
            if(daBannare != null) {
                daBannare.setBannato(true);
                utenteRepository.save(daBannare);
            }
        }
    }
}
