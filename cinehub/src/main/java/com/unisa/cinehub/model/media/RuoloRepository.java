package com.unisa.cinehub.model.media;

import com.unisa.cinehub.data.entity.Ruolo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuoloRepository extends JpaRepository<Ruolo, Ruolo.RuoloID> {
}