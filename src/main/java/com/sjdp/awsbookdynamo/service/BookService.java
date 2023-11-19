package com.sjdp.awsbookdynamo.service;

import com.sjdp.awsbookdynamo.repository.Book;
import com.sjdp.awsbookdynamo.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    Logger logger = LoggerFactory.getLogger(BookService.class);

    public Book getBook(String book){
        Book bookResponse = null;
        try {
            Optional<Book> bookResponseOpt = bookRepository.findById(book);
            if(bookResponseOpt != null) {
                bookResponse = bookResponseOpt.get();
            }
        }catch(Exception e){
            logger.error("[getBook][ERROR] "+e.getMessage(),e);
            e.printStackTrace();
            throw e;
        }
        return bookResponse;
    }
}
