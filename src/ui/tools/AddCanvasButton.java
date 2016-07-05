package ui.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import model.CanvasBO;
import ui.DrawingApp;

public class AddCanvasButton extends JButton
		implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4318672838697016715L;

	private DrawingApp drawingApp;
	
	public AddCanvasButton(DrawingApp drawingApp){
		super("Add Canvas");
		this.drawingApp = drawingApp;
		
		addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JTextField id = new JTextField("Canvas" + drawingApp.getCanvases().getlCanvas().size());
		JTextField positionX = new JTextField(String.valueOf(drawingApp.getCurrentSelection().getX()));
		JTextField positionY = new JTextField(String.valueOf(drawingApp.getCurrentSelection().getY()));
		JTextField canvasWidth = new JTextField("0");
		JTextField canvasHeight = new JTextField("0");
		
		Object[] message = {
				"Canvas ID:", id,
				"Canvas position X:", positionX,
				"Canvas position Y:", positionY,
				"Width of Canvas:", canvasWidth,
				"Height of Canvas:", canvasHeight,
		};
		
		int result = JOptionPane.showConfirmDialog(null, message, "Please enter the values of canvas.", JOptionPane.OK_CANCEL_OPTION);
		
		if(result == JOptionPane.OK_OPTION) {
			CanvasBO newCanvas = new CanvasBO(id.getText(),
										positionX.getText(), positionY.getText(), 
										canvasWidth.getText(), canvasHeight.getText());
			drawingApp.getCanvases().add(newCanvas);
		}
	}

}
