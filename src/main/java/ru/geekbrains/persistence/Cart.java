package ru.geekbrains.persistence;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Scope("prototype")
public class Cart {

    private Map<Product, Integer> cartMap = new HashMap<>();

    public Cart() {
    }

    public Map<Product, Integer> getCartMap() {
        return cartMap;
    }

    public void addProduct(Product product, Integer quantity) {
        if (product != null)
            cartMap.merge(product, quantity, Integer::sum);
    }

    public void delProduct(Product product, Integer quantity) {
        if (cartMap.containsKey(product)) {
           if (quantity != null && cartMap.get(product).compareTo(quantity) > 0) {
               cartMap.put(product, cartMap.get(product) - quantity);
           } else {
               cartMap.remove(product);
           }
        }
    }

    public double getSum() {
        double sum = 0.0;
        for (Map.Entry<Product, Integer> entry : cartMap.entrySet()) {
            sum += entry.getKey().getPrice() * entry.getValue();
        }
        return sum;
    }

}