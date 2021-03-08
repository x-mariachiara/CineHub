package com.unisa.cinehub.data;

import com.unisa.cinehub.data.entity.*;
import com.vaadin.flow.component.treegrid.CollapseEvent;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class UtenteDTO implements Serializable {
    private String email;
    private int hobby;
    private int sesso;
    private String generePreferito;
    private String fasciaEta;
    private Long[] idFilmVisti = {};
    private Long[] idAttoriPreferiti = {};


    public UtenteDTO() {
    }

    public UtenteDTO(Utente utente) {
        this.email = utente.getEmail();
        this.hobby = hobbyToNumber(utente.getHobby());
        this.sesso = sessoToNumber(utente.getSesso());
        this.generePreferito = trovaGenerePreferito(((Recensore) utente).getListaRecensioni());
        this.fasciaEta = trovaFasciaEta(utente.getDataNascita());
        this.idAttoriPreferiti = generateIdAttoriPreferiti(((Recensore) utente));
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getHobby() {
        return hobby;
    }

    public void setHobby(int hobby) {
        this.hobby = hobby;
    }

    public int getSesso() {
        return sesso;
    }

    public void setSesso(int sesso) {
        this.sesso = sesso;
    }

    public String getGenerePreferito() {
        return generePreferito;
    }

    public void setGenerePreferito(String generePreferito) {
        this.generePreferito = generePreferito;
    }

    public String getFasciaEta() {
        return fasciaEta;
    }

    public void setFasciaEta(String fasciaEta) {
        this.fasciaEta = fasciaEta;
    }

    public Long[] getIdFilmVisti() {
        return idFilmVisti;
    }

    public void setIdFilmVisti(Long[] idFilmVisti) {
        this.idFilmVisti = idFilmVisti;
    }

    public Long[] getIdAttoriPreferiti() {
        return idAttoriPreferiti;
    }

    public void setIdAttoriPreferiti(Long[] idAttoriPreferiti) {
        this.idAttoriPreferiti = idAttoriPreferiti;
    }

    private int sessoToNumber(Utente.Sesso sesso) {
        return Arrays.stream(Utente.Sesso.values()).collect(Collectors.toList()).indexOf(sesso);
    }

    private int hobbyToNumber(Utente.Hobby hobby){
        return Arrays.stream(Utente.Hobby.values()).collect(Collectors.toList()).indexOf(hobby);
    }

    private String trovaGenerePreferito(List<Recensione> recensioni){
        if(recensioni.isEmpty()) {
            Random r = new Random();
            int indiceCasuale =  r.nextInt((20 - 0) + 1) + 0;
            String nomeGenere = Genere.NomeGenere.values()[indiceCasuale].toString().toLowerCase(Locale.ROOT);
            nomeGenere = nomeGenere.substring(0, 1).toUpperCase() + nomeGenere.substring(1);
            return nomeGenere;
        } else {
            HashMap<Genere.NomeGenere, Integer> counter = new HashMap<Genere.NomeGenere, Integer>();
            HashSet<Long> filmVisti = new HashSet<>();
            for(Recensione r : recensioni) {
                Film film = r.getFilm();
                int punteggio = r.getPunteggio();
                if(film != null) {
                    filmVisti.add(film.getId());
                    for (Genere g : film.getGeneri()) {
                        counter.computeIfAbsent(g.getNomeGenere(), genere -> punteggio);
                        counter.computeIfPresent(g.getNomeGenere(), (genere, p) -> p += punteggio);
                    }
                }
            }

            Genere.NomeGenere maggiore = counter.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
            String toReturn = maggiore.toString().toLowerCase(Locale.ROOT);
            this.idFilmVisti = filmVisti.toArray(Long[]::new);
            return toReturn.substring(0,1).toUpperCase() + toReturn.substring(1);
        }
    }

    private String trovaFasciaEta(LocalDate dataNascita) {
        LocalDate oggi = LocalDate.now();
        int anni;
        if(oggi.getDayOfMonth() > dataNascita.getDayOfMonth() &&
        oggi.getMonth().getValue() >= dataNascita.getMonth().getValue()) {
            anni = oggi.getYear() - dataNascita.getYear();
        } else {
            anni = oggi.getYear() - dataNascita.getYear() - 1;
        }

        if(anni <= 9) {
            return "0 - 9";
        } else if (anni >= 10 && anni <= 15) {
            return  "10 - 15";
        } else if (anni >= 16 && anni <= 18) {
            return  "16 - 18";
        } else if (anni >= 19 && anni <= 25) {
            return "19 - 25";
        } else if (anni >= 26 && anni <= 35) {
            return "26 - 35";
        } else if (anni >= 36 && anni <= 50) {
            return "36 - 50";
        } else if (anni > 50) {
            return "50+";
        } else {
            return "50+";
        }
    }

    private Long[] generateIdAttoriPreferiti(Recensore recensore) {
        ArrayList<Recensione> recensioni = new ArrayList<>(recensore.getListaRecensioni());
        HashMap<Long, Integer> counter = new HashMap<>();
        for(Recensione recensione : recensioni) {
            Film film = recensione.getFilm();
            if (film != null) {
                for (Ruolo ruolo : film.getRuoli()) {
                    counter.computeIfAbsent(ruolo.getCastId(), r -> recensione.getPunteggio());
                    counter.computeIfPresent(ruolo.getCastId(), (r, p) -> p += recensione.getPunteggio());
                }
            }
        }
        return sortByComparator(counter).stream().toArray(Long[]::new);
    }

    private List<Long> sortByComparator(HashMap<Long, Integer> unsorted){
        List<Long> ordinati = new ArrayList<>();
        Integer max = 1;
        for(Long key : unsorted.keySet()) {
            if(unsorted.get(key) > max) {
                max = unsorted.get(key);
                ordinati.add(0, key);
            }
            ordinati.add(key);
        }
        return  ordinati;
    }
}
