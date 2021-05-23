package ru.geekbrains.persistence.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "order_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<CartEntry> cartEntries;

    @Override
    public String toString() {
        return String.format("Order {id = %s, user_id = %s}", id, user.getId());
    }
}
