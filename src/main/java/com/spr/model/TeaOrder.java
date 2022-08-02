package com.spr.model;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
public class TeaOrder {

    private UUID orderId;
    @Pattern(regexp = "([a-zA-Z]+ *)+", message = "Must be a Valid Name")
    @NotBlank(message = "Delivery Name is required.")
    private String deliveryName;
    @Pattern(regexp = "([a-zA-Z0-9/]+ *)+", message = "Must be a Valid Street")
    @NotBlank(message = "Street is required.")
    private String deliveryStreet;
    @Pattern(regexp = "([a-zA-Z]+ *)+", message = "Must be a Valid City")
    @NotBlank(message = "City is required.")
    private String deliveryCity;
    @Pattern(regexp = "([a-zA-Z]+ *)+", message = "Must be a Valid State")
    @NotBlank(message = "State is required.")
    private String deliveryState;
    @Pattern(regexp = "(^\\d{5}$)|(^\\d{9}$)|(^\\d{5}-\\d{4}$)|(^\\d{2}-\\d{3}$)|(^\\d{3}-\\d{2}$)", message = "Must be a Valid Zip")
    @NotBlank(message = "Zip is required.")
    private String deliveryZip;
    @CreditCardNumber(message = "Not a valid credit card number.")
    private String ccNumber;
    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([2-9][2-9])$", message = "Card Expiration Date is invalid. MM/YY")
    private String ccExpiration;
    @Digits(integer = 3, fraction = 0, message = "Invalid CVV.")
    private String ccCVV;
    private double overallPrice;
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

    public void countOverallPrice() {
        double sum = 0;
        for (Tea tea : order) {
            sum += tea.getKg() * tea.getPrice();
        }
        overallPrice = sum;
    }
}
