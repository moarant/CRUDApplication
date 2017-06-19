package io.zipcoder.crudapp;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by mollyarant on 6/16/17.
 */
public interface PersonService {
    List<Person> getAll();

    Person findById(int id);

    Person findByName(String name);

    void create(Person person);

    void update(Person person);

    void delete(int id);

    boolean exists(Person person);

}
