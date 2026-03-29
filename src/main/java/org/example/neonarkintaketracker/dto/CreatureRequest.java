/*
  Name: Gloria Walls
  Instructor: Professor Jon-Mikel Pearson
  Assignment: Project 2
  Due Date: 03/28/2026
  Course/Section: COSC 4301 – Section 1
  File Name: CreatureRequest.java
  Purpose: This is the Creature request DTO
  createdAt  is omitted because it is created automatically by the system and not
  entered by the user
*/


package org.example.neonarkintaketracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;



public record CreatureRequest ( // Request DTO with validation rules.
                                @NotBlank(message = "Name is required") String name,                   // Reject empty names early.
                                @NotBlank(message = "Species is required") String species,              // Reject missing species.
                                @NotBlank(message = "Danger level is required") String dangerLevel,     // Reject missing danger level.
                                @NotBlank(message = "Condition is required") String condition,          // Reject missing condition.
                                @Size(max = 500,message ="notes must be <= 500 chars") String notes,    // Limit input size.
                                @NotNull(message = "Habitat ID is required") Long habitatId              // Require valid habitat reference.
)
{}
