package com.shelfie.application.dto;

import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for updating book details
 */
public class UpdateBookRequestDto {
    
    @Size(max = 500, message = "Title cannot exceed 500 characters")
    private String title;
    
    @Size(max = 200, message = "Author cannot exceed 200 characters")
    private String author;
    
    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;
    
    @Size(max = 200, message = "Publisher cannot exceed 200 characters")
    private String publisher;
    
    private Integer publishedYear;
    
    @Size(max = 100, message = "Genre cannot exceed 100 characters")
    private String genre;
    
    // Default constructor
    public UpdateBookRequestDto() {}
    
    // Constructor
    public UpdateBookRequestDto(String title, String author, String description, 
                               String publisher, Integer publishedYear, String genre) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.publisher = publisher;
        this.publishedYear = publishedYear;
        this.genre = genre;
    }
    
    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    
    public Integer getPublishedYear() { return publishedYear; }
    public void setPublishedYear(Integer publishedYear) { this.publishedYear = publishedYear; }
    
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
}