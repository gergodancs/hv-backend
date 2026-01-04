package com.example.hvbackend.userManagement.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

//@Entity
@Table(name = "tickets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String status; // Pl: OPEN, IN_PROGRESS, DONE

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User creator; // Aki beküldte a hibát

    private LocalDateTime createdAt = LocalDateTime.now();
}
