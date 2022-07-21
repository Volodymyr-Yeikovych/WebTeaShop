package com.spr.dao;

import com.spr.error.InternalFatalError;
import com.spr.exceptions.InvalidClientIdException;
import com.spr.exceptions.NoSuchEmailException;
import com.spr.exceptions.SQLExceptionWrapper;
import com.spr.model.Client;
import com.spr.model.Tea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository("postgres")
public class ClientDataAccessService implements ClientDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ClientDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertClient(UUID id, Client client) {
        return jdbcTemplate.update("INSERT INTO clients (id, name, password, email, is_admin) VALUES (?, ?, ?, ?, ?);",
                id, client.getName(), client.getPassword(), client.getEmail(), client.isAdmin());
    }

    @Override
    public int updateClient(UUID id, Client client) {
        validateIdAndReturnClient(id);
        return jdbcTemplate.update("UPDATE clients SET name = ?, password = ?, email = ?, is_admin = ? WHERE id = ?;",
                client.getName(), client.getPassword(), client.getEmail(), client.isAdmin(), id);
    }

    @Override
    public int deleteClient(UUID id) {
        validateIdAndReturnClient(id);
        return jdbcTemplate.update("DELETE FROM clients WHERE id = ?;", id);
    }

    @Override
    public List<Client> selectAllClients() {
        return jdbcTemplate.query("SELECT * FROM clients;", this::clientMapper);
    }

    @Override
    public Client selectClientByID(UUID id) {
        return validateIdAndReturnClient(id);
    }

    @Override
    public Client selectClientByEmail(String email) {
        List<Client> client = jdbcTemplate.query("SELECT * FROM clients WHERE email = ?;", this::clientMapper, email);
        if (client.isEmpty()) throw new NoSuchEmailException("Email {" + email + "} does not exist.");
        return client.stream().findFirst().get();
    }



    private Client validateIdAndReturnClient(UUID id) {
        List<Client> clients = jdbcTemplate.query("SELECT * FROM clients WHERE id = ?;", this::clientMapper, id);
        if (clients.isEmpty()) throw new InvalidClientIdException("Client with given id{" + id + "} does not exist.");
        return clients.stream().findFirst().get();
    }

    private Client clientMapper(ResultSet rowSet, int e) {
        try {
            return new Client(UUID.fromString(rowSet.getString("id")), rowSet.getString("name"),
                    rowSet.getString("password"), rowSet.getString("email"),
                    Boolean.getBoolean(rowSet.getString("is_admin")));
        } catch (SQLException ex) {
            throw new SQLExceptionWrapper(ex.getMessage());
        }
    }
}
