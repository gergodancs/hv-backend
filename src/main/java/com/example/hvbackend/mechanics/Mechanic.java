package com.example.hvbackend.mechanics;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mechanics")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mechanic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phoneNumber;
    private String profession;

    private Integer serviceArea; // Itt a kerület száma (pl. 14)
}
