package com.shelfie.domain.repository;

import com.shelfie.domain.model.Book;
import com.shelfie.domain.model.BookId;
import com.shelfie.domain.model.BookStatus;
import com.shelfie.domain.model.UserId;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Book aggregate
 * Part of the domain layer - defines the contract for book persistence
 */
public interface BookRepository {
    
    /**
     * Save a book to the repository
     * @param book the book to save
     * @return the saved book
     */
    Book save(Book book);
    
    /**
     * Find a book by its ID
     * @param id the book ID
     * @return the book if found
     */
    Optional<Book> findById(BookId id);
    
    /**
     * Find a book by its barcode
     * @param barcode the barcode to search for
     * @return the book if found
     */
    Optional<Book> findByBarcode(String barcode);
    
    /**
     * Find a book by its ISBN
     * @param isbn the ISBN to search for
     * @return the book if found
     */
    Optional<Book> findByIsbn(String isbn);
    
    /**
     * Find books by title (partial match, case-insensitive)
     * @param title the title to search for
     * @return list of matching books
     */
    List<Book> findByTitleContaining(String title);
    
    /**
     * Find books by author (partial match, case-insensitive)
     * @param author the author to search for
     * @return list of matching books
     */
    List<Book> findByAuthorContaining(String author);
    
    /**
     * Find books by status
     * @param status the status to filter by
     * @return list of books with the given status
     */
    List<Book> findByStatus(BookStatus status);
    
    /**
     * Find books by genre
     * @param genre the genre to filter by
     * @return list of books in the given genre
     */
    List<Book> findByGenre(String genre);
    
    /**
     * Find all books in the repository
     * @return list of all books
     */
    List<Book> findAll();
    
    /**
     * Check if a book exists with the given barcode
     * @param barcode the barcode to check
     * @return true if a book exists with this barcode
     */
    boolean existsByBarcode(String barcode);
    
    /**
     * Delete a book by its ID
     * @param id the book ID
     */
    void deleteById(BookId id);
    
    /**
     * Count total number of books
     * @return total count of books
     */
    long count();
    
    /**
     * Count books by status
     * @param status the status to count
     * @return count of books with the given status
     */
    long countByStatus(BookStatus status);
}