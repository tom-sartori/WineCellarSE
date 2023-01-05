package ui.app.page.company.advertising.list;

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

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdvertisingList implements Initializable {

    @FXML
    private GridPane cardHolder;

    @FXML
    private ChoiceBox<String> select;
    private ObservableList<AdvertisingCard> cardList = FXCollections.observableArrayList();

    private final int nbColumn = 4;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(State.getInstance().getCurrentUser() != null){
            List<Company> companies = Facade.getInstance().findAllCompaniesByUserId(State.getInstance().getCurrentUser().getId());
            if(select.getItems().size() == 0){
                for (Company c : companies){
                    select.getItems().add(c.getName());
                }
            }

            select.setOnAction((event) -> {
                String selectedItem = select.getValue();
                for(Company c : companies){
                    if(c.getName().equals(selectedItem)){
                        list(c.getId());
                    }
                }
            });
        }
    }

    public void list(ObjectId company){
        cardList.clear();
        List<Advertising> advertisingList;
        advertisingList = Facade.getInstance().getAdvertisingsByCompany(company);

        int maxWidth = 1280;
        int gapBetweenCard = 20;
        double preferredHeight = 230.0;
        double preferredWidth = (maxWidth - (nbColumn + 1) * gapBetweenCard) / nbColumn;
        advertisingList.forEach(advertising -> cardList.add(new AdvertisingCard(advertising, preferredHeight, preferredWidth, "advertisingList")));

        cardHolder.setAlignment(Pos.CENTER);
        cardHolder.setVgap(20.00);
        cardHolder.setHgap(20.00);
        cardHolder.setStyle("-fx-padding:10px;");

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
