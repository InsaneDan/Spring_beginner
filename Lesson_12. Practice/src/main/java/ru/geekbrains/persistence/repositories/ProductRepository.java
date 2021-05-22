package ru.geekbrains.persistence.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.persistence.entities.Product;

import java.math.BigDecimal;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findProductsByPriceBetweenAndTitleLike(BigDecimal minPrice, BigDecimal maxPrice, String partTitle, Pageable varPageSort);
}
