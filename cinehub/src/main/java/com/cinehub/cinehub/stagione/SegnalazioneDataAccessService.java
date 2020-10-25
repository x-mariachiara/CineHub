package com.cinehub.cinehub.stagione;

import com.cinehub.cinehub.gestioneUtente.Utente;
import com.cinehub.cinehub.recensione.Recensione;
import com.cinehub.cinehub.segnalazione.Segnalazione;
import com.cinehub.cinehub.utils.CRUDInterface;
import com.cinehub.cinehub.utils.ItemNotFoundException;
import com.cinehub.cinehub.utils.NotAuthorizatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class SegnalazioneDataAccessService implements CRUDInterface<Segnalazione, Object> {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public SegnalazioneDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Segnalazione> selectAll() {
        String sql = "SELECT * FROM Moderato";
        return jdbcTemplate.query(sql, mapSegnalazioneFromDB());
    }

    public List<Segnalazione> selectByUtente(Utente utente) {
        String sql = "SELECT * FROM Moderato WHERE Moderato.moderatore_id = ?";
        return jdbcTemplate.query(sql, new Object[]{utente.getEmail()}, mapSegnalazioneFromDB());
    }

    public List<Segnalazione> selectByRecensione(Recensione recensione) {
        String sql = "SELECT * FROM Moderato WHERE Moderato.recensione_id = ?";
        return jdbcTemplate.query(sql, new Object[]{recensione.getId()}, mapSegnalazioneFromDB());
    }


    @Override
    public Optional<Segnalazione> selectByKey(Object key) throws ItemNotFoundException {
        return Optional.empty();
    }

    @Override
    public void insertItem(Segnalazione toInsert) throws NotAuthorizatedException {

    }

    public void insertItem(Segnalazione toInsert, Utente utente, Recensione recensione) throws NotAuthorizatedException {
        String sql1 = "SELECT * FROM Moderato WHERE Moderato.moderatore_id = ? AND recensione.recensione_id = ?";
        try {
            jdbcTemplate.queryForObject(sql1, new Object[]{utente.getEmail(), recensione.getId()}, mapSegnalazioneFromDB());
            throw new NotAuthorizatedException();
        } catch (EmptyResultDataAccessException e) {
            String sql2 = "INSERT INTO Moderato VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(sql2, new Object[]{utente.getEmail(), recensione.getId(), toInsert.getTipo(), toInsert.getCreatedAt()});
        }
    }

    @Override
    public void updateItem(Segnalazione toUpdate) throws ItemNotFoundException, NotAuthorizatedException {

    }

    @Override
    public Segnalazione deleteByKey(Object key) throws ItemNotFoundException, NotAuthorizatedException {
        return null;
    }

    private RowMapper<Segnalazione> mapSegnalazioneFromDB() {
        return (resultSet, i) -> {
            String tipo = resultSet.getString("tipo");
            Timestamp createdAt = resultSet.getTimestamp("created_at");
            return new Segnalazione(tipo, createdAt);
        };
    }
}
