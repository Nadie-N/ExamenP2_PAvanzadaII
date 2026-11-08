package com.mycompany.examenp2_paii;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

   
    private static final String URL =
        "jdbc:sqlserver://localhost:1433;"
        + "databaseName=AutoTechManager;"
        + "user=sa;"
        + "password=Milan2020;"
        + "encrypt=true;"
        + "trustServerCertificate=true;"
        + "loginTimeout=30;";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

   
    public static void main(String[] args) {
        try (Connection c = getConnection()) {
            System.out.println("Conexión exitosa a: " + c.getCatalog());
            System.out.println("Servidor: " + c.getMetaData().getURL());
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}