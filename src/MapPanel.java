package FruitEditor;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;

public class MapPanel extends JPanel {
	// FILES.
	private FruitEditor fruitEditor;
	
	// MAP.
	private Map map;
	
	// LISTENER.
	private FruitPanelListener fruitPanelListener;
	
	// DIMENSIONS.
	private int mapWidth;
	private int mapHeight;
	
	// GRID DIMENSIONS.
	private int gridWidth;
	private int gridHeight;
	
	// MOUSE COORDS.
	private int mouseX;
	private int mouseY;
	
	// VIEWPORT.
	private JViewport viewport;
	
	
	public MapPanel(FruitEditor f) {
		fruitEditor = f;
		map = f.getMap();
		
		fruitPanelListener = new FruitPanelListener(fruitEditor);
		mapWidth = map.getCols();
		mapHeight = map.getRows();
		
		gridWidth = gridHeight = 24;
		
		mouseX = mouseY = 0;
	
		setLayout(new FlowLayout());
		
		addMouseListener(fruitPanelListener);
		addMouseMotionListener(fruitPanelListener);
	}
	
	
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		
		map.draw(g);
		
		if (fruitEditor.gridOn()) {
			drawGrid(g);
		}
		
		drawCursor(g);
		
	}
	
	public void drawGrid(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		
		for (int r=0; r < mapHeight; r++) {
			g.drawLine(0, r*mapHeight, mapWidth*map.getScale(), r*mapHeight);
		}
		
		for (int c=0; c < mapWidth; c++) {
			g.drawLine(c*mapWidth, 0, c*mapWidth, mapHeight*map.getScale());
		}
	}
	
	public void drawCursor(Graphics g) {
		Graphics2D g2 = convertTo2d(g);
		
		g2.setStroke(new BasicStroke(2));
		g2.setColor(Color.BLACK);
		
		g2.drawRect((int)mouseX/gridWidth - Map.OFFSET,
				(int)mouseY/gridHeight - Map.OFFSET,
				gridWidth, gridHeight);
	}
	
	public void setViewport(JViewport vp) {
		viewport = vp;
	}
	
	public void setMap(Map m) {
		fruitEditor.setMap(m);
		
		map = m;
		
		mapWidth = m.getCols();
		mapHeight = m.getRows();
		
		//gridWidth = m.getGridWidth();
		//gridHeight = m.getGridHeight();
		
	}
	
	public void mouseMoved(MouseEvent e) {
		
	}
	
	public void mouseClicked(MouseEvent e) {
		
	}
	
	public void mousePressed(MouseEvent e) {
		
	}

	public void mouseReleased(MouseEvent e) {
		
	}
	
	private Graphics2D convertTo2d(Graphics g) {
		return (Graphics2D)g;
	}
}