package com.example.library.mapper;

import com.example.library.dto.BookDTO;
import com.example.library.entity.Book;

import java.util.ArrayList;
import java.util.List;

public class BookMapper {
    public static BookDTO toBookDTO(Book book) {
        if (book == null) return null;
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setGenre(book.getGenre());
        return dto;
    }

    public static List<BookDTO> toBookDTOList(List<Book> books){
        if (books==null) return null;
        List<BookDTO> bookDTOS=new ArrayList<>();
        BookDTO dto = new BookDTO();
        for (Book book:books){
            dto.setId(book.getId());
            dto.setTitle(book.getTitle());
            dto.setAuthor(book.getAuthor());
            dto.setGenre(book.getGenre());
            bookDTOS.add(dto);
        }
        return bookDTOS;
    }


}
