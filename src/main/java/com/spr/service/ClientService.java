package com.spr.service;

import com.spr.dao.ClientDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final ClientDao clientDao;

    @Autowired
    public ClientService(@Qualifier("postgres") ClientDao clientDao) {
        this.clientDao = clientDao;
    }


}
