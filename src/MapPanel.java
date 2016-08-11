package FruitEditor;

import java.awt.*;
import java.awt.event.*;

import java.beans.*;

import javax.swing.*;

import java.io.*;

import java.util.*;

public class MapPanel extends JPanel {
	// FILES.
	private FruitEditor fruitEditor;
	
	// MAP.
	private Map map;
	
	// EVENT LISTENER.
	private FruitPanelListener fruitPanelListener;
	
	// VIEWPORT.
	private JViewport viewport;
	
	// DIMENSIONS.
	private int mapWidth;
	private int mapHeight;
	
	// GRID DIMENSIONS.
	private int gridWidth;
	private int gridHeight;
	
	// MOUSE COORDS.
	private int mouseX;
	private int mouseY;
	private int oldmouseX;
	private int oldmouseY;
	
	
	public MapPanel(FruitEditor f) {
		fruitEditor = f;
		map = f.getMap();
		
		fruitPanelListener = new FruitPanelListener(fruitEditor);
		mapWidth = map.getCols();
		mapHeight = map.getRows();
		
		gridWidth = map.getGridWidth();
		gridHeight = map.getGridHeight();
		
		mouseX = mouseY = 0;
	
		setLayout(new FlowLayout());
		
		addMouseListener(fruitPanelListener);
		addMouseMotionListener(fruitPanelListener);
		addPropertyChangeListener(fruitPanelListener);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paintComponent(g);
		
		Image img = createImage(viewport.getWidth(), viewport.getHeight());
		Graphics g2 = img.getGraphics();
		draw(g2);
		g.drawImage(img, 0, 0, 
				viewport.getWidth(), viewport.getHeight(), null);	
	}
	
	public void draw(Graphics g) {	
		if (map == null) {
			g.setColor(Color.WHITE);
		
			map.draw(g);
		
			if (fruitEditor.gridOn()) {
				drawGrid(g);
			}
		
			drawCursor(g);	
		} else {
			g.setColor(Color.GRAY);
		}
	}
	
	private void drawGrid(Graphics g) {
		g.setColor(Color.GRAY);
		
		for (int r=0; r < mapHeight; r++) {
			g.drawLine(0, r*mapHeight, mapWidth*map.getScale(), r*mapHeight);
		}
		
		for (int c=0; c < mapWidth; c++) {
			g.drawLine(c*mapWidth, 0, c*mapWidth, mapHeight*map.getScale());
		}
	}
	
	private void drawCursor(Graphics g) {
		Graphics2D g2 = convertTo2d(g);
		int mx = mouseX / gridWidth;
		int my = mouseY / gridHeight;
		
		g2.setStroke(new BasicStroke(2));
		g2.setColor(Color.BLACK);
		
		g2.drawRect(mx - Map.OFFSET,
				my - Map.OFFSET,
				gridWidth, gridHeight);
	}
	
	public void update() {
		
	}
	
	public void setViewport(JViewport vp) {
		viewport = vp;
	}
	
	public void setMap(Map m) {
		fruitEditor.setMap(m);
		
		map = m;
		
		mapWidth = m.getCols();
		mapHeight = m.getRows();
		
		gridWidth = m.getGridWidth();
		gridHeight = m.getGridHeight();
		
	}
	
	public void propertyChange(PropertyChangeEvent e) {
		
	}
	
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		
	}
	
	public void mouseClicked(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		int mx = mouseX / gridWidth;
		int my = mouseY / gridHeight;
		
		
		
	}
	
	public void mouseDragged(MouseEvent e) {
		
	}
	
	public void mousePressed(MouseEvent e) {
		
	}

	public void mouseReleased(MouseEvent e) {
		oldmouseX = e.getX();
		oldmouseY = e.getY();
	}
	
	private Graphics2D convertTo2d(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
		return g2;
	}
}