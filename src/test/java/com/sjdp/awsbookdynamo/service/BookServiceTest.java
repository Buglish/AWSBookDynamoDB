package com.sjdp.awsbookdynamo.service;

import com.sjdp.awsbookdynamo.repository.Book;
import com.sjdp.awsbookdynamo.repository.BookRepository;
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

import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookService.class)
public class BookServiceTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).dispatchOptions(true).build();
    }

    @Test
    public void getBookTest(){

        String bookTitle = "Test Book 1";

        Book book = new Book();
        book.setBook(bookTitle);

        Optional<Book> resp = Optional.of(book);

        when(bookRepository.findById(bookTitle)).thenReturn(resp);

        Assert.assertEquals(book, bookService.getBook(bookTitle));

    }


}
