package com.sjdp.awsbookdynamo.controller;

import com.sjdp.awsbookdynamo.model.BookmarkRequest;
import com.sjdp.awsbookdynamo.model.BookmarksBetweenRequest;
import com.sjdp.awsbookdynamo.repository.Book;
import com.sjdp.awsbookdynamo.repository.Bookmark;
import com.sjdp.awsbookdynamo.service.BookService;
import com.sjdp.awsbookdynamo.service.BookmarkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class BookmarkController {

    Logger logger = LoggerFactory.getLogger(BookmarkController.class);

    @Autowired
    BookmarkService bookmarkService;

    @GetMapping("bookmark/last")
    public ResponseEntity<List<Bookmark>> getAllLastBookmarks() {
        ResponseEntity<List<Bookmark>> response = null;

        try {
            List<Bookmark> allAssetLastLocations = bookmarkService.allLastBookmarks();

            if (allAssetLastLocations == null || allAssetLastLocations.isEmpty()) {
                response = new ResponseEntity<List<Bookmark>>(allAssetLastLocations, HttpStatus.NOT_FOUND);
            } else {
                response = new ResponseEntity<List<Bookmark>>(allAssetLastLocations, HttpStatus.OK);
            }
        }catch(Exception e){
            response = new ResponseEntity<List<Bookmark>>(new ArrayList<Bookmark>(), HttpStatus.BAD_GATEWAY);
        }
        return response;
    }

    @PostMapping("bookmark/{book}/between")
    public ResponseEntity<List<Bookmark>> getAssetPositionBetween(@PathVariable String book,@RequestBody BookmarksBetweenRequest bookmarksBetweenRequest) {
        ResponseEntity<List<Bookmark>> response = null;

        try {
            List<Bookmark> allLastBookmarks = bookmarkService.getAllBookmarksBetween(book,bookmarksBetweenRequest);

            if (allLastBookmarks == null || allLastBookmarks.isEmpty()) {
                response = new ResponseEntity<List<Bookmark>>(allLastBookmarks, HttpStatus.NOT_FOUND);
            } else {
                response = new ResponseEntity<List<Bookmark>>(allLastBookmarks, HttpStatus.OK);
            }
        }catch(Exception e){
            response = new ResponseEntity<List<Bookmark>>(new ArrayList<Bookmark>(), HttpStatus.BAD_GATEWAY);
        }
        return response;
    }

    @PostMapping("bookmark/{book}/session")
    public ResponseEntity<List<Bookmark>> getBookSessionsOn(@PathVariable String book,@RequestBody BookmarkRequest bookmarkRequest) {
        ResponseEntity<List<Bookmark>> response = null;

        try {
            List<Bookmark> allLastBookmarks = bookmarkService.getAllSessionBookmarks(book,bookmarkRequest);

            if (allLastBookmarks == null || allLastBookmarks.isEmpty()) {
                response = new ResponseEntity<List<Bookmark>>(allLastBookmarks, HttpStatus.NOT_FOUND);
            } else {
                response = new ResponseEntity<List<Bookmark>>(allLastBookmarks, HttpStatus.OK);
            }
        }catch(Exception e){
            response = new ResponseEntity<List<Bookmark>>(new ArrayList<Bookmark>(), HttpStatus.BAD_GATEWAY);
        }
        return response;
    }


}
