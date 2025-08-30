package com.egronx.furniturehome.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductDTO {
    private Long id;

    @NotBlank
    private String name;

    private String description;

    @NotNull
    @Positive
    private Double price;

    @NotNull
    @Positive
    private Double discount;

    @NotNull
    @Positive
    private Double finalPrice;

    @NotNull
    @Positive
    private Long stock;

    private String category;

    private List<String> images;
}
