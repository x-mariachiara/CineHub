package com.unisa.cinehub.data.repository;

import com.unisa.cinehub.data.entity.Recensione;
import com.unisa.cinehub.data.entity.Recensore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecensioneRepository extends JpaRepository<Recensione, Long> {

    //@Query("delete from Recensione r where r.recensore_email = :email")
    void deleteByRecensore(Recensore recensore);
}
