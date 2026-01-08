package com.example.hvbackend.userManagement.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean enabled = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; // Sima LocalDateTime-ra váltottunk

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @PrePersist
    protected void onCreate() {
        // Csak ez az egy hely állítja be a dátumot mentés előtt
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
}