package ui.app.component.field;

import javafx.scene.layout.Pane;

public abstract class Field extends Pane {

	protected boolean isRequired;

	public abstract String getValue();

	public abstract String getLabel();

	public boolean isValid() {
		if (isRequired && (getValue() == null || getValue().isEmpty())) {
			// The field is required and empty.
			showError();
			return false;
		}
		else {
			// The field is valid.
			hideError();
			return true;
		}
	}

	protected abstract void showError();

	protected abstract void hideError();

	public abstract void setReadOnly(boolean readOnly);
}
