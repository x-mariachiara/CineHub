package com.unisa.cinehub.data.repository;

import com.unisa.cinehub.data.entity.Segnalazione;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SegnalazioneRepository extends JpaRepository<Segnalazione, Segnalazione.SegnalazioneID> {
}
