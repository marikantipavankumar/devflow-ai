    package com.devflow.backend.entity;

    import jakarta.persistence.*;
    import lombok.*;

    import java.time.LocalDateTime;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Entity
    @Table(name="users")
    public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "first_name", nullable = false, length = 100)
        private String firstName;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false, length = 20)
        private Role role;

        @Column(name = "last_name", nullable = false, length = 100)
        private String lastName;

        @Column(name = "username", nullable = false, unique = true, length = 50)
        private String username;

        @Column(name = "email", nullable = false, unique = true, length = 150)
        private String email;

        @Column(name = "password", nullable = false, length = 255)
        private String password;

        @Column(name = "phone_number", nullable = false, unique = true, length = 15)
        private String phoneNumber;

        @Column(name = "profile_image",  length = 500)
        private String profileImage;

        @Column(name = "bio",  length = 500)
        private String bio;

        @Column(name = "is_verified", nullable = false)
        private Boolean isVerified = false;

        @Column(name = "is_active", nullable = false)
        private Boolean isActive = true;

        @Column(name = "created_at", nullable = false, updatable = false)
        private LocalDateTime createdAt;

        @Column(name = "updated_at", nullable = false)
        private LocalDateTime updatedAt;

        @PrePersist
        public void onCreate() {
            createdAt = LocalDateTime.now();
            updatedAt = LocalDateTime.now();
        }

        @PreUpdate
        public void onUpdate() {
            updatedAt = LocalDateTime.now();
        }
    }
