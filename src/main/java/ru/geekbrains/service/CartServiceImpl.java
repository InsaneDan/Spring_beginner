package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.geekbrains.persistence.Cart;
import ru.geekbrains.persistence.Product;
import ru.geekbrains.persistence.ProductRepository;

import java.util.Map;

@Component
//@Scope("prototype")
public class CartServiceImpl implements CartService {

    private Cart cart;

    // по большОму счету добавление продуктового репозитория в корзину не требуется; экспериментировал с @Autowired
    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    public void setCart(Cart cart) {
        this.cart = cart;
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
    public Double getSum() {
        return cart.getSum();
    }


    public void printCart() {
        double sum = 0.0;
        // NOTE: т.к. это мапа, сортировки нет
        for (Map.Entry<Product, Integer> entryMap : cart.getCartMap().entrySet()) {
            Product product = entryMap.getKey();
            System.out.printf("Product id = %-2s | name = %-15s | price = %-8s | quantity = %-3s | sum = %-12s \n",
                    product.getId(), product.getName(), product.getPrice(), entryMap.getValue(), product.getPrice() * entryMap.getValue());
            sum += product.getPrice() * entryMap.getValue();
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