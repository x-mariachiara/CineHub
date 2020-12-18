package com.unisa.cinehub.data.repository;

import com.unisa.cinehub.data.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtenteRepository extends JpaRepository<Utente, String> {
}
