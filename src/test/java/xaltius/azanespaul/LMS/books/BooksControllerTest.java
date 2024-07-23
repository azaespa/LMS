package xaltius.azanespaul.LMS.books;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import xaltius.azanespaul.LMS.books.exceptions.BooksAlreadyBorrowedException;
import xaltius.azanespaul.LMS.books.exceptions.BooksNotFoundException;
import xaltius.azanespaul.LMS.users.Users;
import xaltius.azanespaul.LMS.users.exceptions.UsersNotFoundException;

import java.util.*;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BooksControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BooksService booksService;

    List<Books> booksList;

    @BeforeEach
    void setUp() {
        Books b1 = new Books();
        b1.setId(1L);
        b1.setTitle("Title 1 Test Case");
        b1.setAuthor("Author 1 Test Case");
        b1.setBorrowed(false);
        b1.setBorrowedBy(null);

        Books b2 = new Books();
        b2.setId(2L);
        b2.setTitle("Title 2 Test Case");
        b2.setAuthor("Author 2 Test Case");
        b2.setBorrowed(false);
        b2.setBorrowedBy(null);

        Books b3 = new Books();
        b3.setId(3L);
        b3.setTitle("Title 3 Test Case");
        b3.setAuthor("Author 3 Test Case");
        b3.setBorrowed(false);
        b3.setBorrowedBy(null);

        booksList = new ArrayList<>();
        booksList.add(b1);
        booksList.add(b2);
        booksList.add(b3);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testSaveBooksSuccess() throws Exception {
        // Given
        Map<String, Object> body = new HashMap<>();
        body.put("id", 1L);
        body.put("title", "Title Test Case");
        body.put("author", "Author Test Case");
        body.put("borrowed", false);
        body.put("borrowedBy", null);

        Books savedBooks = new Books();
        savedBooks.setId(1L);
        savedBooks.setTitle("Title Test Case");
        savedBooks.setAuthor("Author Test Case");
        savedBooks.setBorrowed(false);
        savedBooks.setBorrowedBy(null);

        BDDMockito.given(this.booksService.saveBooks(Mockito.any(Books.class))).willReturn(savedBooks);

        // When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(body))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Save One Success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books.id").value(savedBooks.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books.title").value(savedBooks.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books.author").value(savedBooks.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books.borrowed").value(savedBooks.getBorrowed()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books.borrowedBy").isEmpty());
    }

    @Test
    void testGetAllBooksSuccess() throws Exception {
        // Given
        BDDMockito.given(this.booksService.findAllBooks()).willReturn(this.booksList);

        // When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/books").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(this.booksList.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Title 1 Test Case"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].author").value("Author 1 Test Case"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].borrowed").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].borrowedBy").value(IsNull.nullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Title 2 Test Case"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].author").value("Author 2 Test Case"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].borrowed").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].borrowedBy").value(IsNull.nullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id").value("3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].title").value("Title 3 Test Case"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].author").value("Author 3 Test Case"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].borrowed").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].borrowedBy").value(IsNull.nullValue()));
    }

    @Test
    void testGetBooksByIdSuccess() throws Exception {
        // Given
        BDDMockito.given(this.booksService.findBooksById(1L)).willReturn(this.booksList.get(0));

        // When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/books/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Find One Success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books.title").value("Title 1 Test Case"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books.author").value("Author 1 Test Case"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books.borrowed").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books.borrowedBy").value(IsNull.nullValue()));
    }

    @Test
    void testGetBooksByIdNotFound() throws Exception {
        // Given
        BDDMockito.given(this.booksService.findBooksById(1L)).willThrow(new BooksNotFoundException("1"));

        // When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/books/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Could not find a book with id 1 :("))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(400));
    }

    @Test
    void testUpdateBooksByIdSuccess() throws Exception {
        //Given
        Map<String, Object> body = new HashMap<>();
        body.put("id", 1L);
        body.put("title", "Updated Title Test Case");
        body.put("author", "Updated Author Test Case");
        body.put("borrowed", false);
        body.put("borrowedBy", null);

        Books updatedBooks = new Books();
        updatedBooks.setId(1L);
        updatedBooks.setTitle("Updated Title Test Case");
        updatedBooks.setAuthor("Updated Author Test Case");
        updatedBooks.setBorrowed(false);
        updatedBooks.setBorrowedBy(null);

        BDDMockito.given(this.booksService.updateBooksById(Mockito.any(Books.class), eq(1L))).willReturn(updatedBooks);

        // When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(body))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Update One Success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books.title").value("Updated Title Test Case"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books.author").value("Updated Author Test Case"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books.borrowed").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books.borrowedBy").isEmpty());
    }

    @Test
    void testUpdateBooksByIdNotFound() throws Exception{
        //Given
        Map<String, Object> body = new HashMap<>();
        body.put("id", 1L);
        body.put("title", "Updated Title Test Case");
        body.put("author", "Updated Author Test Case");
        body.put("borrowed", false);
        body.put("borrowedBy", null);

        BDDMockito.given(this.booksService.updateBooksById(Mockito.any(Books.class), eq(1L))).willThrow(new BooksNotFoundException("1"));

        // When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(body))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Could not find a book with id 1 :("))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books").isEmpty());

    }
    @Test
    void testDeleteBooksByIdSuccess() throws Exception {
        // Given
        BDDMockito.doNothing().when(this.booksService).deleteBooksById(1L);

        // When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Delete One Success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books").isEmpty());
    }

    @Test
    void testDeleteBooksByIdNotFound() throws Exception {
        // Given
        BDDMockito.doThrow(new BooksNotFoundException("1")).when(this.booksService).deleteBooksById(1L);

        // When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Could not find a book with id 1 :("))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books").isEmpty());
    }

    @Test
    void testBorrowBooksByIdSuccess() throws Exception {
        //Given
        Map<String, Object> usersBody = new HashMap<>();
        usersBody.put("id", 1L);
        usersBody.put("name", "Name Test Case");

        Map<String, Object> body = new HashMap<>();
        body.put("id", 1L);
        body.put("title", "Updated Title Test Case");
        body.put("author", "Updated Author Test Case");
        body.put("borrowed", true);
        body.put("borrowedBy", usersBody);

        Users users = new Users();
        users.setId(1L);
        users.setName("Name Test Case");

        Books borrowedBooks = new Books();
        borrowedBooks.setId(1L);
        borrowedBooks.setTitle("Updated Title Test Case");
        borrowedBooks.setAuthor("Updated Author Test Case");
        borrowedBooks.setBorrowed(true);
        borrowedBooks.setBorrowedBy(users);

        BDDMockito.given(this.booksService.borrowBooksById(eq(1L), eq(1L))).willReturn(borrowedBooks);

        // When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/books/1/borrow/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(body))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Borrow One Success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books.id").value(borrowedBooks.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books.title").value(borrowedBooks.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books.author").value(borrowedBooks.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books.borrowed").value(borrowedBooks.getBorrowed()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books.borrowedBy").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.books.borrowedBy.id").value(borrowedBooks.getBorrowedBy().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books.borrowedBy.name").value(borrowedBooks.getBorrowedBy().getName()));
    }

    @Test
    void testBorrowBooksByBookIdNotFound() throws Exception {
        //Given
        Map<String, Object> usersBody = new HashMap<>();
        usersBody.put("id", 1L);
        usersBody.put("name", "Name Test Case");

        Map<String, Object> body = new HashMap<>();
        body.put("id", 1L);
        body.put("title", "Updated Title Test Case");
        body.put("author", "Updated Author Test Case");
        body.put("borrowed", true);
        body.put("borrowedBy", usersBody);

        BDDMockito.given(this.booksService.borrowBooksById(eq(1L), eq(1L))).willThrow(new BooksNotFoundException("1"));

        // When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/books/1/borrow/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(body))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Could not find a book with id 1 :("))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books").isEmpty());
    }

    @Test
    void testBorrowBooksByUserIdNotFound() throws Exception {
        //Given
        Map<String, Object> usersBody = new HashMap<>();
        usersBody.put("id", 1L);
        usersBody.put("name", "Name Test Case");

        Map<String, Object> body = new HashMap<>();
        body.put("id", 1L);
        body.put("title", "Updated Title Test Case");
        body.put("author", "Updated Author Test Case");
        body.put("borrowed", false);
        body.put("borrowedBy", usersBody);

        BDDMockito.given(this.booksService.borrowBooksById(eq(1L), eq(1L))).willThrow(new UsersNotFoundException("1"));

        // When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/books/1/borrow/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(body))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Could not find a user with id 1 :("))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books").isEmpty());
    }

    @Test
    void testBorrowBooksByIdAlreadyBorrowed() throws Exception {
        // Given
        Map<String, Object> usersBody = new HashMap<>();
        usersBody.put("id", 1L);
        usersBody.put("name", "Name Test Case");

        Map<String, Object> body = new HashMap<>();
        body.put("id", 1L);
        body.put("title", "Updated Title Test Case");
        body.put("author", "Updated Author Test Case");
        body.put("borrowed", true);
        body.put("borrowedBy", usersBody);

        BDDMockito.given(this.booksService.borrowBooksById(eq(1L), eq(1L))).willThrow(new BooksAlreadyBorrowedException("1"));

        // When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/books/1/borrow/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(body))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("The book with id 1 is already borrowed by another user :("))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books").isEmpty());

    }

    @Test
    void testReturnBooksByIdSuccess() throws Exception {
        // Given
        Map<String, Object> body = new HashMap<>();
        body.put("id", 1L);
        body.put("title", "Title Test Case");
        body.put("author", "Author Test Case");
        body.put("borrowed", false);
        body.put("borrowedBy", null);

        Books returnedBooks = new Books();
        returnedBooks.setId(1L);
        returnedBooks.setTitle("Title Test Case");
        returnedBooks.setAuthor("Author Test Case");
        returnedBooks.setBorrowed(false);
        returnedBooks.setBorrowedBy(null);

        BDDMockito.given(this.booksService.returnBooksById(eq(1L))).willReturn(returnedBooks);

        // When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/books/1/return")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(body))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Returned One Success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books.id").value(returnedBooks.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books.title").value(returnedBooks.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books.author").value(returnedBooks.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books.borrowed").value(returnedBooks.getBorrowed()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books.borrowedBy").isEmpty());
    }
    @Test
    void testReturnBooksByIdNotFound() throws Exception {
        // Given
        Map<String, Object> body = new HashMap<>();
        body.put("id", 1L);
        body.put("title", "Title Test Case");
        body.put("author", "Author Test Case");
        body.put("borrowed", false);
        body.put("borrowedBy", null);

        BDDMockito.given(this.booksService.returnBooksById(eq(1L))).willThrow(new BooksNotFoundException("1"));

        // When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/books/1/return")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(body))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Could not find a book with id 1 :("))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books").isEmpty());
    }
}