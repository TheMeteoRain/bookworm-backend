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
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.hateoas.Link;
import java.util.Set;
import javax.annotation.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

@RestController
@RequestMapping(value = "books/{bookId}/reviews")
public class ReviewController {

    @Resource
    private HttpServletRequest request;
    
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BookRepository bookRepository;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getReviewsForBook(@PathVariable long bookId) {
        Set<Review> reviews = bookRepository.findOne(bookId).getReviews();
        ResponseEntity<Review> response = new ResponseEntity(HttpStatus.NOT_FOUND);
        
        if (reviews.size() > 0) {
            response = new ResponseEntity(reviews, HttpStatus.OK);
            
            for (Review review : reviews) {
                System.out.println(review.getMember().getUsername());
                Link bookLink = linkTo(methodOn(BookController.class).getBook(review.getBook().getBookId())).withRel("findBook");
                review.add(bookLink);
            }
        }
        
        return response;
    }

    @Transactional
    @RequestMapping(method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Review> saveReview(@PathVariable long bookId, @RequestBody ReviewData rd) {
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<Review> response = new ResponseEntity(HttpStatus.NOT_FOUND);
        Book findThisBook = bookRepository.findOne(bookId);
        Member findThisMember = memberRepository.findOne(AuthenticatedUser.fromRequest(request).id);
        
        if (findThisBook != null) {
            Review saveThisReview = new Review(findThisBook,findThisMember,rd.getText(),rd.getStars());
            headers.setLocation(linkTo(BookController.class).slash(bookId).slash("reviews").toUri());
            
            boolean added = findThisBook.getReviews().add(saveThisReview);
            if (added) {
               // memberRepository.save(findThisMember);
               // bookRepository.save(findThisBook);
                response = new ResponseEntity(saveThisReview, headers, HttpStatus.CREATED);
            } else {
                // Member can't post multiple reviews for a single book
                response = new ResponseEntity("{\"code\": 409, \"status\": \"Conflict\", \"message\": \"Member can only review once per book\"}", headers, HttpStatus.CONFLICT);
            }
        }

        return response;
    }

}