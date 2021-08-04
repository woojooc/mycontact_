package com.example.mycontact.repository;


import com.example.mycontact.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

//JpaRepository 사용시 자동으로 레퍼지토리 빈으로 등록된다.
public interface PersonRepository extends JpaRepository<Person,Long>{

    List<Person> findByName(String name);

    //jdql 엔티티 기반 쿼리 실행     ?1 첫번째 인자를 의미
    @Query(value = "select person from Person person where person.birthday.monthOfBirthday = ?1")
    List<Person> findByMonthOfBirthday(int monthOfBirthday);

    @Query(value = "select person from Person person where person.birthday.monthOfBirthday = :monthOfBirthday and person.birthday.dayOfBirthday = :dayOfBirthday")
    List<Person> findByMonthOfBirthdayAndDayOfBirthday(@Param("monthOfBirthday")int monthOfBirthday, @Param("dayOfBirthday")int dayOfBirthday);

    // 실제 엔티티를 사용하지 않는 쿼리. 쿼리가 직접 실행
    @Query(value = "select person from Person person where person.deleted = true",nativeQuery = true)
    List<Person> findPeopleDeleted();
}
