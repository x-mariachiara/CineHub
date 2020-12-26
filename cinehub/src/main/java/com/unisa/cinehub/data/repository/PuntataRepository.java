package com.unisa.cinehub.data.repository;

import com.unisa.cinehub.data.entity.Puntata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PuntataRepository extends JpaRepository<Puntata, Puntata.PuntataID> {
}
