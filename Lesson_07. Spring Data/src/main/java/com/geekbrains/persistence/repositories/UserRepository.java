package com.geekbrains.persistence.repositories;

import com.geekbrains.persistence.entities.Order;
import com.geekbrains.persistence.entities.User;
import org.hibernate.Hibernate;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {
}
