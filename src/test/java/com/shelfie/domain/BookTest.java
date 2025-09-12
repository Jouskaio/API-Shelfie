package com.shelfie.domain;

import com.shelfie.domain.model.Book;
import com.shelfie.domain.model.BookStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Book domain entity
 */
class BookTest {
    
    @Test
    void shouldCreateBookWithValidData() {
        // Given
        String title = "Clean Architecture";
        String author = "Robert C. Martin";
        String isbn = "9780134494166";
        String barcode = "123456789";
        
        // When
        Book book = new Book(title, author, isbn, barcode);
        
        // Then
        assertNotNull(book.getId());
        assertEquals(title, book.getTitle());
        assertEquals(author, book.getAuthor());
        assertEquals(isbn, book.getIsbn());
        assertEquals(barcode, book.getBarcode());
        assertEquals(BookStatus.AVAILABLE, book.getStatus());
        assertNotNull(book.getAddedAt());
        assertNotNull(book.getUpdatedAt());
    }
    
    @Test
    void shouldThrowExceptionWhenTitleIsEmpty() {
        // Given
        String title = "";
        String author = "Robert C. Martin";
        String isbn = "9780134494166";
        String barcode = "123456789";
        
        // When & Then
        assertThrows(IllegalArgumentException.class, 
            () -> new Book(title, author, isbn, barcode));
    }
    
    @Test
    void shouldThrowExceptionWhenAuthorIsEmpty() {
        // Given
        String title = "Clean Architecture";
        String author = "";
        String isbn = "9780134494166";
        String barcode = "123456789";
        
        // When & Then
        assertThrows(IllegalArgumentException.class, 
            () -> new Book(title, author, isbn, barcode));
    }
    
    @Test
    void shouldThrowExceptionWhenBarcodeIsEmpty() {
        // Given
        String title = "Clean Architecture";
        String author = "Robert C. Martin";
        String isbn = "9780134494166";
        String barcode = "";
        
        // When & Then
        assertThrows(IllegalArgumentException.class, 
            () -> new Book(title, author, isbn, barcode));
    }
    
    @Test
    void shouldUpdateBookDetails() {
        // Given
        Book book = new Book("Old Title", "Old Author", "9780134494166", "barcode");
        String newTitle = "New Title";
        String newAuthor = "New Author";
        String description = "A great book";
        String publisher = "Tech Books";
        Integer publishedYear = 2023;
        String genre = "Technology";
        
        // When
        book.updateDetails(newTitle, newAuthor, description, publisher, publishedYear, genre);
        
        // Then
        assertEquals(newTitle, book.getTitle());
        assertEquals(newAuthor, book.getAuthor());
        assertEquals(description, book.getDescription());
        assertEquals(publisher, book.getPublisher());
        assertEquals(publishedYear, book.getPublishedYear());
        assertEquals(genre, book.getGenre());
    }
    
    @Test
    void shouldChangeStatusToRead() {
        // Given
        Book book = new Book("Title", "Author", "9780134494166", "barcode");
        
        // When
        book.markAsRead();
        
        // Then
        assertEquals(BookStatus.READ, book.getStatus());
    }
    
    @Test
    void shouldChangeStatusToReading() {
        // Given
        Book book = new Book("Title", "Author", "9780134494166", "barcode2");
        
        // When
        book.markAsReading();
        
        // Then
        assertEquals(BookStatus.READING, book.getStatus());
    }
}