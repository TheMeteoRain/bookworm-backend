package com.bookworm.controllers;

import com.bookworm.models.Member;
import com.bookworm.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class that handles all basic member API endpoints GET and POST.
 * 
 * @version 2017.0522
 * @author Akash Singh akash.singh@cs.tamk.fi
 * @since 1.7
 */
@RestController
@Scope("singleton")
@RequestMapping(value = "/members")
public class MemberController {

    /**
     * Member repository.
     */
    @Autowired
    private MemberRepository memberRepository;
    
    /**
     * Default constructor for Spring.
     */
    public MemberController() {}

    /**
     * Save member to the database.
     * 
     * @param member 
     */
    @RequestMapping(method=RequestMethod.POST)
    public void saveMember(@RequestBody Member member) {
        memberRepository.save(member);
    }

    /**
     * Fetches all members from database.
     * 
     * @return array of members as json
     */
    @RequestMapping(method=RequestMethod.GET)
    public Iterable<Member> getMembers() {
        return memberRepository.findAll();
    }
    
    /**
     * Fetches a member by the given id.
     * 
     * @param memberId member id.
     * @return member as json.
     */
    @RequestMapping(value="/{memberId}", method=RequestMethod.GET)
    public Member getMember(@PathVariable long memberId) {
        return memberRepository.findOne(memberId);
    }
}
