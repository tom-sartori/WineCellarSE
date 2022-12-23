package logic.controller.company;

import logic.controller.AbstractController;
import persistence.dao.company.CompanyDao;
import persistence.entity.company.Company;

/**
 * CompanyController class extending Controller class parametrized with Company class.
 */
public class CompanyController extends AbstractController<Company> {

    /**
     * Instance of CompanyController to ensure Singleton design pattern.
     */
    private static CompanyController instance;

    /**
     * Private constructor for CompanyController to ensure Singleton design pattern.
     */
    private CompanyController() { }

    /**
     * @return the instance of CompanyController to ensure Singleton design pattern.
     */
    public static CompanyController getInstance() {
        if(instance == null){
            instance = new CompanyController();
        }
        return instance;
    }

    /**
     * @return the DAO of the specific Controller (CompanyDao).
     */
    @Override
    protected CompanyDao getDao() {
        return CompanyDao.getInstance();
    }
}
