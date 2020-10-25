package com.cinehub.cinehub.recensione;

import com.cinehub.cinehub.gestioneMedia.Film;
import com.cinehub.cinehub.gestioneUtente.Utente;
import com.cinehub.cinehub.puntata.Puntata;
import com.cinehub.cinehub.utils.CRUDInterface;
import com.cinehub.cinehub.utils.ItemNotFoundException;
import com.cinehub.cinehub.utils.NotAuthorizatedException;
import com.cinehub.cinehub.utils.Recensible;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class RecensioneDataAccessService implements CRUDInterface<Recensione, UUID> {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public RecensioneDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Recensione> selectAll() {
        String sql = "SELECT * FROM Recensione";
        return jdbcTemplate.query(sql, mapRecensioneFromDB());
    }

    public List<Recensione> selectByParent(Recensione recensione) {
        String sql = "SELECT * FROM Recensione WHERE Recensione.parent_id = ?";
        return jdbcTemplate.query(sql, new Object[]{recensione.getId()}, mapRecensioneFromDB());
    }

    public List<Recensione> selectByRecensible(Recensible recensible) {
        String sql = "SELECT * FROM Recensione WHERE Recensione.media_id = ?";
        return jdbcTemplate.query(sql, new Object[]{recensible.getId()}, mapRecensioneFromDB());
    }

    public List<Recensione> selectByUtente(Utente utente) {
        return null;
    }

    @Override
    public Optional<Recensione> selectByKey(UUID key) throws ItemNotFoundException {
        return Optional.empty();
    }

    @Override
    public void insertItem(Recensione toInsert) throws NotAuthorizatedException {
        // Controllare se già presente una recensione "non risposta" già presente.
        // Se non presente inserire la recensione valutativa altrimenti lancia NotAuthorizedException
        String sql = "INSERT INTO Recensione VALUES (?, ?, ?, ?, ?)";

    }

    public void insertItem(Recensione toInsert, Utente utente, Recensible recensible) throws NotAuthorizatedException {
        String sql1 = "SELECT * ";
        String sql = "INSERT INTO Recensione VALUES (?, ?, ?, ?, ?)";

    }

    @Override
    public void updateItem(Recensione toUpdate) throws ItemNotFoundException, NotAuthorizatedException {

    }

    @Override
    public Recensione deleteByKey(UUID key) throws ItemNotFoundException, NotAuthorizatedException {
        return null;
    }

    private RowMapper<Recensione> mapRecensioneFromDB() {
        return (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String contenuto = resultSet.getString("contenuto");
            Timestamp createdAt = resultSet.getTimestamp("created_at");
            Double punteggio = resultSet.getDouble("punteggio");
            return new Recensione(id, contenuto, new ArrayList<>(), createdAt, punteggio, new ArrayList<>(), new ArrayList<>());
        };
    }
}
