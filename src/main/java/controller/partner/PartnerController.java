package controller.partner;

import controller.AbstractController;
import entity.partner.Partner;
import model.Model;
import model.partner.PartnerModel;

/**
 * PartnerController class extending Controller class parametized with Partner class.
 */
public class PartnerController extends AbstractController<Partner> {

    /**
     * instance of PartnerController to ensure Singleton design pattern
     */
    private static PartnerController instance;

    /**
     * private constructor for PartnerController to ensure Singleton design pattern
     */
    private PartnerController() { }

    /**
     * @return the instance of PartnerController to ensure Singleton design pattern
     */
    public static PartnerController getInstance() {
        if(instance == null){
            instance = new PartnerController();
        }
        return instance;
    }

    /**
     * @return the model of the specific Controller (PartnerModel)
     */
    @Override
    protected Model<Partner> getModel() {
        return PartnerModel.getInstance();
    }
}
