package com.unisa.cinehub.model.utente;

import com.unisa.cinehub.data.entity.Segnalazione;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SegnalazioneRepository extends JpaRepository<Segnalazione, Segnalazione.SegnalazioneID> {
}
