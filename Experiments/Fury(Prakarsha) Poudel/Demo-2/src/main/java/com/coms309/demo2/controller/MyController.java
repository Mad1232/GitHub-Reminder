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

    @DeleteMapping("/pet/{id}")
    public String deleteUser(@PathVariable int id) {
        if(petsRepo.existsById(id)) {
            petsRepo.deleteById(id);
            return "Pet with ID " + id + "deleted succesfully.";
        }
        else{
            return "Pet with ID " + id + "does not exist.";
        }
    }

    @DeleteMapping("/pets")
    public String deleteAllUsers() {
        petsRepo.deleteAll();
        return "All pets deleted succesfully.";
    }

    // Update a pet by ID
    @PutMapping("/pet/{id}")
    public String updatePet(@PathVariable int id, @RequestBody Pet petDetails) {
        return petsRepo.findById(id).map(pet -> {
            //updates only name & type for now, as only two lines of code for those
            pet.setPet_name(petDetails.getPet_name());
            pet.setPet_type(petDetails.getPet_type());
            petsRepo.save(pet);
            return "Pet with ID " + id + " updated successfully.";
        }).orElse("Pet with ID " + id + " not found.");
    }


}
