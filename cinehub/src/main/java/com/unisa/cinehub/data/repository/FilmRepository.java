package com.unisa.cinehub.data.repository;


import com.unisa.cinehub.data.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Long> {

    @Query("select f from Film f where lower(f.titolo) like lower(concat('%', :title, '%'))")
    List<Film> findFilmByTitle(@Param("title") String title);

}
