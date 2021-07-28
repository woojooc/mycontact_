package com.example.mycontact.repository;

import com.example.mycontact.domain.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Test
    void hashCodeAndEquals() {
        Person person1 = new Person("K",10,"A");
        Person person2 = new Person("K",10,"A");

        System.out.println(person1.equals(person2));
        System.out.println(person1.hashCode());
        System.out.println(person2.hashCode());

        Map<Person,Integer> map = new HashMap<>();
        map.put(person1, person1.getAge());

        System.out.println(map);
        System.out.println(map.get(person2));
        // 값이 같기 때문에 person2 로도 값을 가져올 수 있을것이라고 예상
        // => 해쉬 코드가 다르기 때문에 가져올 수 없다.
        // 해쉬 코드를 오버라이딩 해서 생성하면. 같은 값을 가지는 것은 같은 객체로 인식되게 만들어
        // 값을 가져올 수 있게 된다.
        // 객체는 생성될 때마다 해쉬코드가 달라지게 된다.
        // DB 에서 가져온 값을 동일하게 사용하려면 해쉬코드를 오버라이딩 해줘야한다.
    }



}