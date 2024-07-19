package xaltius.azanespaul.LMS.books;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import xaltius.azanespaul.LMS.books.exceptions.BooksAlreadyBorrowedException;
import xaltius.azanespaul.LMS.books.exceptions.BooksNotFoundException;
import xaltius.azanespaul.LMS.users.Users;
import xaltius.azanespaul.LMS.users.UsersNotFoundException;
import xaltius.azanespaul.LMS.users.UsersRepository;
import xaltius.azanespaul.LMS.users.UsersService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
class BooksServiceTest {

    @Mock
    BooksRepository booksRepository;

    @InjectMocks
    BooksService booksService;

    @Mock
    UsersRepository usersRepository;

    @InjectMocks
    UsersService usersService;

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
    void testSaveBooksSuccess() {
        // Given
        Books b = new Books();
        b.setId(1L);
        b.setTitle("Title Test Case");
        b.setAuthor("Author Test Case");
        b.setBorrowed(false);
        b.setBorrowedBy(null);

        // When
        booksService.saveBooks(b);

        // Then
        Assertions.assertThat(b).isNotNull();
        Assertions.assertThat(b.getId()).isGreaterThan(0);
        Mockito.verify(booksRepository, Mockito.times(1)).save(b);
    }

    @Test
    void testFindAllBooksSuccess() {
        // Given
        BDDMockito.given(booksRepository.findAll()).willReturn(this.booksList);

        // When
        List<Books> actualBooksList = booksService.findAllBooks();

        // Then
        Assertions.assertThat(actualBooksList.size()).isEqualTo(this.booksList.size());
        Mockito.verify(booksRepository, Mockito.times(1)).findAll();
    }

    @Test
    void testFindBooksByIdSuccess() {
        // Given
        Books b = new Books();
        b.setId(1L);
        b.setTitle("Title Test Case");
        b.setAuthor("Author Test Case");
        b.setBorrowed(false);
        b.setBorrowedBy(null);

        BDDMockito.given(booksRepository.findBooksById(1L)).willReturn(Optional.of(b));

        // When
        Books actualBooks = booksService.findBooksById(1L);

        // Then
        Assertions.assertThat(actualBooks.getId()).isEqualTo(b.getId());
        Assertions.assertThat(actualBooks.getTitle()).isEqualTo(b.getTitle());
        Assertions.assertThat(actualBooks.getAuthor()).isEqualTo(b.getAuthor());
        Assertions.assertThat(actualBooks.getBorrowed()).isEqualTo(b.getBorrowed());
        Assertions.assertThat(actualBooks.getBorrowedBy()).isEqualTo(b.getBorrowedBy());
        Mockito.verify(booksRepository, Mockito.times(1)).findBooksById(1L);
    }

    @Test
    void testFindBooksByIdNotFound() {
        // Given
        BDDMockito.given(booksRepository.findBooksById(1L)).willReturn(Optional.empty());

        // When
        assertThrows(BooksNotFoundException.class, () -> {
            booksService.findBooksById(1L);
        });

        // Then
        Mockito.verify(booksRepository, Mockito.times(1)).findBooksById(1L);
    }

    @Test
    void testUpdateBooksByIdSuccess() {
        // Given
        Books originalBooks = new Books();
        originalBooks.setId(1L);
        originalBooks.setTitle("Original Title Test Case");
        originalBooks.setAuthor("Original Author Test Case");
        originalBooks.setBorrowed(false);
        originalBooks.setBorrowedBy(null);

        Books update = new Books();
        update.setId(1L);
        update.setTitle("Updated Title Test Case");
        update.setAuthor("Updated Author Test Case");
        update.setBorrowed(false);
        update.setBorrowedBy(null);

        BDDMockito.given(booksRepository.findBooksById(1L)).willReturn(Optional.of(originalBooks));
        BDDMockito.given(booksRepository.save(originalBooks)).willReturn(originalBooks);

        // When
        Books updatedBooks = booksService.updateBooksById(update, 1L);

        // Then
        Assertions.assertThat(updatedBooks.getId()).isEqualTo(1L);
        Assertions.assertThat(updatedBooks.getTitle()).isEqualTo(update.getTitle());
        Assertions.assertThat(updatedBooks.getAuthor()).isEqualTo(update.getAuthor());
        Mockito.verify(booksRepository, Mockito.times(1)).findBooksById(1L);
        Mockito.verify(booksRepository, Mockito.times(1)).save(originalBooks);
    }

    @Test
    void testDeleteBooksByIdSuccess() {
        // Given
        Books b = new Books();
        b.setId(1L);
        b.setTitle("Title Test Case");
        b.setAuthor("Author Test Case");
        b.setBorrowed(false);
        b.setBorrowedBy(null);

        BDDMockito.given(booksRepository.findBooksById(1L)).willReturn(Optional.of(b));
        Mockito.doNothing().when(booksRepository).deleteById(1L);
        // When
        booksService.deleteBooksById(1L);

        // Then
        Mockito.verify(booksRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void testDeleteBooksByIdNotFound() {
        // Given
        BDDMockito.given(booksRepository.findBooksById(1L)).willReturn(Optional.empty());

        // When
        assertThrows(BooksNotFoundException.class, () -> {
            booksService.deleteBooksById(1L);
        });

        // Then
        Mockito.verify(booksRepository, Mockito.times(1)).findBooksById(1L);
    }

    @Test
    void testBorrowBooksByIdSuccess() {
        // Given
        Books b = new Books();
        b.setId(1L);
        b.setTitle("Title Test Case");
        b.setAuthor("Author Test Case");
        b.setBorrowed(false);
        b.setBorrowedBy(null);

        Users u = new Users();
        u.setId(1L);
        u.setName("Name Test Case");

        Books update = new Books();
        update.setId(1L);
        update.setTitle("Title Test Case");
        update.setAuthor("Author Test Case");
        update.setBorrowed(true);
        update.setBorrowedBy(u);

        BDDMockito.given(booksRepository.findBooksById(1L)).willReturn(Optional.of(b));
        BDDMockito.given(booksRepository.save(b)).willReturn(b);
        BDDMockito.given(usersRepository.findUsersById(1L)).willReturn(Optional.of(u));

        // When
        Books borrowedBooks = booksService.borrowBooksById(1L, 1L);

        // Then
        Assertions.assertThat(borrowedBooks.getId()).isEqualTo(1L);
        Assertions.assertThat(borrowedBooks.getBorrowed()).isEqualTo(update.getBorrowed());
        Assertions.assertThat(borrowedBooks.getBorrowedBy()).isEqualTo(update.getBorrowedBy());
        Mockito.verify(booksRepository, Mockito.times(1)).findBooksById(1L);
        Mockito.verify(usersRepository, Mockito.times(1)).findUsersById(1L);
        Mockito.verify(booksRepository, Mockito.times(1)).save(b);
    }

    @Test
    void testBorrowBooksByBookIdNotFound() {
        // Given
        BDDMockito.given(booksRepository.findBooksById(1L)).willReturn(Optional.empty());

        // When
        assertThrows(BooksNotFoundException.class, () -> {
            booksService.borrowBooksById(1L, 1L);
        });

        // Then
        Mockito.verify(booksRepository, Mockito.times(1)).findBooksById(1L);
    }

    @Test
    void testBorrowBooksByUserIdNotFound() {
        // Given
        BDDMockito.given(usersRepository.findUsersById(1L)).willReturn(Optional.empty());

        // When
        assertThrows(UsersNotFoundException.class, () -> {
            booksService.borrowBooksById(1L, 1L);
        });

        // Then
        Mockito.verify(usersRepository, Mockito.times(1)).findUsersById(1L);
    }

    @Test
    void testBorrowBooksByIdAlreadyBorrowed() {
        // Given
        Users currentBorrower = new Users();
        currentBorrower.setId(1L);
        currentBorrower.setName("Name Test Case");

        Books borrowedBook = new Books();
        borrowedBook.setId(1L);
        borrowedBook.setTitle("Title Test Case");
        borrowedBook.setAuthor("Author Test Case");
        borrowedBook.setBorrowed(true);
        borrowedBook.setBorrowedBy(currentBorrower);

        Users newBorrower = new Users();
        newBorrower.setId(2L);
        newBorrower.setName("Name 2 Test Case");

        BDDMockito.given(booksRepository.findBooksById(1L)).willReturn(Optional.of(borrowedBook));
        BDDMockito.given(usersRepository.findUsersById(2L)).willReturn(Optional.of(newBorrower));

        // When
        assertThrows(BooksAlreadyBorrowedException.class, () -> {
            booksService.borrowBooksById(1L, 2L);
        });

        // Then
        Mockito.verify(booksRepository, Mockito.times(1)).findBooksById(borrowedBook.getId());
        Mockito.verify(usersRepository, Mockito.times(1)).findUsersById(newBorrower.getId());
    }

    @Test
    void testReturnBooksByIdSuccess() {
        // Given
        Users u = new Users();
        u.setId(1L);
        u.setName("Name Test Case");

        Books b = new Books();
        b.setId(1L);
        b.setTitle("Title Test Case");
        b.setAuthor("Author Test Case");
        b.setBorrowed(true);
        b.setBorrowedBy(u);

        Books update = new Books();
        update.setId(1L);
        update.setTitle("Title Test Case");
        update.setAuthor("Author Test Case");
        update.setBorrowed(false);
        update.setBorrowedBy(null);

        BDDMockito.given(booksRepository.findBooksById(1L)).willReturn(Optional.of(b));
        BDDMockito.given(booksRepository.save(b)).willReturn(b);

        // When
        Books returnedBooks = booksService.returnBooksById(1L);

        // Then
        Assertions.assertThat(returnedBooks.getId()).isEqualTo(1L);
        Assertions.assertThat(returnedBooks.getBorrowed()).isEqualTo(update.getBorrowed());
        Assertions.assertThat(returnedBooks.getBorrowedBy()).isEqualTo(update.getBorrowedBy());
        Mockito.verify(booksRepository, Mockito.times(1)).findBooksById(1L);
        Mockito.verify(booksRepository, Mockito.times(1)).save(b);
    }

    @Test
    void testReturnBooksByBookIdNotFound() {
        // Given
        BDDMockito.given(booksRepository.findBooksById(1L)).willReturn(Optional.empty());

        // When
        assertThrows(BooksNotFoundException.class, () -> {
           booksService.returnBooksById(1L);
        });

        // Then
        Mockito.verify(booksRepository, Mockito.times(1)).findBooksById(1L);
    }
}