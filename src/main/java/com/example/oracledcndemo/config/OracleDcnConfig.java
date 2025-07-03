package com.example.oracledcndemo.config;

import com.example.oracledcndemo.listener.EmployeeTableChangeListener;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.dcn.DatabaseChangeListener;
import oracle.jdbc.dcn.DatabaseChangeRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import oracle.jdbc.OracleStatement;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Properties;
import javax.annotation.PreDestroy;
import java.sql.Connection;
import java.sql.Statement;

@Configuration
public class OracleDcnConfig {

    @Autowired
    private DataSource dataSource;

    private DatabaseChangeRegistration dcr;
    private OracleConnection oracleConnection;

    @PostConstruct
    public void setupDcn() throws Exception {
        Connection conn = dataSource.getConnection();
        oracleConnection = conn.unwrap(OracleConnection.class);

        if (dcr != null) {
            System.out.println("DCN ya registrado.");
            return;
        }

        Properties props = new Properties();
        props.setProperty(OracleConnection.DCN_NOTIFY_ROWIDS, "true");
        dcr = oracleConnection.registerDatabaseChangeNotification(props);

        DatabaseChangeListener listener = new EmployeeTableChangeListener();
        dcr.addListener(listener);

        try (Statement stmt = oracleConnection.createStatement()) {
            ((OracleStatement) stmt).setDatabaseChangeRegistration(dcr);
            stmt.executeQuery("SELECT * FROM EMPLOYEES WHERE 1=0"); // Enlaza sin traer datos
        }

        System.out.println("DCN listener registered successfully, REGID = " + dcr.getRegId());
    }

    @PreDestroy
    public void cleanupDcn() {
        try {
            if (oracleConnection != null && dcr != null) {
                oracleConnection.unregisterDatabaseChangeNotification(dcr);
                System.out.println("DCN unregistered successfully.");
            }
        } catch (Exception e) {
            System.err.println("Error al eliminar el DCN: " + e.getMessage());
        }
    }
}

