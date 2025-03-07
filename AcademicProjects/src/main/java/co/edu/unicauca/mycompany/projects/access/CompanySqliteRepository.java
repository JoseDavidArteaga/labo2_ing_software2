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

    private DatabaseConnection dbConnection;

    public CompanySqliteRepository() {
        dbConnection = new DatabaseConnection();
        initDatabase();
    }

    private void initDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS COMPANY (\n"
                + "	nit TEXT NOT NULL,\n"
                + "	name TEXT NOT NULL,\n"
                + "	phone TEXT NULL,\n"
                +"	pageWeb TEXT NULL,\n"
                + "     sector TEXT NOT NULL CHECK (sector IN ('TECHNOLOGY', 'HEALTH', 'EDUCATION', 'SERVICES', 'OTHER')),\n" //linea corregida
                +"	email TEXT NOT NULL,\n"
                +"	password TEXT NOT NULL\n"
                + ");";
        try (Connection conn = dbConnection.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException ex) {
            Logger.getLogger(CompanySqliteRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public boolean save(Company newCompany) {
        try (Connection conn = dbConnection.connect()){
            //Validate product
            if (newCompany == null || newCompany.getName().isEmpty()) {
                return false;
            }
            //this.connect();
            String sql = "INSERT INTO company (nit, name, phone, pageWeb, sector, email, password) "  
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newCompany.getNit());
            pstmt.setString(2, newCompany.getName());
            pstmt.setString(3, newCompany.getPhone());
            pstmt.setString(4, newCompany.getPageWeb());
            pstmt.setString(5, newCompany.getSector().toString());//linea corregida
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
        try (Connection conn = dbConnection.connect()) {

            String sql = "SELECT nit, name, phone, pageWeb, sector, email, password FROM Company";
            //this.connect();

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                //Company newCompany = new Company(sql, sql, sql, sql, Sector.HEALTH, sql, sql);
                Company newCompany = new Company();
                newCompany.setNit(rs.getString("nit"));
                newCompany.setName(rs.getString("name"));
                newCompany.setPhone(rs.getString("phone"));
                newCompany.setPageWeb(rs.getString("pageWeb"));
                newCompany.setSector(Sector.valueOf(rs.getString("sector")));
                newCompany.setEmail(rs.getString("email"));
                newCompany.setPassword(rs.getString("password"));

                companies.add(newCompany);
            }

        } catch (SQLException ex) {
            Logger.getLogger(CompanyService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return companies;
    }
}


