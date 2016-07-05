package main;

import java.io.File;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import model.CanvasBO;
import model.CanvasModel;
import model.ChapterBO;
import model.ChaptersModel;
import model.MenuBO;
import model.MenusModel;
import model.VideoBO;

public class XMLGenerator {

	public void generateXML(VideoBO sourceVideo, ChaptersModel lChapters, CanvasModel lCanvases, MenusModel lMenus){
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	
			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("scene");
			doc.appendChild(rootElement);
			
			// video element
			Element video = doc.createElement("video");
			rootElement.appendChild(video);
			
			// set attribute to video element
			video.setAttribute("stream", sourceVideo.getName());
			video.setAttribute("width", sourceVideo.getWidth());
			video.setAttribute("height", sourceVideo.getHeight());
			for(ChapterBO chapterBO : lChapters.getlChapters()){
				Element chapter = doc.createElement("chapter");
				chapter.setAttribute("start", chapterBO.getTargetTime());
				video.appendChild(chapter);
			}
			
			// canvases elements
			Element canvases = doc.createElement("canvases");
			rootElement.appendChild(canvases);
			for(CanvasBO canvasBO : lCanvases.getlCanvas()){
				Element canvas = doc.createElement("canvas");
				canvas.setAttribute("id", canvasBO.getId());
				canvases.appendChild(canvas);
				Element rectangle = doc.createElement("rectangle");
				rectangle.setAttribute("size", canvasBO.getCanvasWidth() + " " + canvasBO.getCanvasHeight());
				rectangle.setAttribute("pos", canvasBO.getPositionX() + " " + canvasBO.getPositionY());
				canvas.appendChild(rectangle);
			}
			
			// menus elements
			Element menus = doc.createElement("menus");
			rootElement.appendChild(menus);
			int countMenu = 1;
			for(MenuBO menuBO : lMenus.getlMenus()){
				Element menu = doc.createElement("menu");
				menus.appendChild(menu);
				menu.setAttribute("id", menuBO.getName());
				menu.setAttribute("menuNum", String.valueOf(countMenu));
				menu.setAttribute("loop", menuBO.isLoop());
				menu.setAttribute("start", menuBO.getStartTime());
				menu.setAttribute("end", menuBO.getEndTime());
				Map<JRadioButton, JComboBox> canvasAction = menuBO.getCanvasAction();
				for(JRadioButton key : canvasAction.keySet()){
					Element mbutton = doc.createElement("mbutton");
					menu.appendChild(mbutton);
					mbutton.setAttribute("canvas", key.getText());
					mbutton.setAttribute("type", "goto");
					mbutton.setAttribute("actionArg", (String) canvasAction.get(key).getSelectedItem());
				}
				countMenu++;
			}
			
			// actions elements
			Element actions = doc.createElement("actions");
			rootElement.appendChild(actions);
			for(ChapterBO chapterBO : lChapters.getlChapters()){
				Element action = doc.createElement("action");
				action.setAttribute("id", chapterBO.getName());
				action.setAttribute("goto", chapterBO.getTargetTime());
				actions.appendChild(action);
			}
			
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("./intermediate/InteractiveVideo.xml"));


			transformer.transform(source, result);

			System.out.println("File saved!");
			
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
}
}
