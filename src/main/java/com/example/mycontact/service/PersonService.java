package com.example.mycontact.service;


import com.example.mycontact.domain.Block;
import com.example.mycontact.domain.Person;
import com.example.mycontact.repository.BlockRepository;
import com.example.mycontact.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    //@Autowired
    //BlockRepository blockRepository;

    //차단 제외 전체 사람 조회
    public List<Person> getPeopleExcludeBlocks() {
        //List<Person> people = personRepository.findAll();

        // List<Block> blocks = blockRepository.findAll();
       // List<String> blockNames = blocks.stream().map(Block::getName).collect(Collectors.toList());
       // return people.stream().filter(person->!blockNames.contains(person.getName())).collect(Collectors.toList());


        //return people.stream().filter(person->person.getBlock() == null).collect(Collectors.toList());
    return personRepository.findByBlockIsNull();
    }

    @Transactional(readOnly = true)
    public Person getPerson(Long id) {
        Person person = personRepository.findById(id).get();

        //System.out.println("person : " + person);
        log.info("person : {}",person);
        // 프로덕션에 배포되었을 때 log 출력을 제한할 수 있따. 프로덕션 코드에서는 로그ㄹ활용하는 것이 좋다.

        return person;
    }

    public List<Person> getPeopleByName(String name) {
        //List<Person> people = personRepository.findAll();

        //return people.stream().filter(person -> person.getName().equals(name)).collect(Collectors.toList());
        return personRepository.findByName(name);
    }
}
