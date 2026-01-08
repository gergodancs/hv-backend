package com.example.hvbackend.buildingManagement;


import com.example.hvbackend.ticketManagement.entity.Ticket;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "buildings")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false)
    private Integer postCode; // Numerikus, szűrésre optimalizálva

    private String city;
    private String address;

    @OneToMany(mappedBy = "building")
    private List<Ticket> tickets;
}
