package com.example.mycontact.controller;

import com.example.mycontact.controller.dto.PersonDto;
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
        //log.info("person-> {}", personRepository.findAll());
        personService.put(person);
    }

    @PutMapping("/{id}")
    public void modifyPerson(@PathVariable Long id, @RequestBody PersonDto personDto) {
        personService.modify(id, personDto);
        //log.info("person-> {}", personRepository.findAll());
    }

    //일부 리소스만 업데이트
    @PatchMapping("/{id}")
    public void modifyPerson(@PathVariable Long id, String name) {
        personService.modify(id, name);
        //log.info("person-> {}", personRepository.findAll());
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id) {
        personService.delete(id);
        //log.info("person-> {}", personRepository.findAll());
    }

    //검증 방법 1 리턴  : 실제 지웠는지 상관없이 true 리턴가능해서 부족
//    @DeleteMapping("/{id}")
//    public boolean deletePerson(@PathVariable Long id) {
//        personService.delete(id);
//        log.info("person-> {}", personRepository.findAll());
//
//        return true;
//    }

    //검증 방법 2  삭제된 데이터 리턴
//    @DeleteMapping("/{id}")
//    public boolean deletePerson(@PathVariable Long id) {
//        personService.delete(id);
//        log.info("person-> {}", personRepository.findAll());
//
//        return personRepository.findPeopleDeleted().stream().anyMatch(person -> person.getId().equals(id));
//    }

}
