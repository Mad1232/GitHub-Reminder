package com.coms309.demo2.controller;

import com.coms309.demo2.entity.Pet;
import com.coms309.demo2.entity.Vet;
import com.coms309.demo2.repository.PetsRepo;
import com.coms309.demo2.repository.VetsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pet-vet")
public class PetVetAssignController {

    @Autowired
    private PetsRepo petRepository;

    @Autowired
    private VetsRepo vetRepository;

    // Assign a Vet to a Pet
    @PostMapping("/assign/{petId}/{vetId}")
    public ResponseEntity<String> assignVetToPet(
            @PathVariable int petId, @PathVariable int vetId) {
        Pet pet = petRepository.findById(petId).orElseThrow();
        Vet vet = vetRepository.findById(vetId).orElseThrow();

        pet.getVeterinarians().add(vet);
        petRepository.save(pet);

        return ResponseEntity.ok("Vet assigned to pet successfully.");
    }

    // Get all Vets for a Pet
    @GetMapping("/pets/{petId}/vets")
    public List<Vet> getVetsForPet(@PathVariable int petId) {
        Pet pet = petRepository.findById(petId).orElseThrow();
        return pet.getVeterinarians();
    }

    // Get all Pets for a Vet
    @GetMapping("/vets/{vetId}/pets")
    public List<Pet> getPetsForVet(@PathVariable int vetId) {
        Vet vet = vetRepository.findById(vetId).orElseThrow();
        return vet.getPets();
    }

    // Remove a Vet from a Pet
    @DeleteMapping("/remove/{petId}/{vetId}")
    public ResponseEntity<String> removeVetFromPet(
            @PathVariable int petId, @PathVariable int vetId) {
        Pet pet = petRepository.findById(petId).orElseThrow();
        Vet vet = vetRepository.findById(vetId).orElseThrow();

        pet.getVeterinarians().remove(vet);
        petRepository.save(pet);

        return ResponseEntity.ok("Vet removed from pet successfully.");
    }
}
