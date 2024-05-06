package com.casestudy.employee.service;

import com.casestudy.employee.model.Employee;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CSVParser {
    List<Employee> readCsv(MultipartFile file) throws IOException, CsvValidationException;
}
