package ui.app.component.form;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import ui.app.component.errorLabel.ErrorLabel;
import ui.app.component.labelField.LabelField;
import ui.app.component.labelField.labelFieldMasked.LabelFieldMasked;

import java.net.URL;
import java.util.*;

public class Form extends Observable implements Initializable {

    @FXML
    private ErrorLabel errorLabelController;

    @FXML
    private GridPane labelFieldHolder;

    private ObservableList<LabelField> labelFieldList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        labelFieldHolder.getChildren().clear();
        labelFieldHolder.setAlignment(Pos.CENTER);
        labelFieldHolder.setVgap(20.00);
        labelFieldHolder.setHgap(20.00);
        labelFieldHolder.setStyle("-fx-padding:10px;");
        labelFieldHolder.getChildren().clear();

        int count = 0;
        for (LabelField labelField : labelFieldList) {
            labelFieldHolder.add(labelField, count % 2, count / 2);
            count++;
        }

        errorLabelController.hide();
    }

    public void clearLabelFieldList() {
        labelFieldList.clear();
    }

    public void addLabelField(LabelField labelField) {
        labelFieldList.add(labelField);
    }

    public void addLabelField(LabelFieldMasked labelFieldMasked) {
        labelFieldList.add(labelFieldMasked);
    }

    public void submitButtonClicked() {
        if (isFormValid()) {
            hideErrorLabel();

            setChanged();
            Map<String, Object> labelFieldMap = new HashMap<>();
            labelFieldHolder.getChildren().forEach(node -> {
                LabelField labelField = (LabelField) node;
                labelFieldMap.put(labelField.getLabel(), labelField.getValue());
            });
            notifyObservers(labelFieldMap);
        }
        else {
            // The form isn't valid.
            showErrorLabel("Formulaire non conforme. ");
        }
    }

    public void onKeyEnterSubmitForm(KeyEvent event) {
        // If the user press enter, we submit the form.
        if (event.getCode().toString().equals("ENTER")) {
            submitButtonClicked();
        }
    }

    public void showErrorLabel(String message) {
        errorLabelController.show(message);
    }

    public void hideErrorLabel() {
        errorLabelController.hide();
    }

    private boolean isFormValid() {
        // For each LabelField, check if it is valid.
        return this.labelFieldHolder.getChildren()
                .stream()
                .map(node -> (LabelField) node)
                .filter(Objects::nonNull)
                .filter(labelField -> !labelField.isValid())
                .count() == 0;
    }
}
