package com.casestudy.employee.controller;

import com.casestudy.employee.dto.FilterDto;
import com.casestudy.employee.dto.ResponseDto;
import com.casestudy.employee.model.Employee;
import com.casestudy.employee.service.EmployeeService;
import com.opencsv.exceptions.CsvValidationException;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @PostMapping("/upload")
    public ResponseEntity<String> uploadEmployeeData(@RequestParam("file") MultipartFile file) throws CsvValidationException, IOException {
        return ResponseEntity.ok().body(employeeService.saveEmployeeData(file));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> fetchEmployeeData(@PathVariable Integer id) {
        ResponseDto response = employeeService.fetchEmployee(id);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<String> modifyEmployeeData(@Valid @RequestBody Employee employee) {
        String response = employeeService.modifyEmployee(employee);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/search")
    public ResponseEntity<ResponseDto> searchEmployeeData(@RequestParam(value = "pageNo", required = false) Integer pageNo, @RequestParam(value = "pageSize", required = false) Integer pageSize, @Valid @RequestBody FilterDto filterDto) {
        ResponseDto filteredResponse = employeeService.searchEmployee(pageNo, pageSize, filterDto);
        return ResponseEntity.ok().body(filteredResponse);
    }

    @PostMapping("/export")
    public ResponseEntity<byte[]> exportToCSV(@Valid @RequestBody FilterDto filterDto) throws IOException {
        return employeeService.generateCSV(filterDto);
    }

    @PostMapping("/sampleCSV")
    public ResponseEntity<byte[]> sampleCSV() throws IOException {
        return employeeService.sampleCSV();
    }

}


