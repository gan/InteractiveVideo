package model;

import java.util.ArrayList;
import java.util.Observable;

public class MenusModel extends Observable{
	private ArrayList<MenuBO> lMenus = new ArrayList<MenuBO>();

	public void add(MenuBO menu){
		lMenus.add(menu);
		setChanged();
		notifyObservers();
	}
	
	public ArrayList<MenuBO> getlMenus() {
		return lMenus;
	}
	
	public void setlMenus(ArrayList<MenuBO> lMenus) {
		this.lMenus = lMenus;
	}
}
