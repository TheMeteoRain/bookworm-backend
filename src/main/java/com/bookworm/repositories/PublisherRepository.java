package com.bookworm.repositories;

import com.bookworm.models.Publisher;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Publisher repository used to fetch information from author table and update
 * given said table in database.
 * 
 * @version 2017.0522
 * @author Akash Singh akash.singh@cs.tamk.fi
 * @author Toni Seppalainen toni.seppalainen@cs.tamk.fi
 * @since 1.7
 */
public interface PublisherRepository extends CrudRepository<Publisher, Long> {
    List<Publisher> findByNameContaining(String name);
}