package com.bookworm.controllers;

import com.bookworm.models.Book;
import com.bookworm.models.Member;
import com.bookworm.models.Review;
import com.bookworm.models.ReviewData;
import com.bookworm.repositories.BookRepository;
import com.bookworm.repositories.MemberRepository;
import com.bookworm.security.AuthenticatedUser;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Class that handles all basic review API endpoints GET and POST.
 * 
 * @version 2017.0522
 * @author Akash Singh akash.singh@cs.tamk.fi
 * @since 1.7
 */
@RestController
@Scope("singleton")
@RequestMapping(value = "books/{bookId}/reviews")
public class ReviewController {

    /**
     * Defines an object to provide client request information to a servlet.
     */
    @Resource
    private HttpServletRequest request;
    
    /**
     * Member repository.
     */
    @Autowired
    private MemberRepository memberRepository;

    /**
     * Book repository.
     */
    @Autowired
    private BookRepository bookRepository;

    /**
     * Fetch all reviews for the given book by book id.
     * 
     * @param bookId book's id.
     * @return array of reviews as json.
     */
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

    /**
     * Save one review to the database for the given book.
     * 
     * @param bookId book's id.
     * @param rd review data (text and stars).
     * @return saved review.
     */
    @Transactional
    @RequestMapping(method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Review> saveReview(@PathVariable long bookId, @Validated @RequestBody ReviewData rd) {
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
