package com.coms309.demo2.repository;

import com.coms309.demo2.entity.Medication;
import com.coms309.demo2.entity.Pet;


import com.coms309.demo2.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PetsRepo extends JpaRepository<Pet, Integer>{
    // Custom query to get all pets by owner ID (user ID)
    List<Pet> findByOwner_Id(Long ownerId);

//    Optional<Pet> findByName(String name); // Method to find medication by name
}
