package com.bookworm.repositories;

import com.bookworm.models.Genre;
import org.springframework.data.repository.CrudRepository;

public interface GenreRepository extends CrudRepository<Genre, String> {

}