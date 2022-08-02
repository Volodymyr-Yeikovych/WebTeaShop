package com.spr.dao;

import com.spr.model.TeaOrder;

public interface TeaOrderDao {

    int insertOrder(TeaOrder order);

    int updateOrder(TeaOrder order);


}
