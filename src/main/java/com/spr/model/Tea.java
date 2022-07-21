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
@AllArgsConstructor
@NoArgsConstructor
public class Tea {

    private UUID id;
    private String name;
    @NotNull
    @Min(1)
    // TODO: Force number be integer and if its decimal throw error
    private long kg;
    private long price;

    public Tea(Tea tea) {
        this.id = tea.getId();
        this.name = tea.getName();
        this.kg = tea.getKg();
        this.price = tea.getPrice();
    }
}
