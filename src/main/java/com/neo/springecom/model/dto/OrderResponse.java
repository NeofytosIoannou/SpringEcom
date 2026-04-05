package com.neo.springecom.model.dto;

import java.time.LocalDate;
import java.util.List;

public record OrderResponse(
        String orderId,
        String customerName,
        String status,
        String email,
        LocalDate orderDate,
        List<OrderItemResponse> items
) {
}
