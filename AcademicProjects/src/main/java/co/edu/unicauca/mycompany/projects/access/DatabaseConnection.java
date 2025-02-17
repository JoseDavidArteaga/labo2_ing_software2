
package co.edu.unicauca.mycompany.projects.access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author josed
 */

//Se creo la clase DatabaseConnection que se encarga de manejar la conexión a la base de datos
//Esto se hace para cumplir el primer principio SOLID: Single Responsibility Principle (SRP)
//Cada clase debe tener una única responsabilidad
public class DatabaseConnection {
    private Connection conn;

    public Connection connect() {
        String url = "jdbc:sqlite:C:\\Users\\josed\\OneDrive\\Documentos\\LAB ING SOFT 2\\labo2_ing_software2\\labo2_ing_software2\\BD\\mi_base.db";
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    public void disconnect() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
