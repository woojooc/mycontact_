package com.example.mycontact.domain;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
public class Block {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String reason;

    private LocalDate startDate;
    private LocalDate endDate;
}