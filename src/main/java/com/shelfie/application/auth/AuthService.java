package com.shelfie.application.auth;

import com.shelfie.application.dto.*;
import com.shelfie.domain.model.User;
import com.shelfie.domain.repository.UserRepository;
import com.shelfie.infrastructure.persistence.entity.UserEntity;
import com.shelfie.infrastructure.persistence.repository.JpaUserRepository;
import com.shelfie.infrastructure.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {
    private final UserRepository users;
    private final JpaUserRepository jpaUsers;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwt;

    public AuthService(UserRepository users, JpaUserRepository jpaUsers,
                       PasswordEncoder passwordEncoder, JwtService jwt) {
        this.users = users;
        this.jpaUsers = jpaUsers;
        this.passwordEncoder = passwordEncoder;
        this.jwt = jwt;
    }

    public AuthResponseDto register(RegisterRequestDto req, MultipartFile avatarOpt) {
        if (users.existsByUsername(req.username())) throw new IllegalArgumentException("Username already taken");
        if (users.existsByEmail(req.email()))     throw new IllegalArgumentException("Email already taken");

        var domain = User.create(
                req.username(), req.email(), req.firstName(), req.lastName(),
                passwordEncoder.encode(req.password())
        );

        var id = UUID.fromString(domain.getId().getValue());
        var entity = new UserEntity();
        entity.setId(id);
        entity.setUsername(domain.getUsername());
        entity.setEmail(domain.getEmail());
        entity.setFirstName(domain.getFirstName());
        entity.setLastName(domain.getLastName());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setPasswordHash(domain.getPasswordHash());

        if (avatarOpt != null && !avatarOpt.isEmpty()) {
            try {
                domain.setAvatar(avatarOpt.getBytes(), avatarOpt.getContentType());

                entity.setAvatar(domain.getAvatar());
                entity.setAvatarMime(domain.getAvatarMime());
                entity.setAvatarSize(domain.getAvatarSize());
            } catch (IllegalArgumentException iae) {
                throw iae;
            } catch (Exception e) {
                throw new RuntimeException("Error while reading avatar", e); // 500 avec message explicite
            }
        }

        jpaUsers.save(entity);

        var access  = jwt.generateAccessToken(id.toString(), domain.getUsername());
        var refresh = jwt.generateRefreshToken(id.toString(), domain.getUsername());
        return new AuthResponseDto(access, refresh);
    }

    public AuthResponseDto login(LoginRequestDto req) {
        var ent = jpaUsers.findByUsername(req.usernameOrEmail())
                .or(() -> jpaUsers.findByEmail(req.usernameOrEmail()))
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or email"));

        if (!passwordEncoder.matches(req.password(), ent.getPasswordHash()))
            throw new IllegalArgumentException("Invalid password");

        var access  = jwt.generateAccessToken(ent.getId().toString(), ent.getUsername());
        var refresh = jwt.generateRefreshToken(ent.getId().toString(), ent.getUsername());
        return new AuthResponseDto(access, refresh);
    }
}