package com.example.oracledcndemo.listener;

import oracle.jdbc.dcn.DatabaseChangeEvent;
import oracle.jdbc.dcn.DatabaseChangeListener;
import oracle.jdbc.dcn.RowChangeDescription;
import oracle.jdbc.dcn.TableChangeDescription;

import javax.sql.DataSource;
import java.sql.*;

public class EmployeeTableChangeListener implements DatabaseChangeListener {

    private final DataSource dataSource;

    public EmployeeTableChangeListener(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void onDatabaseChangeNotification(DatabaseChangeEvent event) {
        System.out.println("++Cambio detectado en la base de datos++");

        for (TableChangeDescription tableChange : event.getTableChangeDescription()) {
            for (RowChangeDescription rowChange : tableChange.getRowChangeDescription()) {
                String rowId = rowChange.getRowid().stringValue();
                System.out.println("Operación: " + rowChange.getRowOperation());
                System.out.println("ROWID afectado: " + rowId);

                try (Connection conn = dataSource.getConnection()) {
                    fetchRowByRowId(conn, rowId);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void fetchRowByRowId(Connection conn, String rowId) {
        String sql = "SELECT ID, FIRST_NAME, LAST_NAME, DEPARTMENT_ID, SALARY, HIRED_DATE FROM EMPLOYEES WHERE ROWID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, rowId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Datos del registro actualizado:");
                System.out.printf("ID: %s, Name: %s %s, Dept: %s, Salary: %s, Hired: %s%n",
                        rs.getInt("ID"),
                        rs.getString("FIRST_NAME"),
                        rs.getString("LAST_NAME"),
                        rs.getInt("DEPARTMENT_ID"),
                        rs.getDouble("SALARY"),
                        rs.getDate("HIRED_DATE"));
            } else {
                System.out.println("El registro ya no existe (posiblemente fue eliminado)");
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener los datos por ROWID:");
            e.printStackTrace();
        }
    }
}
