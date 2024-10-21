package com.coms309.demo2.controller;

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




@RestController
public class InventoryController {
    @Autowired
    MedicationRepository repository;

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

    //update structure?
    @PutMapping("/inventory/{id}")
    public Medication putMedication(@PathVariable Long id, @RequestBody Medication med) {
        Medication oldMed = repository.findById(id).get();
        if (med.getName() != null) oldMed.setName(med.getName()); //can update name by sending a structure that only contains name
        if (med.getStock() != null) oldMed.setStock(med.getStock());
        med = repository.save(oldMed);
        return med;
    }

    @GetMapping("/inventory")
    public List<Medication> getAllMedications() {
        List<Medication> meds = repository.findAll();
        return meds;
    }

    @GetMapping("/inventory/{id}")
    public Medication getMedication(@PathVariable Long id) {
        //screen based on type of user
        Medication med = repository.findById(id).get();
        return med;
    }

    @DeleteMapping("/inventory/{id}")
    public String removeMedication(@PathVariable Long id) {
        repository.deleteById(id);
        return "Ok";
    }
}
