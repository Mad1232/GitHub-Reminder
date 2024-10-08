package com.coms309.demo2.controller;

import com.coms309.demo2.entity.Pet;
import com.coms309.demo2.repository.PetsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LoginController {

    @GetMapping("/user/email")
    public String user_email() {
        return "The API works well";
    }

    @GetMapping("/user/pass")
    public String user_password() {
        return "This  API alos does indeed works well";
    }




}
