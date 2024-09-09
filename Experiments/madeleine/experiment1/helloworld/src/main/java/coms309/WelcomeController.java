package coms309;

import org.springframework.aot.hint.support.FilePatternResourceHintsRegistrar;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PatchExchange;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.File;
import java.io.FileNotFoundException;;

@RestController
class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "Hello and welcome to COMS 309";
    }

    @GetMapping("/{name}/{greeting}")
    public String welcome(@PathVariable String name, @PathVariable String greeting) {
        return greeting + " and welcome to COMS 309: " + name;
    }

    @GetMapping("/cat/{name}")
    public String welcome_cat(@PathVariable String name) {
        return name + "\n catcatcatcatcatcatcat\n";
    }
}
