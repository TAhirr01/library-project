package com.example.library.controller;


import com.example.library.dto.BookDTO;
import com.example.library.dto.BookRentalDTO;
import com.example.library.entity.Book;
import com.example.library.entity.BookRental;
import com.example.library.entity.User;
import com.example.library.mapper.BookMapper;
import com.example.library.mapper.BookRentalMapper;
import com.example.library.service.BookRentalService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Book Rental Controller", description = "Kitablar ve Istifadeci ile bagli olan API emeliyyatlati")
@RequestMapping("/api/rentals")
public class BookRentalController {

    private final BookRentalService rentalService;


    @GetMapping
    public ResponseEntity<List<BookRentalDTO>> getAllRentals() {
        return ResponseEntity.ok(rentalService.getAllRentals());
    }

    @PostMapping("/rent")
    public ResponseEntity<?> rentBook(@AuthenticationPrincipal User user, @RequestParam Long bookId) {
        try {
            return ResponseEntity.ok(rentalService.rentBook(user.getId(), bookId));
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

        @GetMapping("/my-books")
    public ResponseEntity<List<BookDTO>> getUserBooks(@AuthenticationPrincipal User user){
        List<BookDTO> books = rentalService.getBooksByUserId(user.getId());
        return ResponseEntity.ok(books);
    }
}
