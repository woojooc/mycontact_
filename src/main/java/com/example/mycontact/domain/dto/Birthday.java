package com.example.mycontact.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@Embeddable
//@AllArgsConstructor
@NoArgsConstructor
@Data   // Tostrim 오버라이딩해서 해쉬값 출력안되게
public class Birthday {
    //int -> Integer  : null값을 허용한다.
    private Integer yearOfBirthday;

    private Integer monthOfBirthday;

    private Integer dayOfBirthday;

    //LocalDate로 한번 맵핑해서 2월 30일같은거 못 넣게 만들 수 있다.
    public Birthday(LocalDate birthday) {
        this.yearOfBirthday = birthday.getYear();
        this.monthOfBirthday = birthday.getMonthValue();
        this.dayOfBirthday = birthday.getDayOfMonth();
    }

    public static Birthday of(LocalDate birthday) {
        return new Birthday(birthday);
    }
}
