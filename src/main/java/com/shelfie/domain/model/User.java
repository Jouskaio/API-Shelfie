package com.shelfie.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * User entity representing a library owner
 */
public class User {

    private static final int AVATAR_MAX_BYTES = 10 * 1024 * 1024; // 2 MB
    private static final Set<String> ALLOWED_MIMES =
            Set.of("image/png", "image/jpeg", "image/gif", "image/webp");

    private UserId id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // security
    private String passwordHash;

    // avatar
    private byte[] avatar;
    private String avatarMime;
    private Long avatarSize;

    private User() { }

    /** Factory for user creation (hash already done) */
    public static User create(String username,
                              String email,
                              String firstName,
                              String lastName,
                              String passwordHash) {
        if (passwordHash == null || passwordHash.isBlank()) {
            throw new IllegalArgumentException("Password hash required");
        }
        User u = new User();
        u.id = new UserId(UUID.randomUUID().toString());
        u.username = u.validateUsername(username);
        u.email = u.validateEmail(email);
        u.firstName = u.validateName(firstName, "First name");
        u.lastName = u.validateName(lastName, "Last name");
        u.passwordHash = passwordHash;
        u.createdAt = LocalDateTime.now();
        u.updatedAt = u.createdAt;
        return u;
    }

    /** Reconstruction from persistence. */
    public static User reconstruct(
            UserId id,
            String username,
            String email,
            String firstName,
            String lastName,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            String passwordHash,
            byte[] avatar,
            String avatarMime,
            Long avatarSize
    ) {
        User u = new User();
        u.id = Objects.requireNonNull(id);
        u.username = username;
        u.email = email;
        u.firstName = firstName;
        u.lastName = lastName;
        u.createdAt = createdAt;
        u.updatedAt = updatedAt;
        u.passwordHash = passwordHash;
        u.avatar = avatar;
        u.avatarMime = avatarMime;
        u.avatarSize = avatarSize;
        return u;
    }

    // ----------- Setters ---------------

    public void updateProfile(String firstName, String lastName) {
        this.firstName = validateName(firstName, "First name");
        this.lastName = validateName(lastName, "Last name");
        touch();
    }

    public void changeEmail(String newEmail) {
        this.email = validateEmail(newEmail);
        touch();
    }

    public void changePasswordHash(String newPasswordHash) {
        if (newPasswordHash == null || newPasswordHash.isBlank()) {
            throw new IllegalArgumentException("Password hash cannot be empty");
        }
        this.passwordHash = newPasswordHash;
        touch();
    }

    /** Replace avatar with validation */
    public void setAvatar(byte[] data, String mime) {
        if (data == null || data.length == 0) {
            throw new IllegalArgumentException("Avatar is empty");
        }
        if (data.length > AVATAR_MAX_BYTES) {
            throw new IllegalArgumentException("Avatar too large (max " + AVATAR_MAX_BYTES + " bytes)");
        }
        if (mime == null || !ALLOWED_MIMES.contains(mime)) {
            throw new IllegalArgumentException("Unsupported avatar mime type");
        }
        this.avatar = data.clone();
        this.avatarMime = mime;
        this.avatarSize = (long) data.length;
        touch();
    }

    public void clearAvatar() {
        this.avatar = null;
        this.avatarMime = null;
        this.avatarSize = null;
        touch();
    }

    public String getFullName() { return firstName + " " + lastName; }

    private void touch() { this.updatedAt = LocalDateTime.now(); }

    // ------------------- Validation -------------------

    private String validateUsername(String username) {
        if (username == null || username.trim().isEmpty())
            throw new IllegalArgumentException("Username cannot be empty");
        if (username.length() < 3 || username.length() > 50)
            throw new IllegalArgumentException("Username must be between 3 and 50 characters");
        if (!username.matches("^[a-zA-Z0-9_]+$"))
            throw new IllegalArgumentException("Username can only contain letters, numbers, and underscores");
        return username.trim();
    }

    private String validateEmail(String email) {
        if (email == null || email.trim().isEmpty())
            throw new IllegalArgumentException("Email cannot be empty");
        if (!email.matches("^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$"))
            throw new IllegalArgumentException("Invalid email format");
        return email.trim().toLowerCase();
    }

    private String validateName(String name, String fieldName) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException(fieldName + " cannot be empty");
        if (name.length() > 100)
            throw new IllegalArgumentException(fieldName + " cannot exceed 100 characters");
        return name.trim();
    }

    // ------------------- Getters -------------------

    public UserId getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public String getPasswordHash() { return passwordHash; } // hash uniquement
    public byte[] getAvatar() { return avatar == null ? null : avatar.clone(); }
    public String getAvatarMime() { return avatarMime; }
    public Long getAvatarSize() { return avatarSize; }

    // equals/hashCode/toString

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        return Objects.equals(id, ((User) o).id);
    }
    @Override public int hashCode() { return Objects.hash(id); }

    @Override public String toString() {
        return "User{id=" + id + ", username='" + username + "', email='" + email + "'}";
    }
}