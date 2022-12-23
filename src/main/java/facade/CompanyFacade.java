package facade;

import logic.controller.company.CompanyController;
import org.bson.types.ObjectId;
import persistence.entity.company.Company;

import java.util.List;

/**
 * Specific facade for Companies.
 */
class CompanyFacade {

    /**
     * Singleton instance.
     */
    private static CompanyFacade instance;

    private CompanyFacade() { }

    /**
     * Get the singleton instance of the company facade.
     *
     * @return The singleton instance.
     */
    public static CompanyFacade getInstance() {
        if (instance == null) {
            instance = new CompanyFacade();
        }
        return instance;
    }

    /**
     * Insert a company.
     *
     * @param company The company to insert.
     * @return The id of the inserted company.
     */
    protected ObjectId insertOneCompany(Company company) {
        return CompanyController.getInstance().insertOne(company);
    }

    /**
     * Get all companies.
     *
     * @return A list of companys.
     */
    protected List<Company> getCompanyList() {
        return CompanyController.getInstance().findAll();
    }

    /**
     * Get a company by its id.
     *
     * @param id The id of the company.
     *
     * @return The company or null if not found.
     */
    protected Company getOneCompany(ObjectId id) {
        return CompanyController.getInstance().findOne(id);
    }

    /**
     * Update a company.
     *
     * @param id The id of the company to update.
     * @param company The new company.
     *
     * @return true if the company has been updated, false otherwise.
     */
    protected boolean updateOneCompany(ObjectId id, Company company) {
        return CompanyController.getInstance().updateOne(id, company);
    }

    /**
     * Delete a company.
     *
     * @param id The id of the company to delete.
     *
     * @return true if the company has been deleted, false otherwise.
     */
    protected boolean deleteOneCompany(ObjectId id) {
        return CompanyController.getInstance().deleteOne(id);
    }
}
