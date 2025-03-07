package com.example.library.mapper;

import com.example.library.dto.BookDTO;
import com.example.library.dto.BookRentalDTO;
import com.example.library.entity.Book;
import com.example.library.entity.BookRental;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookRentalMapper {


    public static BookDTO toBookDTO(Book book) {
        if (book == null) return null;
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setGenre(book.getGenre());
        return dto;
    }

    public static BookRentalDTO toBookRentalDTO(BookRental bookRental) {
        if (bookRental == null) return null;
        BookRentalDTO dto = new BookRentalDTO();
        dto.setId(bookRental.getId());
        dto.setUser(UserMapper.toUserDTO(bookRental.getUser()));
        dto.setBook(toBookDTO(bookRental.getBook()));
        dto.setRentalDate(bookRental.getRentalDate().toString());
        return dto;
    }
}
