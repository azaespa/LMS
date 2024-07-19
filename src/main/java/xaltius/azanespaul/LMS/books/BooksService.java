package xaltius.azanespaul.LMS.books;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xaltius.azanespaul.LMS.books.exceptions.BooksAlreadyBorrowedException;
import xaltius.azanespaul.LMS.books.exceptions.BooksNotFoundException;
import xaltius.azanespaul.LMS.books.exceptions.BooksUpdateErrorException;
import xaltius.azanespaul.LMS.users.Users;
import xaltius.azanespaul.LMS.users.UsersNotFoundException;
import xaltius.azanespaul.LMS.users.UsersRepository;

import java.util.List;

@Service
@Transactional
public class BooksService {

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private UsersRepository usersRepository;

    public Books saveBooks(Books books) {
        return booksRepository.save(books);
    }

    public List<Books> findAllBooks() {
        return booksRepository.findAll();
    }

    public Books findBooksById(Long id) {
        return booksRepository.findBooksById(id)
                .orElseThrow(() -> new BooksNotFoundException(id.toString()));
    }

    public Books updateBooksById(Books updatedBooks, Long id) {

        if (updatedBooks.getBorrowed() && updatedBooks.getBorrowedBy() == null) throw new BooksUpdateErrorException();
        if (!updatedBooks.getBorrowed() && updatedBooks.getBorrowedBy() != null) throw new BooksUpdateErrorException();

        Books originalBooks = booksRepository.findBooksById(id)
                .orElseThrow(() -> new BooksNotFoundException(id.toString()));
        originalBooks.setTitle(updatedBooks.getTitle());
        originalBooks.setAuthor(updatedBooks.getAuthor());
        originalBooks.setBorrowed(updatedBooks.getBorrowed());
        originalBooks.setBorrowedBy(updatedBooks.getBorrowedBy());

        return booksRepository.save(originalBooks);
    }

    public void deleteBooksById(Long id) {
        this.booksRepository.findBooksById(id)
                .orElseThrow(() -> new BooksNotFoundException(id.toString()));
        booksRepository.deleteById(id);
    }

    public Books borrowBooksById(Long bookId, Long userId) {
        Users users = usersRepository.findUsersById(userId)
                .orElseThrow(() -> new UsersNotFoundException(userId.toString()));
        Books borrowedBooks = booksRepository.findBooksById(bookId)
                .orElseThrow(() -> new BooksNotFoundException(bookId.toString()));

        if (borrowedBooks.getBorrowed()) throw new BooksAlreadyBorrowedException(bookId.toString());

        borrowedBooks.setBorrowed(true);
        borrowedBooks.setBorrowedBy(users);

        return booksRepository.save(borrowedBooks);
    }

    public Books returnBooksById(Long bookId) {
        Books returnedBooks = booksRepository.findBooksById(bookId)
                .orElseThrow(() -> new BooksNotFoundException(bookId.toString()));
        returnedBooks.setBorrowed(false);
        returnedBooks.setBorrowedBy(null);

        return booksRepository.save(returnedBooks);
    }
}
