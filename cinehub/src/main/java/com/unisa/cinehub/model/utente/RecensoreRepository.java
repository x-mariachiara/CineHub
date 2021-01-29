package com.unisa.cinehub.model.utente;

import com.unisa.cinehub.data.entity.Recensore;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecensoreRepository extends JpaRepository<Recensore, String> {

    @Query("select r from Recensore r where r.isBannato = false")
    List<Recensore> findNotBanned();
}
