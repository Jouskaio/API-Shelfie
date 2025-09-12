package com.shelfie.presentation.controller;

import com.shelfie.application.dto.BookResponseDto;
import com.shelfie.application.dto.CreateBookRequestDto;
import com.shelfie.application.dto.UpdateBookRequestDto;
import com.shelfie.application.usecase.BookManagementUseCase;
import com.shelfie.domain.model.BookStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for book management operations
 */
@RestController
@RequestMapping("/api/v1/books")
@Tag(name = "Books", description = "Book management operations")
public class BookController {
    
    private final BookManagementUseCase bookManagementUseCase;
    
    public BookController(BookManagementUseCase bookManagementUseCase) {
        this.bookManagementUseCase = bookManagementUseCase;
    }
    
    @PostMapping
    @Operation(summary = "Add a new book to the library")
    public ResponseEntity<BookResponseDto> addBook(@Valid @RequestBody CreateBookRequestDto request) {
        BookResponseDto response = bookManagementUseCase.addBook(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get a book by ID")
    public ResponseEntity<BookResponseDto> getBook(
            @Parameter(description = "Book ID") @PathVariable String id) {
        return bookManagementUseCase.getBookById(id)
                .map(book -> ResponseEntity.ok(book))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/barcode/{barcode}")
    @Operation(summary = "Get a book by barcode")
    public ResponseEntity<BookResponseDto> getBookByBarcode(
            @Parameter(description = "Book barcode") @PathVariable String barcode) {
        return bookManagementUseCase.getBookByBarcode(barcode)
                .map(book -> ResponseEntity.ok(book))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping
    @Operation(summary = "Get all books or search books")
    public ResponseEntity<List<BookResponseDto>> getBooks(
            @Parameter(description = "Search by title") @RequestParam(required = false) String title,
            @Parameter(description = "Search by author") @RequestParam(required = false) String author,
            @Parameter(description = "Filter by status") @RequestParam(required = false) BookStatus status) {
        
        List<BookResponseDto> books;
        
        if (title != null && !title.trim().isEmpty()) {
            books = bookManagementUseCase.searchBooksByTitle(title);
        } else if (author != null && !author.trim().isEmpty()) {
            books = bookManagementUseCase.searchBooksByAuthor(author);
        } else if (status != null) {
            books = bookManagementUseCase.getBooksByStatus(status);
        } else {
            books = bookManagementUseCase.getAllBooks();
        }
        
        return ResponseEntity.ok(books);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update book details")
    public ResponseEntity<BookResponseDto> updateBook(
            @Parameter(description = "Book ID") @PathVariable String id,
            @Valid @RequestBody UpdateBookRequestDto request) {
        try {
            BookResponseDto response = bookManagementUseCase.updateBook(id, request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PatchMapping("/{id}/status")
    @Operation(summary = "Update book status")
    public ResponseEntity<BookResponseDto> updateBookStatus(
            @Parameter(description = "Book ID") @PathVariable String id,
            @Parameter(description = "New status") @RequestParam BookStatus status) {
        try {
            BookResponseDto response = bookManagementUseCase.updateBookStatus(id, status);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a book")
    public ResponseEntity<Void> deleteBook(
            @Parameter(description = "Book ID") @PathVariable String id) {
        try {
            bookManagementUseCase.deleteBook(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/stats")
    @Operation(summary = "Get library statistics")
    public ResponseEntity<BookManagementUseCase.LibraryStatsDto> getLibraryStats() {
        BookManagementUseCase.LibraryStatsDto stats = bookManagementUseCase.getLibraryStats();
        return ResponseEntity.ok(stats);
    }
}