package com.example.oracledcndemo.service;

import com.example.oracledcndemo.mapper.EmployeeMapper;
import com.example.oracledcndemo.model.response.EmployeeResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public List<EmployeeResponse> getAll() {
        return employeeMapper.getAll();
    }
}
