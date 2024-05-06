package com.casestudy.employee.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name")
    @NotBlank(message = "First Name should be provided")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "Last Name should be provided")
    private String lastName;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "doj")
    @NotNull(message = "Date of Joining should be provided")
    private LocalDate doj;

    @Column(name = "department")
    private String department;
}
