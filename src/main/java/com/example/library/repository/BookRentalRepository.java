package com.example.library.repository;

import com.example.library.entity.BookRental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BookRentalRepository extends JpaRepository<BookRental,Long> {
    List<BookRental> findBookByUserId(Long id);
}
