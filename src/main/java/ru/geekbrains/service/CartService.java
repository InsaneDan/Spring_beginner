package ru.geekbrains.service;

import ru.geekbrains.persistence.Product;

public interface CartService {

    void addProduct(Product product, Integer quantity);
    void addProduct(Long prodId, Integer quantity);

    void delProduct(Product product, Integer quantity);

    Double getSum();

    void printCart();

    int getProductQuantity(Product product);
    int getProductQuantity(Long prodId);
}