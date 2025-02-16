package com.example.library.service;

import com.example.library.entity.Book;
import com.example.library.entity.BookRental;
import com.example.library.entity.User;
import com.example.library.repository.BookRentalRepository;
import com.example.library.repository.BookRepository;
import com.example.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookRentalService {

    private final BookRentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Autowired
    public BookRentalService(BookRentalRepository rentalRepository,
                             UserRepository userRepository,
                             BookRepository bookRepository) {
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public BookRental rentBook(Long userId, Long bookId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Book> book = bookRepository.findById(bookId);

        if (user.isPresent() && book.isPresent()) {
            BookRental rental = new BookRental();
            rental.setUser(user.get());
            rental.setBook(book.get());
            rental.setRentalDate(LocalDate.now());
            return rentalRepository.save(rental);
        }
        return null;
    }

    public List<BookRental> getUserRentals(Long userId) {
        return rentalRepository.findBookByUserId(userId);
    }

    public void returnBook(Long rentalId) {
        rentalRepository.deleteById(rentalId);
    }
}
