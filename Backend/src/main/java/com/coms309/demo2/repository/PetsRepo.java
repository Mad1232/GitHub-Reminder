package com.coms309.demo2.repository;

import com.coms309.demo2.entity.Pet;


import com.coms309.demo2.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PetsRepo extends JpaRepository<Pet, Integer>{
    // Custom query to get all pets by owner ID (user ID)
    List<Pet> findByOwner_Id(Long ownerId);
}
