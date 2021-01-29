package com.unisa.cinehub.model.utente;

import com.unisa.cinehub.data.entity.MiPiace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MiPiaceRepository extends JpaRepository<MiPiace, MiPiace.MiPiaceID> {

    @Query("select m from MiPiace m where m.recensioneId = :recensioneId and m.tipo = true")
    List<MiPiace> getNumMiPiace(@Param("recensioneId") Long recensioneId);

    @Query("select m from MiPiace m where m.recensioneId = :recensioneId and m.tipo = false")
    List<MiPiace> getNumNonMiPiace(@Param("recensioneId") Long recensioneId);
}
