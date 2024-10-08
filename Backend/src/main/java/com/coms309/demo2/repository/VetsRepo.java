package com.coms309.demo2.repository;

import com.coms309.demo2.entity.Pet;
import com.coms309.demo2.entity.Vet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Repository
public interface VetsRepo extends JpaRepository<Vet, Integer>{

}
