package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
class WelcomeController {

    @GetMapping("/se309")
    public String welcome() {
        return "This is a 309 class, so welcome everyone";
    }

    @GetMapping("/fury")
    public String fury() {
        return "This is the fury endpoint, showing some extra functionality!";
    }

    @GetMapping("/{name}")
    public String welcome(@PathVariable String name) {
        return "Hello and welcome to COMS 309: " + name;
    }
}
