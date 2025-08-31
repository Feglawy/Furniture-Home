package com.egronx.furniturehome.dto;

import lombok.Data;

@Data
public class CartProductDTO {
    private Long id;
    private Long productId;
    private int quantity;
}
