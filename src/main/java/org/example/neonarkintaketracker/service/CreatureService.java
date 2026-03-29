/*
  Name: Gloria Walls
  Instructor: Professor Jon-Mikel Pearson
  Assignment: Project 2
  Due Date: 03/28/2026
  Course/Section: COSC 4301 – Section 1
  File Name: CreatureService.java
  Purpose: This is the service layer
*/

package org.example.neonarkintaketracker.service;
import org.example.neonarkintaketracker.entity.Creature;
import org.example.neonarkintaketracker.entity.Habitat;
import org.example.neonarkintaketracker.exception.ResourceNotFoundException;
import org.example.neonarkintaketracker.repository.CreatureRepository;
import org.example.neonarkintaketracker.repository.HabitatRepository;
import org.example.neonarkintaketracker.dto.CreatureResponse;
import org.example.neonarkintaketracker.dto.CreatureRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class CreatureService {

    private final CreatureRepository repository;
    private final HabitatRepository habitatRepository;

    //Constructor
    public CreatureService(CreatureRepository repository,HabitatRepository habitatRepository) {
        this.repository = repository;
        this.habitatRepository = habitatRepository;
    }

    /*
     * Return every creature currently in the database.
     * This is the "Read" operation for GET /api/creatures
     */
    public List<Creature> getAllCreatures() {
        return repository.findAll();
    }

    // Return one creature by id (Optional = may not exist)
    public Optional<Creature> getCreatureById(Long id) {
        return repository.findById(id);
    }

    // Create anew creature and add it to the repository
    public CreatureResponse createCreature(CreatureRequest req) {
        // 1) Get the Habitat
        Habitat habitat = habitatRepository.findById(req.habitatId())
                .orElseThrow(() -> new ResourceNotFoundException("Habitat not found with ID: " + req.habitatId()));
        // 2) Map request DTO -> entity
        Creature creature = toEntity(req, habitat);

        // 3) Save (DB assigns id, timestamps, etc.)
        Creature saved = repository.save(creature);

        // 4) Map entity -> response DTO
        return toResponse(saved);
    }

    // Helper method  to map data from request to the Creature Entity
    private Creature toEntity(
            CreatureRequest r,                          // Incoming request DTO.
            Habitat habitat) {                           // Habitat resolved server-side.
        return Creature.builder()                        // Builder pattern for clarity.
                .name(r.name())                              // Copy allowed fields.
                .species(r.species())
                .dangerLevel(r.dangerLevel())
                .condition(r.condition())
                .notes(r.notes())
                .habitat(habitat)                            // Enforce relationship internally.
                .build();                                    // Finish entity creation.
    }

    //Helper method to map data to response
    private CreatureResponse toResponse(
            Creature c) {                              // Internal entity.
        return new CreatureResponse(
                c.getId(),                                 // Extract ID.
                c.getName(),                               // Extract name.
                c.getSpecies(),                            // Extract species.
                c.getDangerLevel(),                        // Extract danger level.
                c.getCondition(),                          // Extract condition.
                c.getNotes(),                              // Extract notes.
                c.getHabitat().getId(),                    // Return habitat as ID.
                c.getCreatedAt().toString()                // Convert timestamp to string.
        );
    }
}
