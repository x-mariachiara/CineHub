package com.unisa.cinehub.data.repository;

import com.unisa.cinehub.data.entity.Recensione;
import com.unisa.cinehub.data.entity.Recensore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface RecensioneRepository extends JpaRepository<Recensione, Long> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Recensione r set r.contenuto = '[contenuto viola le policy]' where r.recensore = :recensore")
    @Transactional
    int bannaAllByRecensore(@Param("recensore")Recensore recensore);

    //void deleteRecensionesByRecensore(Recensore recensore);
}
