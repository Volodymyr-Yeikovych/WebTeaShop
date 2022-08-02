package com.spr.service;

import com.spr.dao.ClientDao;
import com.spr.dao.TeaDao;
import com.spr.error.InternalFatalError;
import com.spr.exceptions.NotEnoughTeaInStockException;
import com.spr.model.Tea;
import com.spr.model.TeaOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TeaService {

    private final TeaDao teaDao;

    @Autowired
    public TeaService(@Qualifier("postgresTea") TeaDao teaDao) {
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

        if (newDbKgNum < 0)
            throw new NotEnoughTeaInStockException("Client ordered too many tea: {" + kgToPurchase + "} " +
                    "when in stock: {" + kgAvailable + "}");

        dbTea.setKg(newDbKgNum);
        teaDao.updateTea(dbTea);

        Tea newTea = new Tea(dbTea.getId(), dbTea.getName(), kgToPurchase, dbTea.getPrice());
        if (newDbKgNum < 50) {
            orderMoreTea(newTea);
        }

        return newTea;
    }

    private void orderMoreTea(Tea tea) {
        Tea newTea = new Tea(tea);
        newTea.setKg(tea.getKg() + 200);
        teaDao.updateTea(newTea);
    }

    public void normalize(Tea tea, TeaOrder order) {
        if (order.containsTea(tea)) {
            Optional<Tea> optReadyTea = order.getTeaById(tea.getId());
            if (optReadyTea.isEmpty()) throw new InternalFatalError("Unexpected empty optional.");
            Tea readyTea = new Tea(optReadyTea.get());
            long newKgNum = readyTea.getKg() + tea.getKg();
            readyTea.setKg(newKgNum);
            order.updateTea(readyTea);
            tea.setNormalized(true);
        }
    }
}
