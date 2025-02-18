package com.example.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Ad boş ola bilməz")
    private String name;

    @Email(message = "Düzgün email daxil edin")
    @NotBlank(message = "Email boş ola bilməz")
    private String email;

    @OneToMany(mappedBy = "user")
    private List<BookRental> rentals; // İcarəyə götürülən kitablar

}
