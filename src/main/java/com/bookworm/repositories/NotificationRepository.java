/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bookworm.repositories;

import com.bookworm.models.Book;
import com.bookworm.models.Notification;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Akash
 */
public interface NotificationRepository extends CrudRepository<Notification, Long> {
    List<Notification> findByBook(Book book);
}
