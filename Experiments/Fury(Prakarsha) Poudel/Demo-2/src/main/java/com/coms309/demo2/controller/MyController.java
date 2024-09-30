package com.coms309.demo2.controller;

import com.coms309.demo2.entity.Pet;
import com.coms309.demo2.repository.PetsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MyController {
    @Autowired
    PetsRepo petsRepo;

    @GetMapping("/mytestapi")
    public String testMyAPI() {
        return "The API works well";
    }

    @PostMapping("/pet")
    public Pet savePet(@RequestBody Pet pet) {
        return petsRepo.save(pet); // Save the pet object to the repository
    }

    @GetMapping("/pets")
    public List<Pet> getAllPets(){
        return petsRepo.findAll(); // Retrieve all pets from the repository
    }
    // Get a pet by ID
    @GetMapping("/pet/{id}")
    public Pet getPetById(@PathVariable int id) {
        return petsRepo.findById(id).orElse(null); // Retrieve pet by ID
    }

}
