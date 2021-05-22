package ru.geekbrains.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.persistence.entities.CartEntry;
import ru.geekbrains.persistence.entities.Order;

import java.util.List;

public interface CartEntryRepository extends JpaRepository<Order, Long> {

//    List<CartEntry> findAllByOrderIdAndOrderByProductNameDesc(Long userId, Long orderId);

}
