package com.geekbrains.persistence.entities;

import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "products")
@NamedQueries({
        @NamedQuery(name = "Product.findAll", query = "FROM Product p"),
        @NamedQuery(name = "Product.findAllSortedByName", query = "FROM Product p ORDER BY p.name"),
        @NamedQuery(name = "Product.findById", query = "FROM Product p WHERE p.id = :id"),
        @NamedQuery(name = "Product.deleteById", query = "DELETE FROM Product p WHERE p.id = :id")
})

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column private String name;
    @Column private BigDecimal price;

    public Product() { }

    public Product(Long id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("Product {id = %-2s | name = %-15s | price = %-8s}", id, name, price);
    }
}
