package org.example.bookservice.mapper;

import org.example.bookservice.dto.BookRequestDTO;
import org.example.bookservice.dto.BookResponseDTO;
import org.example.bookservice.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookResponseDTO toBookResponseDTO(Book book);

    Book toBookEntity(BookRequestDTO bookRequestDTO);

    List<BookResponseDTO> toBookResponseDTOs(List<Book> bookEntities);

    void updateBookFromDTO(BookRequestDTO bookRequestDTO, @MappingTarget Book book);
}
