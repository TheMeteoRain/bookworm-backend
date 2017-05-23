package com.bookworm.repositories;

import com.bookworm.models.Genre;
import org.springframework.data.repository.CrudRepository;

/**
 * Genre repository used to fetch information from author table and update
 * given said table in database.
 * 
 * @version 2017.0522
 * @author Toni Seppalainen toni.seppalainen@cs.tamk.fi
 * @since 1.7
 */
public interface GenreRepository extends CrudRepository<Genre, String> {}