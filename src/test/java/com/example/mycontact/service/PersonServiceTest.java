package com.example.mycontact.service;

import com.example.mycontact.controller.dto.PersonDto;
import com.example.mycontact.domain.Person;
import com.example.mycontact.domain.dto.Birthday;
import com.example.mycontact.exception.PersonNotFoundException;
import com.example.mycontact.exception.RenameNotPermittedException;
import com.example.mycontact.repository.PersonRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    // 각자 역할에 맡게만 테스트하면 된다.

    @InjectMocks
    private PersonService personService;
    @Mock
    private PersonRepository personRepository;

/*
    @Test
    void getAll() {
        when(personRepository.findAll())
                .thenReturn(Lists.newArrayList(new Person("martin"), new Person("dennis"), new Person("tony")));

        List<Person> result = personService.getAll();

        assertThat(result.size()).isEqualTo(3);
        assertThat(result.get(0).getName()).isEqualTo("martin");
        assertThat(result.get(1).getName()).isEqualTo("dennis");
        assertThat(result.get(2).getName()).isEqualTo("tony");
    }

 */

    @Test
    void getAll() {
        when(personRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        Lists.newArrayList(new Person("martin"), new Person("dennis"), new Person("tony"))));

        Page<Person> result = personService.getAll(PageRequest.of(0, 3));

        assertThat(result.getNumberOfElements()).isEqualTo(3);
        assertThat(result.getContent().get(0).getName()).isEqualTo("martin");
        assertThat(result.getContent().get(1).getName()).isEqualTo("dennis");
        assertThat(result.getContent().get(2).getName()).isEqualTo("tony");
    }

    @Test
    void getPeopleByName() {
        when(personRepository.findByName("martin"))
                .thenReturn(Lists.newArrayList(new Person("martin")));

        List<Person> result = personService.getPeopleByName("martin");

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getName()).isEqualTo("martin");
        /*
        List<Person> result = personService.getPeopleByName("kim");

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getName()).isEqualTo("kim");
    */
    }

    @Test
    void getPerson() {
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("martin")));

        Person person = personService.getPerson(1L);

        assertThat(person.getName()).isEqualTo("martin");
        /*
        Person person  = personService.getPerson(3L);

        assertThat(person.getName()).isEqualTo("jhon");

         */
    }

    @Test
    void getPersonifNotFount() {
        when(personRepository.findById(1L))
                .thenReturn(Optional.empty());

        Person person = personService.getPerson(1L);

        assertThat(person).isNull();
    }

    @Test
    void put() {
        //PersonDto dto = PersonDto
        //        .of("martin", "programming", "판교", LocalDate.now(), "programmer", "010-1111-2222");
        // mock에대한 액션을 지정해주지 않음.
        // erturn값이 없거나 하면 따로 지정해줄 필요없음
        // 펄슨 레퍼지토리가 정말 호출되었는지 알 길이 없음.
        // 실제 db에있는 값을 가져와서 검증하듯  가상의 mock 처리라 얜 가져올수 없음.
        // void 리턴에대한 검증 진행.
        //personService.put(dto);
        //verify(personRepository,times(1)).save(any(Person.class));

        personService.put(mockPersonDto());

        verify(personRepository, times(1)).save(argThat(new IsPersonWillBeInserted()));
    }

    @Test
    void modifyIfPersonNotFound() {
        when(personRepository.findById(1L))
                .thenReturn(Optional.empty());

        //personService.modify(1L,mockPersonDto());

        //assertThrows(RuntimeException.class, () -> personService.modify(1L, mockPersonDto()));
        assertThrows(PersonNotFoundException.class, () -> personService.modify(1L, mockPersonDto()));
    }

    @Test
    void modifyIfNameIsDifferent() {
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("tony")));

        //assertThrows(RuntimeException.class, () -> personService.modify(1L, mockPersonDto()));
        assertThrows(RenameNotPermittedException.class, () -> personService.modify(1L, mockPersonDto()));
    }

    @Test
    void modify() {
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("martin")));

        personService.modify(1L, mockPersonDto());

        //verify(personRepository, times(1)).save(any(Person.class));
        verify(personRepository, times(1)).save(argThat(new IsPersonWillBeUpdate()));
    }

    @Test
    void modifyByNameIfPersonNotFound() {
        when(personRepository.findById(1L))
                .thenReturn(Optional.empty());

        //assertThrows(RuntimeException.class, () -> personService.modify(1L, "daniel"));
        assertThrows(PersonNotFoundException.class, () -> personService.modify(1L, "daniel"));
    }

    @Test
    void modifyByName() {
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("martin")));

        personService.modify(1L, "daniel");

        verify(personRepository, times(1)).save(argThat(new IsNameWillBeUpdated()));
    }

    @Test
    void deleteIfPersonNotFound() {
        when(personRepository.findById(1L))
                .thenReturn(Optional.empty());

        //assertThrows(RuntimeException.class, () -> personService.delete(1L));
        assertThrows(PersonNotFoundException.class, () -> personService.delete(1L));
    }

    @Test
    void delete() {
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("martin")));

        personService.delete(1L);

        verify(personRepository, times(1)).save(argThat(new IsPersonWillBeDeleted()));

    }

    //Persondto 생성하는애
    private PersonDto mockPersonDto() {
        return PersonDto
                .of("martin", "programming", "판교", LocalDate.now(), "programmer", "010-1111-2222");
    }

    private static class IsPersonWillBeUpdate implements ArgumentMatcher<Person> {

        @Override
        public boolean matches(Person person) {
            return equals(person.getName(), "martin")
                    && equals(person.getHobby(), "programming")
                    && equals(person.getAddress(), "판교")
                    && equals(person.getBirthday(), Birthday.of(LocalDate.now()))
                    && equals(person.getJob(), "programmer")
                    && equals(person.getPhoneNumber(), "010-1111-2222");
        }

        private boolean equals(Object actual, Object expected) {
            return expected.equals(actual);
        }
    }

    private static class IsPersonWillBeInserted implements ArgumentMatcher<Person> {

        @Override
        public boolean matches(Person person) {
            return equals(person.getName(), "martin")
                    && equals(person.getHobby(), "programming")
                    && equals(person.getAddress(), "판교")
                    && equals(person.getBirthday(), Birthday.of(LocalDate.now()))
                    && equals(person.getJob(), "programmer")
                    && equals(person.getPhoneNumber(), "010-1111-2222");
        }

        private boolean equals(Object actual, Object expected) {
            return expected.equals(actual);
        }
    }

    private static class IsNameWillBeUpdated implements ArgumentMatcher<Person> {

        @Override
        public boolean matches(Person person) {
            return person.getName().equals("daniel");
        }
    }

    private static class IsPersonWillBeDeleted implements ArgumentMatcher<Person> {

        @Override
        public boolean matches(Person person) {
            return person.isDeleted();
        }
    }
}