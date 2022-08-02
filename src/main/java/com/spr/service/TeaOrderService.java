package com.spr.service;

import com.spr.dao.TeaOrderDao;
import com.spr.model.TeaOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeaOrderService {

    public final TeaOrderDao teaOrderDao;

    @Autowired
    public TeaOrderService(TeaOrderDao teaOrderDao) {
        this.teaOrderDao = teaOrderDao;
    }

    public void save (TeaOrder order) {
        order.countOverallPrice();
        teaOrderDao.insertOrder(order);
    }

    public void update (TeaOrder order) {
        teaOrderDao.updateOrder(order);
    }
}
