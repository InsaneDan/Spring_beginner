package ru.geekbrains.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.geekbrains.persistence.entities.Product;
import ru.geekbrains.persistence.repositories.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Page<Product> findAllFilteredPaged(BigDecimal minPrice, BigDecimal maxPrice, String partTitle, Integer pageIndex, Integer productsPerPage) {
        Pageable pageRequest = PageRequest.of(pageIndex - 1, productsPerPage);
        Page<Product> productPage = productRepository.findProductsByPriceBetweenAndTitleLike(minPrice, maxPrice, "%"+partTitle+"%", pageRequest);
        return productPage;
    }

    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product saveOrUpdateProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

}
