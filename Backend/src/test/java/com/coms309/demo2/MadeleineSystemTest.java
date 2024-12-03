package com.coms309.demo2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class MadeleineSystemTest {

    @LocalServerPort
    int port;

    int medicationID;
    long userID;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    @Order(1)
    public void testCreateMedication() {
        // Create a new Medication
        String newMedTest = """
                    {
                        "name": "medName",
                        "stock": "20",
                        "pet": {
                            "pet_id": 5
                        }
                    }
                """;

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(newMedTest)
                .when()
                .post("/inventory");

        // Check status code
        assertEquals(200, response.getStatusCode());

        // Check response body
        try {
            JSONObject jsonResponse = new JSONObject(response.getBody().asString());
            medicationID = jsonResponse.getInt("id");
            assertEquals("medName", jsonResponse.get("name"));
            assertEquals("20", jsonResponse.get("stock"));
        } catch (JSONException e) {
            e.printStackTrace();
            fail("Response parsing failed.");
        }
    }

    @Test
    @Order(2)
    public void testDeleteMedication() {
        // Delete a medication
        Response response = RestAssured.given()
                .when()
                .delete("/medication/" + medicationID);
        // Check status code
        assertEquals(200, response.getStatusCode());

        // Check response body
        String responseString = response.getBody().asString();
        // update this accordingly
        assertEquals("Medication with ID " + medicationID + " deleted succesfully.", responseString);
    }

    @Test
    @Order(3)
    public void testSignupUser() {
        // Create a new User
        String newUserTest = """
                    {
                        "email": "bob@example.com",
                        "password": "123",
                        "phoneNumber": "1234567890",
                        "address": "Knowhere",
                        "pets": []
                    }
                """;

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(newUserTest)
                .when()
                .post("/signup");

        // Check status code
        assertEquals(200, response.getStatusCode());

        // Check response body
        try {
            JSONObject jsonResponse = new JSONObject(response.getBody().asString());
            userID = jsonResponse.getLong("id");
            assertEquals("bob@example.com", jsonResponse.get("email"));
            assertEquals("123", jsonResponse.get("password"));
            assertEquals("1234567890", jsonResponse.get("phoneNumber"));
            assertEquals("Knowhere", jsonResponse.get("address"));
        } catch (JSONException e) {
            e.printStackTrace();
            fail("Response parsing failed.");
        }
    }

    @Test
    @Order(4)
    public void testDeleteUser() {
        // Delete a user
        Response response = RestAssured.given()
                .when()
                .delete("/users/" + userID);
        // Check status code
        assertEquals(200, response.getStatusCode());

        // Check response body
        String responseString = response.getBody().asString();
        // update this accordingly
        assertEquals("User with ID " + userID + " deleted succesfully.", responseString);
    }
}
