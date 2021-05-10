package com.geekbrains.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@AllArgsConstructor
public class ProductFilter {

    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private String partName;

}
