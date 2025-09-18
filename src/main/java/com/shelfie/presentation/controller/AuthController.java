package com.shelfie.presentation.controller;

import com.shelfie.application.auth.AuthService;
import com.shelfie.application.dto.AuthResponseDto;
import com.shelfie.application.dto.LoginRequestDto;
import com.shelfie.application.dto.RegisterRequestDto;
import com.shelfie.infrastructure.persistence.repository.JpaUserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService auth;
    private final JpaUserRepository jpaUsers;

    public AuthController(AuthService auth, JpaUserRepository jpaUsers) {
        this.auth = auth;
        this.jpaUsers = jpaUsers;
    }

    // ----- Form pour Swagger + binding multipart -----
    public static class RegisterForm {
        // champs privés + getters/setters => binding OK
        private String username;
        private String email;
        private String firstName;
        private String lastName;
        private String password;            // nom convivial côté Swagger
        private MultipartFile avatar;       // optionnel

        // getters/setters
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }
        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public MultipartFile getAvatar() { return avatar; }
        public void setAvatar(MultipartFile avatar) { this.avatar = avatar; }
    }

    @Operation(summary = "Register")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RegisterForm.class)
            )
    )
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AuthResponseDto register(@ModelAttribute RegisterForm form) {
        var dto = new RegisterRequestDto(
                form.getUsername(),
                form.getEmail(),
                form.getFirstName(),
                form.getLastName(),
                form.getPassword()
        );
        return auth.register(dto, form.getAvatar());
    }

    @Operation(summary = "Login")
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public AuthResponseDto login(@RequestBody LoginRequestDto req) {
        return auth.login(req);
    }

    @GetMapping(value = "/users/{username}/avatar")
    public ResponseEntity<byte[]> getAvatar(@PathVariable String username) {
        var ent = jpaUsers.findByUsername(username).orElseThrow();
        if (ent.getAvatar() == null) return ResponseEntity.notFound().build();

        long contentLen = ent.getAvatarSize() != null ? ent.getAvatarSize() : ent.getAvatar().length;

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(ent.getAvatarMime()))
                .contentLength(contentLen)
                .body(ent.getAvatar());
    }
}