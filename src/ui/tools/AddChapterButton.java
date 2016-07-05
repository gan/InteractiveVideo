package ui.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import model.ChapterBO;
import ui.DrawingApp;

public class AddChapterButton extends JButton
		implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8798930239354389061L;

	private final DrawingApp drawingApp;
	
	public AddChapterButton(DrawingApp drawingApp) {
		
		super("Add Chapter");
		this.drawingApp = drawingApp;
		
		addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JTextField chapterName = new JTextField("Chapter" + drawingApp.getChapters().getlChapters().size());
		JTextField jumpTime = new JTextField(String.valueOf(drawingApp.getCurrentSelection().getTime()));
		
		Object[] message = {
				"Chapter name:", chapterName,
			    "Set jump time :", jumpTime
		};
		
		int result = JOptionPane.showConfirmDialog(null, message, "Please enter the values of chapter.", JOptionPane.OK_CANCEL_OPTION);
		
		if(result == JOptionPane.OK_OPTION) {
			ChapterBO chapter = new ChapterBO(chapterName.getText(), jumpTime.getText());
			drawingApp.getChapters().add(chapter);
		}
	}

}
