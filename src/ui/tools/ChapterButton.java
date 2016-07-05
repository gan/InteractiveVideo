package ui.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import model.ChapterBO;
import ui.DrawingApp;

public class ChapterButton extends JButton
		implements ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1897728356104935644L;
	
	private final ChapterBO chapter;
	private final DrawingApp drawingApp;
	private final int Id;
	
	public ChapterButton(DrawingApp drawingApp, ChapterBO chapter) {
		
		super(chapter.getName());
		this.drawingApp = drawingApp;
		this.chapter = chapter;
		this.Id = drawingApp.getChapters().getlChapters().indexOf(chapter);
		
		addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		JTextField chapterName = new JTextField(chapter.getName());
		JTextField jumpTime = new JTextField(chapter.getTargetTime());
		
		Object[] message = {
				"Chapter name:", chapterName,
			    "Set jump time :", jumpTime
		};
		
		int result = JOptionPane.showConfirmDialog(null, message, "Please enter the target time of this chapter.", JOptionPane.OK_CANCEL_OPTION);
		
		if(result == JOptionPane.OK_OPTION) {
			ChapterBO chapter = this.drawingApp.getChapters().get(Id);
			chapter.setName(chapterName.getText());
			chapter.setTargetTime(jumpTime.getText());
		}
		
	}
	
}
