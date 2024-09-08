package org.example.bookservice.service;

import lombok.RequiredArgsConstructor;
import org.example.bookservice.cloud.LibraryServiceClient;
import org.example.bookservice.dto.BookDTO;
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


    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id = " + id + " not found"));
        return bookMapper.toBookDTO(book);
    }

    public List<BookDTO> getAllBooks() {
        List<Book> bookEntities = bookRepository.findAll();
        return bookMapper.toBookDTOs(bookEntities);
    }

    public BookDTO getBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException("Book with ISBN = " + isbn + " not found"));
        return bookMapper.toBookDTO(book);
    }

    @Transactional
    public BookDTO addBook(BookDTO bookDTO) {

        if (bookRepository.findByIsbn(bookDTO.getIsbn()).isPresent()) {
            throw new BookWithIsbnExistException("Book with this ISBN already exists");
        }
        Book book = bookMapper.toBookEntity(bookDTO);
        Book savedBook = bookRepository.save(book);
        // Используем Feign Client для регистрации книги в LibraryService
        libraryServiceClient.registerBook(savedBook.getId());
        return bookMapper.toBookDTO(savedBook);
    }



    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id = " + id + " not found"));

        if (!existingBook.getIsbn().equals(bookDTO.getIsbn()) && bookRepository.existsByIsbn(bookDTO.getIsbn())) {
            throw new BookWithIsbnExistException("Book with this ISBN already exists");
        }

        existingBook.setIsbn(bookDTO.getIsbn());
        existingBook.setName(bookDTO.getName());
        existingBook.setGenre(bookDTO.getGenre());
        existingBook.setDescription(bookDTO.getDescription());
        existingBook.setAuthor(bookDTO.getAuthor());

        Book savedBook = bookRepository.save(existingBook);
        return bookMapper.toBookDTO(savedBook);
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book with id = " + id + " not found");
        }
        bookRepository.deleteById(id);
    }


}