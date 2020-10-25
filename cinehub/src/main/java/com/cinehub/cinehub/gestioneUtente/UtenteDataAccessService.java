package com.cinehub.cinehub.gestioneUtente;
import com.cinehub.cinehub.utils.CRUDInterface;
import com.cinehub.cinehub.utils.ItemNotFoundException;
import com.cinehub.cinehub.utils.NotAuthorizatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UtenteDataAccessService implements CRUDInterface<Utente, String> {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UtenteDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Utente> selectAll() {
        String sql = "SELECT * FROM Utente, Recensione WHERE Utente.email = Recensione.user_id";
        return null;
    }

    @Override
    public Optional<Utente> selectByKey(String key) throws ItemNotFoundException {
        String sql = "SELECT * FROM Utente" +
                     "WHERE Utente.email = ?";
        return Optional.empty();
    }

    @Override
    public void insertItem(Utente toInsert) throws NotAuthorizatedException {

    }

    @Override
    public void updateItem(Utente toUpdate) throws ItemNotFoundException, NotAuthorizatedException {

    }

    @Override
    public Utente deleteByKey(String key) throws ItemNotFoundException, NotAuthorizatedException {
        return null;
    }

    private RowMapper<Utente> mapUtenteFromDB() {
        return (resultSet, i) -> {
           String email = resultSet.getString("email");
           String username = resultSet.getString("username");
           String password = resultSet.getString("password");
           Boolean bannato = resultSet.getBoolean("bannato");
           return new Utente(email, username, password, bannato, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        };
    }
}
