package ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import ui.tools.AddCanvasButton;
import ui.tools.AddChapterButton;
import ui.tools.AddMenuButton;
import ui.tools.GenerateButton;
import ui.tools.LoadImageButton;
import ui.tools.LoadVideoButton;
import ui.tools.ReadXMLButton;

public class ToolsPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2325767013719431351L;
	
	private final String title = "Tools";
	
	public ToolsPanel(DrawingApp drawingApp) {
		super();
		
		this.setBackground(Color.WHITE);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createTitledBorder(title));
	
		Box toolBox = Box.createVerticalBox();
		AddChapterButton addChapterButton = new AddChapterButton(drawingApp);
		addChapterButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, addChapterButton.getMinimumSize().height));
		toolBox.add(addChapterButton);
		
		AddCanvasButton addCanvasButton = new AddCanvasButton(drawingApp);
		addCanvasButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, addCanvasButton.getMinimumSize().height));
		toolBox.add(addCanvasButton);
		
		AddMenuButton addMenuButton = new AddMenuButton(drawingApp);
		addMenuButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, addMenuButton.getMinimumSize().height));
		toolBox.add(addMenuButton);
		
		LoadImageButton loadImageButton = new LoadImageButton(drawingApp);
		loadImageButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, loadImageButton.getMinimumSize().height));
		toolBox.add(loadImageButton);
		
		LoadVideoButton loadVideoButton = new LoadVideoButton(drawingApp);
		loadVideoButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, loadVideoButton.getMinimumSize().height));
		toolBox.add(loadVideoButton);
		
		ReadXMLButton readXMLButton = new ReadXMLButton(drawingApp);
		readXMLButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, readXMLButton.getMinimumSize().height));
		toolBox.add(readXMLButton);
		
		GenerateButton generateButton = new GenerateButton(drawingApp);
		generateButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, generateButton.getMinimumSize().height));
		toolBox.add(generateButton);
		
		this.add(toolBox);
	}
	
}
