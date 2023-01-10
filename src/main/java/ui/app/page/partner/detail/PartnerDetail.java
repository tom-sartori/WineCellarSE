package ui.app.page.partner.detail;

import facade.Facade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.bson.types.ObjectId;
import persistence.entity.partner.Partner;
import persistence.entity.partner.PartnerType;
import ui.app.component.field.labelfield.LabelField;
import ui.app.component.field.select.Select;
import ui.app.component.form.Form;
import ui.app.helpers.CustomSceneHelper;

import java.net.URL;
import java.util.*;

public class PartnerDetail implements Initializable, Observer {

    @FXML
    private Form formController;

    private ObjectId updatingPartnerId;
    private static final String labelName = "Nom";
    private static final String labelType = "Type";
    private static final String labelLink = "lien";
    private static final String labelAddress = "Adresse";
    private static final String labelDescription = "Description";
    private static final String labelPhone = "Téléphone";


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updatingPartnerId = null;
        formController.addObserver(this);

        formController.clearFieldList();
        formController.setReadonly(false);
        formController.setSubmitButtonVisibility(true);

        formController.addField(new LabelField(labelName, true));
        formController.addField(new Select(labelType, true, Arrays.stream(PartnerType.values()).map(PartnerType::getName).toArray(String[]::new)));
        formController.addField(new LabelField(labelLink, true));
        formController.addField(new LabelField(labelAddress, true));
        formController.addField(new LabelField(labelDescription, false));
        formController.addField(new LabelField(labelPhone, false));

        formController.setSubmitButtonText("Ajouter");

        formController.initialize(null, null);
    }

    public void switchToUpdate(Partner partner) {
        updatingPartnerId = partner.getId();
        formController.setReadonly(false);
        formController.setSubmitButtonVisibility(true);

        setFormWithValues(partner);
    }

    public void switchToDetail(Partner partner) {
        updatingPartnerId = null;
        formController.setReadonly(true);
        formController.setSubmitButtonVisibility(false);

        setFormWithValues(partner);
    }

    private void setFormWithValues(Partner partner) {
        formController.clearFieldList();

        formController.addField(new LabelField(labelName, partner.getName(), true));
        formController.addField(new Select(labelType, partner.getType().getName(), true, Arrays.stream(PartnerType.values()).map(PartnerType::getName).toArray(String[]::new)));
        formController.addField(new LabelField(labelLink, partner.getLink(), true));
        formController.addField(new LabelField(labelAddress, partner.getAddress(), true));
        formController.addField(new LabelField(labelDescription, partner.getDescription(), false));
        formController.addField(new LabelField(labelPhone, partner.getPhone(), false));

        formController.initialize(null, null);
    }

    @Override
    public void update(Observable o, Object arg) {
        Map<String, Object> labelFieldMap = (Map<String, Object>) arg;

        Partner partner = new Partner(
                labelFieldMap.get(labelName).toString(),
                PartnerType.getPartnerType(labelFieldMap.get(labelType).toString()),
                labelFieldMap.get(labelLink).toString(),
                labelFieldMap.get(labelAddress).toString(),
                labelFieldMap.get(labelDescription).toString(),
                labelFieldMap.get(labelPhone).toString()
        );

        if (updatingPartnerId == null) {
            // Create a partner.
            Facade.getInstance().insertOnePartner(partner);
        }
        else {
            // Update a partner.
            Facade.getInstance().updateOnePartner(updatingPartnerId, partner);
        }

        new CustomSceneHelper().bringNodeToFront("partnerList");
    }
}
