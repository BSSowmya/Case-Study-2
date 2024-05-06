package com.casestudy.employee.service;

import com.casestudy.employee.model.Employee;
import com.casestudy.employee.dto.FilterDto;
import com.casestudy.employee.dto.ResponseDto;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface EmployeeService {

    String saveEmployeeData(MultipartFile file) throws CsvValidationException, IOException;

    ResponseDto fetchEmployee(Integer id);

    String modifyEmployee(Employee employee);

    ResponseDto searchEmployee(Integer pageNo, Integer pageSize, FilterDto filterDTO);

    ResponseEntity<byte[]> generateCSV(FilterDto filterDTO) throws IOException;

    ResponseEntity<byte[]> sampleCSV() throws IOException;

}
