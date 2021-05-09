package ru.geekbrains.persistence.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.persistence.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
