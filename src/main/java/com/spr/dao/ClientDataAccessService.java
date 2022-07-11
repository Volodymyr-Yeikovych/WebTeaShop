package com.spr.dao;

import com.spr.exceptions.InvalidClientIdException;
import com.spr.exceptions.SQLExceptionWrapper;
import com.spr.model.Client;
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
        return jdbcTemplate.update("INSERT INTO clients (id, name, password, email) VALUES (?, ?, ?, ?);",
                id, client.getName(), client.getPassword(), client.getEmail());
    }

    @Override
    public int updateClient(UUID id, Client client) {
        validateIdAndReturnClient(id);
        return jdbcTemplate.update("UPDATE clients SET name = ?, password = ?, email = ?, WHERE id = ?;",
                client.getName(), client.getPassword(), client.getEmail(), id);
    }

    @Override
    public int deleteClient(UUID id) {
        validateIdAndReturnClient(id);
        return jdbcTemplate.update("DELETE FROM clients WHERE id = ?;", id);
    }

    @Override
    public List<Client> getAllClients() {
        return jdbcTemplate.query("SELECT * FROM clients;", this::clientMapper);
    }

    @Override
    public Client getClientById(UUID id) {
        return validateIdAndReturnClient(id);
    }

    private Client validateIdAndReturnClient(UUID id) {
        List<Client> clients = jdbcTemplate.query("SELECT * FROM clients WHERE id = ?;", this::clientMapper, id);
        if (clients.isEmpty()) throw new InvalidClientIdException("Client with given id{"+ id +"} does not exist.");
        return clients.stream().findFirst().get();
    }

    private Client clientMapper(ResultSet rowSet, int e) {
        try {
            return new Client(UUID.fromString(rowSet.getString("id")), rowSet.getString("name"),
                    rowSet.getString("password"), rowSet.getString("email"));
        } catch (SQLException ex) {
            throw new SQLExceptionWrapper(ex.getMessage());
        }
    }
}
