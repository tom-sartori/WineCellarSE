package ui.app.page.company.advertising.list;

import exception.NotFoundException;
import facade.Facade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import org.bson.types.ObjectId;
import persistence.entity.advertising.Advertising;
import persistence.entity.company.Company;
import ui.app.State;
import ui.app.page.company.advertising.AdvertisingCard;

import javax.xml.stream.events.Comment;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdvertisingList implements Initializable {

    @FXML
    private GridPane cardHolder;

    @FXML
    private ChoiceBox<String> select, selectStatus;
    private ObservableList<AdvertisingCard> cardList = FXCollections.observableArrayList();

    private final int nbColumn = 2;

    private ObjectId company;
    private static String status;

    /**
     * Initializes the controller class, the select fields for the company and the status.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(Facade.getInstance().isUserLogged()){

            List<Company> companies;
            if(Facade.getInstance().isAdminLogged()){
                companies = Facade.getInstance().getCompanyList();
                System.out.println(companies.size());
            } else {
                companies = Facade.getInstance().findAllCompaniesByUserId(State.getInstance().getCurrentUser().getId());
            }

            /**
             * If the list was open from de the detail page of a company, the select is initialized with this company
             * Else it is initialized with the first company from the list companies.
             */
            Company c = State.getInstance().getSelectedCompany();
            if(c == null){
                c = companies.get(0);
            }
            company = c.getId();

            if(status == null){
                status = "Toutes";
            }

            select.getSelectionModel().select(c.getName());
            selectStatus.getSelectionModel().select(status);

            if(select.getItems().size() == 0){
                for (Company comp : companies){
                    select.getItems().add(comp.getName());
                }
            }

            select.setOnAction((event) -> {
                String selectedItem = select.getValue();
                if(selectedItem.equals("Toutes")){
                    list(null, status);
                }
                for(Company comp : companies){
                    if(comp.getName().equals(selectedItem)){
                        company = comp.getId();
                        list(comp.getId(),status);
                        State.getInstance().setSelectedCompany(comp);
                    }
                }
            });

            /**
             * If the user is not admin, they can see all status.
             * Admins can see only unvalidated advertisings.
             */
            if(!Facade.getInstance().isAdminLogged()){
                selectStatus.setVisible(true);

                if(selectStatus.getItems().size() == 0){
                    selectStatus.getItems().add("Toutes");
                    selectStatus.getItems().add("Validées");
                    selectStatus.getItems().add("Demandes");
                }

                selectStatus.setOnAction((event) -> {
                    String selectedItem = selectStatus.getValue();
                    status = selectedItem;
                    if(company!=null){
                        list(company,status);
                    }
                });

            } else {
                if(!select.getItems().contains("Toutes")){
                    select.getItems().add("Toutes");
                }
                status = "Demandes";
                selectStatus.setVisible(false);
            }

            list(company,status);
        }
    }

    /**
     * Check the selected company and status value to retrieve the advertisings and put them in the list.
     * @param company the selected company or the first one if there are no company selected.
     * @param status the selected status or "Toutes".
     */
    public void list(ObjectId company, String status){
        try {
            cardList.clear();
            List<Advertising> advertisingList;

            if(company != null){
                if(status.equals("Validées")){
                    advertisingList = Facade.getInstance().getValidatedAdvertisingsByCompany(company);
                } else if(status.equals("Demandes")){
                    advertisingList = Facade.getInstance().getNotValidatedAdvertisingsByCompany(company);
                } else {
                    advertisingList = Facade.getInstance().getAdvertisingsByCompany(company);
                }
            } else {
                advertisingList = Facade.getInstance().getNotValidatedAdvertisings();
                System.out.println("hhhhhhhhhhhh "+advertisingList.size());
            }

            int maxWidth = 1280;
            int gapBetweenCard = 20;
            double preferredHeight = 230.0;
            double preferredWidth = (maxWidth - (nbColumn + 1) * gapBetweenCard) / nbColumn;
            advertisingList.forEach(advertising -> cardList.add(new AdvertisingCard(advertising, "advertisingList")));

            cardHolder.setAlignment(Pos.CENTER);
            cardHolder.setVgap(20.00);
            cardHolder.setHgap(30.00);
            cardHolder.setStyle("-fx-padding:95px;");

        } catch (NotFoundException e){

        }

        onSearch();
    }
    @FXML
    public void onSearch() {
        int count = 0;

        cardHolder.getChildren().clear();
        for (AdvertisingCard card : cardList) {
            cardHolder.add(card, count % nbColumn, count / nbColumn);
            count++;
        }
    }

    public void onAction() {
        this.initialize(null, null);
    }
}
