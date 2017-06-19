package io.zipcoder.crudapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * Created by mollyarant on 6/16/17.
 */
@RestController
@RequestMapping("/person")
public class PersonController {
    private final Logger log = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonService personService;

    @RequestMapping (method= RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    //retrieve people list.  If null or empty, return No Content.  If !empty, return list of people & OK
    public ResponseEntity<List<Person>>getAllPeople(){
        log.info("retrieving all people");
        List<Person> people= personService.getAll();
        if (people== null || people.isEmpty()){
            log.info("no people were found");
            return new ResponseEntity<List<Person>>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<Person>>(HttpStatus.OK);

    }

    @RequestMapping(value="/{id}",method= RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    //get user by ID.  If present, return user & OK. If not, return Not Found.
    public ResponseEntity<Person> get(@PathVariable("id") int id){
        log.info("retrieving person with id: {}", id);
        Person person= personService.findById(id);

        if (person == null){
            log.info("person with id {} not found", id);
            return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Person>(person, HttpStatus.OK);
    }

    @RequestMapping(value="/{id}",method= RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    //search for person, delete if there and return ok.  If not, return Not Found.
    public ResponseEntity<Void> deletePerson(@PathVariable("id") int id){
        log.info("deleting a person with id {}", id);
        Person person = personService.findById(id);

        if (person == null){
            log.info("unable to delete because person with id {} was not found", id);
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        personService.delete(id);
        return new ResponseEntity<Void>(HttpStatus.OK);

    }

    @RequestMapping(method= RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    //create a new person if it does not already exist.
        public ResponseEntity<Void> createPerson(@RequestBody Person person, UriComponentsBuilder pcBuilder){
            log.info("creating a new person {}", person);

            if (personService.exists(person)){
                log.info("Person with name"+ person.getName()+"already exists");
                return new ResponseEntity<Void>(HttpStatus.CONFLICT);
            }
            personService.create(person);

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(pcBuilder.path("/person/{id}").buildAndExpand(person.getId()).toUri());
            return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
        }

    @RequestMapping(method= RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    //search for user, if found update user information
    public ResponseEntity<Person> updatePerson(@PathVariable int id, @RequestBody Person person){
            log.info("updating person {}", person);
            Person targetPerson= personService.findById(id);

            if(targetPerson == null){
                log.info("Person with id {} not found", id);
                return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
            }

            targetPerson.setId(person.getId());
            targetPerson.setAge(person.getAge());
            targetPerson.setName(person.getName());

            personService.update(person);

            return new ResponseEntity<Person>(targetPerson, HttpStatus.OK);

    }




}
