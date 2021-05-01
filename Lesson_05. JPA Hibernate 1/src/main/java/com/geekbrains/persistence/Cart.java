package com.geekbrains.persistence;

import com.geekbrains.persistence.entities.Product;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
@Scope("prototype")
public class Cart {

    private final Map<Product, Integer> cartMap = new HashMap<>();

    public Map<Product, Integer> getCartMap() {
        return cartMap;
    }

    public void addProduct(Product product, Integer quantity) {
        if (product != null) cartMap.merge(product, quantity, Integer::sum);
        if (cartMap.get(product) < 1) cartMap.remove(product);
    }

    public BigDecimal getSum() {
        BigDecimal sum = BigDecimal.valueOf(0);
        for (Map.Entry<Product, Integer> entry : cartMap.entrySet()) {
            sum = sum.add(entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())));
        }
        return sum;
    }

}