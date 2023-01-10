package ui.app.page.company.form.update;

import constant.NodeCreations;
import exception.BadArgumentsException;
import facade.Facade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.bson.types.ObjectId;
import persistence.entity.cellar.Cellar;
import persistence.entity.company.Company;
import persistence.entity.user.User;
import ui.app.State;
import ui.app.component.field.labelfield.LabelField;
import ui.app.component.form.Form;
import ui.app.helpers.CustomSceneHelper;
import ui.app.page.cellar.details.CellarDetails;

import java.net.URL;
import java.util.*;

public class CompanyUpdate implements Initializable, Observer {

    @FXML
    private AnchorPane titlePaneUpdateCompany;

    @FXML
    private AnchorPane mainPaneUpdateCompany;

    @FXML
    private Form formController;

    @FXML
    private VBox managersListUpdateCompany;

    private final CustomSceneHelper sceneHelper = new CustomSceneHelper();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        titlePaneUpdateCompany.getChildren().clear();

        titlePaneUpdateCompany.getChildren().add(new Label("Mise à jour d'une entreprise"));

        formController.addObserver(this);

        formController.clearFieldList();

        if (State.getInstance().getSelectedCompany() != null) {
            Company company = State.getInstance().getSelectedCompany();

            formController.addField(new LabelField("Nom de la société", company.getName(),true));
            formController.addField(new LabelField("Type entreprise", company.getType(),true));
            formController.addField(new LabelField("Description", company.getDescription(),false));
            formController.addField(new LabelField("Adresse", company.getAddress(),true));
            formController.addField(new LabelField("Téléphone", company.getPhoneNumber(),true));
            formController.addField(new LabelField("Site web", company.getWebsiteLink(),false));

            formController.initialize(null, null);
        }

        initManagerList();

        //             formController.addField(new LabelField("Cellar", company.getCellar().toString(),true));
        //             formController.addField(new LabelField("Master manager", company.getMasterManager().toString(),true));

    }

    public void initManagerList(){
        if (State.getInstance().getSelectedCompany() != null) {

            State.getInstance().setSelectedCompany(Facade.getInstance().getOneCompany(State.getInstance().getSelectedCompany().getId()));

            managersListUpdateCompany.getChildren().clear();

            VBox managers = createBoxForManagers();
            HBox managersBoxTitle = createTitleBox("Liste des Gestionnaires", 18);

            Button addManager = NodeCreations.createButton("+");
            addManager.onActionProperty().set(event -> {
                addManagerAction();
            });
            managersBoxTitle.getChildren().add(addManager);
            managers.getChildren().add(managersBoxTitle);


            User masterManager = Facade.getInstance().getOneUser(State.getInstance().getSelectedCompany().getMasterManager());
            managers.getChildren().add(new Label(masterManager.getUsername()));

            for (int i = 0; i < State.getInstance().getSelectedCompany().getManagerList().size(); i++) {
                HBox manager = createManager(State.getInstance().getSelectedCompany().getManagerList().get(i));
                managers.getChildren().add(manager);
            }
            managersListUpdateCompany.getChildren().add(managers);

            Button updateCompanyCellar = NodeCreations.createButton("Mettre à jour la cave");
            updateCompanyCellar.onActionProperty().set(event -> {
                Cellar oneCellar = Facade.getInstance().getOneCellar(State.getInstance().getSelectedCompany().getCellar());
                State.getInstance().setSelectedCellar(oneCellar);
                sceneHelper.bringNodeToFront(CellarDetails.class.getSimpleName());
            });

            managersListUpdateCompany.getChildren().add(updateCompanyCellar);
        }
    }

    public VBox createBoxForManagers(){
        VBox vBox = new VBox();

        vBox.setAlignment(Pos.CENTER);
        vBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setSpacing(10);

        return vBox;
    }

    /**
     * Create a title box for managers.
     *
     * @param title the title of the label
     * @param fontSize the font size of the title
     *
     * @return the title box with the title and the font size set in a HBox.
     */
    public HBox createTitleBox(String title, int fontSize){
        HBox hBox = new HBox();

        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font(fontSize));

        hBox.getChildren().add(titleLabel);

        return hBox;
    }

    /**
     * Create a manager or reader.
     *
     * @param userId the id of the user.
     *
     * @return A Hbox with the information of the user and a button to remove him.
     */
    public HBox createManager(ObjectId userId){
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);

        User oneUser = Facade.getInstance().getOneUser(userId);

        hBox.getChildren().add(new Label(oneUser.getUsername()));

        Button deleteManager = NodeCreations.createButton("-");
        deleteManager.onActionProperty().set(event -> {
            deleteManager(oneUser);
        });

        Button makeMasterManager = NodeCreations.createButton("Promouvoir");
        makeMasterManager.onActionProperty().set(event -> {
            try {
                Facade.getInstance().addManager(State.getInstance().getSelectedCompany().getId(), State.getInstance().getSelectedCompany().getMasterManager());
                Facade.getInstance().promoteNewMasterManager(State.getInstance().getSelectedCompany().getId(), oneUser.getId());
                deleteManager(oneUser);
                initManagerList();
            } catch (BadArgumentsException e) {
                Alert erreur = NodeCreations.createAlert("Erreur", "Erreur lors de la promotion du gestionnaire", e.getMessage(), Alert.AlertType.ERROR);
                erreur.show();
            }
        });

        hBox.getChildren().add(makeMasterManager);
        hBox.getChildren().add(deleteManager);

        return hBox;
    }

    /**
     * Adds a manager to the cellar.
     */
    public void addManagerAction(){
        List<User> userList = Facade.getInstance().getUserList();
        ArrayList<String> userNames = new ArrayList<>();
        List<ObjectId> managerOrReaderList = State.getInstance().getSelectedCompany().getManagerList();

        for (User user : userList) {
            if (!managerOrReaderList.contains(user.getId())){
                userNames.add(user.getUsername());
            }
        }
        ChoiceDialog<String> dialog = new ChoiceDialog<>("", userNames);
        Optional<String> o = dialog.showAndWait();

        if (o.isPresent()) {
            String username = o.get();

            User user = Facade.getInstance().getOneUserByUsername(username);

            try {
                Facade.getInstance().addManager(State.getInstance().getSelectedCompany().getId(), user.getId());
                initManagerList();
            } catch (Exception e) {
                NodeCreations.createAlert("Erreur", "Erreur lors de l'ajout du manager ou du lecteur.", e.getMessage(), Alert.AlertType.ERROR).showAndWait();
            }
        }
    }

    public void deleteManager(User user){
        Alert alert = NodeCreations.createAlert("Suppression d'un manager", "Suppression d'un manager","Êtes-vous sûr de vouloir supprimer le manager ?", Alert.AlertType.CONFIRMATION);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            try {
                Facade.getInstance().removeManager(State.getInstance().getSelectedCompany().getId(), user.getId());
                initManagerList();
            } catch (BadArgumentsException e) {
                Alert error = NodeCreations.createAlert("Erreur", "Erreur", e.getMessage(), Alert.AlertType.ERROR);
                error.showAndWait();
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        Map<String, Object> labelFieldMap = (Map<String, Object>) arg;

        String description = null;
        String websiteLink = null;
        String name = labelFieldMap.get("Nom de la société").toString();
        String type = labelFieldMap.get("Type entreprise").toString();
        if(labelFieldMap.get("Description") != null) {
            description = labelFieldMap.get("Description").toString();
        }
        String address = labelFieldMap.get("Adresse").toString();
        String phoneNumber = labelFieldMap.get("Téléphone").toString();
        if(labelFieldMap.get("Site web") != null) {
            websiteLink = labelFieldMap.get("Site web").toString();
        }

        try{
            if (Facade.getInstance().isManagerOfCompany(State.getInstance().getSelectedCompany().getId())){
                Company company = State.getInstance().getSelectedCompany();

                company.setName(name);
                company.setType(type);
                company.setDescription(description);
                company.setAddress(address);
                company.setPhoneNumber(phoneNumber);
                company.setWebsiteLink(websiteLink);

                Facade.getInstance().updateOneCompany(State.getInstance().getSelectedCompany().getId(), company);

                State.getInstance().setSelectedCompany(company);
                initialize(null, null);
                Alert success = NodeCreations.createAlert("Mise à jour réussie", "L'entreprise a bien été mise à jour", "L'entreprise a bien été mise à jour", Alert.AlertType.INFORMATION);
                success.showAndWait();
            }
        } catch (Exception e) {
            Alert error = NodeCreations.createAlert("Mise à jour refusé", "Vous n'êtes pas propriétaire de l'entreprise", "Vous n'êtes pas propriétaire de l'entreprise, vous ne pouvez donc pas la modifier", Alert.AlertType.ERROR);
            error.show();
        }
    }
}
