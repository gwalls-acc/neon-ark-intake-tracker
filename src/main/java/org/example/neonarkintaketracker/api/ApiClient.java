package org.example.neonarkintaketracker.api;
import org.example.neonarkintaketracker.util.SecurityLogger;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiClient {
    private static final String BASE_URL = "http://localhost:8080/api";
    private static final HttpClient client = HttpClient.newHttpClient();

    // 1. POST - Add Creature
    public static String sendPost(String endpoint, String json) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        // 1. Capture the full response object
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // 2. Check the status code
        int status = response.statusCode();

        if (status == 201) {
            // Success: Return the JSON body (the created creature)
            return response.body();
        } else {
            // Error: Throw an exception with the code so the Menu can see it
            // We include the body because sometimes the server sends a helpful error message
            throw new RuntimeException("HTTP_ERROR:" + status + ":" + response.body());
        }
    }

    // 2. PATCH - Rename Creature
    public static String sendPatch(String endpoint, String json) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .header("Content-Type", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(json)) // Custom method for PATCH
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    // 3. DELETE - Soft Delete
    public static void sendDelete(String endpoint) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .header("Content-Type", "application/json")
                .DELETE() // Specifies the DELETE method
                .build();


        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int status = response.statusCode();

        // 204 No Content is the standard success code for DELETE
        // 200 OK is also commonly used
        if (status != 204 && status != 200) {
            throw new RuntimeException("HTTP_ERROR:" + status);
        }
    }

    // 4.  GET - Show All
    public static String sendGet(String endpoint) throws Exception {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + endpoint))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (java.net.ConnectException e) {
            SecurityLogger.logSafeError(e);
            throw new Exception("External service gateway timeout.");
        }
    }

}// end class
