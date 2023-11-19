package com.sjdp.awsbookdynamo.service;

import com.sjdp.awsbookdynamo.model.BookmarkRequest;
import com.sjdp.awsbookdynamo.model.BookmarksBetweenRequest;
import com.sjdp.awsbookdynamo.repository.*;
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
@SpringBootTest(classes = BookmarkService.class)
public class BookmarkServiceTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @MockBean
    BookRepository bookRepository;
    @MockBean
    BookmarkRepository bookmarkRepository;
    @MockBean
    DynamoDBClientQueryBuilder dynamoDBClientQueryBuilder;

    @InjectMocks
    private BookmarkService bookmarkService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).dispatchOptions(true).build();
    }

    @Test
    public void allLastBookmarksTest() throws Exception {

        String bookTitle = "Test Book 1";

        Book book = new Book();
        book.setBook(bookTitle);
        List<Book> bookList = new ArrayList<Book>();
        bookList.add(book);

        List<Bookmark> bookmarksList = new ArrayList<Bookmark>();

        Bookmark bookmark1 = new Bookmark();
        bookmark1.setBook("Book Title 1");
        bookmark1.setCreatedAt("2023-11-01T01:30:00Z");
        bookmark1.setReadSession(1L);
        bookmark1.setPage(10L);
        bookmark1.setWord(100L);

        when(bookRepository.findAll()).thenReturn(bookList);

        when(dynamoDBClientQueryBuilder.getBookmarks(bookTitle,1)).thenReturn(bookmarksList);

        Assert.assertEquals(bookmarksList, bookmarkService.allLastBookmarks());

    }

    @Test
    public void getAllBookmarksBetweenTest() throws Exception {

        String bookTitle = "Test Book 1";

        BookmarksBetweenRequest req = new BookmarksBetweenRequest();
        req.setStart("2023-11-01T00:30:00Z");
        req.setEnd("2023-11-01T02:30:00Z");

        String start = req.getStart();
        String end = req.getEnd();

        List<Bookmark> bookmarksList = new ArrayList<Bookmark>();

        Bookmark bookmark1 = new Bookmark();
        bookmark1.setBook("Book Title 1");
        bookmark1.setCreatedAt("2023-11-01T01:30:00Z");
        bookmark1.setReadSession(1L);
        bookmark1.setPage(10L);
        bookmark1.setWord(100L);

        when(dynamoDBClientQueryBuilder.getBookmarksBetween(bookTitle,start,end)).thenReturn(bookmarksList);

        Assert.assertEquals(bookmarksList, bookmarkService.getAllBookmarksBetween(bookTitle,req));
    }

    @Test
    public void getAllSessionBookmarksTest1() throws Exception {
        String bookTitle = "Book Title 1";
        Long session = 1L;

        BookmarkRequest req = new BookmarkRequest();
        req.setSession(session);
        req.setCreatedAt("");

        List<Bookmark> bookmarksList = new ArrayList<Bookmark>();

        Bookmark bookmark1 = new Bookmark();
        bookmark1.setBook("Book Title 1");
        bookmark1.setCreatedAt("2023-11-01T01:30:00Z");
        bookmark1.setReadSession(session);
        bookmark1.setPage(10L);
        bookmark1.setWord(100L);

        Bookmark bookmark2 = new Bookmark();
        bookmark2.setBook("Book Title 1");
        bookmark2.setCreatedAt("2023-11-01T02:00:00Z");
        bookmark2.setReadSession(session);
        bookmark2.setPage(20L);
        bookmark2.setWord(150L);

        Bookmark bookmark3 = new Bookmark();
        bookmark2.setBook("Book Title 1");
        bookmark2.setCreatedAt("2023-11-01T03:30:00Z");
        bookmark2.setReadSession(session);
        bookmark2.setPage(50L);
        bookmark2.setWord(10L);

        bookmarksList.add(bookmark1);
        bookmarksList.add(bookmark2);
        bookmarksList.add(bookmark3);

        when(dynamoDBClientQueryBuilder.getSessionBookmarks(bookTitle,session)).thenReturn(bookmarksList);

        Assert.assertEquals(bookmarksList, bookmarkService.getAllSessionBookmarks(bookTitle,req));

    }

    @Test
    public void getAllSessionBookmarksTest2() throws Exception {

        String bookTitle = "Book Title 1";
        Long session = 1L;
        String createdAt = "2023-11-01T01:30:00Z";

        BookmarkRequest req = new BookmarkRequest();
        req.setSession(1L);
        req.setCreatedAt(createdAt);

        List<Bookmark> bookmarksList = new ArrayList<Bookmark>();

        Bookmark bookmark1 = new Bookmark();
        bookmark1.setBook("Book Title 1");
        bookmark1.setCreatedAt("2023-11-01T01:30:00Z");
        bookmark1.setReadSession(1L);
        bookmark1.setPage(10L);
        bookmark1.setWord(100L);

        bookmarksList.add(bookmark1);

        when(dynamoDBClientQueryBuilder.getSessionBookmark(bookTitle,session, createdAt)).thenReturn(bookmarksList);

        Assert.assertEquals(bookmarksList, bookmarkService.getAllSessionBookmarks(bookTitle,req));
    }
}
