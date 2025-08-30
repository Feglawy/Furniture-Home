package com.egronx.furniturehome.dto.Response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OrderStatusChangeDTO {
    private String status;
    private LocalDateTime changedAt;
}
