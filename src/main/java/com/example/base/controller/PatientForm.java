package com.example.base.controller;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PatientForm {
    private Long id;
    @NotEmpty(message = "이름을 비우지 말아주세요")
    private String name;

    @NotEmpty(message = "생년월일을 비우지 말아주세요")
    private String birthday;
    @NotEmpty(message = "성별을 비우지 말아주세요")
    private String gender;

    private String guardianPhoneNumber;
    @Positive(message = "0은 안돼요")
    private Long correctionTime;
    @Positive(message = "0은 안돼요")
    private Long wearingTime;
    @Positive(message = "0은 안돼요")
    private Long correctionDay;
    @Positive(message = "0은 안돼요")
    private Long wearingDay;

}
