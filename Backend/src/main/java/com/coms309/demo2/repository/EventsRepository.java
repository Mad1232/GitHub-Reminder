package com.coms309.demo2.repository;


import com.coms309.demo2.entity.Events;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventsRepository extends JpaRepository<Events, Long> {
    // JpaRepository already provides all CRUD methods, so no need to define anything extra
}