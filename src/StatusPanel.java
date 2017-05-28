package FruitEditor;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

public class StatusPanel extends JPanel implements MouseMotionListener {
	// FILES.
	private FruitEditor fruitEditor;
	
	// INSTANCES.
	private Map map;
	
	private FruitListener fruitListener;
	
	// COMPONENTS.
	private JLabel statusLabel;
	private JLabel currentMapLabel;
	private JLabel cursorPosition;
	
	// PROPERTIES.
	private int mapX;
	private int mapY;
	private String mapName;
	private int mapWidth;
	private int mapHeight;
	
	public StatusPanel(FruitEditor f) {
		fruitEditor = f;
		
		map = f.getMap();
		
		fruitListener = f.getListener();
		
		setPreferredSize(new Dimension(FruitEditor.SCREEN_WIDTH, 16));
		setBorder(new LineBorder(Color.GRAY, 1));
		setLayout(new BorderLayout());
		
		statusLabel = new JLabel("No map selected");
		currentMapLabel = new JLabel();
		cursorPosition = new JLabel("(0,0)");
		
		mapX = 0;
		mapY = 0;
		mapName = "";
		mapWidth = 0;
		mapHeight = 0;
		
		setupPanel();
		
		addMouseMotionListener(fruitListener);
	}
	
	public void update() {
		if (fruitEditor.getMap() != null) {
			setMap(fruitEditor.getMap());
			
			setStatus("\t");
			setCurrentMap(mapName, mapWidth, mapHeight);
			setLocation(mapX, mapY);
		}
		
		repaint();
	}
	
	public synchronized void setMap(Map m) {
		map = m;
		mapName = m.getName();
		mapWidth = m.getWidth();
		mapHeight = m.getHeight();
	}
	
	public void setStatus(String text) {
		statusLabel.setText(text);
	}
	
	public void setCurrentMap(String name, int width, int height) {
		currentMapLabel.setText("Map: " + mapName + " (" + mapWidth + " x " + mapHeight + ")");
	}
	
	public void setCursorLocation(int x, int y) {
		cursorPosition.setText("(" + x + "," + y + ")");
	}
	
	private void setupPanel() {
		add(statusLabel, BorderLayout.WEST);
		add(currentMapLabel, BorderLayout.CENTER);
		add(cursorPosition, BorderLayout.EAST);
	}
	
	public void mouseMoved(MouseEvent e) {
		mapX = e.getX() / map.getGridWidth();
		mapY = e.getY() / map.getGridHeight();
		
		setCursorLocation(mapX, mapY);
	}
	
	public void mouseHovered(MouseEvent e) {
		
	}
	
	public void mouseDragged(MouseEvent e) {
		
	}
}
