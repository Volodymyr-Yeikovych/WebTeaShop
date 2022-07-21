package com.spr.service;

import com.spr.dao.ClientDao;
import com.spr.dao.TeaDao;
import com.spr.model.Tea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TeaService {

    private final TeaDao teaDao;

    @Autowired
    public TeaService(@Qualifier("postgres") TeaDao teaDao) {
        this.teaDao = teaDao;
    }

    public List<Tea> getAllTea() {
        return teaDao.selectAllTea();
    }

    public Tea getTeaById(UUID id) {
        return teaDao.selectTeaById(id);
    }

    public Tea initTeaOrThrow(Tea tea) {
        Tea dbTea = new Tea(getTeaById(tea.getId()));

        long kgToPurchase = tea.getKg();
        long kgAvailable = dbTea.getKg();
        long newDbKgNum = kgAvailable - kgToPurchase;
        //TODO: make a check: if newDbKgNum is less than 0 then throw exception
        //TODO: implement logic that will tell client that he need to select different kg number
        if (true);
        dbTea.setKg(newDbKgNum);
        teaDao.updateTea(dbTea);

        return new Tea(dbTea.getId(), dbTea.getName(), kgToPurchase, dbTea.getPrice());
    }
}
