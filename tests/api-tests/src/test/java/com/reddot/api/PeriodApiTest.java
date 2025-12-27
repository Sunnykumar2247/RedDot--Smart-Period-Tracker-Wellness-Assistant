package com.reddot.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PeriodApiTest {
    
    private static final String BASE_URL = "http://localhost:8080";
    private static String accessToken;
    
    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URL;
        
        // Create test user and get token
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
            .post("/api/auth/signup")
        .then()
            .extract()
            .path("accessToken");
        
        // Login to get token
        accessToken = given()
            .contentType(ContentType.JSON)
            .body("{\n" +
                "  \"email\": \"" + email + "\",\n" +
                "  \"password\": \"" + password + "\"\n" +
                "}")
        .when()
            .post("/api/auth/login")
        .then()
            .extract()
            .path("accessToken");
    }
    
    @Test
    public void testCreatePeriod() {
        given()
            .header("Authorization", "Bearer " + accessToken)
            .contentType(ContentType.JSON)
            .body("{\n" +
                "  \"startDate\": \"2025-01-01\",\n" +
                "  \"endDate\": \"2025-01-05\",\n" +
                "  \"averageFlowIntensity\": \"MODERATE\",\n" +
                "  \"painLevel\": 3,\n" +
                "  \"notes\": \"Test period\"\n" +
                "}")
        .when()
            .post("/api/periods")
        .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("startDate", equalTo("2025-01-01"));
    }
    
    @Test
    public void testGetPeriods() {
        given()
            .header("Authorization", "Bearer " + accessToken)
        .when()
            .get("/api/periods")
        .then()
            .statusCode(200)
            .body("$", instanceOf(java.util.List.class));
    }
}

