package com.spr.dao;

import com.spr.model.Client;
import com.spr.model.Tea;

import java.util.List;
import java.util.UUID;

public interface ClientDao {

    int insertClient(UUID id, Client client);

    default int insertClient(Client client) {
        return insertClient(UUID.randomUUID(), client);
    }

    int updateClient(UUID id, Client client);

    int deleteClient(UUID id);

    List<Client> selectAllClients();
    //make select client by id and by email return optional<client>
    Client selectClientByID(UUID id);

    Client selectClientByEmail(String email);

}
