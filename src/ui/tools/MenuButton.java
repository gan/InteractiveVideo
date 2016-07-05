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
import model.ChapterBO;
import model.MenuBO;

public class MenuButton extends JButton
		implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2960750586032112633L;
	
	private MenuBO menu;
	private DrawingApp drawingApp;
	private final int Id;
	
	public MenuButton(MenuBO menu, DrawingApp drawingApp){
		super(menu.getName());
		this.menu = menu;
		this.drawingApp = drawingApp;
		this.Id = drawingApp.getMenus().getlMenus().indexOf(menu);
		addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JTextField menuName = new JTextField(menu.getName());
		JRadioButton loopButton = new JRadioButton("", Boolean.parseBoolean(menu.isLoop()));
		JTextField activeTimeStart = new JTextField(menu.getStartTime());
		JTextField activeTimeEnd = new JTextField(menu.getEndTime());
		
		List<JRadioButton> buttons = new ArrayList<JRadioButton>();
		List<JComboBox> comboBoxs = new ArrayList<JComboBox>();
		List<ChapterBO> lChapters = drawingApp.getChapters().getlChapters();
		String[] chapters = new String[lChapters.size()];
		for(int i = 0; i < lChapters.size(); i++){
			chapters[i] = lChapters.get(i).getName();
		}
		
		JPanel setMenuPanel = new JPanel(new GridLayout(0,2));

		int i= 0;
		MenuBO setMenu = drawingApp.getMenus().getlMenus().get(Id);
		for(JRadioButton key : setMenu.getCanvasAction().keySet()){
			buttons.add((JRadioButton) setMenuPanel.add(new JRadioButton(key.getText(), key.isSelected())));
			JComboBox comboBox =  (JComboBox) setMenuPanel.add(new JComboBox(chapters));
			comboBox.setSelectedItem(setMenu.getCanvasAction().get(key).getSelectedItem());
			comboBoxs.add(comboBox);
			i++;
		}
		
		Object[] message = {
				"Menu name:", menuName,
				"Is a loop:", loopButton,
			    "Active time start :", activeTimeStart,
			    "Active time end :", activeTimeEnd,
			    "Choisir un canvas :", setMenuPanel,
		};
		
		int result = JOptionPane.showConfirmDialog(null, message, "Information of this menu.", JOptionPane.YES_NO_OPTION);
		
		Map<JRadioButton, JComboBox> canvasAction = new HashMap<JRadioButton, JComboBox>();
		if(result == JOptionPane.OK_OPTION) {
			int canvasChose = 0;
			for(JRadioButton button : buttons){
				canvasAction.put(button, comboBoxs.get(canvasChose));
				canvasChose++;
			}
			MenuBO curMenu = drawingApp.getMenus().getlMenus().get(Id);
			curMenu.setName(menuName.getText());
			curMenu.setLoop(String.valueOf(loopButton.isSelected()));
			curMenu.setStartTime(activeTimeStart.getText());
			curMenu.setEndTime(activeTimeEnd.getText());
			curMenu.setCanvasAction(canvasAction);
		}
	}

}
