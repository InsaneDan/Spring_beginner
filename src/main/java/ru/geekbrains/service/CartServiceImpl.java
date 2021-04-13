package ru.geekbrains.service;

import org.springframework.stereotype.Service;
import ru.geekbrains.persistence.Cart;
import ru.geekbrains.persistence.Product;
import ru.geekbrains.persistence.ProductRepository;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService {

    private Cart cart;
    private final ProductRepository productRepository;

    public CartServiceImpl(ProductRepository productRepository, Cart cart) {
        this.productRepository = productRepository;
        this.cart = cart;
    }

    public void setNewCart() {
        this.cart = new Cart();
    }

    @Override
    public void addProduct(Product product, Integer quantity) {
        cart.addProduct(product, quantity);
    }

    @Override
    public void addProduct(Long prodId, Integer quantity) {
        Product product = productRepository.findById(prodId);
        this.addProduct(product, quantity);
    }

    @Override
    public void delProduct(Product product, Integer quantity) {
            cart.delProduct(product, quantity);
    }

    @Override
    public BigDecimal getSum() {
        return cart.getSum();
    }

    public void printCart() {
        BigDecimal sum = BigDecimal.valueOf(0);
        // NOTE: т.к. это мапа, сортировки нет
        for (Map.Entry<Product, Integer> entryMap : cart.getCartMap().entrySet()) {
            Product product = entryMap.getKey();
            BigDecimal prodSum = product.getPrice().multiply(BigDecimal.valueOf(entryMap.getValue()));
            System.out.printf("Product id = %-2s | name = %-15s | price = %-8s | quantity = %-3s | sum = %-12s \n",
                    product.getId(), product.getName(), product.getPrice(), entryMap.getValue(), prodSum);
            sum = sum.add(prodSum);
        }
        System.out.println("Общая стоимость продуктов в корзине: " + sum);
    }

    @Override
    public int getProductQuantity(Product product) {
        if (cart.getCartMap().containsKey(product)) {
            return cart.getCartMap().get(product);
        }
        return 0;
    }

    @Override
    public int getProductQuantity(Long prodId) {
        Product product = productRepository.findById(prodId);
        return this.getProductQuantity(product);
    }
}