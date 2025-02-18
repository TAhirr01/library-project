package com.example.library.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookRentalDTO {
    private Long id;
    private UserDTO user;
    private BookDTO book;
    private String rentalDate;
}
