package ui;

import javax.swing.JFrame;

import model.CanvasModel;
import model.ChaptersModel;
import model.CurrentSelection;
import model.MenusModel;
import model.VideoBO;

public class DrawingApp extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final WindowPanel 	windowPanel;
	
	private ChaptersModel chapters = new ChaptersModel();
	private CanvasModel canvases = new CanvasModel();
	private MenusModel menus = new MenusModel();
	private VideoBO videoSource = new VideoBO();
	private CurrentSelection currentSelection = new CurrentSelection();
	
	public DrawingApp(){
		super("Drawing Application!");
		
		//this.setPreferredSize(new Dimension(800, 600));
		this.setLocation(0, 200);
		
		init();
		//window content creation
		windowPanel = new WindowPanel(this);
		this.setContentPane(windowPanel);
		//this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.pack();//this sets components sizes, positions
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	//TODO
	private void init(){
		videoSource.setName("myvideo.mp4");
		videoSource.setWidth("500");
		videoSource.setHeight("400");
	}
	
	public MenusModel getMenus(){
		return menus;
	}
	
	public ChaptersModel getChapters(){
		return chapters;
	}
	
	public CanvasModel getCanvases(){
		return canvases;
	}
	
	public VideoBO getVideoSource(){
		return videoSource;
	}
	
	public void update(){
		windowPanel.repaint();
	}
	
	public CurrentSelection getCurrentSelection(){
		return currentSelection;
	}
}
