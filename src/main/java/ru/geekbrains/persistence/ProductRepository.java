package ru.geekbrains.persistence;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ProductRepository {

    private final Map<Long, Product> productMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void createProducts(){
//        for (int i = 1; i < 6; i++) {
//            product = new Product((long) i, "Product " + i, i * 100.0);
//            productMap.put(product.getId(), product);
//        }

        // ручное заполнение
        productMap.put(1L, new Product(1L, "Product 1", 110.05));
        productMap.put(2L, new Product(2L, "Product 2", 20.02));
        productMap.put(3L, new Product(3L, "Product 3", 300.0));
        productMap.put(4L, new Product(4L, "Product 4", 444.44));
        productMap.put(5L, new Product(5L, "Product 5", 55.5));
    }

    // получить список всех продуктов
    public List<Product> findAll() {
        return new ArrayList<>(productMap.values());
    }

    // сохранить продукт
    // если в метод передан новый продукт без заданного id, то будет добавлен очередной
    // если продукт с заданным id уже есть в мапе, то он будет заменен на новый
    public void saveOrUpdate(Product product) {
        if (product.getId() == null) {
            Long id = (long) (productMap.size() + 1);
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
