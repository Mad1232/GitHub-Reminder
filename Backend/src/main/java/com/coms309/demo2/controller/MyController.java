package com.coms309.demo2.controller;

import com.coms309.demo2.entity.Pet;
import com.coms309.demo2.repository.PetsRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.coms309.demo2.entity.Medication;
import com.coms309.demo2.repository.MedicationRepository;
import java.util.List;


/**
 * @author Fury Poudel and Madeleine Carydis
 * Creates and updates Pets
 */
@RestController
@Tag(name = "Pet Controller", description = "Handles pet-related operations and user-pet associations")
public class MyController {
    @Autowired
    PetsRepo petsRepo;

    @Autowired
    MedicationRepository medicationRepo;

    /**
     * Endpoint used for testing. Should not be used anymore
     * @return "The API works well"
     */
    @Operation(summary = "testapi")
    @GetMapping("/mytestapi")
    public String testMyAPI() {
        return "The API works well";
    }

    /**
     * Create a Pet
     * @param pet Pet
     * @return Pet
     */
    @Operation(summary = "Creates a new Pet profile")
    @PostMapping("/pet")
    public ResponseEntity<Pet> savePet(@RequestBody Pet pet) {

        // Check if the Medication object is included
        if (pet.getMedication() != null && pet.getMedication().getId() != null) {
            // Retrieve existing Medication by ID
            Medication medication = medicationRepo.findById(pet.getMedication().getId())
                    .orElseThrow(() -> new RuntimeException("Medication not found"));

            // Set the retrieved Medication object in the Pet entity
            pet.setMedication(medication);
        }

        // Save the pet object
        Pet savedPet = petsRepo.save(pet);

        // Return a ResponseEntity with the saved pet and 200 OK status
        return ResponseEntity.ok(savedPet);
    }

//    @PostMapping("/pets")
//    public ResponseEntity<Pet> addPet(@RequestBody Pet petRequest) {
//        Medication medication = MedicationRepository.findById(petRequest.getMedication().getMedicationId())
//                .orElseThrow(() -> new RuntimeException("Medication not found"));
//
//        petRequest.setMedication(medication);
//        Pet savedPet = PetsRepo.save(petRequest);
//
//        return ResponseEntity.ok(savedPet);
//    }

    /**
     * Get a list of all Pets
     * @return list of all Pets
     */
    @Operation(summary = "Returns a list of all pets")
    @GetMapping("/pets")
    public List<Pet> getAllPets(){
        return petsRepo.findAll(); // Retrieve all pets from the repository
    }

    /**
     * Get Pet by id
     * @param id id of Pet
     * @return Pet
     */
    @Operation(summary = "Gets pet information by ID")
    @GetMapping("/pet/{id}")
    public Pet getPetById(@PathVariable int id) {
        return petsRepo.findById(id).orElse(null); // Retrieve pet by ID
    }

    /**
     * Delete Pet by id
     * @param id id of Pet
     * @return informative message with id
     */
    @Operation(summary = "Deletes a Pet by ID")
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
    
    /**
     * Get all Pets of a user
     * @param id id of User
     * @return list of Pets a User has
     */
    @Operation(summary = "Returns a list of Pets owner by a User ID ")
    @GetMapping("/user-pet/{id}")
    public List<Pet> getUserPetsByID(@PathVariable Long id) {
        // Retrieve pets belonging to the user with the given ID
        return petsRepo.findByOwner_Id(id);
    }

    /**
     * Deletes all Pets
     * @return "All pets deleted succesfully."
     */
    @Operation(summary = "Deletes all Pets")
    @DeleteMapping("/pets")
    public String deleteAllPets() {
        petsRepo.deleteAll();
        return "All pets deleted succesfully.";
    }

    /**
     * Update a Pet by id
     * @param id id of Pet
     * @param petDetails the new Pet information
     * @return informative message with id
     */
    @Operation(summary = "Update pet information by ID")
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
