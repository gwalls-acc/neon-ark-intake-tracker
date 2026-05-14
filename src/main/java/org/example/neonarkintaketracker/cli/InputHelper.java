/*
  Name: Gloria Walls
  Instructor: Professor Jon-Mikel Pearson
  Assignment: Capstone Project
  Due Date: 5/13/2026
  Course/Section: COSC 4301 – Section 1
  File Name: InputHelper.java
  Purpose: This class is responsible for input validation
*/

package org.example.neonarkintaketracker.cli;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class InputHelper {

    private static final List<Integer> VALID_ROLES = Arrays.asList(1, 2, 3, 4, 5);
    private static final List<Integer> VALID_LEVELS = Arrays.asList(1, 2, 3);
    private static final List<Integer> VALID_STATUSES = Arrays.asList(1, 2, 3);

    public static String getRequiredString(Scanner scanner, String prompt) {
        String input = "";
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine().strip(); // Clean up whitespace

            if (!input.isBlank()) {
                return input; // Success!
            } else {
                System.out.println("Error: This field is required. It cannot be left blank.");
            }
        }
    }

    /**
     * Gets a string from the user.
     * If the user enters whitespace or nothing, it returns an empty string.
     */

    //validate non-required text
    public static String getOptionalString(Scanner scanner, String prompt) {
        System.out.print(prompt + " (Optional - press Enter to skip): ");
        String input = scanner.nextLine();

        // Return a stripped version of the string, or an empty string if it's null
        return (input == null) ? "" : input.strip();
    }

    //validate contion
    public static int getValidCondition(Scanner scanner) {
        while (true) {
            System.out.println("Select Role [1-2]: ");
            System.out.println("[1] STABLE");
            System.out.println("[2] QUARANTINED");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                // Now we use the list check you wanted!
                if (VALID_ROLES.contains(choice)) {
                    return choice;
                }
            } else {
                scanner.nextLine();
            }
            System.out.println("Invalid choice. Try again.");
        }
    }

    //validate  danger level
    public static int getValidLevel(Scanner scanner) {
        while (true) {
            System.out.println("Select Danger Level [1-3]: ");
            System.out.println("[1] LOW");
            System.out.println("[2] MEDIUM");
            System.out.println("[3] HIGH");
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                // Now we use the list check you wanted!
                if (VALID_LEVELS.contains(choice)) {
                    return choice;
                }
            } else {
                scanner.nextLine();
            }
            System.out.println("Invalid choice. Try again.");
        }
    }

    //validate status
    public static int getValidStatus(Scanner scanner) {
        while (true) {
            System.out.println("Select Status [1-2]: ");
            System.out.println("[1] ACTIVE");
            System.out.println("[2] INACTIVE");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                // Now we use the list check you wanted!
                if (VALID_STATUSES.contains(choice)) {
                    return choice;
                }
            } else {
                scanner.nextLine();
            }
            System.out.println("Invalid choice. Try again.");
        }
    }

    // validate email address
    public static String getValidEmail(Scanner scanner) {
        // A standard regex for basic email validation
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);

        while (true) {
            System.out.print("Enter Email Address: ");
            String email = scanner.nextLine().trim(); // trim() removes accidental spaces

            if (pattern.matcher(email).matches()) {
                return email; // Valid email! Exit and return the string.
            } else {
                System.out.println("Invalid format. Example: name@domain.com");
            }
        }
    }

    //validate date field
    public static LocalDate getValidDate(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt + " (YYYY-MM-DD): ");
            String input = scanner.nextLine().strip();

            try {
                // LocalDate.parse expects the ISO_LOCAL_DATE format (YYYY-MM-DD) by default
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid format. Please use the YYYY-MM-DD format (e.g., 2026-05-06).");
            }
        }
    }

    public static LocalDate getOptionalDate(Scanner scanner, String prompt, LocalDate startDate) {
        while (true) {
            System.out.print(prompt + " (YYYY-MM-DD or press Enter to skip): ");
            String input = scanner.nextLine().strip();

            // 1. Check if the user skipped it
            if (input.isEmpty()) {
                return null;
            }

            try {
                // 2. Validate format
                LocalDate date = LocalDate.parse(input);

                // 3. Validate logic (must not be before startDate)
                if (startDate != null && date.isBefore(startDate)) {
                    System.out.println("Error: Date cannot be earlier than the start date (" + startDate + ").");
                } else {
                    return date; // Valid format and valid logic
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid format. Please use YYYY-MM-DD.");
            }
        }
    }

    public static int getValidInput(Scanner scanner, int min, int max) {
        int input = -1;
        boolean isValid = false;

        while (!isValid) {
            System.out.print("Enter your choice (" + min + "-" + max + "): ");

            // Check if the next thing in the buffer is actually an integer
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
                scanner.nextLine(); // Clear the newline character from the buffer

                // Check if the integer is within the allowed range
                if (input >= min && input <= max) {
                    isValid = true; // Success!
                } else {
                    System.out.println("Error: Please enter a number between " + min + " and " + max + ".");
                }
            } else {
                // This runs if they typed letters or symbols
                System.out.println("Error: Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear the "trash" input from the buffer
            }
        }

        return input;
    }

    public static boolean getConfirmation(Scanner scanner, String message) {
        while (true) {
            System.out.print(message + " (Y/N): ");
            String input = scanner.nextLine().strip().toLowerCase();

            if (input.equals("y") || input.equals("yes")) return true;
            if (input.equals("n") || input.equals("no")) return false;

            System.out.println("Invalid input. Please enter 'Y' for Yes or 'N' for No.");
        }
    }

    public static boolean isValidId(String input) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }
        try {
            long id = Long.parseLong(input.trim());
            return id > 0; // IDs in the database are positive
        } catch (NumberFormatException e) {
            return false; // Not a number
        }
    }

    // validate int as string (as in Habitat ID)
    public static String getRequiredIntAsString(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().strip();

            // 1. Check if blank first
            if (input.isBlank()) {
                System.out.println("Error: This field is required.");
                continue;
            }

            // 2. Check if "q" for exit (optional but helpful if you use the q-exit strategy)
            if (input.equalsIgnoreCase("q")) {
                return "q";
            }

            // 3. Validate that it is a numeric integer
            try {
                Integer.parseInt(input);
                return input; // Success! Return the validated number as a String
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid whole number.");
            }
        }
    }

    // map integer choice to text for danger level
    public static String mapDangerLevel(int choice) {
        return switch (choice) {
            case 1 -> "LOW";
            case 2 -> "MEDIUM";
            case 3 -> "HIGH";
            default -> "LOW";
        };
    }

   //map integer choice to text for condition
    public static String mapCondition(int choice) {
        return switch (choice) {
            case 1 -> "STABLE";
            case 2 -> "QUARANTINED";
            case 3 -> "CRITICAL";
            default -> "STABLE";
        };
    }


}
