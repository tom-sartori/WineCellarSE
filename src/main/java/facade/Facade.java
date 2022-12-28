package facade;

import logic.controller.advertising.AdvertisingController;
import org.bson.types.ObjectId;
import persistence.entity.advertising.Advertising;
import persistence.entity.partner.Partner;

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

    /**
     * Insert a advertising.
     *
     * @param advertising The advertising to insert.
     * @return The id of the inserted advertising.
     */
    public ObjectId insertOneAdvertising(Advertising advertising) {
        return AdvertisingController.getInstance().insertOne(advertising);
    }

    /**
     * Get all advertisings.
     *
     * @return A list of advertisings.
     */
    public List<Advertising> getAdvertisingList() {
        return AdvertisingFacade.getInstance().getAdvertisingList();
    }

    /**
     * Get an advertising by its id.
     *
     * @param id The id of the advertising.
     * @return The advertising or null if not found.
     */
    public Advertising getOneAdvertising(ObjectId id) {
        return AdvertisingFacade.getInstance().getOneAdvertising(id);
    }

    /**
     * Update a advertising.
     *
     * @param id The id of the advertising to update.
     * @param advertising The new advertising.
     * @return true if the advertising has been updated, false otherwise.
     */
    public boolean updateOneAdvertising(ObjectId id, Advertising advertising) {
        return AdvertisingFacade.getInstance().updateOneAdvertising(id, advertising);
    }

    /**
     * Delete a advertising.
     *
     * @param id The id of the advertising to delete.
     * @return true if the advertising has been deleted, false otherwise.
     */
    public boolean deleteOneAdvertising(ObjectId id) {
        return AdvertisingFacade.getInstance().deleteOneAdvertising(id);
    }

    /**
     * Renew an advertising.
     *
     * @param id The id of the advertising to renew.
     * @param endDate The new end date of the advertising.
     * @return true if the advertising has been renewed, false otherwise.
     */
    public boolean renewOneAdvertising(ObjectId id, Date endDate) {
        return AdvertisingFacade.getInstance().renewOneAdvertising(id, endDate);
    }

    /**
     * Pay for an advertising.
     *
     * @param id The id of the advertising to pay for.
     * @return true if the advertising has been paid, false otherwise.
     */
    public boolean payOneAdvertising(ObjectId id) {
        return AdvertisingFacade.getInstance().payOneAdvertising(id);
    }

    /**
     * Add a view to an advertising.
     *
     * @param id The id of the advertising.
     * @return true if the view was added to the advertising, false otherwise.
     */
    public boolean addView(ObjectId id) {
        return AdvertisingFacade.getInstance().addView(id);
    }

    /**
     * Validate an advertising.
     *
     * @param id The id of the advertising to validate.
     * @return true if the advertising has been validated, false otherwise.
     */
    public boolean validateAdvertising(ObjectId id) {
        return AdvertisingFacade.getInstance().validateAdvertising(id);
    }

    /**
     * Get advertising by their company id.
     *
     * @param company The id of the advertised company.
     * @return A list of advertisings.
     */
    public List<Advertising> getAdvertisingByCompany(ObjectId company) {
        return AdvertisingFacade.getInstance().getAdvertisingByCompany(company);
    }
}
