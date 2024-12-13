package com.coms309.demo2.repository;


import com.coms309.demo2.entity.Events;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventsRepository extends JpaRepository<Events, Long> {
    // JpaRepository already provides all CRUD methods, so no need to define anything extra
    // Custom query to fetch events by user ID through the Pet relationship
    @Query("SELECT e FROM Events e WHERE e.pet.owner.id = :userId")
    List<Events> findEventsByUserId(@Param("userId") Long userId);

    @Query("SELECT e FROM Events e WHERE e.pet.pet_id = :petId")
    List<Events> findEventsByPetId(@Param("petId") int petId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Events e WHERE e.pet.pet_id = :petId")
    void deleteEventsByPetId(@Param("petId") int petId);
}