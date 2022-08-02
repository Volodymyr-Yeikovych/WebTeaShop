package com.spr.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
public class Tea {

    private UUID id;
    private String name;
    @NotNull(message = "Invalid number.")
    @Min(message = "Invalid number.", value = 1)
    @Digits(integer = 9, fraction = 0, message = "Invalid number.")
    private long kg;
    private long price;
    private boolean hasErrors = false;
    private boolean isNormalized = false;

    public Tea(Tea tea) {
        this.id = tea.getId();
        this.name = tea.getName();
        this.kg = tea.getKg();
        this.price = tea.getPrice();
        this.hasErrors = tea.isHasErrors();
        this.isNormalized = tea.isNormalized();
    }

    public Tea(UUID id, String name, long kg, long price) {
        this.id = id;
        this.name = name;
        this.kg = kg;
        this.price = price;
    }
}
