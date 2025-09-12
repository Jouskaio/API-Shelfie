package com.shelfie.infrastructure.config;

import com.shelfie.domain.repository.BookRepository;
import com.shelfie.domain.service.BookDomainService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for domain services
 */
@Configuration
public class DomainConfig {
    
    @Bean
    public BookDomainService bookDomainService(BookRepository bookRepository) {
        return new BookDomainService(bookRepository);
    }
}