
package co.edu.unicauca.mycompany.projects.domain.services;

import co.edu.unicauca.mycompany.projects.domain.entities.Company;
import java.util.regex.Pattern;

/**
 *
 * @author josed
 */
//Se crea una clase para validar los datos, esto se hace para seguir los principios SOLID
public class CompanyValidator {
    // se hace uso de expresiones regulares para validar los datos
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{6,}$";
    
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);
    
    public static boolean validate(Company company) {
        if (company == null) {
            return false;
        }
        if (company.getNit() == null || company.getNit().trim().isEmpty()) {
            return false;
        }
        if (company.getName() == null || company.getName().trim().isEmpty()) {
            return false;
        }
        if (company.getEmail() == null || !isValidEmail(company.getEmail())) {
            return false;
        }
        if (company.getPassword() == null || !isValidPassword(company.getPassword())) {
            return false;
        }
        return true;
    }
    
    private static boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
    
    private static boolean isValidPassword(String password) {
        return PASSWORD_PATTERN.matcher(password).matches();
    }
    
}
