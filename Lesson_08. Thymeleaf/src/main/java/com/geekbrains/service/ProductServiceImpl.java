package com.geekbrains.service;

import com.geekbrains.persistence.entities.Product;
import com.geekbrains.persistence.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getProductList() {
        return productRepository.findAll();
    }

    public Page<Product> getProductsFiltered(BigDecimal minPrice, BigDecimal maxPrice, String partName, Integer pageNum, Integer productsPerPage) {
        Pageable pageRequest = PageRequest.of(pageNum - 1, productsPerPage);
        Page<Product> productPage = productRepository.findProductsByPriceBetweenAndNameLike(minPrice, maxPrice, "%"+partName+"%", pageRequest);
        return productPage;
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() ->
                new NoResultException("Товар с указанным id не существует!"));
    }

    @Override
    public void saveOrUpdate(Product product) {
        productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

}
