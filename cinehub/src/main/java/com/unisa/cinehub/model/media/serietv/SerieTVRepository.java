package com.unisa.cinehub.model.media.serietv;

import com.unisa.cinehub.data.entity.SerieTv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SerieTVRepository extends JpaRepository<SerieTv, Long> {

    @Override
    @Query("select f from SerieTv f where f.visibile = true ")
    List<SerieTv> findAll();

    @Query("select s from SerieTv s where lower(s.titolo) like lower(concat('%', :title, '%')) and s.visibile = true")
    List<SerieTv> findSerieTVByTitle(@Param("title") String title);

    @Query("select case when count(s) > 0 then true else false end from SerieTv s where lower(s.titolo) like lower(:title) and s.annoUscita = :annoUscita")
    boolean existsByTitleAnnoUscita(@Param("title") String title, @Param("annoUscita") Integer annoUscita);

    @Override
    @Query("select case when count(s) = 1 then true else false end from SerieTv s where s.id = :aLong and s.visibile = true ")
    boolean existsById(@Param("aLong") Long aLong);
}
