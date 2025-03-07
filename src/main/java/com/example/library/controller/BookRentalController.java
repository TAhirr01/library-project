package com.example.library.controller;


import com.example.library.dto.BookRentalDTO;
import com.example.library.entity.BookRental;
import com.example.library.mapper.BookRentalMapper;
import com.example.library.service.BookRentalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class BookRentalController {

    private final BookRentalService rentalService;

    public BookRentalController(BookRentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    public ResponseEntity<List<BookRentalDTO>> getAllRentals() {
        return ResponseEntity.ok(rentalService.getAllRentals());
    }

    @PostMapping("/rent")
    public ResponseEntity<?> rentBook(@RequestParam Long userId, @RequestParam Long bookId) {
        try {
            return ResponseEntity.ok(rentalService.rentBook(userId, bookId));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookRentalDTO> getRentalById(@PathVariable Long id) {
        return ResponseEntity.ok(rentalService.getRentalById(id));
    }


    @DeleteMapping("/return/{rentalId}")
    public ResponseEntity<String> returnBook(@PathVariable Long rentalId) {
        return ResponseEntity.ok(rentalService.returnBook(rentalId));
    }
}
