package com.shelfie.application.usecase;

import com.shelfie.application.dto.BookResponseDto;
import com.shelfie.application.dto.CreateBookRequestDto;
import com.shelfie.application.dto.UpdateBookRequestDto;
import com.shelfie.application.mapper.BookMapper;
import com.shelfie.domain.model.Book;
import com.shelfie.domain.model.BookId;
import com.shelfie.domain.model.BookStatus;
import com.shelfie.domain.repository.BookRepository;
import com.shelfie.domain.service.BookDomainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Use case for managing books in the library
 * Part of the application layer
 */
@Service
@Transactional
public class BookManagementUseCase {
    
    private final BookRepository bookRepository;
    private final BookDomainService bookDomainService;
    private final BookMapper bookMapper;
    
    public BookManagementUseCase(BookRepository bookRepository, 
                                BookDomainService bookDomainService,
                                BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookDomainService = bookDomainService;
        this.bookMapper = bookMapper;
    }
    
    /**
     * Add a new book to the library
     */
    public BookResponseDto addBook(CreateBookRequestDto request) {
        Book book = bookMapper.toEntity(request);
        
        // Validate using domain service
        bookDomainService.validateBookForAddition(book);
        
        Book savedBook = bookRepository.save(book);
        return bookMapper.toResponseDto(savedBook);
    }
    
    /**
     * Get a book by its ID
     */
    @Transactional(readOnly = true)
    public Optional<BookResponseDto> getBookById(String id) {
        return bookRepository.findById(new BookId(id))
                .map(bookMapper::toResponseDto);
    }
    
    /**
     * Get a book by its barcode
     */
    @Transactional(readOnly = true)
    public Optional<BookResponseDto> getBookByBarcode(String barcode) {
        return bookRepository.findByBarcode(barcode)
                .map(bookMapper::toResponseDto);
    }
    
    /**
     * Search books by title
     */
    @Transactional(readOnly = true)
    public List<BookResponseDto> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContaining(title).stream()
                .map(bookMapper::toResponseDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Search books by author
     */
    @Transactional(readOnly = true)
    public List<BookResponseDto> searchBooksByAuthor(String author) {
        return bookRepository.findByAuthorContaining(author).stream()
                .map(bookMapper::toResponseDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Get books by status
     */
    @Transactional(readOnly = true)
    public List<BookResponseDto> getBooksByStatus(BookStatus status) {
        return bookRepository.findByStatus(status).stream()
                .map(bookMapper::toResponseDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Get all books
     */
    @Transactional(readOnly = true)
    public List<BookResponseDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toResponseDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Update book details
     */
    public BookResponseDto updateBook(String id, UpdateBookRequestDto request) {
        Book book = bookRepository.findById(new BookId(id))
                .orElseThrow(() -> new IllegalArgumentException("Book not found with id: " + id));
        
        book.updateDetails(
            request.getTitle() != null ? request.getTitle() : book.getTitle(),
            request.getAuthor() != null ? request.getAuthor() : book.getAuthor(),
            request.getDescription(),
            request.getPublisher(),
            request.getPublishedYear(),
            request.getGenre()
        );
        
        Book savedBook = bookRepository.save(book);
        return bookMapper.toResponseDto(savedBook);
    }
    
    /**
     * Update book status
     */
    public BookResponseDto updateBookStatus(String id, BookStatus status) {
        Book book = bookRepository.findById(new BookId(id))
                .orElseThrow(() -> new IllegalArgumentException("Book not found with id: " + id));
        
        switch (status) {
            case AVAILABLE -> book.markAsAvailable();
            case READING -> book.markAsReading();
            case READ -> book.markAsRead();
            default -> throw new IllegalArgumentException("Invalid status: " + status);
        }
        
        Book savedBook = bookRepository.save(book);
        return bookMapper.toResponseDto(savedBook);
    }
    
    /**
     * Delete a book
     */
    public void deleteBook(String id) {
        BookId bookId = new BookId(id);
        if (!bookRepository.findById(bookId).isPresent()) {
            throw new IllegalArgumentException("Book not found with id: " + id);
        }
        bookRepository.deleteById(bookId);
    }
    
    /**
     * Get library statistics
     */
    @Transactional(readOnly = true)
    public LibraryStatsDto getLibraryStats() {
        long totalBooks = bookRepository.count();
        long availableBooks = bookRepository.countByStatus(BookStatus.AVAILABLE);
        long readingBooks = bookRepository.countByStatus(BookStatus.READING);
        long readBooks = bookRepository.countByStatus(BookStatus.READ);
        
        return new LibraryStatsDto(totalBooks, availableBooks, readingBooks, readBooks);
    }
    
    /**
     * DTO for library statistics
     */
    public static class LibraryStatsDto {
        private final long totalBooks;
        private final long availableBooks;
        private final long readingBooks;
        private final long readBooks;
        
        public LibraryStatsDto(long totalBooks, long availableBooks, long readingBooks, long readBooks) {
            this.totalBooks = totalBooks;
            this.availableBooks = availableBooks;
            this.readingBooks = readingBooks;
            this.readBooks = readBooks;
        }
        
        public long getTotalBooks() { return totalBooks; }
        public long getAvailableBooks() { return availableBooks; }
        public long getReadingBooks() { return readingBooks; }
        public long getReadBooks() { return readBooks; }
    }
}