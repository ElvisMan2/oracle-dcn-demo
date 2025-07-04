package com.example.oracledcndemo.service;

import java.util.List;

import com.example.oracledcndemo.model.request.EmployeeRequest;
import com.example.oracledcndemo.model.response.EmployeeResponse;


public interface EmployeeService {
    List<EmployeeResponse> getAll();
    void create(EmployeeRequest employee);
}

