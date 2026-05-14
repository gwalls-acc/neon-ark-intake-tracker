package org.example.neonarkintaketracker.util;

public class SecurityLogger {

    /**
     * Prints a generic error message to the console.
     * Prevents technical details (Spring, SQL, etc.) from being revealed.
     */
    public static void logSafeError(Exception e) {

            String message = e.getMessage().toLowerCase();

            System.out.println("\n[SYSTEM NOTIFICATION]");

            if (message.contains("404")) {
                System.out.println("Status: Resource Not Found.");
                System.out.println("Detail: The ID provided does not match any existing record in the management registry.");
            }
            else if (message.contains("400") || message.contains("bad request")) {
                System.out.println("Status: Invalid Request Format.");
                System.out.println("Detail: The data provided does not meet the required field specifications.");
            }
            else if (message.contains("409") || message.contains("conflict")) {
                System.out.println("Status: Data Conflict.");
                System.out.println("Detail: An entry with this unique identifier already exists in the registry.");
            }
            else {
                System.out.println("Status: Service Communication Failure.");
                System.out.println("Detail: The management service is currently unreachable or timed out.");
            }

            System.out.println("Action: Please verify your input and attempt the operation again.");
        }
    }

