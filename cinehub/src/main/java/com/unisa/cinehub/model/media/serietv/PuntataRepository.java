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

}
