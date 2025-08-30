package com.egronx.furniturehome.dto;

import lombok.Data;

@Data
public class ReviewDTO {
    private Long productId;
    private int rating;
    private String comment;
}
