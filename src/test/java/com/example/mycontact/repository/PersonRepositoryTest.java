package com.example.mycontact.repository;

import com.example.mycontact.domain.Person;
import com.example.mycontact.domain.dto.Birthday;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    void crud() {
        Person person = new Person();
        person.setName("WJ");
        person.setAge(10);
        person.setBloodType("A");

        personRepository.save(person);

        //System.out.println(personRepository.findAll());
            //문제 1 : 객체의 해쉬값 출력 -> toString 오버라이딩
            //문제 2 : Person 생성자 없음. SET,GET -> 1. inteliJ 자동완성 기능 사용, 2. getter setter
            //문제 3 : 자동화된 테스트가 아님. 그냥 콘솔에 로그만 찍을 뿐

        //List<Person> people = personRepository.findAll();
        List<Person> people = personRepository.findByName("WJ");

        assertThat(people.size()).isEqualTo(1);
        assertThat(people.get(0).getName()).isEqualTo("WJ");
        assertThat(people.get(0).getAge()).isEqualTo(10);
        assertThat(people.get(0).getBloodType()).isEqualTo("A");

        //personRepository.deleteAll();
    }

    /*
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
*/

    @Test
    void findByBloodType() {
        /*
        givenPerson("kim",10,"A");
        givenPerson("david",12,"B");
        givenPerson("aniel",13,"O");
        givenPerson("martin",4,"AB");
        givenPerson("ff",56,"AB");
        */
        //Person result = personRepository.findByBloodType("A");
        List<Person> result = personRepository.findByBloodType("AB");

        //result.forEach(System.out::println);
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getName()).isEqualTo("mario");
        assertThat(result.get(1).getName()).isEqualTo("dalis");
    }

    @Test
    void findByBirthdayBetween() {
/*
        givenPerson("kim",10,"A",LocalDate.of(1992,8,16));
        givenPerson("david",12,"B",LocalDate.of(1991,8,16));
        givenPerson("aniel",13,"O",LocalDate.of(1991,7,16));
        givenPerson("martin",4,"AB",LocalDate.of(1991,8,30));
        givenPerson("ff",56,"AB",LocalDate.of(1992,8,11));
*/
        //List<Person> result = personRepository.findByBirthdayBetween(LocalDate.of(1991,8,1),LocalDate.of(1991,8,31));

        List<Person> result = personRepository.findByMonthOfBirthday(8);
        //List<Person> result2 = personRepository.findByMonthOfBirthdayAndDayOfBirthday(8,30);

        //result.forEach(System.out::println);
        //result2.forEach(System.out::println);

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getName()).isEqualTo("martin");
        assertThat(result.get(1).getName()).isEqualTo("mario");
    }

    /*
    private void givenPerson(String name, int age, String bloodType) {
        givenPerson(name,age,bloodType,null);
    }

    private void givenPerson(String name, int age, String bloodType,LocalDate birthday) {
        Person person = new Person(name,age,bloodType);
        //person.setBirthday(new Birthday(birthday.getYear(),birthday.getMonthValue(),birthday.getDayOfMonth()));
        person.setBirthday(new Birthday(birthday));

        personRepository.save(person);
    }
*/
}