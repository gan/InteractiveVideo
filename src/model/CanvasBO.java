package model;

import java.util.Observable;

public class CanvasBO extends Observable{
	protected String id;
	protected String positionX;
	protected String positionY;
	protected String canvasWidth;
	protected String canvasHeight;
	
	public CanvasBO(String id,
			String positionX, String positionY, 
			String canvasWidth, String canvasHeight) {
		super();
		this.id = id;
		this.positionX = positionX;
		this.positionY = positionY;
		this.canvasWidth = canvasWidth;
		this.canvasHeight = canvasHeight;
	}
	
	public void set(String id,
			String positionX, String positionY, 
			String canvasWidth, String canvasHeight) {
		this.id = id;
		this.positionX = positionX;
		this.positionY = positionY;
		this.canvasWidth = canvasWidth;
		this.canvasHeight = canvasHeight;
	}
	
	public String getId(){
		return id;
	}
	public void setId(String id){
		setChanged();
		notifyObservers();
		this.id = id;
	}
	public String getPositionX() {
		return positionX;
	}
	public void setPositionX(String positionX) {
		setChanged();
		notifyObservers();
		this.positionX = positionX;
	}
	public String getPositionY() {
		return positionY;
	}
	public void setPositionY(String positionY) {
		setChanged();
		notifyObservers();
		this.positionY = positionY;
	}
	public String getCanvasWidth() {
		return canvasWidth;
	}
	public void setCanvasWidth(String canvasWidth) {
		setChanged();
		notifyObservers();
		this.canvasWidth = canvasWidth;
	}
	public String getCanvasHeight() {
		return canvasHeight;
	}
	public void setCanvasHeight(String canvasHeight) {
		setChanged();
		notifyObservers();
		this.canvasHeight = canvasHeight;
	}
}
