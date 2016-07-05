package model;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Observable;

public class CanvasModel extends Observable{
	private ArrayList<CanvasBO> lCanvas = new ArrayList<CanvasBO>();
	private String bgImage;
	private boolean isVideo;
	private boolean isImage;
	
	public void add(CanvasBO canvas){
		lCanvas.add(canvas);
		setChanged();
		notifyObservers();
	}
	
	public CanvasBO get(int index) {
		notifyObservers();
		return 	this.lCanvas.get(index);
	}
	
	public void setBgImage(String image){
		this.bgImage = image;
		setChanged();
		notifyObservers();
	}
	
	public String getBgImage() {
		return this.bgImage;
	}
	
	public void changeCanvas() {
		setChanged();
		notifyObservers();
	}
	
	public ArrayList<CanvasBO> getlCanvas() {
		return lCanvas;
	}
	
	public void setlCanvas(ArrayList<CanvasBO> lCanvas) {
		this.lCanvas = lCanvas;
	}

	public boolean isVideo() {
		return isVideo;
	}

	public void setVideo(boolean isVideo) {
		this.isVideo = isVideo;
	}

	public boolean isImage() {
		return isImage;
	}

	public void setImage(boolean isImage) {
		this.isImage = isImage;
	}
	
	
	
}
