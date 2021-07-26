package com.example.mycontact.repository;

import com.example.mycontact.domain.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    void crud() {
        Person person = new Person();
        person.setName("WJ");
        person.setAge(10);

        personRepository.save(person);

        //System.out.println(personRepository.findAll());
            //문제 1 : 객체의 해쉬값 출력 -> toString 오버라이딩
            //문제 2 : Person 생성자 없음. SET,GET -> 1. inteliJ 자동완성 기능 사용, 2. getter setter
            //문제 3 : 자동화된 테스트가 아님. 그냥 콘솔에 로그만 찍을 뿐

        List<Person> people = personRepository.findAll();
        assertThat(people.size()).isEqualTo(1);
        assertThat(people.get(0).getName()).isEqualTo("WJ");
        assertThat(people.get(0).getAge()).isEqualTo(10);

    }

}