package model;

import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JRadioButton;

public class MenuBO {
	protected String nMenu;
	protected String loop;
	protected String startTime;
	protected String endTime;
	protected Map<JRadioButton, JComboBox> canvasAction;
	
	public MenuBO(){
		
	}
	
	public MenuBO(String nMenu, String loop,
				String startTime, String endTime, Map<JRadioButton, JComboBox> canvasAction) {
		super();
		this.nMenu = nMenu;
		this.loop = loop;
		this.startTime = startTime;
		this.endTime = endTime;
		this.canvasAction = canvasAction;
	}
	
	
	public String getName(){
		return nMenu;
	}
	public void setName(String n){
		this.nMenu = n;
	}
	public String isLoop(){
		return loop;
	}
	public void setLoop(String loop){
		this.loop = loop;
	}

	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Map<JRadioButton, JComboBox> getCanvasAction() {
		return canvasAction;
	}
	public void setCanvasAction(Map<JRadioButton, JComboBox> canvasAction) {
		this.canvasAction = canvasAction;
	}
}
