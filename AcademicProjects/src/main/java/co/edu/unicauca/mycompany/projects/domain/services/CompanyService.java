package co.edu.unicauca.mycompany.projects.domain.services;

import co.edu.unicauca.mycompany.projects.access.ICompanyRepository;
import co.edu.unicauca.mycompany.projects.domain.entities.Company;
import java.util.List;

/**
 *
 * @author Libardo Pantoja, Julio Hurtado
 */
public class CompanyService {

    private ICompanyRepository repository;

    public CompanyService(ICompanyRepository repository) {
        this.repository = repository;
    }

    public List<Company> getAllCompanies() {
        return repository.listAll();
    }

    public boolean saveCompany(Company newCompany) {
        System.out.println("Validando compañía: " + newCompany.getNit());
        if (!CompanyValidator.validate(newCompany)) {
            System.out.println("Validación fallida");
            return false;
        }
        System.out.println("Guardando en el repositorio...");
        return repository.save(newCompany);
    }
}
