package ui.app.page.company.list;

import constant.NodeCreations;
import exception.NotFoundException;
import exception.user.NoLoggedUser;
import facade.Facade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import persistence.entity.company.Company;
import persistence.entity.referencing.Referencing;
import ui.app.component.card.CardComponent;
import ui.app.helpers.services.CustomSceneHelper;
import ui.app.page.company.form.create.CompanyCreate;

import java.net.URL;
import java.util.*;

public class CompanyList implements Initializable {

    @FXML
    private HBox titleHBox;

    @FXML
    private FlowPane listFlowPane;

    public static <K, V extends Comparable<V> > Map<K, V> valueSort(final Map<K, V> map) {
        // Static Method with return type Map and
        // extending comparator class which compares values
        // associated with two keys
        Comparator<K> valueComparator = new Comparator<K>() {
            // return comparison results of values of
            // two keys
            public int compare(K k1, K k2) {
                int comp = map.get(k1).compareTo(map.get(k2));
                if (comp == 0){
                    return 1;
                }
                else{
                    return -comp;
                }
        }
    };

    // SortedMap created using the comparator
    Map<K, V> sorted = new TreeMap<K, V>(valueComparator);

    sorted.putAll(map);

    return sorted;
}

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        titleHBox.getChildren().clear();

        try {
            if (Facade.getInstance().getLoggedUser() != null) {
                Button createCompany = NodeCreations.createButton("Créer une entreprise");

                createCompany.setOnAction(event -> {
                    CustomSceneHelper sceneHelper = new CustomSceneHelper();
                    sceneHelper.bringNodeToFront(CompanyCreate.class.getSimpleName());
                });

                titleHBox.getChildren().add(createCompany);
            }
        } catch (NoLoggedUser e) {
            // do nothing
        }

        listFlowPane.getChildren().clear();

        listFlowPane.setHgap(10);
        listFlowPane.setVgap(10);

        try{
            List<Company> allAccessibleCompanies = Facade.getInstance().findAllAccessibleCompanies();

            TreeMap<Company, Integer> companies = new TreeMap<>();

            allAccessibleCompanies.forEach(company -> {
                try{
                    List<Referencing> enCours = Facade.getInstance().getReferencingsByCompanyByStatus(company.getId(), "En cours");
                    int maxImportanceLevel = 0;
                    for (Referencing enCour : enCours) {
                        if (enCour.getImportanceLevel() > maxImportanceLevel) {
                            maxImportanceLevel = enCour.getImportanceLevel();
                        }
                    }
                    companies.put(company, maxImportanceLevel);
                }catch (NotFoundException e){
                    companies.put(company, 0);
                }
            });

            Map<Company, Integer> sortedCompanies = valueSort(companies);

            sortedCompanies.forEach((company, importanceLevel) -> {
                listFlowPane.getChildren().add(CardComponent.createCompanyCard(company));
            });

//            companies.forEach((importanceLevel, company) -> {
//                listFlowPane.getChildren().add(CardComponent.createCompanyCard(company));
//            });
        }catch (NotFoundException e){
            // do nothing
        }
    }
}
