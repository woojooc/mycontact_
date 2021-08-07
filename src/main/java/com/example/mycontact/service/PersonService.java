package com.example.mycontact.service;


import com.example.mycontact.controller.dto.PersonDto;
import com.example.mycontact.domain.Person;
import com.example.mycontact.exception.PersonNotFoundException;
import com.example.mycontact.exception.RenameNotPermittedException;
import com.example.mycontact.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
public class PersonService {

    @Autowired
    PersonRepository personRepository;


    @Transactional(readOnly = true)
    public Person getPerson(Long id) {
        return personRepository.findById(id).orElse(null);
        //Person person = personRepository.findById(id).orElse(null);
        //log.info("person : {}",person);
        //return person;
    }

    public List<Person> getPeopleByName(String name) {

        return personRepository.findByName(name);
    }

    @Transactional
    public void put(PersonDto personDto) {
        Person person = new Person();
        person.set(personDto);
        person.setName(personDto.getName());

        personRepository.save(person);
    }

    @Transactional
    public void modify(Long id, PersonDto personDto) {
        //Person person = personRepository.findById(id).orElseThrow(()->new RuntimeException("아이디가 존재하지 않습니다."));
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);

        if(!person.getName().equals(personDto.getName())) {
            //throw new RuntimeException("이름이 다릅니다.");
            throw new RenameNotPermittedException();
        }

        person.set(personDto);

        personRepository.save(person);
    }

    @Transactional
    public void modify(Long id, String name) {
        //Person person = personRepository.findById(id).orElseThrow(()->new RuntimeException("아이디가 존재하지 않습니다."));
        //Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException("아이디가 존재하지 않습니다."));
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);

        person.setName(name);
        personRepository.save(person);
    }

    @Transactional
    public void delete(Long id) {
        //Person person = personRepository.findById(id).orElseThrow(()->new RuntimeException("아이디가 존재하지 않습니다."));
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);

        person.setDeleted(true);
        personRepository.save(person);
    }

    /*
    public List<Person> getAll() {
        return personRepository.findAll();
    }
    */
    public Page<Person> getAll(Pageable pageable) {
        return personRepository.findAll(pageable);
    }

    public List<Person> getBirthdayFriends() {

        List<Person> people1 = personRepository.findByMonthOfBirthdayAndDayOfBirthday(LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
        List<Person> people2 = personRepository.findByMonthOfBirthdayAndDayOfBirthday(LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth()+1);

        List<Person> birthdayPeople = new ArrayList<>();
        birthdayPeople.addAll(people1);
        birthdayPeople.addAll(people2);

        log.info("birthday person : {}",birthdayPeople);

        return birthdayPeople;
    }
}
