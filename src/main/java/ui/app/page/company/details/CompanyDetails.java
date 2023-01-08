package ui.app.page.company.details;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import persistence.entity.company.Company;
import ui.app.State;
import ui.app.component.field.labelfield.LabelField;
import ui.app.component.form.Form;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class CompanyDetails implements Initializable, Observer {

    @FXML
    private VBox mainPane;

    @FXML
    private Form form;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        form.addObserver(this);

        form.clearFieldList();

        form.addField(new LabelField("Nom de l'entreprise", true));

        form.initialize(null, null);
//        if (State.getInstance().getSelectedCompany() != null) {
//            Company company = State.getInstance().getSelectedCompany();
//
//
//        }
//
//        form.addField(new LabelField("Nom de la société", true));
//        form.addField(new LabelField("Type entreprise", true));
//        form.addField(new LabelField("Logo", true));
//        form.addField(new LabelField("Master manager", true));
//        form.addField(new LabelField("Cellar", true));
//        form.addField(new LabelField("Description", true));
//        form.addField(new LabelField("Adresse", true));
//        form.addField(new LabelField("Téléphone", true));
//        form.addField(new LabelField("Site web", true));
//
//        form.initialize(null, null);
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
