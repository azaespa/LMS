package xaltius.azanespaul.LMS.books.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import xaltius.azanespaul.LMS.books.Books;
import xaltius.azanespaul.LMS.books.dto.BooksDto;
import xaltius.azanespaul.LMS.users.converter.UsersToUsersDtoConverter;

@Component
public class BooksToBooksDtoConverter implements Converter<Books, BooksDto> {

    private final UsersToUsersDtoConverter usersToUsersDtoConverter;

    public BooksToBooksDtoConverter(UsersToUsersDtoConverter usersToUsersDtoConverter) {
        this.usersToUsersDtoConverter = usersToUsersDtoConverter;
    }

    @Override
    public BooksDto convert(Books source) {
        BooksDto booksDto = new BooksDto(source.getId(),
                                    source.getTitle(),
                                    source.getAuthor(),
                                    source.getBorrowed(),
                                    source.getBorrowedBy() != null
                                            ? this.usersToUsersDtoConverter.convert(source.getBorrowedBy()) : null);
        return booksDto;
    }
}
