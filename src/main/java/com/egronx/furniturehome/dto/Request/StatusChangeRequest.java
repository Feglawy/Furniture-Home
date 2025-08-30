package com.egronx.furniturehome.dto.Request;

import com.egronx.furniturehome.entity.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatusChangeRequest {
    @NotNull(message = "Status is required")
    private OrderStatus status;
}
