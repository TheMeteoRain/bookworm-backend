package com.bookworm.controllers;

import com.bookworm.models.Member;
import com.bookworm.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/members")
public class MemberController {

    @Autowired
    MemberRepository database;
    
    public MemberController() {
        
    }

    @RequestMapping(method=RequestMethod.POST)
    public void saveMember(@RequestBody Member c) {
        database.save(c);
    }

    @RequestMapping(method=RequestMethod.GET)
    public Iterable<Member> getMembers() {
        return database.findAll();
    }
    
    @RequestMapping(value="/{memberId}", method=RequestMethod.GET)
    public Member getMember(@PathVariable long memberId) {
        return database.findOne(memberId);
    }
    
    @RequestMapping("/example")
    public Member greeting() {
        return new Member(0, "asd", "asd", "a@b.c");
    }


}