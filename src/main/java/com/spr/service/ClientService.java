package com.spr.service;

import com.spr.dao.ClientDao;
import com.spr.exceptions.EmailAlreadyExistsException;
import com.spr.exceptions.InvalidClientIdException;
import com.spr.exceptions.InvalidPasswordException;
import com.spr.model.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ClientService {

    private static final BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();
    private final ClientDao clientDao;
    private boolean isLoggedIn;

    @Autowired
    public ClientService(@Qualifier("postgresClient") ClientDao clientDao) {
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
        if (!bCryptEncoder.matches(password, client.getPassword())) {
            log.info("Client was not authorised. Given email {}, password {}.", email, password);
            throw new InvalidPasswordException("Invalid password for given client.");
        }
        return client;
    }

    public String encryptPwAndReturn(String password) {
        return bCryptEncoder.encode(password);
    }

    public boolean clientIsLoggedIn() {
        return isLoggedIn;
    }

    public void endSession() {
        isLoggedIn = false;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }
}
