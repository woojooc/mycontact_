package com.example.mycontact.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
//@Getter
//@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
//@ToString//(exclude = "phoneNumber")  // 민감한 정보 로그에서 제외 시키기.
//@EqualsAndHashCode
@Data
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull    // 반드시 있어야함.
    private String name;

    @NonNull
    private int age;

    @NonNull
    private String bloodType;

    private String hobby;

    private String address;

    private LocalDate birthday;

    private String job;

    @ToString.Exclude
    private String phoneNumber;

    // 해쉬 코드  = 같은 객체라는 것을
    // 이퀄스 = 해당 객체가 같은 값을 가지고 있다는 것을

    /*
    // 같은 값을 가지면 동일한 객체로 판단하게 하자 but 해쉬코드는 다른 객체
    public boolean equals(Object object) {
        if(object == null) {
            return false;
        }

        Person person = (Person)object;

        if(!person.getName().equals(this.getName())) {
            return false;
        }

        if(person.getAge() != this.getAge()) {
            return false;
        }

        return true;
    }
    //but 해쉬코드는 다른 객체 => 해쉬 맵같은곳에서 문제가 발생한다.
    public int hashCode() {
        return (name+age).hashCode();
    }
    //해쉬 코드를 오버라이딩해서 생성하기
    */

    //차단기능
    private boolean block;  // getter에서 isBlock이라고 만들어줌

}