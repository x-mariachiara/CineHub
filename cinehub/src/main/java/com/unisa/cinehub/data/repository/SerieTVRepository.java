package com.unisa.cinehub.data.repository;

import com.unisa.cinehub.data.entity.SerieTv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SerieTVRepository extends JpaRepository<SerieTv, Long> {

    @Query("select s from SerieTv s where lower(s.titolo) like lower(concat('%', :title, '%'))")
    List<SerieTv> findSerieTVByTitle(@Param("title") String title);
}
