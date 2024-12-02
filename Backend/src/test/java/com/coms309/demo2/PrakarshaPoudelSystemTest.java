package com.coms309.demo2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.jupiter.api.Assertions.fail;


import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class PrakarshaPoudelSystemTest {

    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void testLoginFlow() {
        // Simulate a login POST request with valid credentials
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body("{\"email\": \"madison@gmail.com\", \"password\": \"madison\"}")
                .when()
                .post("/login");

        // Check status code
        assertEquals(200, response.getStatusCode());

        // Check response body
        String responseString = response.getBody().asString();
        assertEquals("client_view,1", responseString);
    }

    @Test
    public void testCreatePet() {
        // Create a new pet with owner ID 5
        String petPayload = """
        {
            "pet_name": "PizzaHut",
            "pet_type": "Cat",
            "pet_breed": "Lion",
            "pet_diagnosis": "None",
            "pet_age": 1,
            "pet_gender": "M",
            "owner": {
                "id": 5
            }
        }
    """;

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(petPayload)
                .when()
                .post("/pet");

        // Check status code
        assertEquals(200, response.getStatusCode());

        // Check response body
        try {
            JSONObject jsonResponse = new JSONObject(response.getBody().asString());
            assertEquals("PizzaHut", jsonResponse.get("pet_name"));
            assertEquals("Cat", jsonResponse.get("pet_type"));
            assertEquals("Lion", jsonResponse.get("pet_breed"));
            assertEquals("None", jsonResponse.get("pet_diagnosis"));
            assertEquals(1, jsonResponse.get("pet_age"));
            assertEquals("M", jsonResponse.get("pet_gender"));

        } catch (JSONException e) {
            e.printStackTrace();
            fail("Response parsing failed.");
        }
    }

    @Test
    public void testGetAllPets() {
        // Retrieve all pets
        Response response = RestAssured.given()
                .when()
                .get("/pets");

        // Check status code
        assertEquals(200, response.getStatusCode());

        // Check response body
        try {
            JSONArray jsonResponse = new JSONArray(response.getBody().asString());
            assertEquals(true, jsonResponse.length() > 0); // Ensures the list is not empty
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeletePet() {
        // Delete a pet by ID
        Response response = RestAssured.given()
                .when()
                .delete("/pet/16"); //delete id that is previously(already added pet, not recently added one)
        // Check status code
        assertEquals(200, response.getStatusCode());

        // Check response body
        String responseString = response.getBody().asString();
        // update this accordingly
        assertEquals("Pet with ID 16deleted succesfully.", responseString);
    }
}
