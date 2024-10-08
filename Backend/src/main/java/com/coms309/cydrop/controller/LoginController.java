package com.coms309.cydrop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.coms309.cydrop.entity.Pet;
import com.coms309.cydrop.repository.PetsRepo;

import java.util.List;

@RestController
public class LoginController {

    @GetMapping("/user/email")
    public String user_email() {
        return "The API works well";
    }

    @GetMapping("/user/pass")
    public String user_password() {
        return "The API works well";
    }




}
