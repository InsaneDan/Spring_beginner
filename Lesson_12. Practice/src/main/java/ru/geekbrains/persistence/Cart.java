package ru.geekbrains.persistence;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import ru.geekbrains.persistence.entities.Product;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@Component
@Scope(
        value = WebApplicationContext.SCOPE_SESSION,
        proxyMode = ScopedProxyMode.TARGET_CLASS
)
public class Cart {

    private static final AtomicInteger COUNTER = new AtomicInteger(0);
    private final int cartId = COUNTER.incrementAndGet();

    private final Map<Product, Integer> cartMap = new HashMap<>();
    public Map<Product, Integer> getCartMap() {
        return cartMap;
    }

}