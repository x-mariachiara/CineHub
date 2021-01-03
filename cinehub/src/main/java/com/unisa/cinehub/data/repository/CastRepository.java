package com.unisa.cinehub.data.repository;

import com.unisa.cinehub.data.entity.Cast;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CastRepository extends JpaRepository<Cast, Long> {
}
