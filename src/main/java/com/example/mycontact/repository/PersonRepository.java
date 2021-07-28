package com.example.mycontact.repository;


import com.example.mycontact.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//JpaRepository 사용시 자동으로 레퍼지토리 빈으로 등록된다.
public interface PersonRepository extends JpaRepository<Person,Long>{

}
