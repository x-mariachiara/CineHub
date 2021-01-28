package com.unisa.cinehub.model.media;

import com.unisa.cinehub.data.entity.Ruolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.List;

public interface RuoloRepository extends JpaRepository<Ruolo, Ruolo.RuoloID> {

    List<Ruolo> findAllByMediaId(Long mediaId);

    @Transactional
    @Modifying
    void deleteAllByMediaId(Long id);
}
