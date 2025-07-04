package com.example.oracledcndemo.model.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer departmentId;
    private BigDecimal salary;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate hiredDate;
}
