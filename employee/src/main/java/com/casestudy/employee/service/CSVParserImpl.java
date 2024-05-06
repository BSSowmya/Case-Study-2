package com.casestudy.employee.service;

import com.casestudy.employee.model.Employee;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class CSVParserImpl implements  CSVParser{
    private LocalDate parseDate(String dateString) {
        return LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    private boolean isValidGrade(String grade) {
        return grade.matches("^[A-Z]$");
    }

    @Override
    public List<Employee> readCsv(MultipartFile file) throws IOException, CsvValidationException {
        List<Employee> employeeList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        CSVReader csvReader = new CSVReader(reader);
        String[] headers = csvReader.readNext();
        String[] nextRecord;
        while ((nextRecord = csvReader.readNext()) != null) {
            boolean skipRow = false;
            Employee employee = new Employee();
            for (int i = 0; i < headers.length; i++) {
                switch (headers[i].trim()) {
                    case "firstName":
                        if (nextRecord[i] == null || nextRecord[i].isEmpty()) {
                            skipRow = true;
                            break;
                        }
                        employee.setFirstName(nextRecord[i]);
                        break;
                    case "lastName":
                        if (nextRecord[i] == null || nextRecord[i].isEmpty()) {
                            skipRow = true;
                            break;
                        }
                        employee.setLastName(nextRecord[i]);
                        break;
                    case "dob":
                        if (nextRecord[i] == null || nextRecord[i].isEmpty()) {
                            skipRow = true;
                            break;
                        }
                        employee.setDob(parseDate(nextRecord[i]));
                        break;
                    case "doj":
                        if (nextRecord[i] == null || nextRecord[i].isEmpty()) {
                            skipRow = true;
                            break;
                        }
                        employee.setDoj(parseDate(nextRecord[i]));
                        break;
                    case "grade":
                        if (nextRecord[i] == null || nextRecord[i].isEmpty() || (!isValidGrade(nextRecord[i].trim()))) {
                            skipRow = true;
                            break;
                        }
                        employee.setDepartment(nextRecord[i].trim());
                        break;
                    default:
                        break;
                }
            }
            if (!skipRow) {
                employeeList.add(employee);
            }
        }
        return employeeList;
    }
}
