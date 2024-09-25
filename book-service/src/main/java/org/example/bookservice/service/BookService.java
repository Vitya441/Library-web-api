package org.example.bookservice.service;

import lombok.RequiredArgsConstructor;
import org.example.bookservice.cloud.LibraryServiceClient;
import org.example.bookservice.dto.BookRequestDTO;
import org.example.bookservice.dto.BookResponseDTO;
import org.example.bookservice.entity.Book;
import org.example.bookservice.exception.BookNotFoundException;
import org.example.bookservice.exception.BookWithIsbnExistException;
import org.example.bookservice.mapper.BookMapper;
import org.example.bookservice.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final LibraryServiceClient libraryServiceClient;

    public BookResponseDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Книга с id = " + id + " не найдена"));
        return bookMapper.toBookResponseDTO(book);
    }

    public List<BookResponseDTO> getAllBooks() {
        List<Book> bookEntities = bookRepository.findAll();
        return bookMapper.toBookResponseDTOs(bookEntities);
    }

    public BookResponseDTO getBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException("Книга с ISBN = " + isbn + " не найдена"));
        return bookMapper.toBookResponseDTO(book);
    }

    @Transactional
    public BookResponseDTO addBook(BookRequestDTO bookDTO) {
        if (bookRepository.findByIsbn(bookDTO.getIsbn()).isPresent()) {
            throw new BookWithIsbnExistException("Книга с этим ISBN уже существует");
        }
        Book book = bookMapper.toBookEntity(bookDTO);
        Book savedBook = bookRepository.save(book);
        libraryServiceClient.registerBook(savedBook.getId());
        return bookMapper.toBookResponseDTO(savedBook);
    }

    @Transactional
    public BookResponseDTO updateBook(Long id, BookRequestDTO bookDTO) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Книга с id = " + id + " не найдена"));
        if (!existingBook.getIsbn().equals(bookDTO.getIsbn()) && bookRepository.existsByIsbn(bookDTO.getIsbn())) {
            throw new BookWithIsbnExistException("Книга с этим ISBN уже существует");
        }
        bookMapper.updateBookFromDTO(bookDTO, existingBook);
        Book savedBook = bookRepository.save(existingBook);
        return bookMapper.toBookResponseDTO(savedBook);
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Книга с id = " + id + " не найдена");
        }
        bookRepository.deleteById(id);
    }

}