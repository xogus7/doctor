package com.example.base.controller;




import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "이름을 비우지말아주세요^^")
    private String name;
    private String password;

}
