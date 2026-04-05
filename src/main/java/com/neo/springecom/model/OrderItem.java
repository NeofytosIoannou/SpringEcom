package com.neo.springecom.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Product product;

    private int quantity;
    private BigDecimal totalPrice;

    @ManyToOne(fetch= FetchType.LAZY)
    private Order order;

}
