package com.example.library.controller;


import com.example.library.entity.BookRental;
import com.example.library.service.BookRentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class BookRentalController {

    private final BookRentalService rentalService;

    @Autowired
    public BookRentalController(BookRentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping("/rent")
    public BookRental rentBook(@RequestParam Long userId, @RequestParam Long bookId) {
        return rentalService.rentBook(userId, bookId);
    }

    @GetMapping("/user/{userId}")
    public List<BookRental> getUserRentals(@PathVariable Long userId) {
        return rentalService.getUserRentals(userId);
    }

    @DeleteMapping("/return/{rentalId}")
    public void returnBook(@PathVariable Long rentalId) {
        rentalService.returnBook(rentalId);
    }
}
