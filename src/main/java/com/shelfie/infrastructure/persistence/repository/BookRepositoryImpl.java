package com.shelfie.infrastructure.persistence.repository;

import com.shelfie.domain.model.Book;
import com.shelfie.domain.model.BookId;
import com.shelfie.domain.model.BookStatus;
import com.shelfie.domain.repository.BookRepository;
import com.shelfie.infrastructure.persistence.entity.BookEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of BookRepository using Spring Data JPA
 */
@Repository
public class BookRepositoryImpl implements BookRepository {
    
    private final JpaBookRepository jpaBookRepository;
    
    public BookRepositoryImpl(JpaBookRepository jpaBookRepository) {
        this.jpaBookRepository = jpaBookRepository;
    }
    
    @Override
    public Book save(Book book) {
        BookEntity entity = toEntity(book);
        BookEntity savedEntity = jpaBookRepository.save(entity);
        return toDomain(savedEntity);
    }
    
    @Override
    public Optional<Book> findById(BookId id) {
        return jpaBookRepository.findById(id.getValue())
                .map(this::toDomain);
    }
    
    @Override
    public Optional<Book> findByBarcode(String barcode) {
        return jpaBookRepository.findByBarcode(barcode)
                .map(this::toDomain);
    }
    
    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return jpaBookRepository.findByIsbn(isbn)
                .map(this::toDomain);
    }
    
    @Override
    public List<Book> findByTitleContaining(String title) {
        return jpaBookRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Book> findByAuthorContaining(String author) {
        return jpaBookRepository.findByAuthorContainingIgnoreCase(author).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Book> findByStatus(BookStatus status) {
        return jpaBookRepository.findByStatus(status).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Book> findByGenre(String genre) {
        return jpaBookRepository.findByGenre(genre).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Book> findAll() {
        return jpaBookRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public boolean existsByBarcode(String barcode) {
        return jpaBookRepository.existsByBarcode(barcode);
    }
    
    @Override
    public void deleteById(BookId id) {
        jpaBookRepository.deleteById(id.getValue());
    }
    
    @Override
    public long count() {
        return jpaBookRepository.count();
    }
    
    @Override
    public long countByStatus(BookStatus status) {
        return jpaBookRepository.countByStatus(status);
    }
    
    // Mapping methods
    private BookEntity toEntity(Book book) {
        return new BookEntity(
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
    
    private Book toDomain(BookEntity entity) {
        return Book.reconstruct(
            new BookId(entity.getId()),
            entity.getTitle(),
            entity.getAuthor(),
            entity.getIsbn(),
            entity.getBarcode(),
            entity.getDescription(),
            entity.getPublisher(),
            entity.getPublishedYear(),
            entity.getGenre(),
            entity.getStatus(),
            entity.getAddedAt(),
            entity.getUpdatedAt()
        );
    }
}