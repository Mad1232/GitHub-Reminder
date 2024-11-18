package com.coms309.demo2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RestController;

import com.coms309.demo2.entity.Medication;
import com.coms309.demo2.repository.MedicationRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * @author Fury Poudel and Madeleine Carydis
 * Creates and updates medications in the inventory
 */

@RestController
@Tag(name = "Inventory Controller", description = "Handles inventory management for supplies and resources")

public class InventoryController {
    @Autowired
    MedicationRepository repository;

    /**
     * Create a medication
     * @param med new medication
     * @return medication that was created
     */
    @Operation(summary = "Creates a new medication profile")
    @PostMapping("/inventory")
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
    @PutMapping("/inventory/{id}")
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
    @GetMapping("/inventory")
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
    @GetMapping("/inventory/{id}")
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
    @DeleteMapping("/inventory/{id}")
    public String removeMedication(@PathVariable Long id) {
        repository.deleteById(id);
        return "Ok";
    }
}
