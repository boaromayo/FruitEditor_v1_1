package FruitEditor;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

public class StatusPanel extends JPanel {
	// FILES.
	private FruitEditor fruitEditor;
	
	// INSTANCES.
	private Map map;
	private MapPanel mapPanel;
	
	// COMPONENTS.
	private JLabel status;
	private JLabel currentMap;
	private JLabel cursorPosition;
	
	// PROPERTIES.
	private int mapX;
	private int mapY;
	private String mapName;
	private int mapWidth;
	private int mapHeight;
	
	public StatusPanel(FruitEditor f) {
		fruitEditor = f;
		
		map = fruitEditor.getMap();
		mapPanel = fruitEditor.getMapPanel();
		
		setBorder(new LineBorder(Color.GRAY, 1));
		setLayout(new BorderLayout());
		
		status = new JLabel("No map selected");
		currentMap = new JLabel();
		cursorPosition = new JLabel("(0,0)");
		
		mapX = 0;
		mapY = 0;
		mapName = "";
		mapWidth = 0;
		mapHeight = 0;
		
		setupPanel();
	}
	
	public void update() {
		if (fruitEditor.getMap() != null) {
			mapName = map.getName();
			mapWidth = map.getCols();
			mapHeight = map.getRows();
			mapX = mapPanel.getMapX();
			mapY = mapPanel.getMapY();
			
			setCurrentMap(mapName, mapWidth, mapHeight);
			setLocation(mapX, mapY);
		} else {
			setStatus("No map selected");
			currentMap.setText("");
			cursorPosition.setText("(0,0)");
		}
		
		repaint();
	}
	
	public void setStatus(String text) {
		status.setText(text);
	}
	
	public void setCurrentMap(String name, int width, int height) {
		currentMap.setText(mapName + " (" + mapWidth + " x " + mapHeight + ")");
	}
	
	public void setCursorLocation(int x, int y) {
		cursorPosition.setText("(" + x + "," + y + ")");
	}
	
	private void setupPanel() {
		add(status, BorderLayout.WEST);
		add(currentMap, BorderLayout.CENTER);
		add(cursorPosition, BorderLayout.EAST);
	}
}
