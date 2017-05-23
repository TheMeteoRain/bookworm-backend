package com.bookworm.repositories;

import com.bookworm.models.Book;
import com.bookworm.models.Notification;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * Notification repository used to fetch information from author table and update
 * given said table in database.
 * 
 * @version 2017.0522
 * @author Akash Singh akash.singh@cs.tamk.fi
 * @since 1.7
 */
public interface NotificationRepository extends CrudRepository<Notification, Long> {
    /**
     * Fetches a list of notifications by the given book.
     * 
     * @param book find notifications by this book.
     * @return list of notifications.
     */
    List<Notification> findByBook(Book book);
}
