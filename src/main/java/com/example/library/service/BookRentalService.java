package com.example.library.service;

import com.example.library.dto.BookDTO;
import com.example.library.dto.BookRentalDTO;
import com.example.library.entity.Book;
import com.example.library.entity.BookRental;
import com.example.library.entity.User;
import com.example.library.exception.ResourceNotFoundException;
import com.example.library.mapper.BookMapper;
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
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("İstifadəçi tapılmadı"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Kitab tapılmadı"));

        // İstifadəçi artıq kitabı götürübsə, icazə verməyək
        if (rentalRepository.existsByUserAndBook(user, book)) {
            throw new IllegalStateException("İstifadəçi bu kitabı artıq icarəyə götürüb!");
        }

        if (book.getAvailableCopies() <= 0) {
            throw new IllegalStateException("Bu kitabdan artıq mövcud deyil!");
        }

        // Kitabı icarəyə götürək
        BookRental bookRental = new BookRental();
        bookRental.setUser(user);
        bookRental.setBook(book);
        bookRental.setRentalDate(LocalDate.now());

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        BookRental savedRental = rentalRepository.save(bookRental);
        return BookRentalMapper.toBookRentalDTO(savedRental);
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

    public List<BookDTO> getBooksByUserId(Long id) {
        List<Object[]> results = rentalRepository.findBooksByUserid(id);
        return results.stream().map(obj ->
                new BookDTO(
                        ((Number) obj[0]).longValue(),  // ID
                        (String) obj[1],  // Title
                        (String) obj[2],  // Author
                        (String) obj[3]   // Genre
                )
        ).collect(Collectors.toList());

    }
}
