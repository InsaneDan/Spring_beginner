package com.geekbrains.service;

import com.geekbrains.persistence.Cart;
import com.geekbrains.persistence.entities.CartEntry;
import com.geekbrains.persistence.entities.Product;
import com.geekbrains.persistence.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
//@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CartServiceImpl implements CartService {

    private final EntityManager em;
// TODO: исправить в очередном ДЗ
//    @Lezenford (https://github.com/Lezenford) 4 hours ago
//    осталось от времен до JPA, стоит переложить все на JPA, что еще работает на entityManager, это повысит
//    единообразие кода. не стоит без веской причины мешать несколько подходов

    private final ProductRepository productRepository;

    public CartServiceImpl(EntityManager em, ProductRepository productRepository) {
        this.em = em;
        this.productRepository = productRepository;
    }

    @Lookup
    @Override
    public Cart getNewCart() {
        return null;
    }

    public List<CartEntry> findAllProductsById(Long orderId) {
        return em.createQuery("FROM CartEntry c WHERE c.order_id = :orderId", CartEntry.class)
                .setParameter("order_id", orderId)
                .getResultList();
    }

    @Override
    public void addProduct(Cart cart, Product product, Integer quantity) {
        if (product != null) cart.getCartMap().merge(product, quantity, Integer::sum);
        if (cart.getCartMap().get(product) < 1) cart.getCartMap().remove(product);
    }

    @Override
    public void addProduct(Cart cart, Long prodId, Integer quantity) {
        Product product = productRepository.findById(prodId).get();
        this.addProduct(cart, product, quantity);
    }

    @Override
    public BigDecimal getSum(Cart cart) {
        BigDecimal sum = BigDecimal.valueOf(0);
        for (Map.Entry<Product, Integer> entry : cart.getCartMap().entrySet()) {
            sum = sum.add(entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())));
        }
        return sum;
    }

    public void printCart(Cart cart) {
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
    public int getProductQuantity(Cart cart, Product product) {
        if (cart.getCartMap().containsKey(product)) {
            return cart.getCartMap().get(product);
        }
        return 0;
    }

    @Override
    public Integer getItemsAmount(Cart cart) {
        Integer amount = 0;
        for (Map.Entry<Product, Integer> entryMap : cart.getCartMap().entrySet()) {
            amount += entryMap.getValue();
        }
        return amount;
    }

    @Override
    public int getProductQuantity(Cart cart, Long prodId) {
        Product product = productRepository.findById(prodId).get();
        return this.getProductQuantity(cart, product);
    }

    @Override
    public List<Product> getCartListSorted(Cart cart) {
        List<Product> cartList = new ArrayList<>(cart.getCartMap().keySet());
        Collections.sort(cartList, (p1, p2) -> {
            if (p1.getId() > p2.getId()) {
                return 1;
            } else if (p1.getId() < p2.getId()) {
                return -1;
            }
            return 0;
        });
        return cartList;
    }

}