package com.bookworm.repositories;

import com.bookworm.models.Book;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends CrudRepository<Book, Long> {
    @Modifying(clearAutomatically = true) // http://codingexplained.com/coding/java/spring-framework/updating-entities-with-update-query-spring-data-jpa
    @Query("UPDATE Book b SET b.stock = b.stock - :amount WHERE b.id = :id")
    public void reduceStock(@Param("id") Long id, @Param("amount") int amount);
    
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Book b SET b.stock = b.stock + :amount WHERE b.id = :id")
    public void increaseStock(@Param("id") Long id, @Param("amount") int amount);

    Book findOne(Long id);

    Iterable<Book> findByTitleContainingOrDescriptionContainingOrGenreContainingAllIgnoreCase(String word, String word2, String word3);
}