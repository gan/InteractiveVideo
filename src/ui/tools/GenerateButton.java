package ui.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;

import main.XMLGenerator;
import ui.DrawingApp;

public class GenerateButton extends JButton
		implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9149257604872532822L;

	private final DrawingApp drawingApp;
	private final XMLGenerator xmlGenerator = new XMLGenerator();
	
	public GenerateButton(DrawingApp drawingApp) {
		
		super("Generate interactive video files");
		this.drawingApp = drawingApp;
		
		addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		xmlGenerator.generateXML(drawingApp.getVideoSource(), drawingApp.getChapters(), drawingApp.getCanvases(), drawingApp.getMenus());
		try {
			System.out.println("python");
			Process p = Runtime.getRuntime().exec("python intermediate/createCSS.py");
			Process p2 = Runtime.getRuntime().exec("python intermediate/createInteraction.py");
			Process p3 = Runtime.getRuntime().exec("python intermediate/createSegment.py");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
