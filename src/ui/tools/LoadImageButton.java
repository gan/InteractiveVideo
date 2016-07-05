package ui.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import ui.DrawingApp;

public class LoadImageButton extends JButton
implements ActionListener
{
/**
* 
*/
private static final long serialVersionUID = -3271751176061769295L;

private final DrawingApp drawingApp;

public LoadImageButton(DrawingApp drawingApp) {
	
	super("Load an image");
	this.drawingApp = drawingApp;
	
	addActionListener(this);
}

@Override
public void actionPerformed(ActionEvent e) {
JFileChooser chooser = new JFileChooser("./");
	if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
	    File selectedFile = chooser.getSelectedFile();
	    drawingApp.getCanvases().setImage(true);
	    drawingApp.getCanvases().setBgImage(selectedFile.getPath());
	}
}

}
