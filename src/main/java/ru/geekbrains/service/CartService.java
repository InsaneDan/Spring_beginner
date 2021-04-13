package ru.geekbrains.service;

import ru.geekbrains.persistence.Product;

import java.math.BigDecimal;

public interface CartService {

    void addProduct(Product product, Integer quantity);
    void addProduct(Long prodId, Integer quantity);

    void delProduct(Product product, Integer quantity);

    BigDecimal getSum();

    void printCart();

    int getProductQuantity(Product product);
    int getProductQuantity(Long prodId);
}