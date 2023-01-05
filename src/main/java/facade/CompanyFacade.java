package facade;

import exception.BadArgumentsException;
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

    /**
     * Return the list of companies that are accessible.
     *
     * @return The list of accessible companies if any, throws a NotFoundException otherwise.
     */
    public List<Company> findAllAccessibleCompanies() {
        return CompanyController.getInstance().findAllAccessibleCompanies();
    }

    /**
     * Return the list of companies that are not accessible.
     *
     * @return The list of companies that are not accessible if there are any, a NotFoundException is thrown otherwise.
     */
    public List<Company> findAllUnaccessibleCompanies() {
        return CompanyController.getInstance().findAllUnaccessibleCompanies();
    }

    /**
     * Return the companies where the user is a manager or masterManager.
     *
     * @param userId The id of the user.
     *
     * @return The list of companies where the user is a manager or masterManager.
     */
    public List<Company> findAllCompaniesByUserId(ObjectId userId) {
        return CompanyController.getInstance().findAllCompaniesByUserId(userId);
    }

    /**
     * Add a manager to a company.
     *
     * @param companyId The id of the company.
     * @param managerId The id of the manager to add.
     *
     * @return The id of the updated company if the manager was added, throws a BadArgumentsException otherwise.
     * @throws BadArgumentsException if the manager was not added.
     */
    public ObjectId addManager(ObjectId companyId, ObjectId managerId) throws BadArgumentsException {
        return CompanyController.getInstance().addManager(companyId, managerId);
    }

    /**
     * Removes a manager from a company.
     *
     * @param companyId The id of the company.
     * @param managerId The id of the manager.
     *
     * @return The id of the company if the manager was removed successfully, else throws a BadArgumentsException.
     * @throws BadArgumentsException If the company or the manager does not exist.
     */
    public ObjectId removeManager(ObjectId companyId, ObjectId managerId) throws BadArgumentsException {
        return CompanyController.getInstance().removeManager(companyId, managerId);
    }

    /**
     * Refuse a request to publish a new Company.
     *
     * @param companyId The id of the company to refuse.
     *
     * @return The id of the refused company if the operation was successful, else throw an exception.
     * @throws BadArgumentsException If the company does not exist or if the company has already been accepted by an Admin.
     */
    public ObjectId refuseRequest(ObjectId companyId) throws BadArgumentsException {
        return CompanyController.getInstance().refuseRequest(companyId);
    }

    /**
     *  Accept a request to create a Company.
     *
     * @param companyId The id of the company to accept.
     *
     * @return The id of the accepted company if the operation is successful, else throws an exception.
     * @throws BadArgumentsException If the company does not exist or if the company is already accepted.
     */
    public ObjectId acceptRequest(ObjectId companyId) throws BadArgumentsException {
        return CompanyController.getInstance().acceptRequest(companyId);
    }

    /**
     * Promote a user to masterManager.
     *
     * @param companyId The id of the company.
     * @param newMasterManagerId The id of the new masterManager.
     *
     * @return The id of the updated company if the promotion was successful, throws a BadArgumentsException otherwise.
     * @throws BadArgumentsException if the user or the company doesn't exist.
     */
    public ObjectId promoteNewMasterManager(ObjectId companyId, ObjectId newMasterManagerId) throws BadArgumentsException {
        return CompanyController.getInstance().promoteNewMasterManager(companyId, newMasterManagerId);
    }
}
