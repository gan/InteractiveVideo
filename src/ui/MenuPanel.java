package ui;

import java.awt.Color;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import model.MenuBO;
import ui.tools.MenuButton;

public class MenuPanel extends JPanel implements Observer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3293928008471758934L;
	
	private final String title = "Menus";
	
	private final DrawingApp drawingApp;
	
	public MenuPanel(DrawingApp drawingApp){
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
		for(MenuBO menu : drawingApp.getMenus().getlMenus()){
			this.add(new MenuButton(menu, drawingApp));
		}
	}
}
