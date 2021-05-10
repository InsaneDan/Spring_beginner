package com.geekbrains.persistence.repositories;

import com.geekbrains.persistence.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findProductByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    List<Product> findProductByPriceLessThan(BigDecimal maxPrice);

    List<Product> findProductByPriceGreaterThan(BigDecimal minPrice);

    List<Product> findProductByName(String name);
}
