package com.coms309.demo2.controller;

import com.coms309.demo2.entity.Pet;
import com.coms309.demo2.repository.PetsRepo;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.coms309.demo2.entity.Medication;
import com.coms309.demo2.repository.MedicationRepository;
import java.util.List;



@RestController
@Tag(name = "Pet Controller", description = "Handles pet-related operations and user-pet associations")
public class MyController {
    @Autowired
    PetsRepo petsRepo;

    @Autowired
    MedicationRepository medicationRepo;

    @GetMapping("/mytestapi")
    public String testMyAPI() {
        return "The API works well";
    }

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
    // get user's pets list
    @GetMapping("/user-pet/{id}")
    public List<Pet> getUserPetsByID(@PathVariable Long id) {
        // Retrieve pets belonging to the user with the given ID
        return petsRepo.findByOwner_Id(id);
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
