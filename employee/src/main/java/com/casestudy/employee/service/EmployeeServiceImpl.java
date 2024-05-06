package com.casestudy.employee.service;

import com.casestudy.employee.model.Employee;
import com.casestudy.employee.repository.EmployeeRepository;
import com.casestudy.employee.dto.FilterDto;
import com.casestudy.employee.dto.ResponseDto;


import com.opencsv.exceptions.CsvValidationException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;



@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private CSVParser csvParser;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EntityManager entityManager;

    @Override
    public String saveEmployeeData(MultipartFile file) throws CsvValidationException, IOException {
        List<Employee> studentList = csvParser.readCsv(file);
        employeeRepository.saveAll(studentList);
        return "Successfully uploaded";
    }

    @Override
    public ResponseDto fetchEmployee(Integer id) {
        Optional<Employee> fetchedEmployee = employeeRepository.findById(id);
        if (fetchedEmployee.isPresent()) {
            return ResponseDto.builder().employeeList((List.of(fetchedEmployee.get()))).build();
        } else {
            throw new RuntimeException("Student details not found");
        }
    }

    @Override
    public String modifyEmployee(Employee employee) {
        Optional<Employee> fetchedEmployee = employeeRepository.findById(employee.getId());
        if (fetchedEmployee.isPresent()) {
            employee.setId(fetchedEmployee.get().getId());
            employeeRepository.save(employee);
            return "updated successfully";
        } else {
            throw new RuntimeException("Student details not found");
        }

    }

    @Override
    public ResponseDto searchEmployee(Integer pageNo, Integer pageSize, FilterDto filterDTO) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> root = criteriaQuery.from(Employee.class);
        Predicate predicate = criteriaBuilder.conjunction();
        if (filterDTO.getId() != null) {
            Predicate idPredicate = criteriaBuilder.equal(root.get("id"), filterDTO.getId());
            predicate = criteriaBuilder.and(predicate, idPredicate);
        }
        if (filterDTO.getFirstName() != null && !filterDTO.getFirstName().isBlank()) {
            String firstName = filterDTO.getFirstName();
            if (firstName.charAt(firstName.length() - 1) == '*') {
                Predicate firstNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), firstName.substring(0, firstName.length() - 1) + "%");
                predicate = criteriaBuilder.and(predicate, firstNamePredicate);
            } else {
                Predicate firstNamePredicate = criteriaBuilder.equal(criteriaBuilder.lower(root.get("firstName")), firstName);
                predicate = criteriaBuilder.and(predicate, firstNamePredicate);
            }
        }
        if (filterDTO.getLastName() != null && !filterDTO.getLastName().isBlank()) {
            String lastName = filterDTO.getLastName();
            if (lastName.charAt(lastName.length() - 1) == '*') {
                Predicate firstNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), lastName.substring(0, lastName.length() - 1) + "%");
                predicate = criteriaBuilder.and(predicate, firstNamePredicate);
            } else {
                Predicate firstNamePredicate = criteriaBuilder.equal(criteriaBuilder.lower(root.get("lastName")), lastName);
                predicate = criteriaBuilder.and(predicate, firstNamePredicate);
            }
        }
        if (filterDTO.getDepartment() != null && !filterDTO.getDepartment().isBlank()) {
            Predicate gradePredicate = criteriaBuilder.equal(root.get("grade"), filterDTO.getDepartment());
            predicate = criteriaBuilder.and(predicate, gradePredicate);
        }
        if (filterDTO.getDoj() != null) {
            Predicate dojPredicate = criteriaBuilder.equal(root.get("doj"), filterDTO.getDoj());
            predicate = criteriaBuilder.and(predicate, dojPredicate);
        }
        if (filterDTO.getDob() != null) {
            Predicate dobPredicate = criteriaBuilder.equal(root.get("dob"), filterDTO.getDob());
            predicate = criteriaBuilder.and(predicate, dobPredicate);
        }
        criteriaQuery.where(predicate);
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));
        TypedQuery<Employee> query = entityManager.createQuery(criteriaQuery);
        if (pageNo != null && pageSize != null) {
            Pageable pageable = PageRequest.of(pageNo, pageSize);
            query.setFirstResult((int) pageable.getOffset());
            query.setMaxResults(pageSize);
            Integer total = query.getResultList().size();
            List<Employee> resultList = query.getResultList();
            return ResponseDto.builder().totalRecords(total).employeeList(resultList).build();
        } else {
            List<Employee> result = query.getResultList();
            return ResponseDto.builder().totalRecords(result.size()).employeeList(result).build();
        }
    }

    @Override
    public ResponseEntity<byte[]> generateCSV(FilterDto filterDTO) throws IOException {
        List<Employee> employeeList = searchEmployee(null, null, filterDTO).getEmployeeList();
        StringWriter writer = new StringWriter();
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(getHeaders()));
        for (Employee employee : employeeList) {
            csvPrinter.printRecord(employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getDob(), employee.getDoj(), employee.getDepartment());
        }
        csvPrinter.flush();
        csvPrinter.close();
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "Students_Output.csv");
        return ResponseEntity.ok().headers(headers).body(writer.toString().getBytes());
    }

    private String[] getHeaders() {
        Field[] fields = Employee.class.getDeclaredFields();
        String[] headers = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            headers[i] = fields[i].getName();
        }
        return headers;
    }

    @Override
    public ResponseEntity<byte[]> sampleCSV() throws IOException {
        StringWriter writer = new StringWriter();
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(Arrays.copyOfRange(getHeaders(), 1, getHeaders().length)));
        csvPrinter.flush();
        csvPrinter.close();
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "Student_Sample.csv");
        return ResponseEntity.ok().headers(headers).body(writer.toString().getBytes());
    }
}