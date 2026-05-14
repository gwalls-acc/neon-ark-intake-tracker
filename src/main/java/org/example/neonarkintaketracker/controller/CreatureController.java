/*
  Name: Gloria Walls
  Instructor: Professor Jon-Mikel Pearson
  Assignment: Project 2
  Due Date: 03/28/2026
  Course/Section: COSC 4301 – Section 1
  File Name: CreatureController.java
  Purpose: This is the controller which maps the routes to the API endpoints
*/


package org.example.neonarkintaketracker.controller;
import jakarta.validation.Valid;
import org.example.neonarkintaketracker.dto.CreatureRequest;
import org.example.neonarkintaketracker.dto.CreatureResponse;
import org.example.neonarkintaketracker.entity.Creature;
import org.example.neonarkintaketracker.service.CreatureService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/*
 * This controller handles incoming HTTP requests for /api/creatures
 */
@RestController
@RequestMapping("/api/creatures")
public class CreatureController {

    private final CreatureService service;

    public CreatureController(CreatureService service) {
        this.service = service;
    }

    // Map view all creatures to route
    @GetMapping
    public ResponseEntity<List<CreatureResponse>> getAllCreatures() {
        List<CreatureResponse> creatures = service.getAllCreatures();
        return ResponseEntity.ok(creatures);
    }

    // Map getCreatureById to route
    @GetMapping("/{id}")
    public ResponseEntity<CreatureResponse> getCreatureById(@PathVariable Long id) {
        Optional<CreatureResponse> maybeCreature = service.getCreatureById(id);

        if (maybeCreature.isEmpty()) {
            //return ResponseEntity.notFound().build();
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(maybeCreature.get());
    }


    //map register creature to route
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreatureRequest req) {
        try {
            CreatureResponse created = service.createCreature(req);
             return ResponseEntity.status(201).body(created);
        } catch (DataIntegrityViolationException e) {
            // Returns 409 if (habitat_id, name) already exists
            return ResponseEntity.status(409).body("Conflict: Creature name already exists in this habitat.");
        }
    }

    //map rename to route
    @PatchMapping("/{id}/rename")
    public ResponseEntity<CreatureResponse> rename(
            @PathVariable Long id,
            @RequestBody String newName) {

        // Note: If sending raw text, you might need to strip quotes
        // or use a small DTO if your JSON parser is strict.
        CreatureResponse updated = service.renameCreature(id, newName.replace("\"", ""));
        return ResponseEntity.ok(updated);
    }

    // soft delete creature
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteCreature(id);
        return ResponseEntity.noContent().build(); // Sends back 204
    }

    @GetMapping("/api/feedings")
    public ResponseEntity<?> getFeedingsByTime(@RequestParam String time) {
        try {
            // Enforce the HH:mm format specifically
            LocalTime searchTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
            List<CreatureResponse> results = service.getCreaturesForShift(searchTime);

            if (results.isEmpty()) {
                // Requirement: If empty, include a “none need attending” message
                return ResponseEntity.ok(Map.of("message", "None need attending", "results", results));
            }

            return ResponseEntity.ok(results);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("Invalid format. Use 24-hour HH:MM (e.g., 14:30).");
        }
    }


}