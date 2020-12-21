package com.unisa.cinehub.data.repository;


import com.unisa.cinehub.data.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Film, Long> {

}
