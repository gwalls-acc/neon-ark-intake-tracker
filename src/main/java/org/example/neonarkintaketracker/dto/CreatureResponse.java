/*
  Name: Gloria Walls
  Instructor: Professor Jon-Mikel Pearson
  Assignment: Project 2
  Due Date: 03/28/2026
  Course/Section: COSC 4301 – Section 1
  File Name: CreatureResponse.java
  Purpose: This is the Creature response DTO
*/

package org.example.neonarkintaketracker.dto;

public record CreatureResponse(   // Outgoing DTO for API responses.
                                  Long id,                   // Safe server-generated identifier.
                                  String name,               // Creature name.
                                  String species,            // Species label.
                                  String dangerLevel,        // Danger level for display.
                                  String condition,          // Condition for display.
                                  String notes,              // Notes returned by design choice.
                                  Long habitatId,            // Relationship returned as ID only.
                                  String createdAt           // Timestamp formatted for display.
) {}
