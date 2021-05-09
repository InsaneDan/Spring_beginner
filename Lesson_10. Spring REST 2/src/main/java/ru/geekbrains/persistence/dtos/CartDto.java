package ru.geekbrains.persistence.dtos;

import lombok.Data;
import ru.geekbrains.persistence.entities.Product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
public class CartDto {

    private Map<Product, Integer> cartMap = new HashMap<>();
    private BigDecimal totalSum;

}
