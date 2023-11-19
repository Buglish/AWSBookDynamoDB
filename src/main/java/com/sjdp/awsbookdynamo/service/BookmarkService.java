package com.sjdp.awsbookdynamo.service;
import com.sjdp.awsbookdynamo.model.BookmarksBetweenRequest;
import com.sjdp.awsbookdynamo.model.BookmarkRequest;
import com.sjdp.awsbookdynamo.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookmarkService {

    @Autowired
    BookRepository bookRepository;
    @Autowired
    BookmarkRepository bookmarkRepository;

    @Autowired
    DynamoDBClientQueryBuilder dynamoDBClientQueryBuilder;

    Logger logger = LoggerFactory.getLogger(BookService.class);

    static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public List<Bookmark> allLastBookmarks(){
        List<Bookmark> lastBookmarks = new ArrayList<Bookmark>();

        try {
            //get last one order by decending
            Iterable<Book> booksIterable = bookRepository.findAll();
            for(Book book :booksIterable){
                List<Bookmark> bookmarks = dynamoDBClientQueryBuilder.getBookmarks(book.getBook(),1);
                //add to main list
                bookmarks.parallelStream().collect(Collectors.toCollection(() -> lastBookmarks));
            }
        }catch(Exception e){
            logger.error("[allLastBookmarks][ERROR] "+e.getMessage(),e);
            e.printStackTrace();
            return null;
        }

        return lastBookmarks;
    }

    public List<Bookmark> getAllBookmarksBetween(String book, BookmarksBetweenRequest bookmarksBetweenRequest){
        List<Bookmark> bookmarksResponse = new ArrayList<Bookmark>();
        try {
            String start = bookmarksBetweenRequest.getStart();
            String end = bookmarksBetweenRequest.getEnd();
            bookmarksResponse = dynamoDBClientQueryBuilder.getBookmarksBetween(book,start,end);
        }catch(Exception e){
            logger.error("[getAllBookmarksBetween][ERROR] "+e.getMessage(),e);
            e.printStackTrace();
            return null;
        }

        return bookmarksResponse;
    }

    public List<Bookmark> getAllSessionBookmarks(String book, BookmarkRequest bookmarkRequest){
        List<Bookmark> bookmarkResponse = new ArrayList<Bookmark>();

        try {
            Long session = bookmarkRequest.getSession();
            String createdAt = bookmarkRequest.getCreatedAt();

            if(createdAt.isEmpty()) {
                bookmarkResponse = dynamoDBClientQueryBuilder.getSessionBookmarks(book, session);
            }
            else {
                bookmarkResponse = dynamoDBClientQueryBuilder.getSessionBookmark(book, session, createdAt);
            }

        }catch(Exception e){
            logger.error("[getAllSessionBookmarks][ERROR] "+e.getMessage(),e);
            e.printStackTrace();
            return null;
        }

        return bookmarkResponse;
    }

}
