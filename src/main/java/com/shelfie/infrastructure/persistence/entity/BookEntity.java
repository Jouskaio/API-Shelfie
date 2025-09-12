package com.shelfie.infrastructure.persistence.entity;

import com.shelfie.domain.model.BookStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * JPA Entity for Book persistence
 */
@Entity
@Table(name = "books")
public class BookEntity {
    
    @Id
    private String id;
    
    @Column(nullable = false, length = 500)
    private String title;
    
    @Column(nullable = false, length = 200)
    private String author;
    
    @Column(length = 20)
    private String isbn;
    
    @Column(nullable = false, unique = true, length = 50)
    private String barcode;
    
    @Column(length = 2000)
    private String description;
    
    @Column(length = 200)
    private String publisher;
    
    @Column(name = "published_year")
    private Integer publishedYear;
    
    @Column(length = 100)
    private String genre;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookStatus status;
    
    @Column(name = "added_at", nullable = false)
    private LocalDateTime addedAt;
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    // Default constructor for JPA
    protected BookEntity() {}
    
    // Constructor
    public BookEntity(String id, String title, String author, String isbn, String barcode,
                     String description, String publisher, Integer publishedYear, String genre,
                     BookStatus status, LocalDateTime addedAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.barcode = barcode;
        this.description = description;
        this.publisher = publisher;
        this.publishedYear = publishedYear;
        this.genre = genre;
        this.status = status;
        this.addedAt = addedAt;
        this.updatedAt = updatedAt;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    
    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    
    public Integer getPublishedYear() { return publishedYear; }
    public void setPublishedYear(Integer publishedYear) { this.publishedYear = publishedYear; }
    
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    
    public BookStatus getStatus() { return status; }
    public void setStatus(BookStatus status) { this.status = status; }
    
    public LocalDateTime getAddedAt() { return addedAt; }
    public void setAddedAt(LocalDateTime addedAt) { this.addedAt = addedAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}