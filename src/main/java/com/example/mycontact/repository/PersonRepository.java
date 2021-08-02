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

    List<Person> findByBlockIsNull();

    List<Person> findByBloodType(String bloodType);
    // 단일 객체로 받을 시 여러개 리절트 리턴시 오류가 발생한다.

    //List<Person> findByBirthdayBetween(LocalDate startDate, LocalDate endDate);

    //jdql 엔티티 기반 쿼리 실행     ?1 첫번째 인자를 의미
    @Query(value = "select person from Person person where person.birthday.monthOfBirthday = ?1")
    List<Person> findByMonthOfBirthday(int monthOfBirthday);


    //nativeQuery = true  : jpql -> nativequrr로 작성 가능해짐.
    //@Query(value = "select * from person where month_of_birthday = :monthOfBirthday and day_of_birthday = :dayOfBirthday",nativeQuery = true)

    //@Query(value = "select person from Person person where person.birthday.monthOfBirthday = ?1 and person.birthday.dayOfBirthday = ?2")
    @Query(value = "select person from Person person where person.birthday.monthOfBirthday = :monthOfBirthday and person.birthday.dayOfBirthday = :dayOfBirthday")
    List<Person> findByMonthOfBirthdayAndDayOfBirthday(@Param("monthOfBirthday")int monthOfBirthday, @Param("dayOfBirthday")int dayOfBirthday);


}
