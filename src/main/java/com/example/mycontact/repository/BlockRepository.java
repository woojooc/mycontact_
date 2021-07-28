package com.example.mycontact.repository;

import com.example.mycontact.domain.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface BlockRepository extends JpaRepository<Block,Long> {


}
