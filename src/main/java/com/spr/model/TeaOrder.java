package com.spr.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class TeaOrder {

    private UUID orderId;
    private String deliveryName;
    private String deliveryStreet;
    private String deliveryCity;
    private String deliveryState;
    private String deliveryZip;
    private String ccNumber;
    private String ccExpiration;
    private String ccCVV;

    List<Tea> order = new ArrayList<>();

    public void addTeaToOrder(Tea tea) {
        order.add(tea);
    }

    public TeaOrder() {
        this.orderId = UUID.randomUUID();
    }
}
