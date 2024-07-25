package xaltius.azanespaul.LMS.books.dto;

import xaltius.azanespaul.LMS.users.dto.UsersDto;

public record BooksDto(Long id,
                       String title,
                       String author,
                       Boolean borrowed,
                       UsersDto borrowedBy) {


}
