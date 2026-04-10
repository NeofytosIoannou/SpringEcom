package com.neo.springecom.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private String brand;
    private BigDecimal price;
    private String category;
    private Date releaseDate;
    private Boolean productAvailable;
    private int stockQuantity;
    private String imageName;
    private String imageType;
    @Lob
    @JsonIgnore
    private byte[] imageData;



}
