package com.example.demo.dto.commentdtos;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CreateCommentDTO {
    @NotBlank
    @Size(max=255)
    public String text;
    @Min(0)
    @Max(10)
    public int score;
}
