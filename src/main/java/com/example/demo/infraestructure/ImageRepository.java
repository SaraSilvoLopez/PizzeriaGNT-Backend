package com.example.demo.infraestructure;

import java.io.IOException;

import com.example.demo.dto.pizzadtos.ImageDTO;

public interface ImageRepository {
    public String create(ImageDTO dto) throws IOException, InterruptedException;
}
