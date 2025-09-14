package com.shelfie.presentation.controller;

import com.shelfie.infrastructure.persistence.repository.JpaUserRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final JpaUserRepository jpaUsers;

    public UserController(JpaUserRepository jpaUsers) {
        this.jpaUsers = jpaUsers;
    }

    @GetMapping(value = "/{username}/avatar", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> getAvatar(@PathVariable String username) {
        var ent = jpaUsers.findByUsername(username).orElseThrow();
        if (ent.getAvatar() == null) return ResponseEntity.notFound().build();

        long contentLen = ent.getAvatarSize() != null
                ? ent.getAvatarSize()
                : ent.getAvatar().length;

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(ent.getAvatarMime()))
                .contentLength(contentLen)
                .body(ent.getAvatar());
    }
}