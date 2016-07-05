package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import ui.tools.AddMenuButton;
import ui.tools.ChapterButton;
import model.ChapterBO;

public class ChapterPanel extends JPanel implements Observer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5922853901645643231L;

	private final String title = "Chapters";
	
	private final DrawingApp drawingApp;
	public ChapterPanel(DrawingApp drawingApp) {
		super();
		
		this.setBackground(Color.lightGray);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createTitledBorder(title));	
		this.drawingApp = drawingApp;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		this.removeAll();
		this.revalidate();
		for(ChapterBO chapter : drawingApp.getChapters().getlChapters()){
			ChapterButton chapterButton = new ChapterButton(drawingApp, chapter);
			chapterButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, chapterButton.getMinimumSize().height));
			this.add(chapterButton);
		}		
	}

}
