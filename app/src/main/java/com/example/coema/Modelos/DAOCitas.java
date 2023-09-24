package com.example.coema.Modelos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAOCitas {
    private Connection connection;
    private String jdbcUrl = "jdbc:postgresql://dpg-ck7g9u7q54js73famlu0-a.oregon-postgres.render.com:5432/codema"; // Cambia esto por la URL correcta de tu base de datos
    private String jdbcUser = "codema_user";
    private String jdbcPassword = "TL35jKeo53oDQkPDsw6c587xWAMSCmd6";

    public DAOCitas() {
        try {
            Class.forName("org.postgresql.Driver"); // Asegúrate de tener el controlador JDBC adecuado para PostgreSQL o el que estés utilizando.
            connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }




}
