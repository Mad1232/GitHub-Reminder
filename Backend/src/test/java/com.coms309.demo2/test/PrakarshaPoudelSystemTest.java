package com.coms309.demo2.test ;

import com.coms309.demo2.Demo2Application;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(classes = Demo2Application.class)
@AutoConfigureMockMvc
public class PrakarshaPoudelSystemTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLoginFlow() throws Exception {
        // Simulate a login POST request with valid credentials
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"user@example.com\", \"password\": \"password123\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("client_view,1")); // Example expected response
    }

    @Test
    public void testCreatePet() throws Exception {
        // Create a new pet
        mockMvc.perform(post("/pet")
                        .contentType(MediaType.APPLICATION_JSON).content("{\"pet_name\": \"Buddy\", \"pet_type\": \"Dog\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pet_name").value("Buddy"));
    }

    @Test
    public void testGetAllPets() throws Exception {
        // Retrieve all pets
        mockMvc.perform(get("/pets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").isNotEmpty()); // Checks if the list of pets is not empty
    }

    @Test
    public void testDeletePet() throws Exception {
        // Delete a pet by ID
        mockMvc.perform(delete("/pet/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Pet with ID 1 deleted succesfully."));
    }
}
