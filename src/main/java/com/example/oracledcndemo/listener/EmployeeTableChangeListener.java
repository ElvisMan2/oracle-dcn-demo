
package com.example.oracledcndemo.listener;

import oracle.jdbc.dcn.DatabaseChangeEvent;
import oracle.jdbc.dcn.DatabaseChangeListener;

public class EmployeeTableChangeListener implements DatabaseChangeListener {

    @Override
    public void onDatabaseChangeNotification(DatabaseChangeEvent event) {
        System.out.println("Cambio detectado en la base de datos:");
        System.out.println(event.toString());
    }
}
