package com.geekbrains.persistence;

import com.geekbrains.persistence.entities.CartEntry;
import com.geekbrains.persistence.entities.Product;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
//@Scope("prototype")
public class Cart {

    private final Map<Product, Integer> cartMap = new HashMap<>();

    public Map<Product, Integer> getCartMap() {
        return cartMap;
    }

}