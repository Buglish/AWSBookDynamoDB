package com.sjdp.awsbookdynamo.controller;

import com.sjdp.awsbookdynamo.repository.Book;
import com.sjdp.awsbookdynamo.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    BookService bookService;

    @GetMapping("book/{book}")
    public ResponseEntity<Book> getBook(@PathVariable String book) {
        ResponseEntity<Book> response = null;

        try {
            Book bookResponse = bookService.getBook(book);

            if (bookResponse == null) {
                response = new ResponseEntity<Book>(bookResponse, HttpStatus.NOT_FOUND);
            } else {
                response = new ResponseEntity<Book>(bookResponse, HttpStatus.OK);
            }
        }catch(Exception e){
            response = new ResponseEntity<Book>(new Book(), HttpStatus.BAD_GATEWAY);
        }
        return response;
    }

}
