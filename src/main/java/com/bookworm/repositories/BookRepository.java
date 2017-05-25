package com.bookworm.repositories;

import com.bookworm.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Book repository used to fetch information from author table and update
 * given said table in database.
 * 
 * @version 2017.0522
 * @author Akash Singh akash.singh@cs.tamk.fi
 * @since 1.7
 */
public interface BookRepository extends CrudRepository<Book, Long> {

    /**
     * Finds all books
     *
     * @param pageable Pageable-object
     * @return Books
     */
    Page<Book> findAll(Pageable pageable);

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