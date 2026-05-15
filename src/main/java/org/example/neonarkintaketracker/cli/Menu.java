/*
  Name: Gloria Walls
  Instructor: Professor Jon-Mikel Pearson
  Assignment: Capstone Project
  Due Date: 5/13/2026
  Course/Section: COSC 4301 – Section 1
  File Name: Menu.java
  Purpose: This class is responsible for menus in the application
*/
package org.example.neonarkintaketracker.cli;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.example.neonarkintaketracker.dto.CreatureHistoryResponse;
import org.example.neonarkintaketracker.dto.CreatureRequest;
import org.example.neonarkintaketracker.dto.CreatureResponse;
import org.example.neonarkintaketracker.api.ApiClient;
import org.example.neonarkintaketracker.dto.UserAdminResponse;
import org.example.neonarkintaketracker.util.SecurityLogger;
import java.util.List;
import java.util.Scanner;


public class Menu {
    private final Scanner scanner;

  private final ObjectMapper objectMapper = new ObjectMapper()
          .registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
    private final Runnable shutdownTask; // This stores the exit logic

    // Update constructor to accept the lambda
    public Menu(Scanner scanner, Runnable shutdownTask) {
        this.scanner = scanner;
        this.shutdownTask = shutdownTask;
    }


    // Main menu
    public void showMenu(Scanner scanner) {


        boolean running = true;

        while (running) {
            System.out.println("\n=========================================================");
            System.out.println("        NEON ARK CLI SYSTEM)");
            System.out.println("=========================================================");
            System.out.println("\n[ MAIN MENU ]");
            System.out.println("---------------------------------------------------------");
            System.out.println("1. List all creatures");
            System.out.println("2. View creature by ID");
            System.out.println("3. Register new creature");
            System.out.println("4. Rename creature");
            System.out.println("5. Remove creature");
            System.out.println("6. View creature observations/notes");
            System.out.println("7. Find creatures by feeding time");
            System.out.println("\n--- Admin Only ---");
            System.out.println("8. View all system users");
            System.out.println("\n0. Exit");
            System.out.println("-------------------------------------");
            System.out.print("Select an option:");

            int choice = InputHelper.getValidInput(scanner, 0, 9);

            //choices for main menu
            switch (choice) {
                case 1:
                   // Main menu option 1 - view all entries in creatures table
                    viewAllCreatures();

                    break;
                case 2:
                    // Main menu option 2 - view creature by id
                    viewCreatureByID(scanner);
                    break;
                case 3:
                    //  Main menu option 3 - register new creature
                    registerNewCreature(scanner);
                    break;

                case 4:
                    //  Main menu option 4 - rename creature
                    renameCreature();
                    break;

                case 5:
                    // Main menu option 5 - remove creature
                    removeCreature();
                    break;

                case 6:
                    // Main menu option 6 - View creature observations/notes";
                    viewCreatureObservations();
                    break;


                case 7:
                    // Main menu option 7 - Find creatures by feeding time
                    findCreaturesToFeedAtATime();
                    break;


                case 8:
                    //   Main menu option 8 -  View all system users
                    viewAdminUserList();
                    break;

                case 0: // Main menu option 0  - exit
                    running = false; // Terminates the loop
                    System.out.println("Exiting...");
                    //shutdownTask.run(); // This triggers the Spring shutdown
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }

        }

    }

    //Main menu option 1 - view all creatures
    private void viewAllCreatures() {
        boolean backToMain = false;
        while (!backToMain) {

            System.out.println("\n\n" + "-".repeat(90));

            System.out.println("[ 3.1 ] View All Creatures ");
            System.out.println("-".repeat(90));

            try {
                String json = ApiClient.sendGet("/creatures");


                List<CreatureResponse> creatures = objectMapper.readValue(json, new TypeReference<List<CreatureResponse>>() {
                });

                String format = "| %-6s | %-20s | %-25s | %-12s |%n";
                System.out.format("+--------+----------------------+---------------------------+--------------+%n");
                System.out.format(format, "ID", "NAME", "SPECIES", "STATUS");
                System.out.format("+--------+----------------------+----------------------------+--------------+%n");

                for (CreatureResponse c : creatures) {
                    System.out.format(format, c.id(), c.name(), c.species(), c.status());
                }
                System.out.format("+--------+----------------------+---------------------------+--------------+%n");

            } catch (java.net.ConnectException e) {
                // Specifically identifies a connection issue without mentioning the port or server type
                System.out.println("\n[SYSTEM ERROR]");
                System.out.println("Status: Service Unreachable.");
                System.out.println("Detail: The internal management service is not responding.");
                System.out.println("Action: Ensure the backend infrastructure is active and try again.");
            } catch (Exception e) {

                //Handles data parsing or other logic errors
                System.out.println("\n[SYSTEM ERROR]");
                System.out.println("Status: Data Retrieval Failure.");
                System.out.println("Detail: The system encountered an error while processing the registry list.");
                System.out.println("Action: Please refresh the menu or contact a system administrator if the issue persists.");
            }

            System.out.println("-----------Return to Main Menu--------------");
            backToMain = InputHelper.getConfirmation(scanner, "Return to Main Menu?");
        }
    }
    //Main menu option 2
    private void viewCreatureByID(Scanner scanner){
        boolean backToMain = false;
        while (!backToMain) {
            System.out.println("\n\n" + "-".repeat(150));
            System.out.println("[ 2.1 ] View Creature By ID");
            System.out.println("-".repeat(150));
            System.out.print("Please enter Creature ID (or 'q' to go back): ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("q")) {
                return; // Immediately exits the method and returns to the Main Menu loop
            }

            //validate input
            if (!InputHelper.isValidId(input)) {
                System.out.println("[INVALID INPUT] Please enter a positive numeric ID.");
                continue;
            }

            try {
                // 1. Fetch single record from the API
                String json = ApiClient.sendGet("/creatures/" + input);

                // 2. Map JSON to a single CreatureResponse record
                CreatureResponse c = objectMapper.readValue(json, CreatureResponse.class);

                // 3. Display as a "Profile Card"
                System.out.println("\n--- ENTITY PROFILE ---");
                System.out.printf("ID:          %s%n", c.id());
                System.out.printf("DESIGNATION: %s%n", c.name());
                System.out.printf("SPECIES:     %s%n", c.species());
                System.out.printf("DANGER LVL:  %s%n", c.dangerLevel());
                System.out.printf("CONDITION:   %s%n", c.condition());
                System.out.printf("STATUS:      %s%n", c.status());
                System.out.printf("REG. DATE:   %s%n", c.createdAt());
                System.out.println("----------------------");
                System.out.printf("NOTES: %s%n", c.notes() != null ? c.notes() : "No notes on file.");
                System.out.println("----------------------");
            } catch (NumberFormatException nfe) {
                System.out.println("[ERROR] Invalid ID format. Please enter a numeric ID.");
            } catch (Exception e) {
                SecurityLogger.logSafeError(e);
            }
            System.out.println("-----------Return to Main Menu--------------");
            backToMain = InputHelper.getConfirmation(scanner, "Return to Main Menu?");
        }


    }

    // Main menu option 3 - create a new creature
    private void  registerNewCreature(Scanner scanner) {
        boolean backToMain = false;

        while (!backToMain) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("[ 3.3 ] Register New Entity into Neon Ark");
            System.out.println("=".repeat(50));


                // 1. Collect Data
                String name = InputHelper.getRequiredString(scanner, "Enter Creature Name: ");

                String species = InputHelper.getRequiredString(scanner, "Enter Species: ");

                int dangerChoice = InputHelper.getValidLevel(scanner);
                String dangerLevel = InputHelper.mapDangerLevel(dangerChoice);

                int conditionChoice = InputHelper.getValidCondition(scanner);
                String condition = InputHelper.mapCondition(conditionChoice);

                String notes = InputHelper.getOptionalString(scanner, " Type in notes or hit enter to skip");

                // We also need the Habitat ID
                String habitatIdStr = InputHelper.getRequiredIntAsString(scanner, "Enter Assigned Habitat ID: ");

                Long habitatId = Long.parseLong(habitatIdStr);

                //2. Display record values for review
                System.out.println("\n" + "-".repeat(40));
                System.out.println("     REGISTRATION SUMMARY");
                System.out.println("-".repeat(40));
                System.out.printf("NAME:         %s%n", name);
                System.out.printf("SPECIES:      %s%n", species);
                System.out.printf("DANGER LEVEL: %s%n", dangerLevel);
                System.out.printf("CONDITION:    %s%n", condition);
                System.out.printf("HABITAT ID:   %s%n", habitatId);
                System.out.println("-".repeat(40));

            //3.  Confirm user wants to create record
            if (InputHelper.getConfirmation(scanner, "Commit this record to the secure database?")) {
                try {
                    // 4. Create the Request DTO
                    CreatureRequest request = new CreatureRequest(
                            name, species, dangerLevel, condition, notes, habitatId
                    );

                    // 3. Send to API
                    System.out.println("\n[SYSTEM] Transmitting data to secure server...");
                    String requestBody = objectMapper.writeValueAsString(request);
                    String responseJson = ApiClient.sendPost("/creatures", requestBody);

                    // 4. Handle Response
                    CreatureResponse created = objectMapper.readValue(responseJson, CreatureResponse.class);
                    System.out.println("\n[SUCCESS] Entity registered successfully!");
                    System.out.printf("Registry ID assigned: %s%n", created.id());

                } catch (Exception e) {
                    String errorMsg = e.getMessage();

                    handleApiError(e);

                }


            }else{
                   // if user does not confirm yes then discard creature registration
                    System.out.println("\n[CANCELLED] Registration discarded. No data was saved.");
                }
            System.out.println("\n-----------Return to Main Menu--------------");
            backToMain = InputHelper.getConfirmation(scanner, "Return to Main Menu?");
        }
    }

   //Main menu option 4 - rename creature
    private void renameCreature() {
        boolean backToMain = false;
        while (!backToMain) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("[ 4.1 ] Rename Registered Entity");
            System.out.println("=".repeat(50));

            // 1. Get and Validate ID
            String id = InputHelper.getRequiredIntAsString(scanner, "Enter Entity ID: ");
            if (id.equalsIgnoreCase("q")) return;

            try {
                // 2. Fetch the CURRENT record first
                String currentJson = ApiClient.sendGet("/creatures/" + id);
                CreatureResponse current = objectMapper.readValue(currentJson, CreatureResponse.class);
                String oldName = current.name();

                // 3. Prompt for the NEW name
                String newName = InputHelper.getRequiredString(scanner, "Enter New Name: ");
                if (newName.equalsIgnoreCase("q")) return;

                // 4. DISPLAY COMPARISON
                System.out.println("\n" + "-".repeat(30));
                System.out.println("CONFIRM NAME CHANGE");
                System.out.println("-".repeat(30));
                System.out.println("CURRENT: " + oldName);
                System.out.println("NEW:     " + newName);
                System.out.println("-".repeat(30));

                // 5. CONFIRMATION
                if (InputHelper.getConfirmation(scanner, "Commit this change to the registry?")) {
                    // Proceed with the PATCH
                    String patchResponse = ApiClient.sendPatch("/creatures/" + id + "/rename", "\"" + newName + "\"");
                    System.out.println("\n[SUCCESS] Registry updated. New ID designation verified.");
                } else {
                    System.out.println("\n[CANCELLED] Change discarded. Original designation retained.");
                }

            } catch (Exception e) {
                String errorMsg = e.getMessage();

                handleApiError(e);

            }
            System.out.println("\n-----------Return to Main Menu--------------");
            backToMain = InputHelper.getConfirmation(scanner, "Return to Main Menu?");
        }
    }

    // Main menu option 5 - remove creature
    private void removeCreature() {
        boolean backToMain = false;
        while (!backToMain) {

        System.out.println("\n" + "=".repeat(50));
        System.out.println("[ 3.5 ] Decommission Entity (Soft Delete)");
        System.out.println("=".repeat(50));

        // 1. Get and Validate ID
        String id = InputHelper.getRequiredIntAsString(scanner, "Enter Entity ID: ");
        if (id.equalsIgnoreCase("q")) return;

        try {
            // 2. Fetch current details to show the user
            String currentJson = ApiClient.sendGet("/creatures/" + id);
            CreatureResponse current = objectMapper.readValue(currentJson, CreatureResponse.class);

            // Check if it's already inactive
            if ("Inactive".equalsIgnoreCase(current.status())) {
                System.out.println("\n[NOTICE] This entity is already marked as INACTIVE.");
                return;
            }

            // 3. DISPLAY WARNING SUMMARY
            System.out.println("\n" + "!".repeat(30));
            System.out.println("WARNING: DECOMMISSIONING ENTITY");
            System.out.println("!".repeat(30));
            System.out.printf("ID:       %s%n", current.id());
            System.out.printf("NAME:     %s%n", current.name());
            System.out.printf("SPECIES:  %s%n", current.species());
            System.out.printf("STATUS:   %s%n", current.status());
            System.out.println("-".repeat(30));

            // 4. FINAL CONFIRMATION
            System.out.println("Decommissioning will move this entity to the 'Inactive' registry.");
            if (InputHelper.getConfirmation(scanner, "Proceed with decommissioning?")) {

                // 5. Trigger the Soft Delete via API
                ApiClient.sendDelete("/creatures/" + id);
                System.out.println("\n[SUCCESS] Entity " + id + " has been moved to Inactive status.");

            } else {
                System.out.println("\n[CANCELLED] Decommissioning aborted.");
            }

        } catch (Exception e) {
            handleApiError(e); // Handles the 404 if the ID doesn't exist
        }
        System.out.println("\n-----------Return to Main Menu--------------");
        backToMain = InputHelper.getConfirmation(scanner, "Return to Main Menu?");
        }
    }

 //Main menu option 6 - View creature observations
    private void viewCreatureObservations() {
        boolean backToMain = false;
        while (!backToMain) {
            System.out.print("Enter Creature ID to view history: ");
            String id = scanner.nextLine();


            try {
                String path = "/creatures/" + id + "/observations";
                String json = ApiClient.sendGet(path);
                // Since this is ONE creature, we don't need a TypeReference List
                CreatureResponse creature = objectMapper.readValue(json, CreatureResponse.class);

                System.out.println("\n--- Observation History for " + creature.name() + " ---");
                // Loop through and print your observations here
                creature.observations().forEach(obs -> System.out.println("- " + obs));

            } catch (Exception e) {
                System.out.println("Error retrieving observations: " + e.getMessage());
            }

            System.out.println("-----------Return to Main Menu--------------");
            backToMain = InputHelper.getConfirmation(scanner, "Return to Main Menu?");
        }
    }



    //Main menu option 7 - find creatures to feed specific time
    private void findCreaturesToFeedAtATime() {
        boolean backToMain = false;
        while (!backToMain) {

            System.out.println("\n" + "=".repeat(60));
            System.out.println("   NEON ARK OPERATIONAL CHECKLIST: FEEDING SHIFT");
            System.out.println("=".repeat(60));

            System.out.print("Enter target shift time (HH:MM, e.g., 08:00): ");
            String timeInput = scanner.nextLine().trim();

            // Basic validation before even calling the API
            if (timeInput.isEmpty() || timeInput.equalsIgnoreCase("q")) {
                return;
            }

            try {
                // 1. Send the request with the query parameter

               String json = ApiClient.sendGet("/creatures/feedings?time=" + timeInput);

                System.out.println("DEBUG - Server sent: " + json);

                // 2. Check the response type
                // Your requirement: If empty, include a “none need attending” message.
                if (json.contains("None need attending")) {
                    System.out.println("\n>>> [SCAN RESULT]: None need attending at " + timeInput + ".");
                    System.out.println("All local bio-rhythms are within acceptable parameters.");
                } else {
                    // 3. Parse the JSON list into CreatureResponse objects
                    List<CreatureResponse> checklist = objectMapper.readValue(json,
                            new java.util.concurrent.CopyOnWriteArrayList<CreatureResponse>() {
                            }.getClass().getGenericSuperclass() != null ?
                                    new com.fasterxml.jackson.core.type.TypeReference<List<CreatureResponse>>() {
                                    } : null);

                    // 4. Display the results in a clear checklist format
                    System.out.println("\nACTIONABLE CHECKLIST FOR " + timeInput + " SHIFT:");
                    System.out.printf("%-5s | %-20s | %-25s%n", "ID", "ENTITY NAME", "HABITAT LOCATION");
                    System.out.println("-".repeat(60));

                    for (CreatureResponse c : checklist) {
                        System.out.printf("[ ] %-3d | %-20s | %-25s%n",
                                c.id(),
                                c.name(),
                                c.habitatName() != null ? c.habitatName() : "Unknown Sector");
                    }

                    System.out.println("-".repeat(60));
                    System.out.println("TOTAL ENTITIES TO ATTEND: " + checklist.size());
                }

            } catch (RuntimeException e) {
                // This catches the 400 Bad Request error from your ApiClient
                e.printStackTrace();
                //System.out.println("\n[SYSTEM ERROR]: Invalid time format. Please use 24-hour HH:MM.");
            } catch (Exception e) {
                // This catches general connection issues
                //System.out.println("\n[UPLINK ERROR]: Could not reach the Neon Ark server.");
                e.printStackTrace();
            }
            System.out.println("\n-----------Return to Main Menu--------------");
            backToMain = InputHelper.getConfirmation(scanner, "Return to Main Menu?");
        }
    }



    // Main menu option 8 - view users
    private void viewAdminUserList() {
        boolean backToMain = false;
        while (!backToMain) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("  NEON ARK GOVERNANCE - REGISTERED INTERGALACTIC PERSONNEL");
        System.out.println("=".repeat(70));

        try {
            String json = ApiClient.sendGet("/admin/users");
            // Use TypeReference for List of DTOs
            List<UserAdminResponse> users = objectMapper.readValue(json,
                    new TypeReference<List<UserAdminResponse>>(){});

            System.out.printf("%-25s | %-20s | %-15s%n", "NAME", "EMAIL", "ROLES");
            System.out.println("-".repeat(70));

            for (UserAdminResponse u : users) {
                String roleString = String.join(", ", u.roles());
                System.out.printf("%-25s | %-20s | %s%n",
                        u.fullName(), u.email(), roleString);
            }

        } catch (Exception e) {
            handleApiError(e);
        }
        System.out.println("\n-----------Return to Main Menu--------------");
        backToMain = InputHelper.getConfirmation(scanner, "Return to Main Menu?");
    }
    }



  // logic for handling various API errors
    private void handleApiError(Exception e) {
        String msg = e.getMessage();

        // Check if the error message contains specific HTTP codes we've defined
        if (msg != null && msg.contains("HTTP_ERROR:")) {
            if (msg.contains("400")) {
                System.out.println("\n[ERROR] Bad Request: The data sent was invalid or incomplete.");
            } else if (msg.contains("404")) {
                System.out.println("\n[ERROR] Not Found: The requested Entity ID does not exist in the registry.");
            } else if (msg.contains("409")) {
                System.out.println("\n[ERROR] Conflict: A naming or habitat constraint was violated.");
            } else if (msg.contains("500")) {
                System.out.println("\n[ERROR] Server Error: The Neon Ark database is currently unreachable.");
            } else {
                System.out.println("\n[ERROR] Communication Failure: " + msg);
            }
        } else {
            // If it's not an HTTP error (like a network timeout), use your secure logger
            SecurityLogger.logSafeError(e);
        }
    }

    private void viewFullHistory() {
        String id = InputHelper.getRequiredIntAsString(scanner, "Enter Entity ID for Full History: ");
        if (id.equalsIgnoreCase("q")) return;

        try {
            String json = ApiClient.sendGet("/creatures/" + id + "/observations");
            CreatureHistoryResponse history = objectMapper.readValue(json, CreatureHistoryResponse.class);

            System.out.println("\n" + "=".repeat(60));
            System.out.println("HISTORICAL RECORD: " + history.name().toUpperCase());
            System.out.println("ASSIGNED HABITAT: " + history.habitatName());
            System.out.println("=".repeat(60));

            if (history.observations().isEmpty()) {
                System.out.println("No observations recorded for this entity.");
            } else {
                for (CreatureHistoryResponse.ObservationDetail obs : history.observations()) {
                    System.out.printf("[%s] %s%n", obs.timestamp(), obs.authorName());
                    System.out.println("Note: " + obs.content());
                    System.out.println("-".repeat(30));
                }
            }
        } catch (Exception e) {
            handleApiError(e);
        }
    }


}