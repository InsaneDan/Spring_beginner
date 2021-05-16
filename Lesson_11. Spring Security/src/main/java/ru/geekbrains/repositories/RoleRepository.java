package ru.geekbrains.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.entities.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
}
