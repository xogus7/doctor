package com.example.base.domain;


import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 * 기존에 임의로 만들었던 사용자 회원 데이터입니다. 무시하셔도 됩니다.
 */


@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String name;
    private String password;
    private String email;
    private Role role;

//    @OneToMany(mappedBy = "member") //나는 주인이 아니에요. order테이블에 있는 member가 주인이다.
//    private List<Order> orders = new ArrayList<>();
}

