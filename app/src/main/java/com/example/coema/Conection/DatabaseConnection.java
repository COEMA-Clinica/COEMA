package com.example.coema.Conection;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.coema.Registro.RegistroPacientes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

public class DatabaseConnection {

//    private static final String DB_URL = "jdbc:postgresql://dpg-ck7g9u7q54js73famlu0-a.oregon-postgres.render.com:5432/codema";
//    private static final String DB_USER = "codema_user";
//    private static final String DB_PASSWORD = "TL35jKeo53oDQkPDsw6c587xWAMSCmd6";

//    public static boolean insertarPaciente(Integer id, String nombres, String apellidos,
//                                           String correo, String contra, String telefono,
//                                           String fechaNacimiento, String sexo) {
//        try {
//            // Establecer la conexión a la base de datos
//            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
//
//            // Crear la consulta SQL de inserción
//            String sql = "INSERT INTO pacientes (id_paciente, nombres, apellidos, correo, contrasena, telefono, fecha_nacimiento, sexo) VALUES (?, ?, ?, ?, ?, ?, to_date(?,'yyyy-MM-dd'), ?)";
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//
//            // Establecer los valores de los parámetros
//            preparedStatement.setInt(1, id);
//            preparedStatement.setString(2, nombres);
//            preparedStatement.setString(3, apellidos);
//            preparedStatement.setString(4, correo);
//            preparedStatement.setString(5, contra);
//            preparedStatement.setString(6, telefono);
//            preparedStatement.setString(7, fechaNacimiento);
//            preparedStatement.setString(8, sexo);
//            // Ejecutar la consulta de inserción
//            int filasAfectadas = preparedStatement.executeUpdate();
//
//            // Cerrar la conexión
//            connection.close();
//
//            // Comprobar si la inserción fue exitosa
//            return filasAfectadas > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }



    private Context context;

    public DatabaseConnection(Context context) {
        this.context = context;
    }

    private static Connection conn = null;

    public static Connection getConnection() throws SQLException {
        if (conn == null) {
            try {
                Class.forName("org.postgresql.Driver");
                String url = "jdbc:postgresql://dpg-ck7g9u7q54js73famlu0-a.oregon-postgres.render.com:5432/codema";
                String user = "codema_user";
                String password = "TL35jKeo53oDQkPDsw6c587xWAMSCmd6";

                Properties properties = new Properties();
                properties.setProperty("user", user);
                properties.setProperty("password", password);
                properties.setProperty("ssl", "true");
                properties.setProperty("sslmode", "require");

                conn = DriverManager.getConnection(url, properties);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//
//    public class InsertDataAsyncTask extends AsyncTask<Void, Void, Boolean> {
//        private Connection connection;
//        private String data;
//
//        public InsertDataAsyncTask(Connection connection, String data) {
//            this.connection = connection;
//            this.data = data;
//        }
//
//        @Override
//        protected Boolean doInBackground(Void... params) {
//            boolean success = false;
//            try {
//                String query = "INSERT INTO tu_tabla (columna) VALUES (?)";
//                PreparedStatement preparedStatement = connection.prepareStatement(query);
//                preparedStatement.setString(1, data);
//
//                int rowsAffected = preparedStatement.executeUpdate();
//
//                if (rowsAffected > 0) {
//                    success = true;
//                }
//
//                preparedStatement.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            return success;
//        }
//
//        @Override
//        protected void onPostExecute(Boolean insertionResult) {
//            if (insertionResult) {
//                Log.d("ConexionBD", "Conexión a la base de datos realizada correctamente.");
//            } else {
//                Log.e("ConexionBD", "Error al conectar a la base de datos.");
//            }
//        }
//    }
  /*  Connection conexion=null;
     public Connection conexionBD(){
             try {
                 Class.forName("org.postgresql.Driver");
                 String url = "jdbc:postgresql://dpg-ck7g9u7q54js73famlu0-a.oregon-postgres.render.com:5432/codema";
                 String user = "codema_user";
                 String password = "TL35jKeo53oDQkPDsw6c587xWAMSCmd6";


                 conexion = DriverManager.getConnection(url, user,password);
             } catch (Exception e) {
                 e.printStackTrace();
             }
             return conexion;
     }
     protected void cerrarConexion(Connection con)throws Exception{
         con.close();
     }*/
}
