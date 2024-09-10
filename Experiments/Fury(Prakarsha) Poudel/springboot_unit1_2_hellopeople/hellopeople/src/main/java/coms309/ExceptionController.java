package coms309;

/**
 * Controller used to showcase what happens when an exception is thrown
 *
 * @author Vivek Bengre
 */

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ExceptionController {

    @RequestMapping(method = RequestMethod.GET, path = "/error")
    public String triggerException() {
        throw new RuntimeException("Check to see what happens when an exception is thrown");
    }
// this is what happens when springboot receives an error without any customer error message way.
}

