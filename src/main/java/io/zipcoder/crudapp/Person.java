package io.zipcoder.crudapp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by mollyarant on 6/15/17.
 */

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    public String name;
    public int age;

    public Person(int id, String name, int age ){
        this.id= id;
        this.name=name;
        this.age=age;

    }

    public Person(){

    }
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return "Person{"+
                "id=" + id +
                ", name='"+ name + '\''+
                "}";
    }

    @Override
    public boolean equals (Object o){
        if (this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if(id != person.id) return false;
        if(name != null ? !name.equals(person.name) : person.name != null) return false;

        return true;
    }
}
