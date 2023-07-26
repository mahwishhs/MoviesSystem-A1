package com.example.moviessystema1.services;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Base64;

@Provider
public class BasicAuth implements ContainerRequestFilter {


    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Get the Authorization header from the request
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Check if the Authorization header is missing or not starting with "Basic "
        if (authorizationHeader == null || !authorizationHeader.startsWith("Basic ")) {
            // If missing or invalid, return a 401 Unauthorized response with the "WWW-Authenticate" header
            // to prompt the client for basic authentication
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"StoreAPI\"")
                    .build());
            return;
        }

        // Extract the provided username and password from the Authorization header
        String[] credentials = extractCredentials(authorizationHeader);

        // Check if the provided credentials are null or not valid
        if (credentials == null || !isAuthenticated(credentials[0], credentials[1])) {
            // If credentials are invalid, return a 401 Unauthorized response with the "WWW-Authenticate" header
            // to prompt the client for valid basic authentication
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"MovieApi\"")
                    .build());
        }
    }

    // Method to extract username and password from the "Basic" Authorization header
    private String[] extractCredentials(String authorizationHeader) {
        try {
            // Decode the Base64 encoded credentials part of the Authorization header
            byte[] decodedBytes = Base64.getDecoder()
                    .decode(authorizationHeader.substring("Basic ".length()).trim());

            // Convert the decoded bytes to a String using UTF-8 encoding
            String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);

            // Split the decoded string to extract the username and password
            // separated by a colon ":" and return as an array
            return decodedString.split(":", 2);
        } catch (Exception e) {
            // If there is an exception, return null to indicate invalid credentials
            return null;
        }
    }

    // Method to authenticate the provided username and password against the database
    private boolean isAuthenticated(String username, String password) {
        try {
            // Get a database connection using the DBConn class (assumed to be implemented elsewhere)
            Connection connection = DBConn.getConnection();
            //System.out.println("Connected");

            // Prepare the SQL statement to query the UserLogins table for a matching username and password
            String sql = "SELECT COUNT(*) FROM UserLogins WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            // Set the parameters in the prepared statement
            statement.setString(1, username);
            statement.setString(2, password);

            // Execute the query and get the result set
            ResultSet resultSet = statement.executeQuery();

            // Move to the first row of the result set (there should be only one row)
            resultSet.next();

            // Get the count value from the first column of the result set
            // If the count is greater than 0, it means the provided username and password are valid
            int count = resultSet.getInt(1);

            // Close the database resources
            resultSet.close();
            statement.close();
            connection.close();

            // Return true if count > 0, indicating successful authentication, otherwise return false
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
