package com.example.library.service;

import com.example.library.dto.BookDTO;
import com.example.library.entity.Book;
import com.example.library.mapper.BookMapper;
import com.example.library.mapper.BookRentalMapper;
import com.example.library.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookDTO> getAllBooks() {
       List<Book> books=bookRepository.findAll();
       return BookMapper.toBookDTOList(books);
    }

    public BookDTO getBookById(Long id) {
        Book book= bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Kitab tapılmadı, ID:"+id));
        return BookMapper.toBookDTO(book);
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Book book,Long id) {
        Book updatedBook= bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Kitab tapılmadı, ID:"+id));
        updatedBook.setTitle(book.getTitle());
        updatedBook.setAuthor(book.getAuthor());
        updatedBook.setPublicationYear(book.getPublicationYear());
        updatedBook.setAvailableCopies(book.getAvailableCopies());

        return bookRepository.save(updatedBook);
    }

    public void deleteBook(Long id) {
        bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Kitab tapılmadı, ID:"+id));
        bookRepository.deleteById(id);
    }

    public Book updateAvailableCopies(Long id, int availableCopies) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));
        book.setAvailableCopies(availableCopies);
         bookRepository.save(book);
         return book;
          // Əgər DTO işlədirsənsə
    }

}
