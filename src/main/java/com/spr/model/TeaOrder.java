package com.spr.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    private List<Tea> order = new ArrayList<>();

    public void addTeaToOrder(Tea tea) {
        order.add(tea);
    }

    public TeaOrder() {
        this.orderId = UUID.randomUUID();
    }

    public boolean containsTea(Tea tea) {
        for (Tea t : getOrder()) {
            if (t.getId().equals(tea.getId())) return true;
        }
        return false;
    }

    public Optional<Tea> getTeaById(UUID id) {
        for (Tea t : getOrder()) {
            if (t.getId().equals(id)) return Optional.of(t);
        }
        return Optional.empty();
    }

    public void updateTea(Tea tea) {
        int i = 0;
        for (Tea t : getOrder()) {
            if (t.getId().equals(tea.getId())) {
                getOrder().set(i, tea);
            }
            i++;
        }
    }
}
