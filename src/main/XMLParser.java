package main;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import model.CanvasBO;
import model.CanvasModel;
import model.ChapterBO;
import model.ChaptersModel;
import model.MenuBO;
import model.MenusModel;
import model.VideoBO;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ui.DrawingApp;

public class XMLParser {
	
	private DrawingApp drawingApp;
	private ChaptersModel chapters;
	private CanvasModel canvases;
	private MenusModel menus;
	private VideoBO videoSource;
	
	public XMLParser(DrawingApp drawingApp) {
		
		this.drawingApp = drawingApp;
		this.chapters = drawingApp.getChapters();
		this.canvases = drawingApp.getCanvases();
		this.menus = drawingApp.getMenus();
		this.videoSource = drawingApp.getVideoSource();
	}
	
	public void readFromXML(File file){
		
		try{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();
			
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			
			// videoSource
			Element videoElement = (Element) doc.getElementsByTagName("video").item(0);
			videoSource.setName(videoElement.getAttribute("stream"));
			videoSource.setWidth(videoElement.getAttribute("width"));
			videoSource.setHeight(videoElement.getAttribute("height"));
			System.out.println("parsed video");
			
			// chapters
			NodeList chapterList = doc.getElementsByTagName("action");
			for(int i = 0; i < chapterList.getLength(); i++){
				Element actionElement = (Element) chapterList.item(i);
				chapters.add(new ChapterBO(actionElement.getAttribute("id"), 
											actionElement.getAttribute("goto")));
			}
			System.out.println("parsed chapters");
			
			// canvases
			NodeList canvasList = doc.getElementsByTagName("canvas");
			for(int i = 0; i < chapterList.getLength(); i++){
				Element canvasElement = (Element) canvasList.item(i);
				String canvasId = canvasElement.getAttribute("id");
				Element rectangle = (Element) canvasElement.getElementsByTagName("rectangle").item(0);
				String[] size = rectangle.getAttribute("size").split(" ");
				String width = size[0];
				String height = size[1];
				String[] pos = rectangle.getAttribute("pos").split(" ");
				String x = pos[0];
				String y = pos[1];
				canvases.add(new CanvasBO(canvasId, x, y, width, height));
			}
			System.out.println("parsed canvases");
			
			// menus
			String[] chapters = new String[this.chapters.getlChapters().size()];
			for(int i = 0; i < this.chapters.getlChapters().size(); i++){
				chapters[i] = this.chapters.getlChapters().get(i).getName();
			}
			NodeList menuList = doc.getElementsByTagName("menu");
			for(int i = 0; i < menuList.getLength(); i++){
				Element menuElement = (Element) menuList.item(i);
				String id = menuElement.getAttribute("id");
				String loop = menuElement.getAttribute("loop");
				String timeStart = menuElement.getAttribute("start");
				String timeEnd = menuElement.getAttribute("end");
				NodeList mbuttonList = menuElement.getElementsByTagName("mbutton");
				
				MenuBO loadedMenu = new MenuBO(id, loop, timeStart, timeEnd, createCanvasAction(chapters, mbuttonList));
				menus.add(loadedMenu);
			}
			System.out.println("parsed menus");
			
		} catch (Exception e) {
			System.out.println("parser error");
			e.printStackTrace();
	    }
	}
	
	private HashMap<JRadioButton, JComboBox> createCanvasAction(String[] chapters, NodeList mbuttonList) {
		HashMap<JRadioButton, JComboBox> canvasAction = new HashMap<JRadioButton, JComboBox>();
		for(CanvasBO canvas : canvases.getlCanvas()){
			JRadioButton radioButton = new JRadioButton(canvas.getId());
			JComboBox comboBox = new JComboBox(chapters);
			String selectChapter = this.mbuttonListContains(mbuttonList, canvas.getId());
			if(!selectChapter.equals("0")){
				radioButton.setSelected(true);
				comboBox.setSelectedItem(selectChapter);
			}
			else{
				radioButton.setSelected(false);
			}
			canvasAction.put(radioButton, comboBox);
		}
		return canvasAction; 
	}
	
	private String mbuttonListContains(NodeList mbuttonList, String canvasId) {
		for(int j = 0; j < mbuttonList.getLength(); j++){
			Element mbuttonElement = (Element) mbuttonList.item(j);
			if(canvasId.equals(mbuttonElement.getAttribute("canvas")))
				return mbuttonElement.getAttribute("actionArg");
		}
		return "0";
	}
	
}
