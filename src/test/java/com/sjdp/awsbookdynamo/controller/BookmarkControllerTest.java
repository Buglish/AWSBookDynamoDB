package com.sjdp.awsbookdynamo.controller;

import com.sjdp.awsbookdynamo.AWSBookDynamoDBApplication;
import com.sjdp.awsbookdynamo.model.BookmarkRequest;
import com.sjdp.awsbookdynamo.model.BookmarksBetweenRequest;
import com.sjdp.awsbookdynamo.repository.Book;
import com.sjdp.awsbookdynamo.repository.Bookmark;
import com.sjdp.awsbookdynamo.repository.BookmarkId;
import com.sjdp.awsbookdynamo.service.BookmarkService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AWSBookDynamoDBApplication.class)
public class BookmarkControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @MockBean
    private BookmarkService bookmarkService;

    @InjectMocks
    private BookmarkController bookmarkController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).dispatchOptions(true).build();
    }

    @Test
    public void getAllLastBookmarksTest() throws Exception {

        List<Bookmark> bookmarksList = new ArrayList<Bookmark>();

        Bookmark bookmark1 = new Bookmark();
        bookmark1.setBook("Book Title 1");
        bookmark1.setCreatedAt("2023-11-02T08:30:00Z");
        bookmark1.setReadSession(2L);
        bookmark1.setPage(100L);
        bookmark1.setWord(50L);

        Bookmark bookmark2 = new Bookmark();
        bookmark2.setBook("Book Title 2");
        bookmark2.setCreatedAt("2023-11-04T08:00:00Z");
        bookmark2.setReadSession(3L);
        bookmark2.setPage(211L);
        bookmark2.setWord(18L);

        bookmarksList.add(bookmark1);
        bookmarksList.add(bookmark2);

        when(bookmarkService.allLastBookmarks()).thenReturn(bookmarksList);

        Assert.assertEquals(bookmarksList, bookmarkController.getAllLastBookmarks().getBody());
    }

    @Test
    public void getAssetPositionBetweenTest() throws Exception {

        String bookTitle = "Book Title 1";

        BookmarksBetweenRequest req = new BookmarksBetweenRequest();
        req.setStart("2023-11-01T01:30:00Z");
        req.setEnd("2023-11-01T03:30:00Z");

        List<Bookmark> bookmarksList = new ArrayList<Bookmark>();

        Bookmark bookmark1 = new Bookmark();
        bookmark1.setBook("Book Title 1");
        bookmark1.setCreatedAt("2023-11-01T01:30:00Z");
        bookmark1.setReadSession(1L);
        bookmark1.setPage(10L);
        bookmark1.setWord(100L);

        Bookmark bookmark2 = new Bookmark();
        bookmark2.setBook("Book Title 1");
        bookmark2.setCreatedAt("2023-11-01T02:00:00Z");
        bookmark2.setReadSession(1L);
        bookmark2.setPage(20L);
        bookmark2.setWord(150L);

        Bookmark bookmark3 = new Bookmark();
        bookmark2.setBook("Book Title 1");
        bookmark2.setCreatedAt("2023-11-01T03:30:00Z");
        bookmark2.setReadSession(1L);
        bookmark2.setPage(50L);
        bookmark2.setWord(10L);

        bookmarksList.add(bookmark1);
        bookmarksList.add(bookmark2);
        bookmarksList.add(bookmark3);

        when(bookmarkService.getAllBookmarksBetween(bookTitle,req)).thenReturn(bookmarksList);

        Assert.assertEquals(bookmarksList, bookmarkController.getAssetPositionBetween(bookTitle,req).getBody());
    }

    @Test
    public void getBookSessionsOnTest1() throws Exception {

        String bookTitle = "Book Title 1";

        BookmarkRequest req = new BookmarkRequest();
        req.setSession(1L);

        List<Bookmark> bookmarksList = new ArrayList<Bookmark>();

        Bookmark bookmark1 = new Bookmark();
        bookmark1.setBook("Book Title 1");
        bookmark1.setCreatedAt("2023-11-01T01:30:00Z");
        bookmark1.setReadSession(1L);
        bookmark1.setPage(10L);
        bookmark1.setWord(100L);

        Bookmark bookmark2 = new Bookmark();
        bookmark2.setBook("Book Title 1");
        bookmark2.setCreatedAt("2023-11-01T02:00:00Z");
        bookmark2.setReadSession(1L);
        bookmark2.setPage(20L);
        bookmark2.setWord(150L);

        Bookmark bookmark3 = new Bookmark();
        bookmark2.setBook("Book Title 1");
        bookmark2.setCreatedAt("2023-11-01T03:30:00Z");
        bookmark2.setReadSession(1L);
        bookmark2.setPage(50L);
        bookmark2.setWord(10L);

        bookmarksList.add(bookmark1);
        bookmarksList.add(bookmark2);
        bookmarksList.add(bookmark3);

        when(bookmarkService.getAllSessionBookmarks(bookTitle,req)).thenReturn(bookmarksList);

        Assert.assertEquals(bookmarksList, bookmarkController.getBookSessionsOn(bookTitle,req).getBody());
    }

    @Test
    public void getBookSessionsOnTest2() throws Exception {

        String bookTitle = "Book Title 1";

        BookmarkRequest req = new BookmarkRequest();
        req.setSession(1L);
        req.setCreatedAt("2023-11-01T01:30:00Z");

        List<Bookmark> bookmarksList = new ArrayList<Bookmark>();

        Bookmark bookmark1 = new Bookmark();
        bookmark1.setBook("Book Title 1");
        bookmark1.setCreatedAt("2023-11-01T01:30:00Z");
        bookmark1.setReadSession(1L);
        bookmark1.setPage(10L);
        bookmark1.setWord(100L);

        bookmarksList.add(bookmark1);

        when(bookmarkService.getAllSessionBookmarks(bookTitle,req)).thenReturn(bookmarksList);

        Assert.assertEquals(bookmarksList, bookmarkController.getBookSessionsOn(bookTitle,req).getBody());
    }

}