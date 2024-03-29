package ui.app.component.form;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import ui.app.component.errorlabel.ErrorLabel;
import ui.app.component.field.Field;

import java.net.URL;
import java.util.*;

public class Form extends Observable implements Initializable {

    @FXML
    private ErrorLabel errorLabelController;

    @FXML
    private GridPane fieldHolder;

    @FXML
    private Button submitButton;

    private ObservableList<Field> fieldList = FXCollections.observableArrayList();

    private boolean isReadonly;

    private VBox vBox;

    public Form() {
        vBox = new VBox();
        // Ajouter d'autres éléments au VBox ici
    }

    public VBox getVBox() {
        return vBox;
    }

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

        if (isReadonly) {
            fieldHolder.getChildren().forEach(node -> {
                Field field = (Field) node;
                field.setReadOnly(isReadonly);
            });
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

    public void setSubmitButtonText(String text) {
        submitButton.setText(text);
    }

    public void setSubmitButtonVisibility(boolean visible) {
        submitButton.setVisible(visible);
    }

    public void setReadonly(boolean readonly) {
        this.isReadonly = readonly;
        fieldHolder.getChildren().forEach(node -> {
            Field field = (Field) node;
            field.setReadOnly(readonly);
        });
    }

}
