package com.unisa.cinehub.model.service;

import com.unisa.cinehub.data.entity.Recensore;
import com.unisa.cinehub.data.entity.Utente;
import com.unisa.cinehub.data.entity.VerificationToken;
import com.unisa.cinehub.data.repository.RecensioneRepository;
import com.unisa.cinehub.data.repository.UtenteRepository;
import com.unisa.cinehub.data.repository.VerificationTokenRepository;
import com.unisa.cinehub.model.exception.*;
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

    @Autowired
    private RecensioneRepository recensioneRepository;

    public UtenteService(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }


    public Utente signup(Utente utente) throws UserUnderAgeException, AlreadyExsistsException, BannedException {
        if (LocalDate.now().getYear() - utente.getDataNascita().getYear() < 13) {
            throw new UserUnderAgeException();
        } else if (utenteRepository.existsById(utente.getEmail())) {
            Utente u = utenteRepository.findById(utente.getEmail()).get();
            if(u.getBannato()) {
                throw new BannedException("L'utente  con email: " + utente.getEmail() + "è bannato");
            } else {
                throw new AlreadyExsistsException("L'utente con email: " + utente.getEmail() + " esiste già");
            }
        } else {
            utente.setActive(false);
            utente.setBannato(false);
            utente.setPassword(new BCryptPasswordEncoder().encode(utente.getPassword()));
            return utenteRepository.save(utente);
        }
    }

    public Utente findByEmail(String email) throws InvalidBeanException, BeanNotExsistException {
        if (email != null && !email.isBlank()){
            if(utenteRepository.existsById(email)) {
                return utenteRepository.findById(email).get();
            }
            else throw new BeanNotExsistException("L'utente con email: " + email + " non esiste");
        } else {
            throw new InvalidBeanException(email + " non valida");
        }

    }


    public UserDetails findUserDetailsByEmail(String email) throws UsernameNotFoundException {
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNotExpired = true;
        boolean accountNotBanned = true;


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
    }

    public void deleteUtente(Utente utente) throws BeanNotExsistException {
        if(utenteRepository.existsById(utente.getEmail())) {
            utenteRepository.delete(utente);
        }
        else throw new BeanNotExsistException();
    }

    public Utente saveRegisteredUser(Utente utente) throws InvalidBeanException {
        if(utenteRepository.existsById(utente.getEmail()) && utente.getActive()) {
            return utenteRepository.save(utente);
        } else {
            throw new InvalidBeanException(utente + " non valido");
        }

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


    public Utente bannaRecensore(String email) throws BeanNotExsistException, InvalidBeanException {
        if(email != null && !email.isBlank()) {
            Recensore daBannare = (Recensore) utenteRepository.findById(email).orElse(null);
            if(daBannare != null) {
                daBannare.setBannato(true);
                daBannare.setUsername(daBannare.getUsername() + " [Utente Bannato]");
                System.out.println("campi modificati: " + recensioneRepository.bannaAllByRecensore(daBannare));
                daBannare =  utenteRepository.save(daBannare);
                return daBannare;
            } else {
                throw new BeanNotExsistException("L'utente con email: " + email + "non esiste");
            }
        } else {
            throw  new InvalidBeanException(email + " non valida");
        }
    }
}
