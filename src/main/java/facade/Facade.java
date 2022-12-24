package facade;

import exception.BadCredentialException;
import exception.InvalidUsernameException;
import exception.NotFoundException;
import logic.controller.referencing.ReferencingController;
import org.bson.types.ObjectId;
import persistence.entity.partner.Partner;
import persistence.entity.referencing.Referencing;
import persistence.entity.user.User;

import java.util.Date;
import java.util.List;

/**
 * Global facade partner interface.
 * This interface is used to define the methods that will be used by the UI.
 */
public class Facade implements FacadeInterface {

    /**
     * Singleton instance.
     */
    private static Facade instance;

    private Facade() { }

    /**
     * Get the singleton instance of the facade.
     *
     * @return The singleton instance.
     */
    public static Facade getInstance() {
        if (instance == null) {
            instance = new Facade();
        }
        return instance;
    }

    /**
     * Insert a partner.
     *
     * @param partner The partner to insert.
     * @return The id of the inserted partner.
     */
    @Override
    public ObjectId insertOnePartner(Partner partner) {
        return PartnerFacade.getInstance().insertOnePartner(partner);
    }

    /**
     * Get all partners.
     *
     * @return A list of partners.
     */
    @Override
    public List<Partner> getPartnerList() {
        return PartnerFacade.getInstance().getPartnerList();
    }

    /**
     * Get a partner by its id.
     *
     * @param id The id of the partner.
     * @return The partner.
     */
    @Override
    public Partner getOnePartner(ObjectId id) {
        return PartnerFacade.getInstance().getOnePartner(id);
    }

    /**
     * Update a partner.
     *
     * @param id The id of the partner to update.
     * @param partner The new partner.
     * @return true if the partner has been updated, false otherwise.
     */
    @Override
    public boolean updateOnePartner(ObjectId id, Partner partner) {
        return PartnerFacade.getInstance().updateOnePartner(id, partner);
    }

    /**
     * Delete a partner.
     *
     * @param id The id of the partner to delete.
     * @return true if the partner has been deleted, false otherwise.
     */
    @Override
    public boolean deleteOnePartner(ObjectId id) {
        return PartnerFacade.getInstance().deleteOnePartner(id);
    }

    @Override
    public ObjectId register(User user) throws InvalidUsernameException {
        return UserFacade.getInstance().register(user);
    }

    /**
     * Login a user.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return The user if the login is successful.
     */
    @Override
    public User login(String username, String password) throws BadCredentialException {
        return UserFacade.getInstance().login(username, password);
    }

    /**
     * Get one user by its id.
     * @param id The id of the user.
     * @return The user.
     */
    @Override
    public User getOneUser(ObjectId id) {
        return UserFacade.getInstance().getOneUser(id);
    }

    /**
     * Get a user by its username.
     * @param username The username of the user to find.
     * @return The user.
     * @throws NotFoundException if the user is not found.
     */
    @Override
    public User getOneUserByUsername(String username) throws NotFoundException {
        return UserFacade.getInstance().getOneUserByUsername(username);
    }

    /**
     * Update a user.
     *
     * @param id The id of the user to update.
     * @param user The new user.
     * @return true if the user has been updated, false otherwise.
     */
    @Override
    public boolean updateOneUser(ObjectId id, User user) {
        return UserFacade.getInstance().updateOneUser(id, user);
    }

    @Override
    public boolean deleteOneUser(ObjectId id) {
        return UserFacade.getInstance().deleteOneUser(id);
    }

    /**
     * Insert a referencing.
     *
     * @param referencing The referencing to insert.
     * @return The id of the inserted referencing.
     */
    public ObjectId insertOneReferencing(Referencing referencing) {
        return ReferencingFacade.getInstance().insertOneReferencing(referencing);
    }

    /**
     * Get all referencings.
     *
     * @return A list of referencings.
     */
    public List<Referencing> getReferencingList() {
        return ReferencingFacade.getInstance().getReferencingList();
    }

    /**
     * Get a referencing by its id.
     *
     * @param id The id of the referencing.
     * @return The referencing or null if not found.
     */
    public Referencing getOneReferencing(ObjectId id) {
        return ReferencingFacade.getInstance().getOneReferencing(id);
    }

    /**
     * Get referencings by their importanceLevel.
     *
     * @param importanceLevel The level of importance of the searched referencings.
     * @return A list of referencings.
     */
    public List<Referencing> getReferencingByLevel(int importanceLevel) {
        return ReferencingFacade.getInstance().getReferencingByLevel(importanceLevel);
    }

    /**
     * Get referencings by their company id.
     *
     * @param company The id of the referenced company.
     * @return A list of referencings.
     */
    public List<Referencing> getReferencingByCompany(ObjectId company) {
        return ReferencingFacade.getInstance().getReferencingByCompany(company);
    }

    /**
     * Update a referencing.
     *
     * @param id The id of the referencing to update.
     * @param referencing The new referencing.
     * @return true if the referencing has been updated, false otherwise.
     */
    public boolean updateOneReferencing(ObjectId id, Referencing referencing) {
        return ReferencingFacade.getInstance().updateOneReferencing(id, referencing);
    }

    /**
     * Delete a referencing.
     *
     * @param id The id of the referencing to delete.
     * @return true if the referencing has been deleted, false otherwise.
     */
    public boolean deleteOneReferencing(ObjectId id) {
        return ReferencingFacade.getInstance().deleteOneReferencing(id);
    }

    /**
     * Update the status of a referencing.
     *
     * @param id The id of the referencing to update.
     * @param referencing The new referencing.
     * @return true if the referencing has been updated, false otherwise.
     */
    public boolean updateStatus(ObjectId id, Referencing referencing){ return ReferencingFacade.getInstance().updateStatus(id, referencing);}
}
