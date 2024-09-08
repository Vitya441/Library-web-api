package org.example.bookservice.mapper;

import org.example.bookservice.dto.BookDTO;
import org.example.bookservice.entity.Book;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDTO toBookDTO(Book book);

    Book toBookEntity(BookDTO bookDTO);

    List<BookDTO> toBookDTOs(List<Book> bookEntities);

    List<Book> toBookEntities(List<BookDTO> bookDTOs);

}
