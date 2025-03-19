package com.example.library.controller;

import com.example.library.dto.BookDTO;
import com.example.library.entity.Book;
import com.example.library.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
@Tag(name = "Book Controller", description = "Kitablar üçün API əməliyyatları")
public class BookController {

    private final BookService bookService;

    @Operation(summary = "Bütün kitabları əldə et", description = "Bütün kitab siyahısını qaytarır")
    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }


    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @Operation(summary = "Yeni kitab əlavə et", description = "Kitab əlavə etmək üçün endpoint")
    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        return ResponseEntity.ok(bookService.addBook(book));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@Valid @RequestBody Book book,@PathVariable Long id){
        return ResponseEntity.ok(bookService.updateBook(book,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/update-copies")
    public ResponseEntity<Book> updateAvailableCopies(
            @PathVariable Long id,
            @RequestBody Map<String, Integer> updateData) {
        int availableCopies = updateData.get("availableCopies");
        Book updatedBook = bookService.updateAvailableCopies(id, availableCopies);
        return ResponseEntity.ok(updatedBook);
    }
}