package ui.app.theme.menu.role;

import java.util.List;

public interface MenuEnumInterface {

	String getNavigationTitle();
	Class<?> getControllerClass();
	List<? extends MenuEnumInterface> getSubMenus();
}

