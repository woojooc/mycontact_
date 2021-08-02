package com.example.mycontact.service;

import com.example.mycontact.domain.Block;
import com.example.mycontact.domain.Person;
import com.example.mycontact.repository.BlockRepository;
import com.example.mycontact.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonServiceTest {

    @Autowired
    private PersonService personService;

   // @Autowired
    //private PersonRepository personRepository;

   // @Autowired
    //private BlockRepository blockRepository;

    @Test
    void getPeopleExcludeBlocks() {
        //givenPeople();
        //givenBlocks();

        // 블럭되지 않은 사람들 리스트트
       List<Person> result = personService.getPeopleExcludeBlocks();

        //System.out.println(result);
        // 리스트의 객체가 한줄씩
        //result.forEach(System.out::println);

        assertThat(result.size()).isEqualTo(3);
        //assertThat(result.get(0).getName()).isEqualTo("jhon");
        //assertThat(result.get(1).getName()).isEqualTo("mario");
        assertThat(result.get(0).getName()).isEqualTo("martin");
        assertThat(result.get(1).getName()).isEqualTo("kim");
        assertThat(result.get(2).getName()).isEqualTo("dalis");
    }

    @Test
    void getPeopleByName() {
        //givenPeople();
        List<Person> result = personService.getPeopleByName("kim");

        //result.forEach(System.out::println);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getName()).isEqualTo("kim");
    }

/*
    @Test
    void cascadeTest() {
        givenPeople();

        List<Person> result = personRepository.findAll();

        result.forEach(System.out::println);

        Person person = result.get(3);
        person.getBlock().setStartDate(LocalDate.now());
        person.getBlock().setEndDate(LocalDate.now());

        personRepository.save(person);
        personRepository.findAll().forEach(System.out::println);

//        personRepository.delete(person);
//        personRepository.findAll().forEach(System.out::println);
//        blockRepository.findAll().forEach(System.out::println);

        person.setBlock(null);
        personRepository.save(person);
        personRepository.findAll().forEach(System.out::println);
        blockRepository.findAll().forEach(System.out::println);
        // block에는 남아있어서 orphanemove true

    }
*/

    @Test
    void getPerson() {
        //givenPeople();

        Person person  = personService.getPerson(3L);

        //System.out.println(person);

        assertThat(person.getName()).isEqualTo("jhon");
    }

    /*
    private void givenBlocks() {
        givenBlock("kim");
    }
    private Block givenBlock(String name) {
        return blockRepository.save(new Block(name));
    }


    private void givenPeople() {
        givenPerson("kim",10,"A");
        givenPerson("david",10,"B");
        givenBlockPerson("alis",14,"O");
        givenBlockPerson("kim",11,"AB");

    }

    private void givenBlockPerson(String name, int age, String bloodType) {
        Person blockPerson = new Person(name,age,bloodType);
        //blockPerson.setBlock(givenBlock(name));
        blockPerson.setBlock(new Block(name));

        personRepository.save(blockPerson);
    }

    private void givenPerson(String name, int age, String bloodType) {
        personRepository.save(new Person(name,age,bloodType));
    }
    */
}