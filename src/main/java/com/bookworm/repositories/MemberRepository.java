package com.bookworm.repositories;

import com.bookworm.models.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Long> {
    Member findFirstByUsername(String username);
}