/*
  Name: Gloria Walls
  Instructor: Professor Jon-Mikel Pearson
  Assignment: Project 2
  Due Date: 03/28/2026
  Course/Section: COSC 4301 – Section 1
  File Name: Creature.java
  Purpose: This is the model for the Creature Entity
*/
package org.example.neonarkintaketracker.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "creatures")

public class Creature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, length = 120)
    private String species;

    @Column(name = "danger_level", nullable = false, length = 30)
    private String dangerLevel;

    @Column(nullable = false, length = 30)
    private String condition;

    @Column(columnDefinition = "TEXT")
    private String notes;

    // Many Creatures -> One Habitat
    @ManyToOne(optional = false)
    @JoinColumn(name = "habitat_id", nullable = false)
    @JsonManagedReference
    private Habitat habitat;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
