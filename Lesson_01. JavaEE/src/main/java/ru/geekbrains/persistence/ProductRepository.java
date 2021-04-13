package ru.geekbrains.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ProductRepository {

    private final Map<Long, Product> productMap = new ConcurrentHashMap<>();
    private final AtomicLong identity = new AtomicLong(0);

    // получить список всех продуктов
    public List<Product> findAll() {
        return new ArrayList<>(productMap.values());
    }

    // сохранить продукт
    public void saveOrUpdate(Product product) {
        if (product.getId() == null) {
            Long id = identity.incrementAndGet();
            product.setId(id);
        }
        productMap.put(product.getId(), product);
    }

    // найти продукт по id
    public Product findById(Long id) { return productMap.get(id); }

    // удалить продукт по id
    public void deleteById(Long id) {
        productMap.remove(id);
    }
}
