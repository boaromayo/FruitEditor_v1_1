package FruitEditor;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class LabelPanel extends JPanel {
	// FILES.
	private FruitEditor fruitEditor;
	
	// COMPONENTS.
	private JLabel currentMap;
	private JLabel cursorPosition;
	
	// PROPERTIES.
	private int mapX;
	private int mapY;
	private String mapName;
	private int mapWidth;
	private int mapHeight;
	
	public LabelPanel(FruitEditor f) {
		fruitEditor = f;
		
		setLayout(new BorderLayout());
		
		currentMap = new JLabel("No map selected");
		cursorPosition = new JLabel("(0,0)");
		
		mapX = 0;
		mapY = 0;
		mapName = "";
		mapWidth = 0;
		mapHeight = 0;
		
		setupPanels();
	}
	
	public void update() {
		if (fruitEditor.getMap() != null) {
			mapName = fruitEditor.getMap().getName();
			mapWidth = fruitEditor.getMap().getCols();
			mapHeight = fruitEditor.getMap().getRows();
			
			currentMap.setText(mapName + " (" + mapWidth + "x" + mapHeight + ")");
			//cursorPosition.setText("(" + mapX + "," + mapY + ")");
		} else {
			currentMap.setText("No map selected");
			cursorPosition.setText("(0,0)");
		}
	}
	
	public void setupPanels() {
		add(currentMap, BorderLayout.CENTER);
		add(cursorPosition, BorderLayout.EAST);
	}
}
