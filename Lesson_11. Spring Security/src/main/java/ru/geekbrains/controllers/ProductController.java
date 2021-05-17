package ru.geekbrains.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.entities.Product;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    @GetMapping({"", "/"})
    public String getProductsPage() {
        return "Products List Page / список товаров";
    }

    @GetMapping("/edit")
    public String editProductList() {
        return "Edit Product List / страница со списком товаров с возможностью редактировать и создавать новые товары";
    }

    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable Long productId) {
        return "Edit Product Form / форма редактирование товара";
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createProduct(@RequestBody Product product) {
        return "Save Product Page / сохранить параметры товара";
    }

    @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String updateProduct(@RequestBody Product product) {
        return "Update Product Page / изменить параметры товара";
    }

    @DeleteMapping("/{id}")
    public String deleteProductById(@PathVariable Long id) {
        return "Delete Product Page / удалить товар";
    }

}
