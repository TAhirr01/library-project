package com.example.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Kitabın adı boş ola bilməz")
    private String title;
    @NotBlank(message = "Müəllif adı boş ola bilməz")
    private String author;
    @NotNull
    private String isbn;
    @NotBlank(message = "Janr adı boş ola bilməz")
    private String genre;
    @Positive(message = "Buraxilis tarixi menfi ola bilmez")
    private Integer publicationYear;
    @Min(value = 1, message = "Kitab sayı ən azı 1 olmalıdır")
    private Integer availableCopies;
}
