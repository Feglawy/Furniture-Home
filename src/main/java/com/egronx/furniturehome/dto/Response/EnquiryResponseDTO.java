package com.egronx.furniturehome.dto.Response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnquiryResponseDTO {
    private Long id;
    @NotNull
    private Long userId;
    @NotBlank
    private String content;

    private String adminReply;

    @NotNull
    private boolean closed;
}
