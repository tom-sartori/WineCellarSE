package ui.app.page.company.form.update;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import persistence.entity.company.Company;
import ui.app.State;
import ui.app.component.field.labelfield.LabelField;
import ui.app.component.form.Form;

import java.net.URL;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class CompanyUpdate implements Initializable, Observer {

    @FXML
    private AnchorPane titlePaneCreateBottle;

    @FXML
    private AnchorPane mainPaneCreateBottle;

    @FXML
    private Form formController;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        titlePaneCreateBottle.getChildren().add(new Label("Description d'une entreprise"));

        formController.addObserver(this);

        formController.clearFieldList();

        if (State.getInstance().getSelectedCompany() != null) {
            Company company = State.getInstance().getSelectedCompany();

            formController.addField(new LabelField("Nom de la société", company.getName(),true));
            formController.addField(new LabelField("Type entreprise", company.getType(),true));
            formController.addField(new LabelField("Logo", company.getLogoLink(),true));
            formController.addField(new LabelField("Master manager", company.getMasterManager().toString(),true));
            formController.addField(new LabelField("Cellar", company.getCellar().toString(),true));
            formController.addField(new LabelField("Description", company.getDescription(),true));
            formController.addField(new LabelField("Adresse", company.getAddress(),true));
            formController.addField(new LabelField("Téléphone", company.getPhoneNumber(),true));
            formController.addField(new LabelField("Site web", company.getWebsiteLink(),true));

            formController.initialize(null, null);
        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
