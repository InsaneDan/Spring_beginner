package com.geekbrains.controller.rest;

import com.geekbrains.controller.ProductController;
import com.geekbrains.persistence.entities.Product;
import com.geekbrains.persistence.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductRestController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final ProductRepository productRepository;

    public ProductRestController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping(value = "/{id}")
    public Product getProductById (@PathVariable Long id) {
        return productRepository.findById(id).orElseThrow(() ->
                new NoResultException("Товар с указанным id (" + id + ") не существует!"));
    }

    @GetMapping(value = "/")
    public List<Product> getAllProducts () {
        return productRepository.findAll();
    }

    @PostMapping(value = "/")
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @GetMapping(value = "/delete/{id}")
    public void deleteProductById (@PathVariable Long id) {
        productRepository.deleteById(id);
    }

}
