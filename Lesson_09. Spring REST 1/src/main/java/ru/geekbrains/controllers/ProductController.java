package ru.geekbrains.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.entities.Product;
import ru.geekbrains.services.ProductService;

import java.math.BigDecimal;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping("")
    public ResponseEntity<?> getAllProducts(@RequestParam(defaultValue = "1") Integer pageIndex,
                                         @RequestParam(defaultValue = "0") BigDecimal minPrice,
                                         @RequestParam(defaultValue = "1000000") BigDecimal maxPrice,
                                         @RequestParam(defaultValue = "") String partName,
                                         @RequestParam(defaultValue = "5") Integer productsPerPage) {
        Page<Product> page = productService.findAllFilteredPaged(minPrice, maxPrice, partName, pageIndex, productsPerPage);
        if (page.getContent().size() > 0) {
            return ResponseEntity.ok(page.getContent());
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        if (productService.findById(id).isPresent()) {
            return ResponseEntity.ok(productService.findById(id).get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        if (product.getId() == null) {
            return ResponseEntity.ok(productService.saveOrUpdate(product));
        }
        return new ResponseEntity<>(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }

    @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateProduct(@RequestBody Product product) {
        if (product.getId() != null) {
            return ResponseEntity.ok(productService.saveOrUpdate(product));
        }
        return new ResponseEntity<>(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
        if (productService.findById(id).isPresent()) {
            productService.deleteProductById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
