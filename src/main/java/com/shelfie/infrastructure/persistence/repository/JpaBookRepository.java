package com.shelfie.infrastructure.persistence.repository;

import com.shelfie.domain.model.BookStatus;
import com.shelfie.infrastructure.persistence.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for BookEntity
 */
@Repository
public interface JpaBookRepository extends JpaRepository<BookEntity, String> {
    
    Optional<BookEntity> findByBarcode(String barcode);
    
    Optional<BookEntity> findByIsbn(String isbn);
    
    @Query("SELECT b FROM BookEntity b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<BookEntity> findByTitleContainingIgnoreCase(@Param("title") String title);
    
    @Query("SELECT b FROM BookEntity b WHERE LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%'))")
    List<BookEntity> findByAuthorContainingIgnoreCase(@Param("author") String author);
    
    List<BookEntity> findByStatus(BookStatus status);
    
    List<BookEntity> findByGenre(String genre);
    
    boolean existsByBarcode(String barcode);
    
    long countByStatus(BookStatus status);
}