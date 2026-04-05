package com.neo.springecom.model.dto;

public record OrderItemRequest(
    int productId,
    int quantity)
{

}
