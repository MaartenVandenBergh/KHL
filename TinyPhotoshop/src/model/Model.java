package model;

import ui.view.View;

public interface Model {
	public void registerView(View v);
	public void removeView(View v);
	public void notifyAllViews();
	public void notifyView(int index);

}
