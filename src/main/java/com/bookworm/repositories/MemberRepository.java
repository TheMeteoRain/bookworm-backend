package com.bookworm.repositories;

import com.bookworm.models.Member;
import org.springframework.data.repository.CrudRepository;

/**
 * Member repository used to fetch information from author table and update
 * given said table in database.
 * 
 * @version 2017.0522
 * @author Akash Singh akash.singh@cs.tamk.fi
 * @since 1.7
 */
public interface MemberRepository extends CrudRepository<Member, Long> {
    /**
     * Fetches a member by the given username.
     * 
     * @param username member's username.
     * @return first member with the given username.
     */
    Member findFirstByUsername(String username);
}