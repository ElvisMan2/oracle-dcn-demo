package com.example.oracledcndemo.config;


import com.example.oracledcndemo.listener.EmployeeTableChangeListener;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleDriver;
import oracle.jdbc.dcn.DatabaseChangeEvent;
import oracle.jdbc.dcn.DatabaseChangeListener;
import oracle.jdbc.dcn.DatabaseChangeRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.*;
import java.util.Properties;

@Configuration
public class OracleDcnConfig {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void setupDcn() throws Exception {
        OracleConnection conn = dataSource.getConnection().unwrap(OracleConnection.class);

        Properties props = new Properties();
        props.setProperty(OracleConnection.DCN_NOTIFY_ROWIDS, "true");
        DatabaseChangeRegistration dcr = conn.registerDatabaseChangeNotification(props);

        DatabaseChangeListener listener = new EmployeeTableChangeListener();
        dcr.addListener(listener);

        try (Statement stmt = conn.createStatement()) {
            ((oracle.jdbc.OracleStatement) stmt).setDatabaseChangeRegistration(dcr);
            stmt.executeQuery("SELECT * FROM EMPLOYEES WHERE DEPARTMENT_ID = 10");
        }

        System.out.println("DCN listener registered successfully");
    }
}

