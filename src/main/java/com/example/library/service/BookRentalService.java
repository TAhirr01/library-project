package com.example.library.service;

import com.example.library.dto.BookRentalDTO;
import com.example.library.entity.Book;
import com.example.library.entity.BookRental;
import com.example.library.entity.User;
import com.example.library.mapper.BookRentalMapper;
import com.example.library.repository.BookRentalRepository;
import com.example.library.repository.BookRepository;
import com.example.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookRentalService {

    private final BookRentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public BookRentalService(BookRentalRepository rentalRepository,
                             UserRepository userRepository,
                             BookRepository bookRepository) {
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public List<BookRentalDTO> getAllRentals() {
        List<BookRental> rentals = rentalRepository.findAll();
        return rentals.stream()
                .map(BookRentalMapper::toBookRentalDTO)
                .collect(Collectors.toList());
    }

    public BookRentalDTO rentBook(Long userId, Long bookId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("İstifadəçi tapılmadı"));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Kitab tapılmadı"));

        // **Eyni kitabı iki dəfə icarəyə götürməyə icazə vermirik**
        boolean alreadyRented = rentalRepository.existsByUserAndBook(user, book);
        if (alreadyRented) {
            throw new RuntimeException("Bu kitab artıq icarəyə götürülüb!");
        }

        BookRental bookRental = new BookRental();
        bookRental.setUser(user);
        bookRental.setBook(book);
        bookRental.setRentalDate(LocalDate.now());

        rentalRepository.save(bookRental);
        return BookRentalMapper.toBookRentalDTO(bookRental);
    }

    public BookRentalDTO getRentalById(Long id) {
        Optional<BookRental> rental = rentalRepository.findById(id);
        return rental.map(BookRentalMapper::toBookRentalDTO).orElse(null);
    }

    public String returnBook(Long rentalId) {
        Optional<BookRental> rentalOpt = rentalRepository.findById(rentalId);
        if (rentalOpt.isPresent()) {
            rentalRepository.deleteById(rentalId);
            return "Kitab uğurla qaytarıldı.";
        } else {
            throw new RuntimeException("İcarə tapılmadı!");
        }
    }

}
