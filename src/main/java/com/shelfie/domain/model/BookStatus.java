package com.shelfie.domain.model;

/**
 * Enumeration representing the status of a book in the library
 */
public enum BookStatus {
    AVAILABLE("Available to read"),
    READING("Currently reading"),
    READ("Already read"),
    WISHLIST("Want to read");
    
    private final String description;
    
    BookStatus(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}