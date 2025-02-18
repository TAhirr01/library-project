package com.example.library.repository;

import com.example.library.entity.Book;
import com.example.library.entity.BookRental;
import com.example.library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BookRentalRepository extends JpaRepository<BookRental,Long> {
    List<BookRental> findBookByUserId(Long id);
    boolean existsByUserAndBook(User user, Book book);
}
