package com.reddot.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AuthApiTest {
    
    private static final String BASE_URL = "http://localhost:8080";
    
    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URL;
    }
    
    @Test
    public void testSignup() {
        String email = "test" + System.currentTimeMillis() + "@example.com";
        
        given()
            .contentType(ContentType.JSON)
            .body("{\n" +
                "  \"email\": \"" + email + "\",\n" +
                "  \"password\": \"Test123456\",\n" +
                "  \"firstName\": \"Test\",\n" +
                "  \"lastName\": \"User\",\n" +
                "  \"consentGiven\": true\n" +
                "}")
        .when()
            .post("/api/auth/signup")
        .then()
            .statusCode(201)
            .body("accessToken", notNullValue())
            .body("user", notNullValue());
    }
    
    @Test
    public void testLogin() {
        // First signup
        String email = "test" + System.currentTimeMillis() + "@example.com";
        String password = "Test123456";
        
        given()
            .contentType(ContentType.JSON)
            .body("{\n" +
                "  \"email\": \"" + email + "\",\n" +
                "  \"password\": \"" + password + "\",\n" +
                "  \"firstName\": \"Test\",\n" +
                "  \"lastName\": \"User\",\n" +
                "  \"consentGiven\": true\n" +
                "}")
        .when()
            .post("/api/auth/signup");
        
        // Then login
        given()
            .contentType(ContentType.JSON)
            .body("{\n" +
                "  \"email\": \"" + email + "\",\n" +
                "  \"password\": \"" + password + "\"\n" +
                "}")
        .when()
            .post("/api/auth/login")
        .then()
            .statusCode(200)
            .body("accessToken", notNullValue())
            .body("refreshToken", notNullValue());
    }
    
    @Test
    public void testInvalidLogin() {
        given()
            .contentType(ContentType.JSON)
            .body("{\n" +
                "  \"email\": \"invalid@example.com\",\n" +
                "  \"password\": \"wrongpassword\"\n" +
                "}")
        .when()
            .post("/api/auth/login")
        .then()
            .statusCode(401);
    }
}

