package ui.view;

import ui.controller.Controller;

public interface View {
	public void update();
	
	public void setController(Controller cont);
	public Controller getController();
}
