package com.bookworm.repositories;

import com.bookworm.models.Publisher;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PublisherRepository extends CrudRepository<Publisher, Long> {
    List<Publisher> findByNameContaining(String name);
}