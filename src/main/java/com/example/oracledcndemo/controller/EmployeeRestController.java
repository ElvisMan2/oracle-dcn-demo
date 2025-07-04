package com.example.oracledcndemo.controller;

import com.example.oracledcndemo.model.request.EmployeeRequest;
import com.example.oracledcndemo.model.response.EmployeeResponse;
import com.example.oracledcndemo.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeRestController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/")
    public List<EmployeeResponse> getAllEmployees() {
        return employeeService.getAll();
    }

    @PostMapping("/")
    public void createEmployee(@RequestBody EmployeeRequest employee) {
        employeeService.create(employee);
    }

}

