package co.edu.unicauca.mycompany.projects.access;

import static co.edu.unicauca.mycompany.projects.access.CompanyArraysRepository.myArray;
import co.edu.unicauca.mycompany.projects.domain.entities.Company;
import co.edu.unicauca.mycompany.projects.domain.entities.Sector;
import co.edu.unicauca.mycompany.projects.domain.services.CompanyService;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementación del repositorio con Sqlite
 *
 * @author Libardo, Julio
 */
public class CompanySqliteRepository implements ICompanyRepository {

    private Connection conn;

    public CompanySqliteRepository() {
        initDatabase();
    }

    public void connect() {
        // SQLite connection string
        //String url = "jdbc:sqlite:./myDatabase.db"; //Para Linux/Mac
        //String url = "jdbc:sqlite:C:/sqlite/db/myDatabase.db"; //Para Windows
        String url = "jdbc:sqlite:C:\\Users\\josed\\OneDrive\\Documentos\\LAB ING SOFT 2\\labo2_ing_software2\\labo2_ing_software2\\BD\\mi_base.db";

        try {
            conn = DriverManager.getConnection(url);

        } catch (SQLException ex) {
            Logger.getLogger(CompanyService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initDatabase() {
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS COMPANY (\n"
                + "	nit String NOT NULL,\n"
                + "	name String NOT NULL,\n"
                + "	phone String NULL,\n"
                +"	pageWeb String NULL\n"
                +"	sector Sector NOT NULL\n"
                +"	email String NOT NULL\n"
                +"	password String NOT NULL\n"
                + ");";
        try {
            this.connect();
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            //this.disconnect();

        } catch (SQLException ex) {
            Logger.getLogger(CompanyService.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    
    @Override
    public boolean save(Company newCompany) {
        try {
            //Validate product
            if (newCompany == null || newCompany.getName().isEmpty()) {
                return false;
            }
            //this.connect();
            String sql = "INSERT INTO company ( nit, name, phone, pageWeb, sector, email, password  ) "   //posible error
                    + "VALUES ( ?, ?, ? )";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newCompany.getName());
            pstmt.setString(2, newCompany.getName());
            pstmt.setString(3, newCompany.getPhone());
            pstmt.setString(4, newCompany.getPageWeb());
            //pstmt.Sector(5, newCompany.getSector());
            pstmt.setString(6, newCompany.getEmail());
            pstmt.setString(7, newCompany.getPassword());
            pstmt.executeUpdate();
            //this.disconnect();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CompanyService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<Company> listAll() {
        List<Company> companies = new ArrayList<>();
        try {
            String sql = "SELECT * FROM companies"; 
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Company newCompany = new Company(
                    rs.getString("nit"),
                    rs.getString("name"),
                    rs.getString("phone"),
                    rs.getString("website"),
                    Sector.valueOf(rs.getString("sector")), // Convertir el valor de sector a un valor de enum
                    rs.getString("email"),
                    rs.getString("password")
                );
                myArray.add(newCompany); // Agregar al repositorio de empresas
            }
        } catch (SQLException ex) {
            Logger.getLogger(CompanyService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myArray; // Retornar la lista actualizada
    }
}


