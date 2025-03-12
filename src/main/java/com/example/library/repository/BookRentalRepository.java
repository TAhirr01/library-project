package com.example.library.repository;

import com.example.library.entity.Book;
import com.example.library.entity.BookRental;
import com.example.library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BookRentalRepository extends JpaRepository<BookRental,Long> {
    @Query(value = "SELECT b.id, b.title, b.author, b.genre FROM book b " +
            "JOIN book_rental r ON b.id = r.book_id " +
            "WHERE r.user_id = :id "+
            " ORDER BY rental_date asc",
            nativeQuery = true)
    List<Object[]> findBooksByUserid(@Param("id") Long id);//Burada Obeject massivi qaytarmaqimizin sebebi native quarydi normalda hibernate ozu goturduyumuz melumatlari qaytaracaqimiz entitynin fildlerini menimsedir burda amma custom quary olduguna gore hibernate bunu ozu ede bilmir biz ozumuz BookDTO objectine map eliyirik
    boolean existsByUserAndBook(User user, Book book);
}
