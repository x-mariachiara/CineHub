package com.unisa.cinehub.model.utente;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unisa.cinehub.data.Dataset;
import com.unisa.cinehub.data.UtenteDTO;
import com.unisa.cinehub.data.entity.*;
import com.unisa.cinehub.model.media.film.FilmRepository;
import com.unisa.cinehub.model.recensione.RecensioneRepository;
import com.unisa.cinehub.model.exception.*;
import com.unisa.cinehub.model.recensione.RecensioneService;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.atmosphere.cpr.AtmosphereResourceEventListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.*;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private RecensioneRepository recensioneRepository;

    @Autowired
    private RecensoreRepository recensoreRepository;

    @Autowired
    private FilmRepository filmRepository;

    public UtenteService(UtenteRepository utenteRepository, VerificationTokenRepository verificationTokenRepository, RecensioneRepository recensioneRepository, RecensoreRepository recensoreRepository, FilmRepository filmRepository) {
        this.utenteRepository = utenteRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.recensioneRepository = recensioneRepository;
        this.recensoreRepository = recensoreRepository;
        this.filmRepository = filmRepository;
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

    public void deleteUtente(Utente utente, VerificationToken token) throws BeanNotExsistException {
        if(utenteRepository.existsById(utente.getEmail())) {
            verificationTokenRepository.delete(token);
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
                daBannare.setUsername(daBannare.getUsername() + "[Utente-Bannato]");
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

    public Film filmConsgiliato(Utente utente) throws IOException {
        Film consigliato = new Film();
        if(utente instanceof Recensore) {
            Recensore recensore = (Recensore) utente;
            ObjectMapper mapper = new ObjectMapper();

            URL url = new URL ("http://127.0.0.1:5000/consigliato");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            UtenteDTO dto = new UtenteDTO(recensore);
            String jsonStr = mapper.writeValueAsString(dto);
            System.out.println(jsonStr);

            try(OutputStream outputStream = con.getOutputStream()) {
                byte[] input = jsonStr.getBytes(StandardCharsets.UTF_8);
                outputStream.write(input, 0, input.length);
            }

            //Come risposta potremmo inviare semplicemente l'id del Film da consigliare
            StringBuilder response = new StringBuilder();
            try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                String responseLine = null;

                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
            }

            Long idFilmConsigliato = Long.valueOf(response.toString());
            consigliato = filmRepository.getOne(idFilmConsigliato);

        }
        return consigliato;
    }

    public String exportData() throws IOException {
        List<Recensore> recensori = recensoreRepository.findAll();
        List<UtenteDTO> utentiDTO = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        for(Recensore r : recensori) utentiDTO.add(new UtenteDTO(r));

        Dataset dataset = new Dataset(utentiDTO);

        URL url = new URL ("http://127.0.0.1:5000/import");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        String jsonStr = mapper.writeValueAsString(dataset);



//        try(OutputStream outputStream = con.getOutputStream()) {
//            byte[] input = jsonStr.getBytes(StandardCharsets.UTF_8);
//            outputStream.write(input, 0, input.length);
//        }

        return jsonStr;
    }

}
