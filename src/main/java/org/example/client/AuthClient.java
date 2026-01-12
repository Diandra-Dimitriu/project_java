package org.example.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.LoginRequest;
import org.example.dto.RegisterRequest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class AuthClient {

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper(); // Parses JSON
    private final String BASE_URL = "http://localhost:8080/api/auth";

    // Returns User ID if successful, or -1 if failed
    public int login(String email, String password) {
        try {
            // 1. Create the Request Object
            LoginRequest req = new LoginRequest();
            req.setEmail(email);
            req.setPassword(password);

            // 2. Convert to JSON String
            String jsonBody = mapper.writeValueAsString(req);

            // 3. Send POST Request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/login"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            // 4. Get Response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // Success! Parse the JSON response to get userId
                Map<String, Object> responseMap = mapper.readValue(response.body(), Map.class);
                return (int) responseMap.get("userId");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // Login failed
    }

    // Returns true if registered, false otherwise
    public boolean register(String email, String password) {
        try {
            RegisterRequest req = new RegisterRequest();
            req.setEmail(email);
            req.setPassword(password);

            String jsonBody = mapper.writeValueAsString(req);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/register"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            return response.statusCode() == 200;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}