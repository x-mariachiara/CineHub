package com.unisa.cinehub.data.repository;

import com.unisa.cinehub.data.entity.Genere;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenereRepository extends JpaRepository<Genere, Genere.NomeGenere> {
}
