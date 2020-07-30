package com.example.bootjpa.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue
    @Column(name="member_id")
    private Long id;

  //  @NotEmpty는 DTO에 적어준다
    private String name;

    @Embedded
    private Address address;

}
