package ui.tools;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import ui.DrawingApp;
import model.CanvasBO;
import model.ChapterBO;
import model.MenuBO;

public class AddMenuButton extends JButton
		implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1185759882179851898L;

	private DrawingApp drawingApp;
	
	public AddMenuButton(DrawingApp drawingApp){
		super("Add Menu");
		this.drawingApp = drawingApp;
		
		addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JTextField chapterName = new JTextField("Menu" + drawingApp.getMenus().getlMenus().size());
		JRadioButton loopButton = new JRadioButton("True");
		JTextField activeTimeStart = new JTextField("0");
		JTextField activeTimeEnd = new JTextField("0");
		List<JRadioButton> buttons = new ArrayList<JRadioButton>();
		List<JComboBox> comboBoxs = new ArrayList<JComboBox>();
		List<ChapterBO> lChapters = drawingApp.getChapters().getlChapters();
		String[] chapters = new String[lChapters.size()];
		for(int i = 0; i < lChapters.size(); i++){
			chapters[i] = lChapters.get(i).getName();
		}
		
		JPanel setMenuPanel = new JPanel(new GridLayout(0,2));

		for(CanvasBO canvas : drawingApp.getCanvases().getlCanvas()){
			buttons.add((JRadioButton) setMenuPanel.add(new JRadioButton(canvas.getId())));
			comboBoxs.add((JComboBox) setMenuPanel.add(new JComboBox(chapters)));
		}
		
		Object[] message = {
				"Menu name:", chapterName,
				"Is a loop:", loopButton,
				"Active time start :", activeTimeStart,
				"Active time end :", activeTimeEnd,
				"Choisir un canvas :", setMenuPanel
		};
		
		int result = JOptionPane.showConfirmDialog(null, message, "Please enter the values of menu.", JOptionPane.OK_CANCEL_OPTION);
		
		Map<JRadioButton, JComboBox> canvasAction = new HashMap<JRadioButton, JComboBox>();
		List<CanvasBO> lCanvas = drawingApp.getCanvases().getlCanvas();
		if(result == JOptionPane.OK_OPTION) {
			int canvasChose = 0;
			for(JRadioButton button : buttons){
				if(button.isSelected())
					canvasAction.put(button, comboBoxs.get(canvasChose));
				canvasChose++;
			}
			MenuBO newMenu = new MenuBO(chapterName.getText(), String.valueOf(loopButton.isSelected()),
										activeTimeStart.getText(), activeTimeEnd.getText(), canvasAction);
			drawingApp.getMenus().add(newMenu);
		}
	}

}
