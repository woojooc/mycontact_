package com.example.mycontact.controller;

import com.example.mycontact.domain.Person;
import com.example.mycontact.repository.PersonRepository;
import com.example.mycontact.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/person")
@RestController
@Slf4j
public class PersonController {

    @Autowired
    PersonService personService;

    //log
    @Autowired
    PersonRepository personRepository;

    //@RequestMapping(method = RequestMethod.GET)
    @GetMapping("/{id}")
    //@RequestMapping(value = "/{id}")
    public Person getPerson(@PathVariable Long id) {//@RequestParam Long id) {
        return personService.getPerson(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)     // 200-> 201
    public void postPerson(@RequestBody Person person) {
        log.info("person-> {}",personRepository.findAll());

        personService.put(person);
    }

    @PutMapping("/{id}")
    public void modifyPerson(@PathVariable Long id, @RequestBody Person person) {
        personService.modify(id,person);
        log.info("person-> {}",personRepository.findAll());
    }
}
