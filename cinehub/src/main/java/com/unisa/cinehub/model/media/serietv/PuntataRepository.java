package com.unisa.cinehub.model.media.serietv;

import com.unisa.cinehub.data.entity.Puntata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PuntataRepository extends JpaRepository<Puntata, Puntata.PuntataID> {

    @Override
    @Query("select p from Puntata p where p.visibile = true")
    List<Puntata> findAll();

    @Query("select p from Puntata p where lower(p.titolo) like lower(concat('%', :title, '%'))")
    List<Puntata> findPuntataByTitle(@Param("title") String title);

    @Override
    @Query("select case when count(p) = 1 then true else false end from Puntata p where p.puntataID = :puntataID and p.visibile = true")
    boolean existsById(@Param("puntataID") Puntata.PuntataID puntataID);
}
