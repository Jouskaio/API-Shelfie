package com.shelfie.domain.model;

import java.util.Objects;

/**
 * Value object representing a Book identifier
 */
public class BookId {
    
    private final String value;
    
    public BookId(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("BookId cannot be null or empty");
        }
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookId bookId = (BookId) o;
        return Objects.equals(value, bookId.value);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
    
    @Override
    public String toString() {
        return "BookId{" + value + '}';
    }
}