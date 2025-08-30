package com.egronx.furniturehome.dto.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderProductDTO {
    private Long productId;
    private int quantity;
    private double unitPrice;
}