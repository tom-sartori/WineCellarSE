package logic.controller.company;

import exception.BadArgumentsException;
import facade.Facade;
import logic.controller.AbstractController;
import org.bson.types.ObjectId;
import persistence.dao.company.CompanyDao;
import persistence.entity.company.Company;
import persistence.entity.user.User;

import java.util.List;

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

    // TODO Only available to current masterManager
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

        User newManager = Facade.getInstance().getOneUser(newMasterManagerId);

        if (newManager == null) {
            throw new BadArgumentsException("The new master manager does not exist.");
        }

        Company company = Facade.getInstance().getOneCompany(companyId);

        if (company == null) {
            throw new BadArgumentsException("The company does not exist.");
        }

        company.setMasterManager(newMasterManagerId);

        boolean b = getDao().updateOne(companyId, company);

        if (b) {
            return newMasterManagerId;
        } else {
            throw new BadArgumentsException("The new master manager could not be promoted.");
        }
    }

    // TODO available only for Admin
    // TODO SEND NOTIF
    /**
     *  Accept a request to create a Company.
     *
     * @param companyId The id of the company to accept.
     *
     * @return The id of the accepted company if the operation is successful, else throws an exception.
     * @throws BadArgumentsException If the company does not exist or if the company is already accepted.
     */
    public ObjectId acceptRequest(ObjectId companyId) throws BadArgumentsException {
        Company company = Facade.getInstance().getOneCompany(companyId);

        if (company == null) {
            throw new BadArgumentsException("The company does not exist.");
        }

        if (company.isAccessible()) {
            throw new BadArgumentsException("The company has already been accepted by an Admin.");
        }

        company.setAccessible(true);

        boolean b = getDao().updateOne(companyId, company);

        if (b) {
            return companyId;
        } else {
            throw new BadArgumentsException("The request could not be accepted.");
        }
    }

    // TODO available only for Admin
    // TODO SEND NOTIF
    /**
     * Refuse a request to publish a new Company.
     *
     * @param companyId The id of the company to refuse.
     *
     * @return The id of the refused company if the operation was successful, else throw an exception.
     * @throws BadArgumentsException If the company does not exist or if the company has already been accepted by an Admin.
     */
    public ObjectId refuseRequest(ObjectId companyId) throws BadArgumentsException {
        Company company = Facade.getInstance().getOneCompany(companyId);

        if (company == null) {
            throw new BadArgumentsException("The company does not exist.");
        }

        if (company.isAccessible()) {
            throw new BadArgumentsException("The company has already been accepted by an Admin.");
        }

        boolean b = getDao().deleteOne(companyId);

        if (b) {
            return companyId;
        } else {
            throw new BadArgumentsException("The request could not be refused.");
        }
    }

    /**
     * Return the list of companies that are accessible.
     *
     * @return The list of accessible companies if any, throws a NotFoundException otherwise.
     */
    public List<Company> findAllAccessibleCompanies() {
        return getDao().findAllAccessibleCompanies();
    }

    /**
     * Return the list of companies that are not accessible.
     *
     * @return The list of companies that are not accessible if there are any, a NotFoundException is thrown otherwise.
     */
    public List<Company> findAllUnaccessibleCompanies() {
        return getDao().findAllUnaccessibleCompanies();
    }

    /**
     * Return the companies where the user is a manager or masterManager.
     *
     * @param userId The id of the user.
     *
     * @return The list of companies where the user is a manager or masterManager.
     */
    public List<Company> findAllCompaniesByUserId(ObjectId userId) {
        return getDao().findAllCompaniesByUserId(userId);
    }
    /**
     * Add a manager to a company.
     *
     * @param companyId The id of the company.
     * @param managerId The id of the manager to add.
     *
     * @return The id of the updated company if the manager was added, throws a BadArgumentsException otherwise.
     * @throws BadArgumentsException if the company or the manager does not exist.
     */
    public ObjectId addManager(ObjectId companyId, ObjectId managerId) throws BadArgumentsException {
        return getDao().addManager(companyId, managerId);
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
        return getDao().removeManager(companyId, managerId);
    }

    /**
     * Add a user to the list of users that follow the company.
     *
     * @param companyId The id of the company.
     * @param userId The id of the user.
     *
     * @return The id of the company if the user was added successfully, else throws a BadArgumentsException.
     * @throws BadArgumentsException If the company or the user does not exist.
     */
    public ObjectId followCompany(ObjectId companyId, ObjectId userId) throws BadArgumentsException {
        return getDao().followCompany(companyId, userId);
    }

    /**
     * remove a user from the list of users that follow the company.
     *
     * @param companyId The id of the company.
     * @param userId The id of the user.
     *
     * @return The id of the company if the user was added successfully, else throws a BadArgumentsException.
     * @throws BadArgumentsException If the company or the user does not exist.
     */
    public ObjectId unfollowCompany(ObjectId companyId, ObjectId userId) throws BadArgumentsException {
        return getDao().unfollowCompany(companyId, userId);
    }



}
