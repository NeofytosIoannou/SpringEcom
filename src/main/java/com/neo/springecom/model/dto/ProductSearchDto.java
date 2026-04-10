package com.neo.springecom.model.dto;

import java.math.BigDecimal;

public record ProductSearchDto(
        Integer id,
        String name,
        String description,
        String brand,
        BigDecimal price,
        String category,
        Boolean productAvailable,
        Integer stockQuantity
) {}