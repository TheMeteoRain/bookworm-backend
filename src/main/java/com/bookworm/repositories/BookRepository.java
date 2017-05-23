package com.bookworm.repositories;

import com.bookworm.models.Book;
import org.springframework.data.repository.CrudRepository;

/**
 * Book repository used to fetch information from author table and update
 * given said table in database.
 * 
 * @version 2017.0522
 * @author Akash Singh akash.singh@cs.tamk.fi
 * @since 1.7
 */
public interface BookRepository extends CrudRepository<Book, Long> {}