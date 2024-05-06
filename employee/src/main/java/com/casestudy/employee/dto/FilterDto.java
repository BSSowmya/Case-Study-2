package com.casestudy.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilterDto {
    private Integer id;

    private String firstName;

    private String lastName;

    private LocalDate dob;

    private LocalDate doj;


    private String department;
}
