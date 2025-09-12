package com.shelfie.application.mapper;

import com.shelfie.application.dto.BookResponseDto;
import com.shelfie.application.dto.CreateBookRequestDto;
import com.shelfie.domain.model.Book;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between Book entities and DTOs
 */
@Component
public class BookMapper {
    
    /**
     * Convert Book entity to BookResponseDto
     */
    public BookResponseDto toResponseDto(Book book) {
        if (book == null) {
            return null;
        }
        
        return new BookResponseDto(
            book.getId().getValue(),
            book.getTitle(),
            book.getAuthor(),
            book.getIsbn(),
            book.getBarcode(),
            book.getDescription(),
            book.getPublisher(),
            book.getPublishedYear(),
            book.getGenre(),
            book.getStatus(),
            book.getAddedAt(),
            book.getUpdatedAt()
        );
    }
    
    /**
     * Convert CreateBookRequestDto to Book entity
     */
    public Book toEntity(CreateBookRequestDto dto) {
        if (dto == null) {
            return null;
        }
        
        Book book = new Book(dto.getTitle(), dto.getAuthor(), dto.getIsbn(), dto.getBarcode());
        
        // Update additional details if provided
        if (hasAdditionalDetails(dto)) {
            book.updateDetails(
                dto.getTitle(),
                dto.getAuthor(),
                dto.getDescription(),
                dto.getPublisher(),
                dto.getPublishedYear(),
                dto.getGenre()
            );
        }
        
        return book;
    }
    
    private boolean hasAdditionalDetails(CreateBookRequestDto dto) {
        return dto.getDescription() != null || 
               dto.getPublisher() != null || 
               dto.getPublishedYear() != null || 
               dto.getGenre() != null;
    }
}