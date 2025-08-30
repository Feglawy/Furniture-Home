package com.egronx.furniturehome.dto.Request;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnquiryRequestDTO {
    private String content;
}
