package com.spr.service;

import com.spr.dao.ClientDao;
import com.spr.exceptions.EmailAlreadyExistsException;
import com.spr.exceptions.InvalidClientIdException;
import com.spr.exceptions.InvalidPasswordException;
import com.spr.model.Client;
import com.spr.model.Tea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final ClientDao clientDao;

    @Autowired
    public ClientService(@Qualifier("postgres") ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public boolean clientExists(Client client) {
        try {
            clientDao.selectClientByID(client.getId());
            return true;
        } catch (InvalidClientIdException e) {
            return false;
        }
    }

    public void addClient(Client client) {
        getAllClients().stream().map(Client::getEmail).forEach(e -> {
            if (e.equals(client.getEmail()))
                throw new EmailAlreadyExistsException("Email {" + client.getEmail() + "} already exists.");
        });
        clientDao.insertClient(client);
    }

    public void updateClient(UUID id, Client client) {

    }

    public void deleteClient(UUID id) {

    }

    public List<Client> getAllClients() {
        return clientDao.selectAllClients();
    }

    public Client getClientById(UUID id) {
        return clientDao.selectClientByID(id);
    }

    public Client getClientByEmail(String email) {
        return clientDao.selectClientByEmail(email);
    }

    public Client loginClientOrThrow(String email, String password) {
        Client client = new Client(getClientByEmail(email));
        if (!client.getPassword().equals(password))
            throw new InvalidPasswordException("Invalid password for given client.");
        return client;
    }

}
