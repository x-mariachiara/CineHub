package com.unisa.cinehub.data.repository;

import com.unisa.cinehub.data.entity.SerieTv;
import com.unisa.cinehub.data.entity.Stagione;
import com.unisa.cinehub.data.entity.Stagione.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StagioneRepository extends JpaRepository<Stagione, StagioneID> {
}
