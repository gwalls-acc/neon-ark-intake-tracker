/*
  Name: Gloria Walls
  Instructor: Professor Jon-Mikel Pearson
  Assignment: Project 2
  Due Date: 03/28/2026
  Course/Section: COSC 4301 – Section 1
  File Name: NeonArkIntakeTrackerApplication.java
  Purpose: This is the main file of the Neon Ark Tracker Application
  It is an application for tracking animals in the Neon Ark habitat system,
*/

package org.example.neonarkintaketracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NeonArkIntakeTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NeonArkIntakeTrackerApplication.class, args);
    }

}
