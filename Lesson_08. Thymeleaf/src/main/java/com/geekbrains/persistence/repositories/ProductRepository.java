package com.geekbrains.persistence.repositories;

import com.geekbrains.persistence.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findProductByName(String name);
    List<Product> findProductByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    List<Product> findProductByPriceLessThan(BigDecimal maxPrice);
    List<Product> findProductByPriceGreaterThan(BigDecimal minPrice);

    // @Query(SELECT p FROM Product p WHERE (p.price BETWEEN ?1 AND ?2) AND p.name=?3 LIMIT ?4 OFFSET ?5) // запрос не проверен!
    Page<Product> findProductsByPriceBetweenAndNameLike(BigDecimal minPrice, BigDecimal maxPrice, String partName, Pageable varPageSort);
}
