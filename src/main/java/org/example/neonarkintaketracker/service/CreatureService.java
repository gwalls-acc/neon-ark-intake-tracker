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
import org.example.neonarkintaketracker.entity.FeedingSchedule;
import org.example.neonarkintaketracker.entity.Observation;
import org.springframework.transaction.annotation.Transactional;
import org.example.neonarkintaketracker.entity.Creature;
import org.example.neonarkintaketracker.entity.Habitat;
import org.example.neonarkintaketracker.exception.ResourceNotFoundException;
import org.example.neonarkintaketracker.repository.CreatureRepository;
import org.example.neonarkintaketracker.repository.HabitatRepository;
import org.example.neonarkintaketracker.dto.CreatureResponse;
import org.example.neonarkintaketracker.dto.CreatureRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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


    public List<CreatureResponse> getAllCreatures() {
        return repository.findAll().stream()
                .map(this::mapToResponse) // Convert each Entity to DTO
                .toList();
    }

    public Optional<CreatureResponse> getCreatureById(Long id) {
        return repository.findById(id)
                .map(this::mapToResponse);
    }

    //map creature record to response object
    private CreatureResponse mapToResponse(Creature creature) {
        // 1. Initialize an empty list to avoid null issues later
        List<String> scheduleStrings = new java.util.ArrayList<>();

        // 2. Grab the raw schedules from the creature entity
        List<FeedingSchedule> rawSchedules = creature.getFeedingSchedules();

        // 3. Null-Safe Check: Only loop if the list actually exists
        if (rawSchedules != null) {
            for (FeedingSchedule s : rawSchedules) {
                // Since you only have feeding_time, we use that.
                // Adjust the getter name if it's named differently (e.g., getFeedingTime())
                scheduleStrings.add("Scheduled at: " + s.getFeedingTime());
            }
        }

        var rawObs = creature.getObservations();
        List<String> observationStrings = (rawObs == null)
                ? java.util.Collections.emptyList()
                : rawObs.stream()
                .map(o -> o.toString()) // Use o.getNote() or similar if toString is too messy
                .toList();

        return new CreatureResponse(
                creature.getId(),
                creature.getName(),
                creature.getSpecies(),
                creature.getDangerLevel(),
                creature.getCondition(),
                creature.getNotes(),
                creature.getHabitat().getId(),
                creature.getCreatedAt().toString(),
                creature.getStatus(),
                creature.getHabitat().getName(),
                creature.getHabitat().getZone(),
                creature.getLastFedAt(),
               scheduleStrings,
                // Map the observations to Strings so they are easy to display
                observationStrings

        );
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
                .status("ACTIVE")
                .build();                                    // Finish entity creation.
    }

    //Helper method to map data to response
    private CreatureResponse toResponse(
            Creature c) {

        // 1. Initialize an empty list to avoid null issues later
        List<String> scheduleStrings = new java.util.ArrayList<>();

        // 2. Grab the raw schedules from the creature entity
        List<FeedingSchedule> rawSchedules = c.getFeedingSchedules();

        // 3. Null-Safe Check: Only loop if the list actually exists
        if (rawSchedules != null) {
            for (FeedingSchedule s : rawSchedules) {
                // Since you only have feeding_time, we use that.
                // Adjust the getter name if it's named differently (e.g., getFeedingTime())
                scheduleStrings.add("Scheduled at: " + s.getFeedingTime());
            }
        }

        var rawObs = c.getObservations();
        List<String> observationStrings = (rawObs == null)
                ? java.util.Collections.emptyList()
                : rawObs.stream()
                .map(o -> o.toString()) // Use o.getNote() or similar if toString is too messy
                .toList();
        // Internal entity.
        return new CreatureResponse(
                c.getId(),                                 // Extract ID.
                c.getName(),                               // Extract name.
                c.getSpecies(),                            // Extract species.
                c.getDangerLevel(),                        // Extract danger level.
                c.getCondition(),                          // Extract condition.
                c.getNotes(),                              // Extract notes.
                c.getHabitat().getId(),                    // Return habitat as ID.
                c.getCreatedAt().toString(),                // Convert timestamp to string.
                c.getStatus(),
                c.getHabitat().getName(),
                c.getHabitat().getZone(),
                c.getLastFedAt(),
                scheduleStrings,
                // Map the observations to Strings so they are easy to display
                observationStrings

        );
    }



    //rename creature
    @Transactional
    public CreatureResponse renameCreature(Long id, String newName) {
        Creature creature = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));

        creature.setName(newName);
        return toResponse(repository.save(creature));
    }

    // soft delete creature
    @Transactional
    public void deleteCreature(Long id) {
        Creature creature = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));

        // Perform the Soft Delete
        creature.setStatus("INACTIVE");
        repository.save(creature);
    }

    @Transactional
    public CreatureResponse recordFeeding(Long id) {
        Creature creature = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));

        LocalDateTime now = LocalDateTime.now();
        creature.setLastFedAt(now);

        // Optional: Auto-log an observation for accountability
        Observation feedingNote = new Observation();
        feedingNote.setCreature(creature);
        feedingNote.setContent("Routine feeding completed.");
        feedingNote.setCreatedAt(now);
        // feedingNote.setAuthor(currentUser); // Link this once Login is ready!

        creature.getObservations().add(feedingNote);

        return toResponse(repository.save(creature));
    }

    public List<CreatureResponse> getCreaturesForShift(LocalTime time) {
        // 1. Fetch creatures that have a schedule matching the exact time
        List<Creature> creatures = repository.findCreaturesByScheduledTime(time);

        // 2. Map the entities to DTOs for the CLI
        return creatures.stream()
                .map(this::toResponse)
                .toList();
    }

    public CreatureResponse getCreatureWithObservations(Long id) {
        // 1. Find the creature or throw an error if the ID doesn't exist
        Creature creature = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Creature not found with ID: " + id));

        // 2. Map the data to your Record
        return new CreatureResponse(
                creature.getId(),
                creature.getName(),
                creature.getSpecies(),
                creature.getDangerLevel(),
                creature.getCondition(),
                creature.getNotes(),
                creature.getHabitat() != null ? creature.getHabitat().getId() : null,
                creature.getCreatedAt() != null ? creature.getCreatedAt().toString() : null,
                creature.getStatus(),
                creature.getHabitat() != null ? creature.getHabitat().getName() : "N/A",
                creature.getHabitat() != null ? creature.getHabitat().getZone() : "N/A",
                creature.getLastFedAt(), // This might stay as LocalDateTime if you're comfortable

                // 3. Map Feeding Times (Strings)
                creature.getFeedingSchedules().stream()
                        .map(s -> s.getFeedingTime().toString())
                        .toList(),

                // 4. Map Observations (Strings) - using your getCreatedAt()
                creature.getObservations().stream()
                        .sorted(java.util.Comparator.comparing(Observation::getCreatedAt).reversed())
                        .map(obs -> obs.getCreatedAt().toString() + " - " + obs.getContent())
                        .toList()
        );
    }
}
