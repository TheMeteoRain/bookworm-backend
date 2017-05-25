package com.bookworm.repositories;

import com.bookworm.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends CrudRepository<Book, Long> {

    /**
     * Finds all books
     *
     * @param pageable Pageable-object
     * @return Books
     */
    Page<Book> findAll(Pageable pageable);

    @Modifying(clearAutomatically = true) // http://codingexplained.com/coding/java/spring-framework/updating-entities-with-update-query-spring-data-jpa
    @Query("UPDATE Book b SET b.stock = b.stock - :amount WHERE b.id = :id")
    public void reduceStock(@Param("id") Long id, @Param("amount") int amount);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Book b SET b.stock = b.stock + :amount WHERE b.id = :id")
    public void increaseStock(@Param("id") Long id, @Param("amount") int amount);

    Book findOne(Long id);

    /**
     * Gets books with given word in title, description, genre or author's name.
     *
     * @param word search word
     * @param word2 search word
     * @param word3 search word
     * @param word4 search word
     * @param word5 search word
     * @param pageable pageable-object
     * @return books that match
     */
    Page<Book> findDistinctByTitleContainingOrDescriptionContainingOrAuthors_FirstNameContainingOrAuthors_LastNameContainingOrGenreContainingAllIgnoreCase(String word, String word2, String word3, String word4, String word5, Pageable pageable);

    /**
     * Finds books of given genre
     *
     * @param genre genre of the book
     * @param pageable Pageable-object
     * @return books that match
     */
    Page<Book> findByGenre(String genre, Pageable pageable);
}