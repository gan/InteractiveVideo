package ui.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import ui.DrawingApp;
import model.CanvasBO;

public class CanvasButton extends JButton
		implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4553308275360931238L;
	
	private CanvasBO canvas;
	private DrawingApp drawingApp;
	private final int Id;
	
	public CanvasButton(CanvasBO canvas, DrawingApp drawingApp) {
		super(canvas.getId());
		this.canvas = canvas;
		this.drawingApp = drawingApp;
		this.Id = drawingApp.getCanvases().getlCanvas().indexOf(canvas);
		
		addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JTextField id = new JTextField(canvas.getId());
		JTextField positionX = new JTextField(canvas.getPositionX());
		JTextField positionY = new JTextField(canvas.getPositionY());
		JTextField canvasWidth = new JTextField(canvas.getCanvasWidth());
		JTextField canvasHeight = new JTextField(canvas.getCanvasHeight());
		
		Object[] message = {
				"Canvas ID:", id,
				"Canvas position X:", positionX,
				"Canvas position Y:", positionY,
				"Width of Canvas:", canvasWidth,
				"Height of Canvas:", canvasHeight,
		};
		
		int result = JOptionPane.showConfirmDialog(null, message, "Please enter the values of canvas.", JOptionPane.OK_CANCEL_OPTION);
		
		if(result == JOptionPane.OK_OPTION) {
			CanvasBO canvas = drawingApp.getCanvases().getlCanvas().get(Id);
			canvas.set(id.getText(), 
						positionX.getText(), positionY.getText(), 
						canvasWidth.getText(), canvasHeight.getText());
			drawingApp.getCanvases().changeCanvas();
		}
	}

}
