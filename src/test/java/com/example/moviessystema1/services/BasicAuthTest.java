package com.example.moviessystema1.services;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import javax.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.util.Base64;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BasicAuthTest {
    @Mock
    private ContainerRequestContext requestContext;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFilterWithValidCredentials() throws IOException {
        // Create an instance of BasicAuth
        BasicAuth basicAuth = new BasicAuth();

        // Create a mock ContainerRequestContext
        ContainerRequestContext requestContext = mock(ContainerRequestContext.class);

        // Create a valid Authorization header with credentials
        String validCredentials = "username:password";
        String validAuthorizationHeader = "Basic " + Base64.getEncoder().encodeToString(validCredentials.getBytes());

        // Set the valid Authorization header to the mock request context
        when(requestContext.getHeaderString(HttpHeaders.AUTHORIZATION)).thenReturn(validAuthorizationHeader);

        // Handle the abortWith method invocation with a custom Answer
        doAnswer(invocation -> {
            // Get the Response object passed to abortWith
            Response response = invocation.getArgument(0);

            // Assert the desired behavior of abortWith here (e.g., check the response status, etc.)
            Assert.assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());

            // Return null since abortWith is void
            return null;
        }).when(requestContext).abortWith(any(Response.class));

        // Call the filter method with the mock request context
        basicAuth.filter(requestContext);

        // Verify that the request context's abortWith method was invoked
        verify(requestContext).abortWith(any(Response.class));
    }

    @Test
    public void testFilterWithInvalidCredentials() throws IOException {
        // Create an instance of BasicAuth
        BasicAuth basicAuth = new BasicAuth();

        // Create a mock ContainerRequestContext
        ContainerRequestContext requestContext = mock(ContainerRequestContext.class);

        // Create an invalid Authorization header with invalid credentials
        String invalidCredentials = "invalidUsername:invalidPassword";
        String invalidAuthorizationHeader = "Basic " + Base64.getEncoder().encodeToString(invalidCredentials.getBytes());

        // Set the invalid Authorization header to the mock request context
        when(requestContext.getHeaderString(HttpHeaders.AUTHORIZATION)).thenReturn(invalidAuthorizationHeader);

        // Handle the abortWith method invocation with a custom Answer
        doAnswer(invocation -> {
            // Get the Response object passed to abortWith
            Response response = invocation.getArgument(0);

            // Assert the desired behavior of abortWith here (e.g., check the response status, etc.)
            Assert.assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());

            // Return null since abortWith is void
            return null;
        }).when(requestContext).abortWith(any(Response.class));

        // Call the filter method with the mock request context
        basicAuth.filter(requestContext);

        // Verify that the request context's abortWith method was invoked
        verify(requestContext).abortWith(any(Response.class));
    }

}