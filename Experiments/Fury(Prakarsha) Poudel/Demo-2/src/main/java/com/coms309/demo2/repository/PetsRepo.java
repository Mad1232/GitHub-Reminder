package com.coms309.demo2.repository;

import com.coms309.demo2.entity.Pet;


import com.coms309.demo2.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PetsRepo extends JpaRepository<Pet, Integer>{
}
