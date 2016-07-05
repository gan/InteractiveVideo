package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

public class WindowPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3717772915986624613L;

	@SuppressWarnings("unused")
	private final ToolsPanel toolsPanel;
	private final VideoPanel videoPanel;
	private final ChapterPanel chapterPanel;
	private final CanvasPanel canvasPanel;
	private final MenuPanel menuPanel;
	
	private DrawingApp drawingApp;
	
	public WindowPanel(DrawingApp drawingApp) {
		super();
		
		this.drawingApp = drawingApp;
		this.setLayout(new BorderLayout());
		//this.setPreferredSize(new Dimension(100, 1000));
		
		this.add(toolsPanel = new ToolsPanel(drawingApp), BorderLayout.WEST);
		this.add(videoPanel = new VideoPanel(drawingApp), BorderLayout.CENTER);
		this.add(chapterPanel = new ChapterPanel(drawingApp), BorderLayout.EAST);
		this.add(canvasPanel = new CanvasPanel(drawingApp), BorderLayout.SOUTH);
		this.add(menuPanel = new MenuPanel(drawingApp), BorderLayout.NORTH);
		
		drawingApp.getChapters().addObserver(chapterPanel);
		drawingApp.getMenus().addObserver(menuPanel);
		drawingApp.getCanvases().addObserver(videoPanel);
		drawingApp.getCanvases().addObserver(canvasPanel);
		this.setVisible(true);
	}
	
}
