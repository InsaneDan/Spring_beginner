package com.geekbrains.service;

import com.geekbrains.persistence.entities.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    List<Product> getProductList();

    void saveOrUpdate(Product product);

    Product getProductById(Long id);

    void deleteById(Long id);

    Page<Product> getProductListPageable(Integer pageNum, Integer productsPerPage);
}
