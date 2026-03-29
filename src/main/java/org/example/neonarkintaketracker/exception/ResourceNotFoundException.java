/*
  Name: Gloria Walls
  Instructor: Professor Jon-Mikel Pearson
  Assignment: Project 2
  Due Date: 03/28/2026
  Course/Section: COSC 4301 – Section 1
  File Name: ResourceNotFoundException.java
  Purpose: Generate a 404 error if a resource is not found
*/


package org.example.neonarkintaketracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// This tells Spring to always return 404 when this is thrown
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}