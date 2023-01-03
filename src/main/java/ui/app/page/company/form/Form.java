package ui.app.page.company.form;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import ui.app.component.errorlabel.ErrorLabel;
import ui.app.component.field.Field;

import java.awt.*;
import java.net.URL;
import java.util.*;

public class Form extends Observable implements Initializable {

    @FXML
    private ErrorLabel errorLabelController;

    @FXML
    private GridPane fieldHolder;

    private Label label;

    private ObservableList<Field> fieldList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fieldHolder.getChildren().clear();
        fieldHolder.setAlignment(Pos.CENTER);
        fieldHolder.setVgap(20.00);
        fieldHolder.setHgap(20.00);
        fieldHolder.setStyle("-fx-padding:10px;");
        fieldHolder.getChildren().clear();

        int count = 0;
        for (Field field : fieldList) {
            fieldHolder.add(field, count % 2, count / 2);
            count++;
        }

        errorLabelController.hide();
    }

    public void clearFieldList() {
        fieldList.clear();
    }

    public void addField(Field field) {
        fieldList.add(field);
    }

    public void submitButtonClicked() {
        if (isFormValid()) {
            hideErrorLabel();

            setChanged();
            Map<String, Object> fieldMap = new HashMap<>();
            fieldHolder.getChildren().forEach(node -> {
                Field field = (Field) node;
                fieldMap.put(field.getLabel(), field.getValue());
            });
            notifyObservers(fieldMap);
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
        // For each Field, check if it is valid.
        return this.fieldHolder.getChildren()
                .stream()
                .map(node -> (Field) node)
                .filter(Objects::nonNull)
                .filter(field -> !field.isValid())
                .count() == 0;
    }
}
