package com.coms309.demo2.controller;

import com.coms309.demo2.entity.*;
import com.coms309.demo2.repository.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jdk.jfr.Event;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


/**
 * @author Fury Poudel
 * Endpoints for Admin account
 */
@RestController
@Tag(name = "Admin Controller", description = "Shows all endpoints accessible by admin account")

public class AdminController {

    @Autowired
    PetsRepo petsRepo;

    @Autowired
    MedicationRepository repository;

    @Autowired
    private VetsRepo vetsRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventsRepository eventsRepository;

    /**
     * Get a list of all Pets
     * @return list of all Pets
     */
    @Operation(summary = "Returns a list of all pets")
    @GetMapping("/admin/pets")
    public List<Pet> getAllPets(){
        return petsRepo.findAll(); // Retrieve all pets from the repository
    }

    /**
     * Get Pet by id
     * @param id id of Pet
     * @return Pet
     */
    @Operation(summary = "Gets pet information by ID")
    @GetMapping("/admin/pet/{id}")
    public Pet getPetById(@PathVariable int id) {
        return petsRepo.findById(id).orElse(null); // Retrieve pet by ID
    }

    /**
     * Delete Pet by id
     * @param id id of Pet
     * @return informative message with id
     */
    @Operation(summary = "Deletes a Pet by ID")
    @DeleteMapping("/admin/pet/{id}")
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
    @GetMapping("/admin/user-pet/{id}")
    public List<Pet> getUserPetsByID(@PathVariable Long id) {
        // Retrieve pets belonging to the user with the given ID
        return petsRepo.findByOwner_Id(id);
    }

    /**
     * Deletes all Pets
     * @return "All pets deleted succesfully."
     */
    @Operation(summary = "Deletes all Pets")
    @DeleteMapping("/admin/pets")
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
    @PutMapping("/admin/pet/{id}")
    public String updatePet(@PathVariable int id, @RequestBody Pet petDetails) {
        return petsRepo.findById(id).map(pet -> {
            //updates only name & type for now, as only two lines of code for those
            pet.setPet_name(petDetails.getPet_name());
            pet.setPet_type(petDetails.getPet_type());
            petsRepo.save(pet);
            return "Pet with ID " + id + " updated successfully.";
        }).orElse("Pet with ID " + id + " not found.");
    }

    /**
     * Get all Vets
     * @return a list of all the Vets
     */
    @Operation(summary = "Get all Vets", description = "Returns a list of all the vets")
    @GetMapping("/admin/vets")
    public List<Vet> getAllVets() {
        return vetsRepo.findAll(); // Retrieve all vets from the repository
    }

    /**
     * Get a vet by ID
     * @param id id of Vet
     * @return Vet
     */
    @Operation(summary = "Get vet by ID", description = "Gets vet by ID")
    @GetMapping("/admin/vet/{id}")
    public Vet getVetById(@PathVariable int id) {
        return vetsRepo.findById(id).orElse(null); // Retrieve vet by ID
    }

    /**
     * Delete Vet by id
     * @param id id of Vet
     * @return informative message with id
     */
    @Operation(summary = "Delete Vet by ID", description = "Delete vet by ID")
    @DeleteMapping("/admin/vet/{id}")
    public String deleteVet(@PathVariable int id) {
        if (vetsRepo.existsById(id)) {
            vetsRepo.deleteById(id);
            return "Vet with ID " + id + "deleted succesfully.";
        } else {
            return "Vet with ID " + id + "does not exist.";
        }
    }

    /**
     * Delete all Vets
     * @return "All Vets deleted succesfully."
     */
    @Operation(summary = "Delete all vets", description = "Delete all vets")
    @DeleteMapping("/admin/vets")
    public String deleteAllVets() {
        vetsRepo.deleteAll();
        return "All Vets deleted succesfully.";
    }

    /**
     * Create a Vet
     * @param vet new Vet details
     * @return vet
     */
    @Operation(summary = "Create a new Vet profile", description = "Create a new vet")
    @PostMapping("/admin/vet")
    public Vet saveVet(@RequestBody Vet vet) {
        return vetsRepo.save(vet); // Save the vet object to the repository
    }

    /**
     * Get all Reminders scheduled
     */
    @Operation(summary = "Get a list of all events", description = "Returns all events with details like reminder name, time, and ID")
    @GetMapping("/admin/events")
    public List<Events> getAllEvents() {
        return eventsRepository.findAll(); // Retrieves all Events from the database
    }

    /**
     * Create a medication
     * @param med new medication
     * @return medication that was created
     */
    @Operation(summary = "Creates a new medication profile")
    @PostMapping("/admin/inventory")
    public ResponseEntity<Medication> addNewMedication(@RequestBody Medication med) {
        List<Medication> allMeds = repository.findAll();
        for (Medication checkMed : allMeds) {
            if (checkMed.getName().equals(med.getName())) {
                return ResponseEntity.badRequest().build();
            }
        }
        med = repository.save(med);
        return ResponseEntity.ok().body(med);
    }

    /**
     * Updates a medication by id
     * @param id id of the medication you want to update
     * @param med the new medication data
     * @return the new medication data
     */
    @Operation(summary = "Update/Edit medication details")
    @PutMapping("/admin/inventory/{id}")
    public Medication putMedication(@PathVariable Long id, @RequestBody Medication med) {
        Medication oldMed = repository.findById(id).get();
        if (med.getName() != null) oldMed.setName(med.getName()); //can update name by sending a structure that only contains name
        if (med.getStock() != null) oldMed.setStock(med.getStock());
        med = repository.save(oldMed);
        return med;
    }

    /**
     * Get all medications in the inventory and their data
     * @return list of medications in the inventory
     */
    @Operation(summary = "Get all registered medications")
    @GetMapping("/admin/inventory")
    public List<Medication> getAllMedications() {
        List<Medication> meds = repository.findAll();
        return meds;
    }

    /**
     * Get medication by id
     * @param id id of medication
     * @return medication details
     */
    @Operation(summary = "Get medication by ID")
    @GetMapping("/admin/inventory/{id}")
    public Medication getMedication(@PathVariable Long id) {
        //screen based on type of user
        Medication med = repository.findById(id).get();
        return med;
    }

    /**
     * Delete medication by id
     * @param id id of medication
     * @return "ok"
     */
    @Operation(summary = "Delete a medication by ID")
    @DeleteMapping("/admin/inventory/{id}")
    public String removeMedication(@PathVariable Long id) {
        repository.deleteById(id);
        return "Deleted Medication with ID " + id + "successfully.";
    }

    /**
     * Get user by ID, handling case if user is not found
     * @param id user id
     * @return user
     */
    @Operation(summary = "Get user by ID", description = "Gets user by ID and handles case if user is not found")
    @GetMapping("/admin/users/{id}")
    public User getUser(@PathVariable Long id) {
        /// <summary>My super duper data</summary>
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            /// <summary>The unique identifier</summary>
            User foundUser = user.get();
            foundUser.setPassword(null); // Hide password
            return foundUser;
        } else {
            throw new RuntimeException("User not found");
        }
    }

    /**
     * Get all users, hiding their passwords
     * @return a list of all users with passwords obscured
     */
    @Operation(summary = "Get all users", description = "Get all users, hiding their passwords and return a list of all users with passwords obscured")
    @GetMapping("/admin/users")
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        for (User cUser : users) {
            cUser.setPassword(null); // Hide passwords
        }
        return users;
    }

    @Operation(summary = "Delete all users", description = "Delete all users")
    @DeleteMapping("/admin/users")
    public String removeUsers() {
        repository.deleteAll();
        return "all users deleted";
    }

    /**
     * Delete user by ID
     * @param id id of user
     * @return informative message
     */
    @Operation(summary = "Delete user by ID", description = "Delete user by ID")
    @DeleteMapping("/admin/users/{id}")
    public String removeUser(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id); // Delete user
            return "User with ID " + id + " deleted succesfully.";
        } else {
            return "User not found";
        }
    }





}
