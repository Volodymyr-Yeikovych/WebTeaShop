package com.spr.dao;

import com.spr.model.TeaOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("postgresTeaOrder")
public class TeaOrderDataAccessService implements TeaOrderDao{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TeaOrderDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public int insertOrder(TeaOrder order) {
        return jdbcTemplate.update("INSERT INTO public.tea_order(id, delivery_name, delivery_street, delivery_city, " +
                "delivery_state, delivery_zip, cc_number, cc_expiration, cc_cvv, overall_price) VALUES " +
                        "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);",
                order.getOrderId(), order.getDeliveryName(), order.getDeliveryStreet(), order.getDeliveryCity(),
                order.getDeliveryState(), order.getDeliveryZip(), order.getCcNumber(), order.getCcExpiration(),
                order.getCcCVV(), order.getOverallPrice());
    }

    @Override
    public int updateOrder(TeaOrder order) {
        return jdbcTemplate.update("UPDATE public.tea_order SET id=?, delivery_name=?, delivery_street=?, " +
                "delivery_city=?, delivery_state=?, delivery_zip=?, cc_number=?, cc_expiration=?, cc_cvv=?, overall_price=?" +
                "WHERE id = ?;",
                order.getOrderId(), order.getDeliveryName(), order.getDeliveryStreet(), order.getDeliveryCity(),
                order.getDeliveryState(), order.getDeliveryZip(), order.getCcNumber(), order.getCcExpiration(),
                order.getCcCVV(), order.getOverallPrice(), order.getOrderId());
    }
}
