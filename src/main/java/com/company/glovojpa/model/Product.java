package com.company.glovojpa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private Double cost;
}
