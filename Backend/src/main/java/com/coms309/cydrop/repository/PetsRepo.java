package com.coms309.cydrop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coms309.cydrop.entity.Pet;


@Repository
public interface PetsRepo extends JpaRepository<Pet, Integer>{
}
