package com.shelfie.infrastructure.persistence.repository;

import com.shelfie.domain.model.User;
import com.shelfie.domain.model.UserId;
import com.shelfie.domain.repository.UserRepository;
import com.shelfie.infrastructure.persistence.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpa;

    public UserRepositoryImpl(JpaUserRepository jpa) { this.jpa = jpa; }

    private static UserEntity toEntity(User u) {
        var e = new UserEntity();
        e.setId(UUID.fromString(u.getId().getValue()));
        e.setUsername(u.getUsername());
        e.setEmail(u.getEmail());
        e.setFirstName(u.getFirstName());
        e.setLastName(u.getLastName());
        e.setCreatedAt(u.getCreatedAt());
        e.setUpdatedAt(u.getUpdatedAt());
        e.setPasswordHash(u.getPasswordHash()); // si dispo
        e.setAvatar(u.getAvatar());
        e.setAvatarMime(u.getAvatarMime());
        e.setAvatarSize(u.getAvatarSize());
        return e;
    }

    private static User toDomain(UserEntity e) {
        return User.reconstruct(
                new UserId(e.getId().toString()),
                e.getUsername(),
                e.getEmail(),
                e.getFirstName(),
                e.getLastName(),
                e.getCreatedAt(),
                e.getUpdatedAt(),
                e.getPasswordHash(),
                e.getAvatar(),
                e.getAvatarMime(),
                e.getAvatarSize()
        );
    }

    @Override public User save(User user) {
        var id = UUID.fromString(user.getId().getValue());
        var existing = jpa.findById(id).orElse(null);

        UserEntity e = (existing != null) ? existing
                : UserEntity.create(
                id,
                user.getUsername(),
                user.getEmail(),
                /* passwordHash */ null, // to be set later
                user.getFirstName(),
                user.getLastName(),
                /* avatar */ null,
                /* mime  */ null,
                /* size  */ null,
                user.getCreatedAt(),
                user.getUpdatedAt()
        );

        e.setUsername(user.getUsername());
        e.setEmail(user.getEmail());
        e.setFirstName(user.getFirstName());
        e.setLastName(user.getLastName());
        e.setUpdatedAt(user.getUpdatedAt());

        var saved = jpa.save(e);
        return toDomain(saved);
    }

    @Override public Optional<User> findById(UserId id) {
        return jpa.findById(UUID.fromString(id.getValue())).map(UserRepositoryImpl::toDomain);
    }
    @Override public Optional<User> findByUsername(String username) {
        return jpa.findByUsername(username).map(UserRepositoryImpl::toDomain);
    }
    @Override public Optional<User> findByEmail(String email) {
        return jpa.findByEmail(email).map(UserRepositoryImpl::toDomain);
    }
    @Override public List<User> findAll() {
        return jpa.findAll().stream().map(UserRepositoryImpl::toDomain).toList();
    }
    @Override public boolean existsByUsername(String username) { return jpa.existsByUsername(username); }
    @Override public boolean existsByEmail(String email) { return jpa.existsByEmail(email); }
    @Override public void deleteById(UserId id) { jpa.deleteById(UUID.fromString(id.getValue())); }
    @Override public long count() { return jpa.count(); }
}