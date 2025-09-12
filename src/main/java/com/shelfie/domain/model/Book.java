package com.shelfie.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Book entity representing a physical book in the digital library
 * Core domain entity following DDD principles
 */
public class Book {
    
    private BookId id;
    private String title;
    private String author;
    private String isbn;
    private String barcode;
    private String description;
    private String publisher;
    private Integer publishedYear;
    private String genre;
    private BookStatus status;
    private LocalDateTime addedAt;
    private LocalDateTime updatedAt;
    
    // Private constructor for framework use
    private Book() {}
    
    // Constructor for creating new books
    public Book(String title, String author, String isbn, String barcode) {
        this.id = new BookId(UUID.randomUUID().toString());
        this.title = validateTitle(title);
        this.author = validateAuthor(author);
        this.isbn = validateIsbn(isbn);
        this.barcode = validateBarcode(barcode);
        this.status = BookStatus.AVAILABLE;
        this.addedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    // Factory method for reconstruction from persistence
    public static Book reconstruct(BookId id, String title, String author, String isbn, 
                                 String barcode, String description, String publisher, 
                                 Integer publishedYear, String genre, BookStatus status,
                                 LocalDateTime addedAt, LocalDateTime updatedAt) {
        Book book = new Book();
        book.id = id;
        book.title = title;
        book.author = author;
        book.isbn = isbn;
        book.barcode = barcode;
        book.description = description;
        book.publisher = publisher;
        book.publishedYear = publishedYear;
        book.genre = genre;
        book.status = status;
        book.addedAt = addedAt;
        book.updatedAt = updatedAt;
        return book;
    }
    
    // Business methods
    public void updateDetails(String title, String author, String description, 
                            String publisher, Integer publishedYear, String genre) {
        this.title = validateTitle(title);
        this.author = validateAuthor(author);
        this.description = description;
        this.publisher = publisher;
        this.publishedYear = publishedYear;
        this.genre = genre;
        this.updatedAt = LocalDateTime.now();
    }
    
    public void markAsRead() {
        this.status = BookStatus.READ;
        this.updatedAt = LocalDateTime.now();
    }
    
    public void markAsReading() {
        this.status = BookStatus.READING;
        this.updatedAt = LocalDateTime.now();
    }
    
    public void markAsAvailable() {
        this.status = BookStatus.AVAILABLE;
        this.updatedAt = LocalDateTime.now();
    }
    
    // Validation methods
    private String validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be empty");
        }
        if (title.length() > 500) {
            throw new IllegalArgumentException("Book title cannot exceed 500 characters");
        }
        return title.trim();
    }
    
    private String validateAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Book author cannot be empty");
        }
        if (author.length() > 200) {
            throw new IllegalArgumentException("Book author cannot exceed 200 characters");
        }
        return author.trim();
    }
    
    private String validateIsbn(String isbn) {
        if (isbn != null && !isbn.trim().isEmpty()) {
            String cleanIsbn = isbn.replaceAll("[^0-9X]", "");
            if (cleanIsbn.length() != 10 && cleanIsbn.length() != 13) {
                throw new IllegalArgumentException("ISBN must be 10 or 13 digits");
            }
        }
        return isbn;
    }
    
    private String validateBarcode(String barcode) {
        if (barcode == null || barcode.trim().isEmpty()) {
            throw new IllegalArgumentException("Book barcode cannot be empty");
        }
        return barcode.trim();
    }
    
    // Getters
    public BookId getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public String getBarcode() { return barcode; }
    public String getDescription() { return description; }
    public String getPublisher() { return publisher; }
    public Integer getPublishedYear() { return publishedYear; }
    public String getGenre() { return genre; }
    public BookStatus getStatus() { return status; }
    public LocalDateTime getAddedAt() { return addedAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", status=" + status +
                '}';
    }
}