package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple Hello World Controller to display the string returned
 *
 * @author Vivek Bengre
 */

@RestController
class WelcomeController {

    @GetMapping("/welcome")
    public String welcome() {
        return "The port is changed to 8081, this is Se309, springboot_unit2";
    }
}
