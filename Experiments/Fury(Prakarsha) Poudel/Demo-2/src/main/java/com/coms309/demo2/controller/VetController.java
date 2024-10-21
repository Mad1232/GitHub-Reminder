package com.coms309.demo2.controller;

import com.coms309.demo2.entity.Pet;
import com.coms309.demo2.entity.Vet;
import com.coms309.demo2.repository.PetsRepo;
import com.coms309.demo2.repository.VetsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VetController {
    @Autowired
    private VetsRepo vetsRepo;

    // Change the return type and method name to reflect that this method retrieves Vets
    @GetMapping("/vets")
    public List<Vet> getAllVets() {
        return vetsRepo.findAll(); // Retrieve all vets from the repository
    }

    // Get a vet by ID
    @GetMapping("/vet/{id}")
    public Vet getVetById(@PathVariable int id) {
        return vetsRepo.findById(id).orElse(null); // Retrieve vet by ID
    }

    @DeleteMapping("/vet/{id}")
    public String deleteUser(@PathVariable int id) {
        if (vetsRepo.existsById(id)) {
            vetsRepo.deleteById(id);
            return "Vet with ID " + id + "deleted succesfully.";
        } else {
            return "Vet with ID " + id + "does not exist.";
        }
    }

    @DeleteMapping("/vets")
    public String deleteAllUsers() {
        vetsRepo.deleteAll();
        return "All Vets deleted succesfully.";
    }

    @PostMapping("/vet")
    public Vet saveVet(@RequestBody Vet vet) {
        return vetsRepo.save(vet); // Save the vet object to the repository
    }


}
