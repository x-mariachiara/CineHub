package com.unisa.cinehub.model.media.film;


import com.unisa.cinehub.data.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Long> {

    @Override
    @Query("select f from Film f where f.visibile = true")
    List<Film> findAll();

    @Query("select f from Film f where lower(f.titolo) like lower(concat('%', :title, '%')) and f.visibile = true")
    List<Film> findFilmByTitle(@Param("title") String title);

    @Query("select case when count(f) > 0 then true else false end from Film f where lower(f.titolo) like lower(:title) and f.annoUscita = :annoUscita")
    boolean existsByTitleAnnoUscita(@Param("title") String title, @Param("annoUscita") Integer annoUscita);

    @Override
    @Query("select case when count(f) = 1 then true else false end from Film  f where f.id = :aLong and f.visibile = true")
    boolean existsById(@Param("aLong") Long aLong);
}
