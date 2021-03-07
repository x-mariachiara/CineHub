package com.unisa.cinehub.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unisa.cinehub.data.entity.Recensore;

import java.io.Serializable;
import java.util.*;

public class Dataset implements Serializable {

    private Object[][] data;
    private String target;
    private String[] feature_names = {"email", "hobby", "sesso", "generePreferito", "fasciaEta", "idFilmVisti", "idAttoriPreferiti"};

    public Dataset(List<UtenteDTO> recensoriDTO) throws JsonProcessingException {
        this.data = creazioneMatrice(recensoriDTO);
    }

    private Object[][] creazioneMatrice(List<UtenteDTO> recensoriDTO) throws JsonProcessingException {
        Object[][] matrice = new Object[recensoriDTO.size()][feature_names.length];
        ObjectMapper mapper = new ObjectMapper();

        for(int i = 0, j=0; i < recensoriDTO.size(); i++) {
            UtenteDTO r = recensoriDTO.get(i);
            switch (j){
                case 0:
                    matrice[i][j] = r.getEmail();
                    j++;
                case 1:
                    matrice[i][j] = r.getHobby();
                    j++;
                case 2:
                    matrice[i][j] = r.getSesso();
                    j++;
                case 3:
                    matrice[i][j] = r.getGenerePreferito();
                    j++;
                case 4:
                    matrice[i][j] = r.getFasciaEta();
                    j++;
                case 5:
                    matrice[i][j] = r.getIdFilmVisti();
                    j++;
                case 6:
                    matrice[i][j] = r.getIdAttoriPreferiti();
                    j++;
                default:
                    break;
            }
            j=0;
        }

        return matrice;
    }

    public Object[][] getData() {
        return data;
    }

    public void setData(Object[][] data) {
        this.data = data;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String[] getFeature_names() {
        return feature_names;
    }

    public void setFeature_names(String[] feature_names) {
        this.feature_names = feature_names;
    }
}
