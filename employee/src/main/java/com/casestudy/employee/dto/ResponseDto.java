package com.casestudy.employee.dto;

import com.casestudy.employee.model.Employee;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class ResponseDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Employee> employeeList;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer totalRecords;
}
