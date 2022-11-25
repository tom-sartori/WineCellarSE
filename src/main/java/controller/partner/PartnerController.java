package controller.partner;

import controller.Controller;
import entity.partner.Partner;
import model.partner.PartnerModel;

public class PartnerController extends Controller {
    /// TODO : Class will change with the implementation of the generic controller.

    private final PartnerModel model;

    public PartnerController() {
        model = new PartnerModel();
    }

    public Partner getPartners(){
        return model.findFirst();
    }
}
