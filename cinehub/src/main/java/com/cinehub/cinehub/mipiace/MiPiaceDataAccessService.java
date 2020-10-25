package com.cinehub.cinehub.mipiace;

import com.cinehub.cinehub.gestioneUtente.Utente;
import com.cinehub.cinehub.recensione.Recensione;
import com.cinehub.cinehub.utils.CRUDInterface;
import com.cinehub.cinehub.utils.ItemNotFoundException;
import com.cinehub.cinehub.utils.NotAuthorizatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class MiPiaceDataAccessService implements CRUDInterface<MiPiace, Object> {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public MiPiaceDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<MiPiace> selectAll() {
        return null;
    }

    @Override
    public Optional<MiPiace> selectByKey(Object key) throws ItemNotFoundException {
        return Optional.empty();
    }

    public List<MiPiace> selectByUtente(Utente utente) {
        String sql = "SELECT * FROM Vote WHERE Vote.user_id = ?";
        return jdbcTemplate.query(sql, new Object[]{utente.getEmail()}, mapMiPiaceFromDB());
    }

    public List<MiPiace> selectByRecensione(Recensione recensione) {
        String sql = "SELECT * FROM Vote WHERE Vote.recensione_id = ?";
        return jdbcTemplate.query(sql, new Object[]{recensione.getId()}, mapMiPiaceFromDB());
    }

    @Override
    public void insertItem(MiPiace toInsert) throws NotAuthorizatedException {
        String sql = "INSERT INTO Vote VALUES (?, ?, ?, ?)";
    }

    public void insertItem(MiPiace toInsert, Utente utente, Recensione recensione) throws NotAuthorizatedException {
        String sql = "INSERT INTO Vote VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, new Object[]{utente.getEmail(), recensione.getId(), toInsert.getCreatedAt(), toInsert.isTipo()});
    }

    @Override
    public void updateItem(MiPiace toUpdate) throws ItemNotFoundException, NotAuthorizatedException {

    }

    @Override
    public MiPiace deleteByKey(Object key) throws ItemNotFoundException, NotAuthorizatedException {
        return null;
    }

    public MiPiace deleteByKey(Utente utente, Recensione recensione) throws ItemNotFoundException, NotAuthorizatedException {
        String sql1 = "DELETE FROM Vote WHERE Vote.user_id = ? AND Vote.recensione_id = ?";
        String sql2 = "SELECT * FROM Vote WHERE Vote.user_id = ? AND Vote.recensione_id = ?";
        try {
            MiPiace toReturn = jdbcTemplate.queryForObject(sql2, new Object[]{utente.getEmail(), recensione.getId()}, mapMiPiaceFromDB());
            jdbcTemplate.update(sql1, new Object[]{utente.getEmail(), recensione.getId()});
            return toReturn;
        } catch (EmptyResultDataAccessException e) {
            throw new NotAuthorizatedException();
        }
    }

    private RowMapper<MiPiace> mapMiPiaceFromDB() {
        return (resultSet, i) -> {
            Boolean tipo = resultSet.getBoolean("positive");
            Timestamp createdAt = resultSet.getTimestamp("created_at");
            return new MiPiace(tipo, createdAt);
        };
    }
}
