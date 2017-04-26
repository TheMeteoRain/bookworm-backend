package com.bookworm.repositories;

import com.bookworm.models.Book;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends CrudRepository<Book, Long> {
    Iterable<Book> findAll();
    void delete(Book entity);
    void delete(Long id);
    
    @Modifying(clearAutomatically = true) // http://codingexplained.com/coding/java/spring-framework/updating-entities-with-update-query-spring-data-jpa
    @Query("UPDATE Book b SET b.stock = b.stock - :amount WHERE b.id = :id")
    public void reduceStock(@Param("id") Long id, @Param("amount") int amount);
    
    Book findOne(Long id);
}