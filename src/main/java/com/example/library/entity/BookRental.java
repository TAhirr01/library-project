package com.example.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class BookRental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "İstifadəçi seçilməlidir")
    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonIgnore
    private User user;
    @NotNull(message = "Kitab seçilməlidir")
    @ManyToOne
    @JoinColumn(name = "book_id")
    @JsonIgnore
    private Book book;
    @PastOrPresent(message = "İcarə tarixi keçmişdə və ya indiki vaxtda olmalıdır")
    private LocalDate rentalDate;
    @FutureOrPresent(message = "İcarə tarixi gelecek və ya indiki vaxtda olmalıdır")
    private LocalDate returnDate;
}
