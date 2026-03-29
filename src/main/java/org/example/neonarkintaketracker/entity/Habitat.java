/*
  Name: Gloria Walls
  Instructor: Professor Jon-Mikel Pearson
  Assignment: Project 2
  Due Date: 03/28/2026
  Course/Section: COSC 4301 – Section 1
  File Name: Habitat.java
  Purpose: This is the model for the Habitat Entity
*/

package org.example.neonarkintaketracker.entity;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "habitats")
public class Habitat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 40)
    private String biome;

    @Column(nullable = false, length = 120)
    private String location;

    @Column(name = "min_temp_c", nullable = false)
    private Integer minTempC;

    @Column(name = "max_temp_c", nullable = false)
    private Integer maxTempC;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // One Habitat -> Many Creatures
    @OneToMany(mappedBy = "habitat")
    @JsonBackReference
    private List<Creature> creatures;

}
