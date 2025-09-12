package com.shelfie.domain.service;

import com.shelfie.domain.model.Book;
import com.shelfie.domain.repository.BookRepository;

/**
 * Domain service for book-related business logic that doesn't naturally fit in entities
 */
public class BookDomainService {
    
    private final BookRepository bookRepository;
    
    public BookDomainService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    /**
     * Check if a book with the given barcode already exists
     * @param barcode the barcode to check
     * @return true if book exists
     */
    public boolean isBookAlreadyInLibrary(String barcode) {
        return bookRepository.existsByBarcode(barcode);
    }
    
    /**
     * Validate that a book can be added to the library
     * @param book the book to validate
     * @throws IllegalStateException if book already exists
     */
    public void validateBookForAddition(Book book) {
        if (isBookAlreadyInLibrary(book.getBarcode())) {
            throw new IllegalStateException("Book with barcode " + book.getBarcode() + " already exists in the library");
        }
    }
    
    /**
     * Generate recommendations based on similar books
     * @param book the reference book
     * @return recommendation message
     */
    public String generateRecommendations(Book book) {
        // This is a simplified implementation
        // In a real system, this might involve complex algorithms or external services
        return "Consider exploring more books by " + book.getAuthor() + 
               " or in the " + book.getGenre() + " genre.";
    }
}