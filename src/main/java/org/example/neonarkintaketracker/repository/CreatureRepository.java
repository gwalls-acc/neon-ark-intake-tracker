/*
  Name: Gloria Walls
  Instructor: Professor Jon-Mikel Pearson
  Assignment: Project 2
  Due Date: 03/28/2026
  Course/Section: COSC 4301 – Section 1
  File Name: CreatureRepository.java
  Purpose: This is the repository for the creatures table.
*/

package org.example.neonarkintaketracker.repository;
import org.example.neonarkintaketracker.entity.Creature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

// Simple CRUD + paging/sorting out of the box
@Repository
public interface CreatureRepository extends JpaRepository<Creature, Long> {
    // No extra methods needed for basic "read" functionality

    // Core CRUD methods you get for free:
    // save(entity)        -> insert or update a creature
    // findById(id)        -> get one creature by primary key
    // findAll()           -> get all creatures
    // deleteById(id)      -> delete by primary key
    // delete(entity)      -> delete by passing the entity itself

    // Paging and sorting methods are also included automatically.

    @Query("SELECT DISTINCT c FROM Creature c " +
            "JOIN FETCH c.habitat " +             // Makes sure habitat is loaded
            "JOIN FETCH c.feedingSchedules s " +  // Makes sure schedules are loaded
            "WHERE s.feedingTime = :time")
    List<Creature> findCreaturesByScheduledTime(@Param("time") LocalTime time);
}