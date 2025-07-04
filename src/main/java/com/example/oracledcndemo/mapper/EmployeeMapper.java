package com.example.oracledcndemo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.oracledcndemo.model.response.EmployeeResponse;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    List<EmployeeResponse> getAll();
}
