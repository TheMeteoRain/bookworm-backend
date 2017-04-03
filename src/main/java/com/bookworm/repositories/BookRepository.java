package com.bookworm.repositories;

import com.bookworm.models.Book;
import java.io.Serializable;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
    Iterable<Book> findAll();
    void delete(Book entity);
    void delete(Long id);
    Book findOne(Long id);
}