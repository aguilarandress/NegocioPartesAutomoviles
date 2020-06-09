package com.negocio.automoviles.database;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DatabaseSource {
    public static DriverManagerDataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSource.setUrl("jdbc:sqlserver://localhost;databaseName=negocio_autosPartes");
        dataSource.setUsername("pepito");
        dataSource.setPassword("pepito1234");
        return dataSource;
    }
}
