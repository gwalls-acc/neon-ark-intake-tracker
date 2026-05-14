/*
  Name: Gloria Walls
  Instructor: Professor Jon-Mikel Pearson
  Assignment: Capstone Project
  Due Date: 5/14/2026
  Course/Section: COSC 4301 – Section 1
  File Name: NeonArkIntakeTrackerApplication.java
  Purpose: This is the main file of the Neon Ark Tracker Application
  It is an application for tracking animals in the Neon Ark habitat system,
*/

package org.example.neonarkintaketracker;
import org.example.neonarkintaketracker.cli.Menu;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import java.util.Scanner;

@SpringBootApplication
@EnableJpaAuditing
public class NeonArkIntakeTrackerApplication implements CommandLineRunner {

    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {

        context = SpringApplication.run(NeonArkIntakeTrackerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        // Spring calls this automatically after the server starts
        Scanner scanner = new Scanner(System.in);

        Menu menu = new Menu(scanner, () -> {
            if (context != null) {
                // Gracefully shut down Spring and Tomcat
                int exitCode = SpringApplication.exit(context, () -> 0);
                System.exit(exitCode);
            }
        });
        menu.showMenu(scanner);
    }

    public static void shutdown() {
        if (context != null) {
            int exitCode = SpringApplication.exit(context, () -> 0);
            System.exit(exitCode);
        }
    }

}
