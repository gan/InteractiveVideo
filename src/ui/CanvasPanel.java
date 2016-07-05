package ui;

import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import model.CanvasBO;
import ui.tools.CanvasButton;
public class CanvasPanel extends JPanel implements Observer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 693399026725056106L;

	private final String title = "Canvases";
	
	private final DrawingApp drawingApp;
	
	public CanvasPanel(DrawingApp drawingApp){
		super();
		
		this.setBackground(Color.lightGray);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setBorder(BorderFactory.createTitledBorder(title));	
		this.drawingApp = drawingApp;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		this.removeAll();
		this.revalidate();
		for(CanvasBO canvas : drawingApp.getCanvases().getlCanvas()){
			this.add(new CanvasButton(canvas, drawingApp));
		}
	}
}
