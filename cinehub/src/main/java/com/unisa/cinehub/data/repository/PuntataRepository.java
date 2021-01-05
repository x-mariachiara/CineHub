package com.unisa.cinehub.data.repository;

import com.unisa.cinehub.data.entity.Puntata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PuntataRepository extends JpaRepository<Puntata, Puntata.PuntataID> {

    @Query("select p from Puntata p where lower(p.titolo) like lower(concat('%', :title, '%'))")
    List<Puntata> findPuntataByTitle(@Param("title") String title);

}
