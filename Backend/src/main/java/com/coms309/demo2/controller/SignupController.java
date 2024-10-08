<<<<<<<< HEAD:Backend/src/main/java/com/coms309/cydrop/controller/SignupController.java
package com.coms309.cydrop.controller;
========
package com.coms309.demo2.controller;
>>>>>>>> fury-branch:Backend/src/main/java/com/coms309/demo2/controller/SignupController.java

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

<<<<<<<< HEAD:Backend/src/main/java/com/coms309/cydrop/controller/SignupController.java
import com.coms309.cydrop.entity.User;
import com.coms309.cydrop.repository.UserRepository;
========
import com.coms309.demo2.entity.User;
import com.coms309.demo2.repository.UserRepository;
>>>>>>>> fury-branch:Backend/src/main/java/com/coms309/demo2/controller/SignupController.java

import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class SignupController {
    @Autowired
    UserRepository repository;

    @PostMapping("/signup")
    public String signup(@RequestBody User user) {
        List<User> allUsers = repository.findAll();
        for (User checkUser : allUsers) {
            if (checkUser.getUsername().equals(user.getUsername())) {
                return "User already exists";
            }
        }
        user = repository.save(user);
        // save is our Create mapping
        return "OK";
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        User user = repository.findById(id).get();
        user.setPassword(null);
        // that way someone's password is hidden
        return user;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        List<User> users = repository.findAll();
        for (User cUser : users) {
            cUser.setPassword(null);
        }
        return users;
    }

    @PutMapping("/users/{id}/username")
    public String changeUsername(@PathVariable Long id, @RequestBody String username) {
        User user = repository.findById(id).get();
        user = repository.save(user);
        return "Ok";
    }

    @DeleteMapping("/users/{id}")
    public String removeUser(@PathVariable Long id) {
        repository.deleteById(id);
        return "OK";
    }
}
