/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bookworm.repositories;

import com.bookworm.models.Purchase;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Akash
 */
public interface PurchaseRepository extends CrudRepository<Purchase, Long> {
    Purchase findOne(Long id);
    Iterable<Purchase> findAll();
}
