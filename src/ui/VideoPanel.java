package ui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Label;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import model.CanvasBO;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.windows.Win32FullScreenStrategy;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

public class VideoPanel extends JPanel 
						implements ActionListener, MouseListener, Observer {
	
	private static final long serialVersionUID = 2325767013719431351L;
	
	private final String title = "Video";
	
	ArrayList<Shape> shapes = new ArrayList<Shape>();
	private DrawingApp drawingApp;
	private Label sizeLabel;
	private Label locationLabel;
	private Label timeLabel;
	private JButton controlButton = new JButton("Pause");
	
	//private Point startDrag = new Point();
	//private Point endDrag = new Point();
	
	private Image backgroundImage = null;
	
	private EmbeddedMediaPlayer emp;
	private JProgressBar bar;
	private Canvas c = new Canvas();
	private JPanel northPanel = new JPanel();
	private FlowLayout flowLayout = new FlowLayout();
	
	private int counter = 0;
	private Timer timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
        	timeLabel.setText(String.valueOf(counter) + " s");
        	drawingApp.getCurrentSelection().setTime(counter);
        	if(counter < (int)emp.getLength()/1000)
        		counter++;
        }
    });
	
	public VideoPanel(DrawingApp drawingApp) {
		super();
		this.drawingApp = drawingApp;
		this.setBorder(BorderFactory.createTitledBorder(title));	
		this.setLayout(new BorderLayout());

		setUpNorthPanel();
		
		setUpSouthPanel();
		
		this.setPreferredSize(new Dimension(Integer.parseInt(drawingApp.getVideoSource().getWidth()), 
											Integer.parseInt(drawingApp.getVideoSource().getHeight())));
        this.addMouseListener(this);
	}
	
	private class UpdateBar extends MediaPlayerEventAdapter
    {
        public void positionChanged(MediaPlayer mp, float pos)
        {
            int value = Math.min(100, Math.round(pos * 100.0f));
            bar.setValue(value);
        }
    }
	
	private void setUpSouthPanel() {
		bar = new JProgressBar(0,100);
        bar.setStringPainted(true);
		this.add(bar, BorderLayout.SOUTH);
		
		bar.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				int currentPos = e.getX();
				long currentTime = currentPos * emp.getLength() / getSize().width;
				int currentBarPos = currentPos * 100 / getSize().width;
				bar.setValue(currentBarPos);
				emp.setTime(currentTime);
				counter= (int)currentTime/1000;
			}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
		});
	}
	

	private void setUpNorthPanel() {
		northPanel.setLayout(flowLayout);
		northPanel.add(sizeLabel = new Label());
		this.add(northPanel, BorderLayout.NORTH);
		
		northPanel.add(controlButton);
		controlButton.addActionListener(new ActionListener(){
			boolean pause = false;
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!pause){
					controlButton.setText("Play");
					emp.pause();
					timer.stop();
				}
				else{
					controlButton.setText("Pause");
					emp.play();
					timer.start();
				}
				pause = !pause;
			}
			
		});
		northPanel.add(timeLabel = new Label());
		northPanel.add(locationLabel = new Label());
		
		sizeLabel.setText("w : " + getSize().width + "; h : " + getSize().height);
        locationLabel.setText("x : 0; y: 0");
	}
	
	private void setUpPanel() {	
		c.addMouseListener(this);
		c.setBackground(Color.black);
		this.add(c, BorderLayout.CENTER);	
	
		new NativeDiscovery().discover();
		
	    MediaPlayerFactory mpf = new MediaPlayerFactory();
		
	    emp = mpf.newEmbeddedMediaPlayer(new Win32FullScreenStrategy(drawingApp));
		
		emp.setVideoSurface(mpf.newVideoSurface(c));
	
		emp.setEnableMouseInputHandling(false);
		emp.setEnableKeyInputHandling(false);
		
		emp.addMediaPlayerEventListener(new UpdateBar());
	}
	
	public void setVideoName(final String videoTitle) {
	    new Thread(new Runnable() {
	        @Override
	        public void run() {
	            
	        }
	    }).start();
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setSizeLabel();
		if(this.backgroundImage != null){
			g.drawImage(backgroundImage, 0, 0, getSize().width, getSize().height, null);
		}
		//.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH)
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(Color.RED);
		//g2.draw(currentShape);
		for (Shape s : shapes) {
		     g2.draw(s);
	    }
	}

	
	private Rectangle2D.Float makeRectangle(int x1, int y1, int x2, int y2) {
	      return new Rectangle2D.Float(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		locationLabel.setText("x : " + x + ";" + " y: " + y);
		drawingApp.getCurrentSelection().setX(x);
		drawingApp.getCurrentSelection().setY(y);
	}


	@Override
	public void mousePressed(MouseEvent e) {
  	  	//startDrag = new Point(e.getX(), e.getY());	
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		/*Shape r = makeRectangle(startDrag.x, startDrag.y, e.getX(), e.getY());
		shapes.add(r);
		startDrag = null;
		endDrag = null;
		update();*/
	}
	
	private void update() {		
		drawingApp.update();
	}


	@Override
	public void mouseEntered(MouseEvent e) {}


	@Override
	public void mouseExited(MouseEvent e) {}


	@Override
	public void actionPerformed(ActionEvent e) {}

	private void setSizeLabel() {
		sizeLabel.setText("w : " + getSize().width + "; h : " + getSize().height);
		EventQueue.invokeLater(new Runnable()
	    {
	        public void run()
	        {
	        	drawingApp.getVideoSource().setWidth(String.valueOf(getSize().width));
	        	drawingApp.getVideoSource().setHeight(String.valueOf(getSize().height));
	        
	            //repaint();
	            //validate();
	        } 
	    });
	}
	
	private void setImage() {
		this.remove(c);
		String mediaPath = this.drawingApp.getCanvases().getBgImage();
		backgroundImage = Toolkit.getDefaultToolkit().createImage(mediaPath);
		
	}
	
	private void setVideo() {
		// create a player to play the media specified in the URL
		setUpPanel();
		String mediaPath = this.drawingApp.getCanvases().getBgImage();
		emp.prepareMedia(mediaPath);
		emp.play();
		timer.start();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(this.drawingApp.getCanvases().isVideo()){
			this.setVideo();
			this.drawingApp.getCanvases().setVideo(false);
		}
		if(this.drawingApp.getCanvases().isImage()){
			this.setImage();
			this.drawingApp.getCanvases().setImage(false);
		}
		//this.setVideoName(mediaPath);
		shapes.clear();
		for(CanvasBO menu : drawingApp.getCanvases().getlCanvas()){
			Shape r = makeRectangle(Integer.parseInt(menu.getPositionX()), Integer.parseInt(menu.getPositionY()), 
									Integer.parseInt(menu.getPositionX())+Integer.parseInt(menu.getCanvasWidth()), 
									Integer.parseInt(menu.getPositionY())+Integer.parseInt(menu.getCanvasHeight()));
			shapes.add(r);
		}
		update();
	}
}
