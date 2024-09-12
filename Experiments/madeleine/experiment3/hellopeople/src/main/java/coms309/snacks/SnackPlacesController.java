package coms309.snacks;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;

/**
 * Controller used to showcase Create and Read from a LIST
 *
 * @author Madeleine Carydis
 */

@RestController
public class SnackPlacesController {

    // Note that there is only ONE instance of SnackPlacesController in
    // Springboot system.
    HashMap<String, SnackPlace> snackPlacesList = new HashMap<>();

    // CRUDL (create/read/update/delete/list)
    // use POST, GET, PUT, DELETE, GET methods for CRUDL

    // THIS IS THE LIST OPERATION
    // gets all the places in the list and returns it in JSON format
    // This controller takes no input.
    // Springboot automatically converts the list to JSON format
    // in this case because of @ResponseBody
    // Note: To LIST, we use the GET method
    @GetMapping("/snackPlaces")
    public HashMap<String, SnackPlace> getAllSnackPlaces() {
        return snackPlacesList;
    }

    // THIS IS THE CREATE OPERATION
    // springboot automatically converts JSON input into a bakery object and
    // the method below enters it into the list.
    // It returns a string message in THIS example.
    // in this case because of @ResponseBody
    // Note: To CREATE we use POST method
    @PostMapping("/snackPlaces/bakery")
    public String createBakeryString(@RequestBody Bakery snackPlace) {
        System.out.println(snackPlace);
        snackPlacesList.put(snackPlace.getName(), snackPlace);
        return "New bakery " + snackPlace.getName() + " Saved";
    }

    // THIS IS THE CREATE OPERATION
    // springboot automatically converts JSON input into a fromagerie and
    // the method below enters it into the list.
    // It returns a string message in THIS example.
    // in this case because of @ResponseBody
    // Note: To CREATE we use POST method
    @PostMapping("/snackPlaces/fromagerie")
    public String createfromagerie(@RequestBody Fromagerie snackPlace) {
        System.out.println(snackPlace);
        snackPlacesList.put(snackPlace.getName(), snackPlace);
        return "New fromagerie " + snackPlace.getName() + " Saved";
    }

    // THIS IS THE READ OPERATION
    // Springboot gets the PATHVARIABLE from the URL
    // We extract the place from the HashMap.
    // springboot automatically converts Person to JSON format when we return it
    // in this case because of @ResponseBody
    // Note: To READ we use GET method
    @GetMapping("/snackPlaces/{name}")
    public SnackPlace getSnackPlace(@PathVariable String name) {
        SnackPlace s = snackPlacesList.get(name);
        return s;
    }

    // THIS IS THE UPDATE OPERATION
    // We extract the place from the HashMap and modify it.
    // Springboot automatically converts the place to JSON format
    // Springboot gets the PATHVARIABLE from the URL
    // Here we are returning what we sent to the method
    // in this case because of @ResponseBody
    // Note: To UPDATE we use PUT method
    @PutMapping("/snackPlaces/{name}")
    public SnackPlace updateSnackPlace(@PathVariable String name, @RequestBody SnackPlace s) {
        snackPlacesList.replace(name, s);
        return snackPlacesList.get(name);
    }

    // THIS IS THE DELETE OPERATION
    // Springboot gets the PATHVARIABLE from the URL
    // We return the entire list -- converted to JSON
    // in this case because of @ResponseBody
    // Note: To DELETE we use delete method

    @DeleteMapping("/snackPlaces/{name}")
    public HashMap<String, SnackPlace> deletePerson(@PathVariable String name) {
        snackPlacesList.remove(name);
        return snackPlacesList;
    }
}
