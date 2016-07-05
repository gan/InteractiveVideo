package ui.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import main.XMLGenerator;
import main.XMLParser;
import ui.DrawingApp;

public class ReadXMLButton extends JButton
		implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6899661457272995041L;

	private final DrawingApp drawingApp;
	private final XMLParser xmlParser;
	
	public ReadXMLButton(DrawingApp drawingApp) {
		
		super("Read a xml file");
		this.drawingApp = drawingApp;
		this.xmlParser = new XMLParser(drawingApp);
		
		addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser chooser = new JFileChooser("./");
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
	        File selectedFile = chooser.getSelectedFile();
	        xmlParser.readFromXML(selectedFile);
		}
		drawingApp.update();
	}

}
