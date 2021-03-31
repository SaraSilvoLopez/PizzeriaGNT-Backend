package com.example.demo.dto.ingredientdtos;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

public class CreateOrUpdateIngredientDTO {
    @NotEmpty
    @Size(max=255)
    public String name;
    @NotNull
    public BigDecimal price;
}
