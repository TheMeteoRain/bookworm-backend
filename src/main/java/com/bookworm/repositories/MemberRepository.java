package com.bookworm.repositories;

import com.bookworm.models.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Long> {
    Iterable<Member> findAll();
    void delete(Member entity);
    void delete(Long id);
    Member findOne(Long id);
}