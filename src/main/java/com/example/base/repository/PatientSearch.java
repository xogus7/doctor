package com.example.base.repository;

import com.example.base.domain.Status;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PatientSearch {
    private String memberName;
    private String birthday;
    private Status status;
}
