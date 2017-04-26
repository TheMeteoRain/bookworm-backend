package com.bookworm.controllers;

import com.bookworm.models.Book;
import com.bookworm.models.Member;
import com.bookworm.models.Review;
import com.bookworm.models.ReviewData;
import com.bookworm.repositories.BookRepository;
import com.bookworm.repositories.MemberRepository;
import com.bookworm.security.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

@RestController
@RequestMapping(value = "books/{bookId}/reviews")
public class ReviewController {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BookRepository bookRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Set<Review> getReviewsForBook(@PathVariable long bookId) {
        Set<Review> reviews = bookRepository.findOne(bookId).getReviews();
        return reviews;
    }

    @RequestMapping(method=RequestMethod.POST)
    public void saveReview(@RequestBody ReviewData rd, @PathVariable long bookId, HttpServletRequest req, HttpServletResponse res) {

        Book b = bookRepository.findOne(bookId);
        Member m = memberRepository.findOne(AuthenticatedUser.fromRequest(req).id);
        Review r = new Review(b,m,rd.getText(),rd.getStars());

        boolean added = b.getReviews().add(r);
        if (added) {
            memberRepository.save(m);
            bookRepository.save(b);
            res.setStatus(200);
        } else {
            // Member can't post multiple reviews for a single book
            res.setStatus(400);
        }
    }

}