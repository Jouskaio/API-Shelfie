package com.shelfie.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @JdbcTypeCode(SqlTypes.UUID)          // mappe le type UUID Postgres proprement
    private UUID id;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Lob
    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "avatar", columnDefinition = "bytea")
    private byte[] avatar;

    @Column(name = "avatar_mime", length = 100)
    private String avatarMime;

    @Column(name = "avatar_size")
    private Long avatarSize;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Requis par JPA
    public UserEntity() {}

    // Factory pratique pour créer une entité depuis le domaine
    public static UserEntity create(
            UUID id,
            String username,
            String email,
            String passwordHash,
            String firstName,
            String lastName,
            byte[] avatar,
            String avatarMime,
            Long avatarSize,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        UserEntity e = new UserEntity();
        e.id = id;
        e.username = username;
        e.email = email;
        e.passwordHash = passwordHash;
        e.firstName = firstName;
        e.lastName = lastName;
        e.avatar = avatar;
        e.avatarMime = avatarMime;
        e.avatarSize = avatarSize;
        e.createdAt = createdAt;
        e.updatedAt = updatedAt;
        return e;
    }

    // Getters / Setters (public)
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public byte[] getAvatar() { return avatar; }
    public void setAvatar(byte[] avatar) { this.avatar = avatar; }
    public String getAvatarMime() { return avatarMime; }
    public void setAvatarMime(String avatarMime) { this.avatarMime = avatarMime; }
    public Long getAvatarSize() { return avatarSize; }
    public void setAvatarSize(Long avatarSize) { this.avatarSize = avatarSize; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}