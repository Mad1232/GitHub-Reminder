package com.coms309.demo2.controller;

import com.coms309.demo2.entity.Pet;
import com.coms309.demo2.entity.User;
import com.coms309.demo2.entity.Vet;
import com.coms309.demo2.repository.PetsRepo;
import com.coms309.demo2.repository.UserRepository;
import com.coms309.demo2.repository.VetsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class VetController {
    @Autowired
    private VetsRepo vetsRepo;

    @Autowired
    private UserRepository userRepository;

    // Change the return type and method name to reflect that this method retrieves
    // Vets
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

    // Create link between vet and customer
    @PostMapping("/vets/{vetID}/customers/{customerID}")
    public List<User> addCustomer(@PathVariable Integer vetID, @PathVariable Long customerID) {
        Optional<Vet> vetOptional = vetsRepo.findById(vetID);
        if (!vetOptional.isPresent()) {
            throw new RuntimeException("Vet does not exist");
        }
        Vet vet = vetOptional.get();

        Optional<User> customerOptional = userRepository.findById(customerID);
        if (!customerOptional.isPresent()) {
            throw new RuntimeException("User does not exist");
        }
        User customer = customerOptional.get();

        List<User> customers = vet.getCustomers();
        if (!customers.contains(customer)) { // If it does, exit early; list already contains selected user
            customers.add(customer);
            vetsRepo.save(vet);
        }

        return customers;
    }

    // Remove link between vet and customer
    @DeleteMapping("/vets/{vetID}/customers/{customerID}")
    public List<User> removeCustomer(@PathVariable Integer vetID, @PathVariable Long customerID) {
        Optional<Vet> vetOptional = vetsRepo.findById(vetID);
        if (!vetOptional.isPresent()) {
            throw new RuntimeException("Vet does not exist");
        }
        Vet vet = vetOptional.get();

        Optional<User> customerOptional = userRepository.findById(customerID);
        if (!customerOptional.isPresent()) {
            throw new RuntimeException("User does not exist");
        }
        User customer = customerOptional.get();

        List<User> customers = vet.getCustomers();
        customers.remove(customer);
        
        vetsRepo.save(vet);

        return customers;
    }
}
