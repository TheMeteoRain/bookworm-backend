package com.bookworm.repositories;

import com.bookworm.models.User;
import java.io.Serializable;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    Iterable<User> findAll();
    void delete(User entity);
    void delete(Long id);
    User findOne(Long id);
}