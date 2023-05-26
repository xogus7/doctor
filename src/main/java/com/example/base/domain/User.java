package com.example.base.domain;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * "app_user" 테이블과 매핑되도록 지정합니다.
 */
@Entity(name = "app_user")
@Table(name = "app_user")
@NoArgsConstructor
@Getter
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @NotEmpty
    @Column(unique = true)
    private String email;
    @NotEmpty
    @Column
    private String name;

//    @Column(name = "password", nullable = false)
//    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Builder
    public User(int id, String email, String name, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
//        this.password = password;
        this.role = role;
    }



    /**
     *  사용자 정보를 업데이트할 때 사용하는 메소드입니다.
     */
    public User update(String name) {
        this.name = name;
        return this;
    }

    /**
     *  사용자 권한의 key 값을 반환합니다.
     */
    public String getRoleKey() {
        return this.role.getKey();
    }
}