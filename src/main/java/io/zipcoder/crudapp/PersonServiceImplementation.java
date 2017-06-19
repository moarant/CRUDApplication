package io.zipcoder.crudapp;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by mollyarant on 6/18/17.
 */
@Service
public class PersonServiceImplementation implements PersonService {
    private static final AtomicInteger counter = new AtomicInteger();
    private static List <Person> people = new ArrayList<Person>(
            Arrays.asList(
                    new Person(counter.incrementAndGet(), "Paul", 35),
                    new Person(counter.incrementAndGet(), "Shaun", 22),
                    new Person(counter.incrementAndGet(), "Olivia", 55)));

    @Override
    //return entire list of people
    public List<Person> getAll() {
        return people;
    }

    @Override
    //search for a specific person by their id
    public Person findById(int id) {
        for (Person person: people){
            if (person.getId() == id){
                return person;
            }
        }
        return null;
    }

    @Override
    //find a specific person by name.  Check that name given in search matches a person in the list.
    //if so, return that person
    public Person findByName(String name) {
        for (Person person: people){
            if (person.getName().equals(name)){
                return person;
            }
        }
        return null;
    }

    @Override
    //add a new person to the list.  increment the id, then take in person information
    public void create(Person person) {
        person.setId(counter.incrementAndGet());
        people.add(person);

    }

    @Override
    //update a specific person based on their user id.  give id, then updated person details.
    public void update(Person person) {
        int index= people.indexOf(findById(person.getId()));
        people.set(index, person);

    }

    @Override
    //remove a person from list based on a targeted id.
    public void delete(int id) {
        Person targetPerson = findById(id);
        people.remove(targetPerson);

    }

    @Override
    //if that name exists, return that person.
    public boolean exists(Person person) {
        return findByName(person.getName())!= null;
    }
}
