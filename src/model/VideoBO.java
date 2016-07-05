package model;

import java.util.Observable;

public class VideoBO extends Observable {
	protected String name;
	protected String width;
	protected String height;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
		setChanged();
		notifyObservers();
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
		setChanged();
		notifyObservers();
	}
}
