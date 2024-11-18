package com.coms309.demo2.controller;

import com.coms309.demo2.entity.Pet;
import com.coms309.demo2.entity.User;
import com.coms309.demo2.entity.Vet;
import com.coms309.demo2.repository.PetsRepo;
import com.coms309.demo2.repository.UserRepository;
import com.coms309.demo2.repository.VetsRepo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Fury Poudel and Madeleine Carydis
 * Creates and updates a vet and allows the vet to link themself to customers
 */
@RestController
@Tag(name = "Vet Controller", description = "Manages veterinarians and their customer assignments")
public class VetController {
    @Autowired
    private VetsRepo vetsRepo;

    @Autowired
    private UserRepository userRepository;

    // Change the return type and method name to reflect that this method retrieves
    // Vets
    /**
     * Get all Vets
     * @return a list of all the Vets
     */
    @Operation(summary = "Get all Vets", description = "Returns a list of all the vets")
    @GetMapping("/vets")
    public List<Vet> getAllVets() {
        return vetsRepo.findAll(); // Retrieve all vets from the repository
    }

    /**
     * Get a vet by ID
     * @param id id of Vet
     * @return Vet
     */
    @Operation(summary = "Get vet by ID", description = "Gets vet by ID")
    @GetMapping("/vet/{id}")
    public Vet getVetById(@PathVariable int id) {
        return vetsRepo.findById(id).orElse(null); // Retrieve vet by ID
    }

    /**
     * Delete Vet by id
     * @param id id of Vet
     * @return informative message with id
     */
    @Operation(summary = "Delete Vet by ID", description = "Delete vet by ID")
    @DeleteMapping("/vet/{id}")
    public String deleteUser(@PathVariable int id) {
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
    @DeleteMapping("/vets")
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
    @PostMapping("/vet")
    public Vet saveVet(@RequestBody Vet vet) {
        return vetsRepo.save(vet); // Save the vet object to the repository
    }

    /**
     * Customer with id or email
     */
    public static class CustomerID {
        @Getter
        private Long id;
        
        @Getter
        private String email;
    }

    /** 
     * Create link between vet and customer
     * Valid bodies:
     * <ul>
     *   <li> { "id": 7 }
     *   <li> { "email": "foo@bar.baz" }
     * </ul>
     * 
     * @param vetID id of vet
     * @param customerID id or email of customer
     * @return customer
     */
    @Operation(summary = "Create link between vet and customer")
    @PostMapping("/vets/{vetID}/customers")
    public User addCustomer(@PathVariable Integer vetID, @RequestBody CustomerID customerID) {
        Optional<Vet> vetOptional = vetsRepo.findById(vetID);
        if (!vetOptional.isPresent()) {
            throw new RuntimeException("Vet does not exist");
        }
        Vet vet = vetOptional.get();

        Optional<User> customerOptional;
        if (customerID.id != null) {
            customerOptional = userRepository.findById(customerID.id);
        } else if (customerID.email != null) {
            customerOptional = userRepository.findByEmail(customerID.email);
        } else {
            throw new RuntimeException("Invalid body");
        }

        if (!customerOptional.isPresent()) {
            throw new RuntimeException("User does not exist");
        }
        User customer = customerOptional.get();

        List<User> customers = vet.getCustomers();
        if (!customers.contains(customer)) { // If it does, exit early; list already contains selected user
            customers.add(customer);
            vetsRepo.save(vet);
        }

        return customer;
    }

    /**
     * Remove link between vet and customer
     * @param vetID id of vet 
     * @param customerID id of customer
     * @return Vet's new list of customers
     */
    @Operation(summary = "Removes a link between vet and customer")
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
