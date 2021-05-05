package com.geekbrains.service;

import com.geekbrains.persistence.entities.Product;
import com.geekbrains.persistence.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
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

    public Page<Product> getProductListPageable(Integer pageNum, Integer productsPerPage) {
        // default parameters
        if (pageNum == null || pageNum == 0)  pageNum = 1;
        if (productsPerPage == null || productsPerPage == 0)  productsPerPage = 10;
        // часть списка товаров --- Slice<T>
        PageRequest pageRequest = PageRequest.of(pageNum - 1, productsPerPage);
        final Page<Product> products = productRepository.findAll(pageRequest);
        return products;
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
