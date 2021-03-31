package com.example.demo.dto.pizzadtos;


import java.util.HashSet;
import java.util.UUID;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateOrUpdatePizzaDTO {
    @NotEmpty
    @Size(max=255)
    public String name;
    @NotEmpty
    public HashSet<UUID> ingredients;
    @NotEmpty
    @Size(max=255)
    public String description;
    @NotNull
    public UUID image;
}
