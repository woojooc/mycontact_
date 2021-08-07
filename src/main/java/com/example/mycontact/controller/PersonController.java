package com.example.mycontact.controller;

import com.example.mycontact.controller.dto.PersonDto;
import com.example.mycontact.domain.Person;
import com.example.mycontact.exception.PersonNotFoundException;
import com.example.mycontact.exception.RenameNotPermittedException;
import com.example.mycontact.exception.dto.ErrorResponse;
import com.example.mycontact.repository.PersonRepository;
import com.example.mycontact.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping(value = "/api/person")
@RestController
@Slf4j
public class PersonController {

    @Autowired
    PersonService personService;

    //log
    @Autowired
    PersonRepository personRepository;

    //@GetMapping
    //public List<Person> getAll(){return personService.getAll();}

    @GetMapping
    public Page<Person> getAll(@PageableDefault Pageable pageable){
        return personService.getAll(pageable);
    }

    //@RequestMapping(method = RequestMethod.GET)
    @GetMapping("/{id}")
    //@RequestMapping(value = "/{id}")
    public Person getPerson(@PathVariable Long id) {//@RequestParam Long id) {
        return personService.getPerson(id);
    }

    //펄슨으로 그냥 받으면 위험 ID 나 delete같은건 사용자 입력 받는게 아님.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)     // 200-> 201
    public void postPerson(@RequestBody @Valid PersonDto personDto) {
        //log.info("person-> {}", personRepository.findAll());
        personService.put(personDto);
    }

    @PutMapping("/{id}")
    public void modifyPerson(@PathVariable Long id, @RequestBody PersonDto personDto) {
        /*
        try {
            personService.modify(id, personDto);
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(),ex);
        }
*/
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

    // Per Con에서만 적용됨.
    /*
    @ExceptionHandler(value = RenameNotPermittedException.class)
    public ResponseEntity<ErrorResponse> handleRenameNoPermittedException(RenameNotPermittedException ex) {
        //return new ResponseEntity<>(ErrorResponse.of(HttpStatus.BAD_REQUEST.value(),ex.getMessage()),HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(ErrorResponse.of(HttpStatus.BAD_REQUEST,ex.getMessage()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = PersonNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePersonNotFoundException(PersonNotFoundException ex) {
        return new ResponseEntity<>(ErrorResponse.of(HttpStatus.BAD_REQUEST,ex.getMessage()),HttpStatus.BAD_REQUEST);
    }

    //최상위
    @ExceptionHandler(value = RuntimeException.class)
    public  ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        //return new ResponseEntity<>(ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR,ex.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        log.error("서버오류 : {} ", ex.getMessage(),ex);
        return new ResponseEntity<>(ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR,"알 수 없는 서버 오류가 발생하였습니다."),HttpStatus.INTERNAL_SERVER_ERROR);
    }
    //서버 오류 메세지를 노출하는 것은 해커들의 표적이 될 수 있다. 위험
*/


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
