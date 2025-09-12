package com.shelfie.domain.repository;

import com.shelfie.domain.model.User;
import com.shelfie.domain.model.UserId;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for User aggregate
 * Part of the domain layer - defines the contract for user persistence
 */
public interface UserRepository {
    
    /**
     * Save a user to the repository
     * @param user the user to save
     * @return the saved user
     */
    User save(User user);
    
    /**
     * Find a user by their ID
     * @param id the user ID
     * @return the user if found
     */
    Optional<User> findById(UserId id);
    
    /**
     * Find a user by their username
     * @param username the username to search for
     * @return the user if found
     */
    Optional<User> findByUsername(String username);
    
    /**
     * Find a user by their email
     * @param email the email to search for
     * @return the user if found
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Find all users in the repository
     * @return list of all users
     */
    List<User> findAll();
    
    /**
     * Check if a user exists with the given username
     * @param username the username to check
     * @return true if a user exists with this username
     */
    boolean existsByUsername(String username);
    
    /**
     * Check if a user exists with the given email
     * @param email the email to check
     * @return true if a user exists with this email
     */
    boolean existsByEmail(String email);
    
    /**
     * Delete a user by their ID
     * @param id the user ID
     */
    void deleteById(UserId id);
    
    /**
     * Count total number of users
     * @return total count of users
     */
    long count();
}